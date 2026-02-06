package com.zes.device;

import com.zes.device.models.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;

import static com.zes.device.ZES_DeviceApplication.*;

public class ZES_Producer implements Runnable {
    private static final int ZES_gv_BUFFER_SIZE = 512;
    private static final int ZES_gv_CHECKSUM_OFFSET = 510;
    private static final int ZES_gv_CHECKSUM_SIZE = 2;
    private static final int ZES_gv_INFO_TYPE_OFFSET = 9;
    private static final int ZES_gv_INFO_TYPE_SIZE = 1;
    private static final int ZES_gv_ICT_NUMBER_OFFSET = 10;
    private static final int ZES_gv_ICT_NUMBER_SIZE = 8;

    private final BlockingQueue<ZES_TypeMysqlDB> queueType0;
    private final BlockingQueue<ZES_TypeMysqlDB> queueType1;
    private final BlockingQueue<ZES_TypeMysqlDB> queueType2;
    private final BlockingQueue<ZES_TypeMysqlDB> queueType3;
    private final BlockingQueue<ZES_TypeMysqlDB> queueType4;
    private final int threadNo;
    private final ServerSocket serverSocket;
    public ZES_Producer(BlockingQueue<ZES_TypeMysqlDB> queueType0, BlockingQueue<ZES_TypeMysqlDB> queueType1, BlockingQueue<ZES_TypeMysqlDB> queueType2, BlockingQueue<ZES_TypeMysqlDB> queueType3, BlockingQueue<ZES_TypeMysqlDB> queueType4, int threadNo, ServerSocket serverSocket)
    {
        this.queueType0 = queueType0;
        this.queueType1 = queueType1;
        this.queueType2 = queueType2;
        this.queueType3 = queueType3;
        this.queueType4 = queueType4;
        this.threadNo = threadNo;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run()
    {
        try
        {
            serverSocket.setSoTimeout(10000);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }


        while (!Thread.currentThread().isInterrupted())
        {
            try
            {
                Socket ZES_lv_socket = serverSocket.accept();
                LocalDateTime now = LocalDateTime.now();
                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
                ZES_lv_socket.setSoTimeout(2000);
                System.out.println("data receive time => "+ now.format(fmt));
                ZES_readBytesAndEnqueue(ZES_lv_socket);
            }
            catch (IOException e)
            {
                if (!Thread.currentThread().isInterrupted())
                {
                    ZES_gv_logger.severe("IOException in Producer thread " + threadNo + ": " + e.getMessage());
                }
            }
            catch (InterruptedException e)
            {
                ZES_gv_logger.info("Producer thread " + threadNo + " interrupted, shutting down");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void ZES_readBytesAndEnqueue(Socket socket) throws IOException, InterruptedException
    {
        try
        {
            long ZES_lv_timestamp = Instant.now().toEpochMilli();
            InputStream ZES_lv_inputStream = socket.getInputStream();
            // Read bytes from the input stream - 전체 데이터를 보장하기 위해 루프로 읽기
            byte[] ZES_lv_buffer = new byte[ZES_gv_BUFFER_SIZE];

            for(int ci = 0; ci < 5; ci++)
            {
                int ZES_lv_totalBytesRead = 0;

                while (ZES_lv_totalBytesRead < ZES_gv_BUFFER_SIZE)
                {
                    int ZES_lv_bytesRead = ZES_lv_inputStream.read(ZES_lv_buffer, ZES_lv_totalBytesRead, ZES_gv_BUFFER_SIZE - ZES_lv_totalBytesRead);

                    if (ZES_lv_bytesRead == -1)
                    {
                        throw new IOException("Connection closed before reading complete data. Read " + ZES_lv_totalBytesRead + " bytes");
                    }

                    ZES_lv_totalBytesRead += ZES_lv_bytesRead;
                }

                if(ZES_validateCheckSum(ZES_lv_buffer))
                {
                    int ZES_lv_infoType = (int) ZES_convertByteArrayToLong(ZES_lv_buffer, ZES_gv_INFO_TYPE_OFFSET, ZES_gv_INFO_TYPE_SIZE);
                    String ZES_lv_ictNumber = ZES_convertByteArrayToString(ZES_lv_buffer, ZES_gv_ICT_NUMBER_OFFSET, ZES_gv_ICT_NUMBER_SIZE);

                    if(ZES_filterIctNumber(ZES_lv_ictNumber))
                    {
                        System.out.println(" ZES_lv_infoType =>"+ ZES_lv_infoType);
                        System.out.println(" ZES_lv_ictNumber =>"+ ZES_lv_ictNumber);
                        switch (ZES_lv_infoType)
                        {
                            case 0:
                                queueType0.put(new ZES_Type0(ZES_lv_timestamp, ZES_lv_buffer, ZES_lv_ictNumber));
//                            ZES_gv_logger.info("thread" + threadNo + " in queue : " + queueType0.size());
                                break;
                            case 1:
                                queueType1.put(new ZES_Type1(ZES_lv_timestamp, ZES_lv_buffer, ZES_lv_ictNumber));
//                            ZES_gv_logger.info("thread" + threadNo + " in queue : " + queueType1.size());
                                break;
                            case 2:
                                queueType2.put(new ZES_Type2(ZES_lv_timestamp, ZES_lv_buffer, ZES_lv_ictNumber));
//                            ZES_gv_logger.info("thread" + threadNo + " in queue : " + queueType2.size());
                                break;
                            case 3:
                                queueType3.put(new ZES_Type3(ZES_lv_timestamp, ZES_lv_buffer, ZES_lv_ictNumber));
//                            ZES_gv_logger.info("thread" + threadNo + " in queue : " + queueType3.size());
                                break;
                            case 4:
                                queueType4.put(new ZES_Type4(ZES_lv_timestamp, ZES_lv_buffer, ZES_lv_ictNumber));
//                            ZES_gv_logger.info("thread" + threadNo + " in queue : " + queueType4.size());
                                break;
                            default:
                                ZES_gv_logger.warning("Unknown info type: " + ZES_lv_infoType + " from ICT: " + ZES_lv_ictNumber);
                                break;
                        }
                    }
                }
                else
                {
                    ZES_gv_logger.warning("Checksum validation failed for ICT: " +
                            ZES_convertByteArrayToString(ZES_lv_buffer, ZES_gv_ICT_NUMBER_OFFSET, ZES_gv_ICT_NUMBER_SIZE));
                }
            }
        }
        catch (IOException e)
        {
            ZES_gv_logger.severe("IOException in thread " + threadNo + ": " + e.getMessage());
        }
        catch (InterruptedException e)
        {
            ZES_gv_logger.warning("Thread " + threadNo + " interrupted");
            Thread.currentThread().interrupt(); // Restore interrupted status
            throw e; // 예외를 다시 던져서 상위에서 처리할 수 있도록
        }
        finally
        {
            try
            {
                socket.close();
            }
            catch (IOException e)
            {
                ZES_gv_logger.warning("Error closing socket: " + e.getMessage());
            }
        }
    }

    private static boolean ZES_validateCheckSum(byte[] dataBuffer)
    {
//        return true;
        long ZES_lv_checkSum = 0;
        for (int i = 0; i < 510; i++)
        {
            ZES_lv_checkSum += dataBuffer[i] & 0xff;
        }
        return ZES_lv_checkSum == ZES_convertByteArrayToLong(dataBuffer, ZES_gv_CHECKSUM_OFFSET, ZES_gv_CHECKSUM_SIZE);
    }

    private boolean ZES_filterIctNumber(String ictNumber)
    {
        return !ictNumber.equals("P0000000") && !ictNumber.contains("\u0000");
    }
}
