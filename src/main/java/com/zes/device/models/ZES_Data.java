package com.zes.device.models;

public class ZES_Data
{
    public final String ZES_gv_key;
    public final int ZES_gv_offset;
    public int ZES_gv_size = 0;
    public int ZES_gv_delimit_size;
    public String ZES_gv_dataType = "long";
    public Object ZES_gv_value;
    public Object ZES_gv_prevValue;
    public boolean ZES_gv_isNewValue = true;

    public ZES_Data(String key, int offset, int size)
    {
        this.ZES_gv_key = key;
        this.ZES_gv_offset = offset;
        this.ZES_gv_size = size;
    }

    public ZES_Data(String key, int offset, String dataType, int delimitSize)
    {
        this.ZES_gv_key = key;
        this.ZES_gv_offset = offset;
        this.ZES_gv_dataType = dataType;
        this.ZES_gv_delimit_size = delimitSize;
    }

    public void setValue(Object value)
    {
        this.ZES_gv_value = value;
    }

    public void setPrevValue(Object value)
    {
        this.ZES_gv_prevValue = value;
        this.ZES_gv_isNewValue = !ZES_gv_value.equals(ZES_gv_prevValue);
    }

    public boolean isNewValue()
    {
        return ZES_gv_isNewValue;
    }
}
