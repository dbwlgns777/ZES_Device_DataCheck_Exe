package com.zes.device.models;

import com.influxdb.client.domain.WritePrecision;
import com.influxdb.client.write.Point;

import java.sql.ResultSet;
import java.sql.SQLException;

import static com.zes.device.ZES_SQLGenerator.convertTimestampToDateFormat;

public abstract class ZES_TypeMysqlDB extends ZES_Type
{
    protected Point ZES_gv_point;
    private final String ZES_gv_measurement;

    public ZES_TypeMysqlDB(long timestamp, byte[] bytes, String ictNumber)
    {
        super(timestamp, bytes, ictNumber);
        ZES_gv_measurement =  convertTimestampToDateFormat(timestamp, "yyyyMM");
    }

    protected void ZES_initPoint(String type)
    {
        ZES_gv_point = new Point(ZES_gv_measurement)
                .addTag("ict_number", ZES_gv_ictNumber)
                .addTag("type", type)
                .time(ZES_gv_timestamp, WritePrecision.MS);
    }

    @Override
    protected void ZES_parseData(ZES_Data data, ResultSet resultSet) throws SQLException
    {
        switch (data.ZES_gv_dataType)
        {
            case "long":
                data.setValue(ZES_getLong(ZES_gv_bytes, data.ZES_gv_offset, data.ZES_gv_size));
                ZES_gv_point.addField(data.ZES_gv_key, (long) data.ZES_gv_value);
                if (ZES_gv_hasPrevData)
                {
                    data.setPrevValue(resultSet.getLong(data.ZES_gv_key));
                }
                break;
            case "double":
                data.setValue(ZES_getDouble(ZES_gv_bytes, data.ZES_gv_offset, data.ZES_gv_delimit_size));
                ZES_gv_point.addField(data.ZES_gv_key, (double) data.ZES_gv_value);
                if (ZES_gv_hasPrevData)
                {
                    data.setPrevValue(resultSet.getDouble(data.ZES_gv_key));
                }
                break;
            case "time":
                data.setValue(ZES_getTime(ZES_gv_bytes, data.ZES_gv_offset));
                ZES_gv_point.addField(data.ZES_gv_key, data.ZES_gv_value.toString());
                if (ZES_gv_hasPrevData)
                {
                    data.setPrevValue(resultSet.getString(data.ZES_gv_key));
                }
                break;
        }
    }
}
