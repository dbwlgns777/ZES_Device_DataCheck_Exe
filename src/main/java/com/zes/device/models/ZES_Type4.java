package com.zes.device.models;

import com.zes.device.ZES_SQLGenerator;
import com.zes.device.config.ZES_MysqlConfig;

import java.sql.*;

public class ZES_Type4 extends ZES_TypeMysqlDB
{
    private static final ZES_Data[] ZES_gv_DATA_MAP =
    {
        new ZES_Data("flag",20,4),
        new ZES_Data("slide_position_setting0",24,"double",0),
        new ZES_Data("slide_position_setting1",28,"double",0),
        new ZES_Data("slide_position_setting2",32,"double",0),
        new ZES_Data("slide_position_setting3",36,"double",0),
        new ZES_Data("slide_position_setting4",40,"double",0),
        new ZES_Data("slide_position_setting5",44,"double",0),
        new ZES_Data("slide_position_setting6",48,"double",0),
        new ZES_Data("slide_position_setting7",52,"double",0),
        new ZES_Data("slide_position_setting8",56,"double",0),
        new ZES_Data("slide_position_setting9",60,"double",0),
        new ZES_Data("slide_position_setting10",64,"double",0),
        new ZES_Data("slide_position_setting11",68,"double",0),
        new ZES_Data("slide_position_setting12",72,"double",0),
        new ZES_Data("slide_position_setting13",76,"double",0),
        new ZES_Data("slide_position_setting14",80,"double",0),
        new ZES_Data("slide_position_setting15",84,"double",0),
        new ZES_Data("slide_position_setting16",88,"double",0),
        new ZES_Data("slide_position_setting17",92,"double",0),
        new ZES_Data("slide_position_setting18",96,"double",0),
        new ZES_Data("slide_position_setting19",100,"double",0),
        new ZES_Data("slide_position_setting20",104,"double",0),
        new ZES_Data("slide_position_setting21",108,"double",0),
        new ZES_Data("slide_position_setting22",112,"double",0),
        new ZES_Data("slide_position_setting23",116,"double",0),
        new ZES_Data("slide_position_setting24",120,"double",0),
        new ZES_Data("slide_position_setting25",124,"double",0),
        new ZES_Data("slide_position_setting26",128,"double",0),
        new ZES_Data("slide_position_setting27",132,"double",0),
        new ZES_Data("slide_position_setting28",136,"double",0),
        new ZES_Data("slide_position_setting29",140,"double",0),
        new ZES_Data("slide_position_setting30",144,"double",0),
        new ZES_Data("slide_position_setting31",148,"double",0),
        new ZES_Data("slide_position_setting32",152,"double",0),
        new ZES_Data("slide_position_setting33",156,"double",0),
        new ZES_Data("slide_position_setting34",160,"double",0),
        new ZES_Data("slide_position_setting35",164,"double",0),
        new ZES_Data("slide_position_setting36",168,"double",0),
        new ZES_Data("slide_position_setting37",172,"double",0),
        new ZES_Data("slide_position_setting38",176,"double",0),
        new ZES_Data("slide_position_setting39",180,"double",0),
        new ZES_Data("slide_position_setting40",184,"double",0),
        new ZES_Data("slide_position_setting41",188,"double",0),
        new ZES_Data("slide_position_setting42",192,"double",0),
        new ZES_Data("slide_position_setting43",196,"double",0),
        new ZES_Data("slide_position_setting44",200,"double",0),
        new ZES_Data("slide_position_setting45",204,"double",0),
        new ZES_Data("slide_position_setting46",208,"double",0),
        new ZES_Data("slide_position_setting47",212,"double",0),
        new ZES_Data("slide_position_setting48",216,"double",0),
        new ZES_Data("slide_position_setting49",220,"double",0),
        new ZES_Data("slide_position_setting50",224,"double",0),
        new ZES_Data("slide_position_setting51",228,"double",0),
        new ZES_Data("slide_position_setting52",232,"double",0),
        new ZES_Data("slide_position_setting53",236,"double",0),
        new ZES_Data("slide_position_setting54",240,"double",0),
        new ZES_Data("slide_position_setting55",244,"double",0),
        new ZES_Data("slide_position_setting56",248,"double",0),
        new ZES_Data("slide_position_setting57",252,"double",0),
        new ZES_Data("slide_position_setting58",256,"double",0),
        new ZES_Data("slide_position_setting59",260,"double",0),
        new ZES_Data("slide_position_setting60",264,"double",0),
        new ZES_Data("slide_position_setting61",268,"double",0),
        new ZES_Data("slide_position_setting62",272,"double",0),
        new ZES_Data("slide_position_setting63",276,"double",0),
        new ZES_Data("slide_position_setting64",280,"double",0),
        new ZES_Data("slide_position_setting65",284,"double",0),
        new ZES_Data("slide_position_setting66",288,"double",0),
        new ZES_Data("slide_position_setting67",292,"double",0),
        new ZES_Data("slide_position_setting68",296,"double",0),
        new ZES_Data("slide_position_setting69",300,"double",0),
        new ZES_Data("slide_position_setting70",304,"double",0),
        new ZES_Data("slide_position_setting71",308,"double",0),
        new ZES_Data("slide_position_setting72",312,"double",0),
        new ZES_Data("slide_position_setting73",316,"double",0),
        new ZES_Data("slide_position_setting74",320,"double",0),
        new ZES_Data("slide_position_setting75",324,"double",0),
        new ZES_Data("slide_position_setting76",328,"double",0),
        new ZES_Data("slide_position_setting77",332,"double",0),
        new ZES_Data("slide_position_setting78",336,"double",0),
        new ZES_Data("slide_position_setting79",340,"double",0),
        new ZES_Data("slide_position_setting80",344,"double",0),
        new ZES_Data("slide_position_setting81",348,"double",0),
        new ZES_Data("slide_position_setting82",352,"double",0),
        new ZES_Data("slide_position_setting83",356,"double",0),
        new ZES_Data("slide_position_setting84",360,"double",0),
        new ZES_Data("slide_position_setting85",364,"double",0),
        new ZES_Data("slide_position_setting86",368,"double",0),
        new ZES_Data("slide_position_setting87",372,"double",0),
        new ZES_Data("slide_position_setting88",376,"double",0),
        new ZES_Data("slide_position_setting89",380,"double",0),
        new ZES_Data("slide_position_setting90",384,"double",0),
        new ZES_Data("slide_position_setting91",388,"double",0),
        new ZES_Data("slide_position_setting92",392,"double",0),
        new ZES_Data("slide_position_setting93",396,"double",0),
        new ZES_Data("slide_position_setting94",400,"double",0),
        new ZES_Data("slide_position_setting95",404,"double",0),
        new ZES_Data("slide_position_setting96",408,"double",0),
        new ZES_Data("slide_position_setting97",412,"double",0),
        new ZES_Data("slide_position_setting98",416,"double",0),
        new ZES_Data("slide_position_setting99",420,"double",0),
        new ZES_Data("slide_position_high_limit",424,"double",0),
        new ZES_Data("slide_position_low_limit",428,"double",0),
        new ZES_Data("slide_position_diff",432,"double",0),
        new ZES_Data("slide_position_current",436,"double",0),
        new ZES_Data("slide_position_num",440,2),
        new ZES_Data("slide_position_state",442,1),
        new ZES_Data("slide_position_mode",443,1)
    };
    private static final String ZES_gv_Type = "4";
    private static final String ZES_gv_tableName = "pms_real_data_4";

    public ZES_Type4(long timestamp, byte[] bytes, String ictNumber)
    {
        super(timestamp, bytes, ictNumber);
    }

    @Override
    public ZES_Type4 ZES_saveRealTime()
    {
        ZES_initPoint(ZES_gv_Type);
        try
        (
                Connection ZES_lv_conn = ZES_MysqlConfig.getConnection();
                PreparedStatement ZES_lv_preparedStatement = ZES_SQLGenerator.findByIctNumber(ZES_lv_conn, ZES_gv_ictNumber, ZES_gv_tableName);
                ResultSet ZES_lv_prevResultSet = ZES_lv_preparedStatement.executeQuery();
        )
        {
            ZES_parse(ZES_gv_DATA_MAP, ZES_lv_prevResultSet);
            if(ZES_gv_hasPrevData)
            {
                ZES_SQLGenerator.update(ZES_lv_conn, ZES_gv_DATA_MAP, ZES_gv_tableName, ZES_gv_ictNumber, ZES_gv_timestamp);
            }
            else
            {
                ZES_SQLGenerator.insert(ZES_lv_conn, ZES_gv_DATA_MAP, ZES_gv_ictNumber, ZES_gv_tableName, ZES_gv_timestamp);
            }
        }
        catch (SQLException e) {
            ZES_parse(ZES_gv_DATA_MAP, null);
            ZES_handleException(e);
        }
        finally
        {
            return this;
        }
    }
}
