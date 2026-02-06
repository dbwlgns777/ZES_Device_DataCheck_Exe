package com.zes.device;

import com.zes.device.models.ZES_TypeMysqlDB;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class ZES_DeviceApplication
{
    public static final Logger ZES_gv_logger = Logger.getGlobal();
    private static final int ZES_gv_NUM_PRODUCER_THREADS = 1;
    private static final int ZES_gv_NUM_CONSUMER_THREADS = 5;

    public static void main(String[] args) throws IOException
    {
        int ZES_lv_LISTENING_PORT = 9600;
        if (args.length == 1)
        {
            ZES_lv_LISTENING_PORT = Integer.parseInt(args[0]);
        }
        else if (args.length > 1)
        {
            System.err.println("Usage: java -jar MyServer.jar [<port>]");
            System.exit(1);
        }

        BlockingQueue<ZES_TypeMysqlDB> ZES_lv_queueType0 = new ArrayBlockingQueue<>(1000);
        BlockingQueue<ZES_TypeMysqlDB> ZES_lv_queueType1 = new ArrayBlockingQueue<>(1000);
        BlockingQueue<ZES_TypeMysqlDB> ZES_lv_queueType2 = new ArrayBlockingQueue<>(1000);
        BlockingQueue<ZES_TypeMysqlDB> ZES_lv_queueType3 = new ArrayBlockingQueue<>(1000);
        BlockingQueue<ZES_TypeMysqlDB> ZES_lv_queueType4 = new ArrayBlockingQueue<>(1000);
        ExecutorService ZES_lv_producerThreadPool = Executors.newFixedThreadPool(ZES_gv_NUM_PRODUCER_THREADS);
        ExecutorService ZES_lv_consumerThreadPool = Executors.newFixedThreadPool(ZES_gv_NUM_CONSUMER_THREADS);

        ServerSocket ZES_lv_serverSocket = new ServerSocket(ZES_lv_LISTENING_PORT, 1024);
        for (int i = 0; i < ZES_gv_NUM_PRODUCER_THREADS; i++)
        {
            ZES_lv_producerThreadPool.submit(new ZES_Producer(ZES_lv_queueType0, ZES_lv_queueType1, ZES_lv_queueType2, ZES_lv_queueType3, ZES_lv_queueType4, i, ZES_lv_serverSocket));
        }

        //ZES_lv_consumerThreadPool.submit(new ZES_Consumer(ZES_lv_queueType0, 0));
        //ZES_lv_consumerThreadPool.submit(new ZES_Consumer(ZES_lv_queueType1, 1));
        //ZES_lv_consumerThreadPool.submit(new ZES_Consumer(ZES_lv_queueType2, 2));
        //ZES_lv_consumerThreadPool.submit(new ZES_Consumer(ZES_lv_queueType3, 3));
        //ZES_lv_consumerThreadPool.submit(new ZES_Consumer(ZES_lv_queueType4, 4));
        // Graceful shutdown hook 추가
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            ZES_gv_logger.info("Shutting down application...");
            ZES_lv_producerThreadPool.shutdown();
            ZES_lv_consumerThreadPool.shutdown();
            try
            {
                if (!ZES_lv_producerThreadPool.awaitTermination(10, TimeUnit.SECONDS))
                {
                    ZES_gv_logger.warning("Producer threads did not terminate gracefully, forcing shutdown");
                    ZES_lv_producerThreadPool.shutdownNow();
                }
                if (!ZES_lv_consumerThreadPool.awaitTermination(10, TimeUnit.SECONDS))
                {
                    ZES_gv_logger.warning("Consumer threads did not terminate gracefully, forcing shutdown");
                    ZES_lv_consumerThreadPool.shutdownNow();
                }
            }
            catch (InterruptedException e)
            {
                ZES_gv_logger.severe("Shutdown interrupted");
                ZES_lv_producerThreadPool.shutdownNow();
                ZES_lv_consumerThreadPool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }));
    }

    public static long ZES_convertByteArrayToLong(byte[] byteData, int position, int size)
    {
        long ZES_lv_value = 0;
        for (int i = 0; i < size; i++)
        {
            ZES_lv_value = (ZES_lv_value << 8) | (byteData[position + i] & 0xFF);
        }
        return ZES_lv_value;
    }

    public static String ZES_convertByteArrayToString(byte[] byteData, int position, int size)
    {
        byte[] ZES_lv_subArray = new byte[size];
        System.arraycopy(byteData, position, ZES_lv_subArray, 0, size);
        return new String(ZES_lv_subArray);
    }

}