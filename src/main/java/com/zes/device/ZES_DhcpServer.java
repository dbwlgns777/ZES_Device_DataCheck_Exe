package com.zes.device;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Locale;
import java.util.logging.Logger;

public class ZES_DhcpServer implements Runnable, AutoCloseable {
    private static final Logger ZES_gv_logger = Logger.getGlobal();
    private static final int ZES_gv_SERVER_PORT = 67;
    private static final int ZES_gv_CLIENT_PORT = 68;
    private static final int ZES_gv_MIN_PACKET_SIZE = 240;
    private static final int ZES_gv_MAGIC_COOKIE = 0x63825363;
    private static final byte ZES_gv_OP_BOOTREQUEST = 1;
    private static final byte ZES_gv_OP_BOOTREPLY = 2;
    private static final byte ZES_gv_HTYPE_ETHERNET = 1;
    private static final byte ZES_gv_HLEN_ETHERNET = 6;
    private static final byte ZES_gv_DHCP_DISCOVER = 1;
    private static final byte ZES_gv_DHCP_OFFER = 2;
    private static final byte ZES_gv_DHCP_REQUEST = 3;
    private static final byte ZES_gv_DHCP_ACK = 5;
    private static final byte ZES_gv_OPT_MESSAGE_TYPE = 53;
    private static final byte ZES_gv_OPT_SERVER_ID = 54;
    private static final byte ZES_gv_OPT_REQUESTED_IP = 50;
    private static final byte ZES_gv_OPT_SUBNET_MASK = 1;
    private static final byte ZES_gv_OPT_ROUTER = 3;
    private static final byte ZES_gv_OPT_LEASE_TIME = 51;
    private static final byte ZES_gv_OPT_END = (byte) 255;

    private final InetAddress serverIp;
    private final InetAddress offerIp;
    private final InetAddress subnetMask;
    private final InetAddress gateway;
    private final int leaseSeconds;
    private final boolean enabled;
    private volatile boolean running;
    private DatagramSocket socket;

    private ZES_DhcpServer(InetAddress serverIp, InetAddress offerIp, InetAddress subnetMask, InetAddress gateway, int leaseSeconds, boolean enabled) {
        this.serverIp = serverIp;
        this.offerIp = offerIp;
        this.subnetMask = subnetMask;
        this.gateway = gateway;
        this.leaseSeconds = leaseSeconds;
        this.enabled = enabled;
    }

    public static ZES_DhcpServer ZES_fromEnvironment(String defaultServerIp) {
        boolean enabled = ZES_getBoolean("zes.dhcp.enabled", "ZES_DHCP_ENABLED", true);
        String serverIpValue = ZES_getValue("zes.dhcp.server.ip", "ZES_DHCP_SERVER_IP");
        if (serverIpValue == null || serverIpValue.isBlank()) {
            serverIpValue = defaultServerIp;
        }
        String offerIpValue = ZES_getValue("zes.dhcp.offer.ip", "ZES_DHCP_OFFER_IP");
        if (offerIpValue == null || offerIpValue.isBlank()) {
            offerIpValue = "192.168.0.10";
        }
        String maskValue = ZES_getValue("zes.dhcp.subnet.mask", "ZES_DHCP_SUBNET_MASK");
        if (maskValue == null || maskValue.isBlank()) {
            maskValue = "255.255.255.0";
        }
        String gatewayValue = ZES_getValue("zes.dhcp.gateway", "ZES_DHCP_GATEWAY");
        String leaseValue = ZES_getValue("zes.dhcp.lease.seconds", "ZES_DHCP_LEASE_SECONDS");
        int leaseSeconds = 3600;
        if (leaseValue != null && !leaseValue.isBlank()) {
            try {
                leaseSeconds = Integer.parseInt(leaseValue);
            } catch (NumberFormatException e) {
                ZES_gv_logger.warning("Invalid lease seconds value: " + leaseValue + ", using default 3600.");
            }
        }
        return new ZES_DhcpServer(
                ZES_toAddress(serverIpValue, "server"),
                ZES_toAddress(offerIpValue, "offer"),
                ZES_toAddress(maskValue, "subnet mask"),
                gatewayValue == null || gatewayValue.isBlank() ? null : ZES_toAddress(gatewayValue, "gateway"),
                leaseSeconds,
                enabled
        );
    }

    public void ZES_start() {
        if (!enabled) {
            ZES_gv_logger.info("DHCP server disabled, skipping startup.");
            return;
        }
        running = true;
        Thread thread = new Thread(this, "ZES-DHCP-Server");
        thread.setDaemon(true);
        thread.start();
    }

    @Override
    public void run() {
        try {
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(InetAddress.getByName("0.0.0.0"), ZES_gv_SERVER_PORT));
            socket.setBroadcast(true);
            ZES_gv_logger.info("DHCP server started. Offering " + offerIp.getHostAddress());
            byte[] buffer = new byte[1024];
            while (running) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                ZES_handlePacket(packet);
            }
        } catch (BindException e) {
            ZES_gv_logger.warning("Failed to bind DHCP server port 67. Run with elevated privileges. " + e.getMessage());
        } catch (IOException e) {
            if (running) {
                ZES_gv_logger.warning("DHCP server error: " + e.getMessage());
            }
        } finally {
            ZES_closeSocket();
        }
    }

    private void ZES_handlePacket(DatagramPacket packet) throws IOException {
        if (packet.getLength() < ZES_gv_MIN_PACKET_SIZE) {
            return;
        }
        ByteBuffer buffer = ByteBuffer.wrap(packet.getData(), 0, packet.getLength()).order(ByteOrder.BIG_ENDIAN);
        byte op = buffer.get();
        if (op != ZES_gv_OP_BOOTREQUEST) {
            return;
        }
        byte htype = buffer.get();
        byte hlen = buffer.get();
        buffer.get(); // hops
        int xid = buffer.getInt();
        buffer.getShort(); // secs
        buffer.getShort(); // flags
        buffer.getInt(); // ciaddr
        buffer.getInt(); // yiaddr
        buffer.getInt(); // siaddr
        buffer.getInt(); // giaddr
        byte[] chaddr = new byte[16];
        buffer.get(chaddr);
        buffer.position(236);
        int cookie = buffer.getInt();
        if (cookie != ZES_gv_MAGIC_COOKIE) {
            return;
        }

        byte messageType = 0;
        InetAddress requestedIp = null;
        while (buffer.hasRemaining()) {
            byte option = buffer.get();
            if (option == ZES_gv_OPT_END) {
                break;
            }
            if (option == 0) {
                continue;
            }
            int len = buffer.get() & 0xff;
            if (buffer.remaining() < len) {
                break;
            }
            byte[] data = new byte[len];
            buffer.get(data);
            if (option == ZES_gv_OPT_MESSAGE_TYPE && len == 1) {
                messageType = data[0];
            } else if (option == ZES_gv_OPT_REQUESTED_IP && len == 4) {
                requestedIp = InetAddress.getByAddress(data);
            }
        }

        if (htype != ZES_gv_HTYPE_ETHERNET || hlen != ZES_gv_HLEN_ETHERNET) {
            return;
        }
        if (messageType == ZES_gv_DHCP_DISCOVER) {
            ZES_sendReply(packet, xid, chaddr, ZES_gv_DHCP_OFFER);
        } else if (messageType == ZES_gv_DHCP_REQUEST) {
            if (requestedIp != null && !requestedIp.equals(offerIp)) {
                ZES_gv_logger.warning("Requested IP " + requestedIp.getHostAddress() + " does not match offer " + offerIp.getHostAddress());
            }
            ZES_sendReply(packet, xid, chaddr, ZES_gv_DHCP_ACK);
        }
    }

    private void ZES_sendReply(DatagramPacket request, int xid, byte[] chaddr, byte messageType) throws IOException {
        ByteBuffer reply = ByteBuffer.allocate(300).order(ByteOrder.BIG_ENDIAN);
        reply.put(ZES_gv_OP_BOOTREPLY);
        reply.put(ZES_gv_HTYPE_ETHERNET);
        reply.put(ZES_gv_HLEN_ETHERNET);
        reply.put((byte) 0); // hops
        reply.putInt(xid);
        reply.putShort((short) 0);
        reply.putShort((short) 0);
        reply.putInt(0); // ciaddr
        reply.put(offerIp.getAddress());
        reply.put(serverIp.getAddress()); // siaddr
        reply.putInt(0); // giaddr
        reply.put(Arrays.copyOf(chaddr, 16));
        reply.put(new byte[64]); // sname
        reply.put(new byte[128]); // file
        reply.putInt(ZES_gv_MAGIC_COOKIE);
        reply.put(ZES_gv_OPT_MESSAGE_TYPE);
        reply.put((byte) 1);
        reply.put(messageType);
        reply.put(ZES_gv_OPT_SERVER_ID);
        reply.put((byte) 4);
        reply.put(serverIp.getAddress());
        reply.put(ZES_gv_OPT_SUBNET_MASK);
        reply.put((byte) 4);
        reply.put(subnetMask.getAddress());
        if (gateway != null) {
            reply.put(ZES_gv_OPT_ROUTER);
            reply.put((byte) 4);
            reply.put(gateway.getAddress());
        }
        reply.put(ZES_gv_OPT_LEASE_TIME);
        reply.put((byte) 4);
        reply.putInt(leaseSeconds);
        reply.put(ZES_gv_OPT_END);

        int length = reply.position();
        DatagramPacket response = new DatagramPacket(reply.array(), length, InetAddress.getByName("255.255.255.255"), ZES_gv_CLIENT_PORT);
        socket.send(response);
        ZES_gv_logger.info("Sent DHCP " + ZES_messageTypeLabel(messageType) + " to " + ZES_formatMac(chaddr));
    }

    @Override
    public void close() {
        running = false;
        ZES_closeSocket();
    }

    private void ZES_closeSocket() {
        if (socket != null && !socket.isClosed()) {
            socket.close();
        }
    }

    private static InetAddress ZES_toAddress(String value, String label) {
        try {
            return InetAddress.getByName(value);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("Invalid " + label + " address: " + value, e);
        }
    }

    private static String ZES_formatMac(byte[] chaddr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6 && i < chaddr.length; i++) {
            if (i > 0) {
                sb.append(':');
            }
            sb.append(String.format("%02x", chaddr[i]));
        }
        return sb.toString();
    }

    private static String ZES_messageTypeLabel(byte type) {
        switch (type) {
            case ZES_gv_DHCP_OFFER:
                return "OFFER";
            case ZES_gv_DHCP_ACK:
                return "ACK";
            default:
                return "UNKNOWN";
        }
    }

    private static String ZES_getValue(String propertyKey, String envKey) {
        String propertyValue = System.getProperty(propertyKey);
        if (propertyValue != null && !propertyValue.isBlank()) {
            return propertyValue;
        }
        String envValue = System.getenv(envKey);
        if (envValue != null && !envValue.isBlank()) {
            return envValue;
        }
        return null;
    }

    private static boolean ZES_getBoolean(String propertyKey, String envKey, boolean defaultValue) {
        String value = ZES_getValue(propertyKey, envKey);
        if (value == null || value.isBlank()) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.toLowerCase(Locale.ROOT));
    }
}
