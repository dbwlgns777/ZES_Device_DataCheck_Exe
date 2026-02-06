package com.zes.device.models;

import com.zes.device.ZES_SQLGenerator;
import com.zes.device.config.ZES_MysqlConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ZES_Type0 extends ZES_TypeMysqlDB
{
    private static final ZES_Data[] ZES_gv_DATA_MAP =
    {
        new ZES_Data("spm",20,2),
        new ZES_Data("angle",22,2),
        new ZES_Data("main_current",24,"double", 2),
        new ZES_Data("slide_current",28,"double", 2),
        new ZES_Data("vs",32,2),
        new ZES_Data("preset_counter",34,4),
        new ZES_Data("total_counter",38,4),
        new ZES_Data("whole_counter",42,4),
        new ZES_Data("limit_counter",46,4),
        new ZES_Data("loadton",50,2),
        new ZES_Data("loadton_high",52,2),
        new ZES_Data("loadton_avg",54,2),
        new ZES_Data("rd_total",58,4),
        new ZES_Data("rd1",62,4),
        new ZES_Data("rd2",66,4),
        new ZES_Data("flag",70,4),
        new ZES_Data("preset_counter_state",74,1),
        new ZES_Data("total_counter_state",75,1),
        new ZES_Data("temperature1",90,2),
        new ZES_Data("temperature2",92,2),
        new ZES_Data("temperature3",94,2),
        new ZES_Data("temperature4",96,2),
        new ZES_Data("temperature5",98,2),
        new ZES_Data("temperature6",100,2),
        new ZES_Data("temperature7",102,2),
        new ZES_Data("temperature8",104,2),
        new ZES_Data("stop_angle",106,2),
        new ZES_Data("slip_angle",108,2),
        new ZES_Data("keycam",110,2),
        new ZES_Data("run_ready",112,2),
        new ZES_Data("run_ok",114,2),
        new ZES_Data("motor_state",116,2),
        new ZES_Data("motor_vector",118,2),
        new ZES_Data("press_state",120,2),
        new ZES_Data("error_num",122,2),
        new ZES_Data("slide_position",124,"double",0),
        new ZES_Data("run_time",128,"time", 2),
        new ZES_Data("stop_time",136,"time", 2),
        new ZES_Data("X00",144,1),
        new ZES_Data("X01",145,1),
        new ZES_Data("X02",146,1),
        new ZES_Data("X03",147,1),
        new ZES_Data("X04",148,1),
        new ZES_Data("I53",149,1),
        new ZES_Data("M21",150,1),
        new ZES_Data("I26",151,1),
        new ZES_Data("X10",152,1),
        new ZES_Data("MSP1",153,1),
        new ZES_Data("MSP2",154,1),
        new ZES_Data("MSP3",155,1),
        new ZES_Data("I52",156,1),
        new ZES_Data("I48",157,1),
        new ZES_Data("I40",158,1),
        new ZES_Data("I34",159,1),
        new ZES_Data("I35",160,1),
        new ZES_Data("I39",161,1),
        new ZES_Data("I27",162,1),
        new ZES_Data("I28",163,1),
        new ZES_Data("I29",164,1),
        new ZES_Data("I30",165,1),
        new ZES_Data("X0D",166,1),
        new ZES_Data("X21",167,1),
        new ZES_Data("X11",168,1),
        new ZES_Data("I25",169,1),
        new ZES_Data("R21",170,1),
        new ZES_Data("I42",171,1),
        new ZES_Data("I41",172,1),
        new ZES_Data("X12",173,1),
        new ZES_Data("X0F",174,1),
        new ZES_Data("X0E",175,1),
        new ZES_Data("X0C",176,1),
        new ZES_Data("X09",177,1),
        new ZES_Data("X07",178,1),
        new ZES_Data("X05",179,1),
        new ZES_Data("SP1",180,1),
        new ZES_Data("SP2",181,1),
        new ZES_Data("C01",244,1),
        new ZES_Data("C02",245,1),
        new ZES_Data("C03",246,1),
        new ZES_Data("C04",247,1),
        new ZES_Data("C05",248,1),
        new ZES_Data("C06",249,1),
        new ZES_Data("C07",250,1),
        new ZES_Data("C08",251,1),
        new ZES_Data("C09",252,1),
        new ZES_Data("C10",253,1),
        new ZES_Data("C11",254,1),
        new ZES_Data("C12",255,1),
        new ZES_Data("C13",256,1),
        new ZES_Data("C14",257,1),
        new ZES_Data("C15",258,1),
        new ZES_Data("C16",259,1),
        new ZES_Data("C17",260,1),
        new ZES_Data("C18",261,1),
        new ZES_Data("C19",262,1),
        new ZES_Data("C20",263,1),
        new ZES_Data("C21",264,1),
        new ZES_Data("C22",265,1),
        new ZES_Data("C23",266,1),
        new ZES_Data("C24",267,1),
        new ZES_Data("C25",268,1),
        new ZES_Data("C26",269,1),
        new ZES_Data("C27",270,1),
        new ZES_Data("C28",271,1),
        new ZES_Data("O24",272,1),
        new ZES_Data("error01",304,1),
        new ZES_Data("error02",305,1),
        new ZES_Data("error03",306,1),
        new ZES_Data("error04",307,1),
        new ZES_Data("error05",308,1),
        new ZES_Data("error06",309,1),
        new ZES_Data("error07",310,1),
        new ZES_Data("error08",311,1),
        new ZES_Data("error09",312,1),
        new ZES_Data("error10",313,1),
        new ZES_Data("error11",314,1),
        new ZES_Data("error12",315,1),
        new ZES_Data("error13",316,1),
        new ZES_Data("error14",317,1),
        new ZES_Data("error15",318,1),
        new ZES_Data("error16",319,1),
        new ZES_Data("error17",320,1),
        new ZES_Data("error18",321,1),
        new ZES_Data("error19",322,1),
        new ZES_Data("error20",323,1),
        new ZES_Data("error21",324,1),
        new ZES_Data("error22",325,1),
        new ZES_Data("error23",326,1),
        new ZES_Data("error24",327,1),
        new ZES_Data("error25",328,1),
        new ZES_Data("error26",329,1),
        new ZES_Data("error27",330,1),
        new ZES_Data("error28",331,1),
        new ZES_Data("error29",332,1),
        new ZES_Data("error30",333,1),
        new ZES_Data("error31",334,1),
        new ZES_Data("error32",335,1),
        new ZES_Data("error33",336,1),
        new ZES_Data("error34",337,1),
        new ZES_Data("error35",338,1),
        new ZES_Data("error36",339,1),
        new ZES_Data("error37",340,1),
        new ZES_Data("error38",341,1),
        new ZES_Data("error39",342,1),
        new ZES_Data("error40",343,1),
        new ZES_Data("error41",344,1),
        new ZES_Data("error42",345,1),
        new ZES_Data("error43",346,1),
        new ZES_Data("error44",347,1),
        new ZES_Data("error45",348,1),
        new ZES_Data("error46",349,1),
        new ZES_Data("error47",350,1),
        new ZES_Data("error48",351,1),
        new ZES_Data("error49",352,1),
        new ZES_Data("error50",353,1),
        new ZES_Data("error51",354,1),
        new ZES_Data("error52",355,1),
        new ZES_Data("error53",356,1),
        new ZES_Data("error54",357,1),
        new ZES_Data("error55",358,1),
        new ZES_Data("error56",359,1),
        new ZES_Data("error57",360,1),
        new ZES_Data("error58",361,1),
        new ZES_Data("error59",362,1),
        new ZES_Data("error60",363,1),
        new ZES_Data("error61",364,1),
        new ZES_Data("error62",365,1),
        new ZES_Data("error63",366,1),
        new ZES_Data("error64",367,1),
        new ZES_Data("error65",368,1),
        new ZES_Data("error66",369,1),
        new ZES_Data("error67",370,1),
        new ZES_Data("error68",371,1),
        new ZES_Data("error69",372,1),
        new ZES_Data("error70",373,1),
        new ZES_Data("error71",374,1),
        new ZES_Data("error72",375,1),
        new ZES_Data("error73",376,1),
        new ZES_Data("error74",377,1),
        new ZES_Data("error75",378,1),
        new ZES_Data("error76",379,1),
        new ZES_Data("error77",380,1),
        new ZES_Data("error78",381,1),
        new ZES_Data("error79",382,1),
        new ZES_Data("error80",383,1),
        new ZES_Data("error81",384,1),
        new ZES_Data("error82",385,1),
        new ZES_Data("error83",386,1),
        new ZES_Data("error84",387,1),
        new ZES_Data("error85",388,1),
        new ZES_Data("error86",389,1),
        new ZES_Data("error87",390,1),
        new ZES_Data("error88",391,1),
        new ZES_Data("error89",392,1),
        new ZES_Data("error90",393,1),
        new ZES_Data("error91",394,1),
        new ZES_Data("error92",395,1),
        new ZES_Data("error93",396,1),
        new ZES_Data("error94",397,1),
        new ZES_Data("error95",398,1),
        new ZES_Data("error96",399,1),
        new ZES_Data("error97",400,1),
        new ZES_Data("error98",401,1),
        new ZES_Data("error99",402,1)
    };
    private static final String ZES_gv_Type = "0";
    private static final String ZES_gv_tableName = "pms_real_data_0";

    public ZES_Type0(long timestamp, byte[] bytes, String ictNumber)
    {
        super(timestamp, bytes, ictNumber);
    }

    @Override
    public ZES_Type0 ZES_saveRealTime()
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
            List<String> ZES_lv_queries = new ArrayList<>();
            if(ZES_gv_hasPrevData)
            {
                String updateQuery = ZES_SQLGenerator.getUpdateQuery(ZES_gv_DATA_MAP, ZES_gv_ictNumber, ZES_gv_tableName, ZES_gv_timestamp);
                ZES_lv_queries.add(updateQuery);
            }
            else
            {
                String insertQuery = ZES_SQLGenerator.getInsertQuery(ZES_gv_DATA_MAP, ZES_gv_ictNumber, ZES_gv_tableName, ZES_gv_timestamp);
                ZES_lv_queries.add(insertQuery);
            }
            ZES_SQLGenerator.executeBatchQuery(ZES_lv_conn, ZES_lv_queries);

        }
        catch (SQLException e)
        {
            ZES_parse(ZES_gv_DATA_MAP, null);
            ZES_handleException(e);
        }
        finally
        {
            return this;
        }
    }
}
