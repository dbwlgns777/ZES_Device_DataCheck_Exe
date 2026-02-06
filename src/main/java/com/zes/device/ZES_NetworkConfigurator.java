package com.zes.device;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Logger;

public class ZES_NetworkConfigurator {
    private static final Logger ZES_gv_logger = Logger.getGlobal();
    private static final String ZES_gv_DEFAULT_STATIC_IP = "192.168.0.19";
    private static final int ZES_gv_DEFAULT_PREFIX = 24;
    private static final String ZES_gv_DEFAULT_LINUX_IFACE = "eth0";
    private static final String ZES_gv_DEFAULT_WINDOWS_IFACE = "Ethernet";

    private final String interfaceName;
    private final String staticIp;
    private final int prefix;
    private final String gateway;
    private final boolean enabled;

    private ZES_NetworkConfigurator(String interfaceName, String staticIp, int prefix, String gateway, boolean enabled) {
        this.interfaceName = interfaceName;
        this.staticIp = staticIp;
        this.prefix = prefix;
        this.gateway = gateway;
        this.enabled = enabled;
    }

    public static ZES_NetworkConfigurator ZES_fromEnvironment() {
        String iface = ZES_getValue("zes.net.iface", "ZES_NET_IFACE");
        if (iface == null || iface.isBlank()) {
            iface = ZES_isWindows() ? ZES_gv_DEFAULT_WINDOWS_IFACE : ZES_gv_DEFAULT_LINUX_IFACE;
        }
        String staticIp = ZES_getValue("zes.net.static.ip", "ZES_NET_STATIC_IP");
        if (staticIp == null || staticIp.isBlank()) {
            staticIp = ZES_gv_DEFAULT_STATIC_IP;
        }
        String prefixValue = ZES_getValue("zes.net.prefix", "ZES_NET_PREFIX");
        int prefix = ZES_gv_DEFAULT_PREFIX;
        if (prefixValue != null && !prefixValue.isBlank()) {
            try {
                prefix = Integer.parseInt(prefixValue);
            } catch (NumberFormatException e) {
                ZES_gv_logger.warning("Invalid prefix value: " + prefixValue + ", using default " + ZES_gv_DEFAULT_PREFIX);
            }
        }
        String gateway = ZES_getValue("zes.net.gateway", "ZES_NET_GATEWAY");
        String enabledValue = ZES_getValue("zes.net.config.enabled", "ZES_NET_CONFIG_ENABLED");
        boolean enabled = enabledValue == null || enabledValue.isBlank() || Boolean.parseBoolean(enabledValue);
        return new ZES_NetworkConfigurator(iface, staticIp, prefix, gateway, enabled);
    }

    public void ZES_applyStaticIp() {
        if (!enabled) {
            ZES_gv_logger.info("Network configuration disabled, skipping static IP setup.");
            return;
        }
        if (ZES_isWindows()) {
            ZES_applyStaticIpWindows();
        } else {
            ZES_applyStaticIpLinux();
        }
    }

    public void ZES_restoreDhcp() {
        if (!enabled) {
            ZES_gv_logger.info("Network configuration disabled, skipping DHCP restore.");
            return;
        }
        if (ZES_isWindows()) {
            ZES_restoreDhcpWindows();
        } else {
            ZES_restoreDhcpLinux();
        }
    }

    private void ZES_applyStaticIpLinux() {
        ZES_gv_logger.info("Applying static IP " + staticIp + "/" + prefix + " to interface " + interfaceName);
        ZES_runCommand(List.of("ip", "addr", "flush", "dev", interfaceName), "Flush existing IP addresses");
        ZES_runCommand(List.of("ip", "addr", "add", staticIp + "/" + prefix, "dev", interfaceName), "Add static IP address");
        ZES_runCommand(List.of("ip", "link", "set", interfaceName, "up"), "Bring interface up");
    }

    private void ZES_restoreDhcpLinux() {
        ZES_gv_logger.info("Restoring DHCP on interface " + interfaceName);
        ZES_runCommand(List.of("ip", "addr", "flush", "dev", interfaceName), "Flush existing IP addresses");
        ZES_runCommand(List.of("dhclient", "-r", interfaceName), "Release DHCP lease");
        ZES_runCommand(List.of("dhclient", interfaceName), "Request DHCP lease");
    }

    private void ZES_applyStaticIpWindows() {
        ZES_gv_logger.info("Applying static IP " + staticIp + "/" + prefix + " to interface " + interfaceName);
        List<String> command = new ArrayList<>();
        command.add("netsh");
        command.add("interface");
        command.add("ip");
        command.add("set");
        command.add("address");
        command.add("name=" + interfaceName);
        command.add("static");
        command.add(staticIp);
        command.add(ZES_prefixToNetmask(prefix));
        if (gateway != null && !gateway.isBlank()) {
            command.add(gateway);
        } else {
            command.add("none");
        }
        ZES_runCommand(command, "Apply static IP address");
    }

    private void ZES_restoreDhcpWindows() {
        ZES_gv_logger.info("Restoring DHCP on interface " + interfaceName);
        ZES_runCommand(List.of("netsh", "interface", "ip", "set", "address", "name=" + interfaceName, "dhcp"),
                "Enable DHCP on interface");
    }

    private void ZES_runCommand(List<String> command, String description) {
        ProcessBuilder builder = new ProcessBuilder(command);
        builder.redirectErrorStream(true);
        try {
            Process process = builder.start();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    ZES_gv_logger.info(line);
                }
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                ZES_gv_logger.warning(description + " failed with exit code " + exitCode);
            } else {
                ZES_gv_logger.info(description + " completed successfully.");
            }
        } catch (IOException e) {
            ZES_gv_logger.warning(description + " failed: " + e.getMessage());
        } catch (InterruptedException e) {
            ZES_gv_logger.warning(description + " interrupted: " + e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static boolean ZES_isWindows() {
        return System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("win");
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

    private static String ZES_prefixToNetmask(int prefix) {
        int mask = 0xffffffff << (32 - prefix);
        int value = mask;
        return ((value >> 24) & 0xff) + "." + ((value >> 16) & 0xff) + "." + ((value >> 8) & 0xff) + "." + (value & 0xff);
    }
}
