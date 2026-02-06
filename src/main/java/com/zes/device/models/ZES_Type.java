package com.zes.device.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.zes.device.ZES_DeviceApplication.*;

public abstract class ZES_Type
{
    protected final long ZES_gv_timestamp;
    protected final byte[] ZES_gv_bytes;
    public String ZES_gv_ictNumber;
    protected Boolean ZES_gv_hasAnyNewValue = null;
    protected boolean ZES_gv_hasPrevData = false;
    protected abstract void ZES_parseData(ZES_Data data, ResultSet resultSet) throws SQLException;

    abstract public ZES_Type ZES_saveRealTime() throws SQLException;

    public ZES_Type(long timestamp, byte[] bytes, String ictNumber)
    {
        this.ZES_gv_timestamp = timestamp;
        this.ZES_gv_bytes = bytes.clone(); // Create a defensive copy of the byte array
        this.ZES_gv_ictNumber = ictNumber;
    }

    static long ZES_getLong(byte[] bytes, int offset, int size)
    {
        return ZES_convertByteArrayToLong(bytes, offset, size);
    }

    static double ZES_getDouble(byte[] bytes, int offset, int delimitSize)
    {
        int ZES_lv_intSize = 4 - delimitSize;
        long ZES_lv_intValue = ZES_convertByteArrayToLong(bytes, offset, ZES_lv_intSize);
        if(delimitSize == 0)
        {
            return (double) ZES_lv_intValue / 100;
        }
        else
        {
            long ZES_lv_fractionValue = ZES_convertByteArrayToLong(bytes, offset + ZES_lv_intSize, delimitSize);
            return Double.parseDouble(ZES_lv_intValue + "." + ZES_lv_fractionValue);
        }
    }

    static String ZES_getTime(byte[] bytes, int offset)
    {
        long ZES_lv_hour = ZES_convertByteArrayToLong(bytes, offset, 2);
        long ZES_lv_min = ZES_convertByteArrayToLong(bytes, offset + 2, 2);
        long ZES_lv_sec = ZES_convertByteArrayToLong(bytes, offset + 4, 2);
        String[] ZES_lv_time = {ZES_addLeadingZero(ZES_lv_hour), ZES_addLeadingZero(ZES_lv_min), ZES_addLeadingZero(ZES_lv_sec)};
//        long ZES_lv_milliSec = ZES_convertByteArrayToLong(ZES_gv_bytes, offset + 2, 2);
        return String.join(":", ZES_lv_time);
    }

    static String ZES_addLeadingZero(long number) {
        return String.format("%02d", number);
    }

    protected void ZES_handleParseException(Exception e)
    {
        ZES_handleException(e);
        Thread.yield();
    }

    protected void ZES_handleException(Exception e)
    {
        ZES_gv_logger.severe("This error occurs from " + ZES_gv_ictNumber);
        e.printStackTrace();
    }

    protected void ZES_parse(ZES_Data[] dataMap, ResultSet resultSet)
    {
        try
        {
            ZES_gv_hasPrevData = resultSet != null && resultSet.next();
            for (ZES_Data data : dataMap)
            {
                ZES_parseData(data, resultSet);
                if (ZES_gv_hasAnyNewValue == null || !ZES_gv_hasAnyNewValue)
                {
                    ZES_gv_hasAnyNewValue = data.isNewValue();
                }
            }
        }
        catch (Exception e)
        {
            ZES_handleParseException(e);
        }
    }
}
