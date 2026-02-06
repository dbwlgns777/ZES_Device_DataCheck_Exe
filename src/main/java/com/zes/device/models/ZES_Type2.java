package com.zes.device.models;

import com.zes.device.ZES_SQLGenerator;
import com.zes.device.config.ZES_MysqlConfig;

import java.sql.*;

public class ZES_Type2 extends ZES_TypeMysqlDB
{
    private static final ZES_Data[] ZES_gv_DATA_MAP =
    {
        new ZES_Data("flag",20,4),
        new ZES_Data("parameter0",24,2),
        new ZES_Data("parameter1",26,2),
        new ZES_Data("parameter2",28,2),
        new ZES_Data("parameter3",30,2),
        new ZES_Data("parameter4",32,2),
        new ZES_Data("parameter5",34,2),
        new ZES_Data("parameter6",36,2),
        new ZES_Data("parameter7",38,2),
        new ZES_Data("parameter8",40,2),
        new ZES_Data("parameter9",42,2),
        new ZES_Data("parameter10",44,2),
        new ZES_Data("parameter11",46,2),
        new ZES_Data("parameter12",48,2),
        new ZES_Data("parameter13",50,2),
        new ZES_Data("parameter14",52,2),
        new ZES_Data("parameter15",54,2),
        new ZES_Data("parameter16",56,2),
        new ZES_Data("parameter17",58,2),
        new ZES_Data("parameter18",60,2),
        new ZES_Data("parameter19",62,2),
        new ZES_Data("parameter20",64,2),
        new ZES_Data("parameter21",66,2),
        new ZES_Data("parameter22",68,2),
        new ZES_Data("parameter23",70,2),
        new ZES_Data("parameter24",72,2),
        new ZES_Data("parameter25",74,2),
        new ZES_Data("parameter26",76,2),
        new ZES_Data("parameter27",78,2),
        new ZES_Data("parameter28",80,2),
        new ZES_Data("parameter29",82,2),
        new ZES_Data("parameter30",84,2),
        new ZES_Data("parameter31",86,2),
        new ZES_Data("parameter32",88,2),
        new ZES_Data("parameter33",90,2),
        new ZES_Data("parameter34",92,2),
        new ZES_Data("parameter35",94,2),
        new ZES_Data("parameter36",96,2),
        new ZES_Data("parameter37",98,2),
        new ZES_Data("parameter38",100,2),
        new ZES_Data("parameter39",102,2),
        new ZES_Data("parameter40",104,2),
        new ZES_Data("parameter41",106,2),
        new ZES_Data("parameter42",108,2),
        new ZES_Data("parameter43",110,2),
        new ZES_Data("parameter44",112,2),
        new ZES_Data("parameter45",114,2),
        new ZES_Data("parameter46",116,2),
        new ZES_Data("parameter47",118,2),
        new ZES_Data("parameter48",120,2),
        new ZES_Data("parameter49",122,2),
        new ZES_Data("parameter50",124,2),
        new ZES_Data("parameter51",126,2),
        new ZES_Data("parameter52",128,2),
        new ZES_Data("parameter53",130,2),
        new ZES_Data("parameter54",132,2),
        new ZES_Data("parameter55",134,2),
        new ZES_Data("parameter56",136,2),
        new ZES_Data("parameter57",138,2),
        new ZES_Data("parameter58",140,2),
        new ZES_Data("parameter59",142,2),
        new ZES_Data("parameter60",144,2),
        new ZES_Data("parameter61",146,2),
        new ZES_Data("parameter62",148,2),
        new ZES_Data("parameter63",150,2),
        new ZES_Data("parameter64",152,2),
        new ZES_Data("parameter65",154,2),
        new ZES_Data("parameter66",156,2),
        new ZES_Data("parameter67",158,2),
        new ZES_Data("parameter68",160,2),
        new ZES_Data("parameter69",162,2),
        new ZES_Data("parameter70",164,2),
        new ZES_Data("parameter71",166,2),
        new ZES_Data("parameter72",168,2),
        new ZES_Data("parameter73",170,2),
        new ZES_Data("parameter74",172,2),
        new ZES_Data("parameter75",174,2),
        new ZES_Data("parameter76",176,2),
        new ZES_Data("parameter77",178,2),
        new ZES_Data("parameter78",180,2),
        new ZES_Data("parameter79",182,2),
        new ZES_Data("parameter80",184,2),
        new ZES_Data("parameter81",186,2),
        new ZES_Data("parameter82",188,2),
        new ZES_Data("parameter83",190,2),
        new ZES_Data("parameter84",192,2),
        new ZES_Data("parameter85",194,2),
        new ZES_Data("parameter86",196,2),
        new ZES_Data("parameter87",198,2),
        new ZES_Data("parameter88",190,2),
        new ZES_Data("parameter89",202,2),
        new ZES_Data("parameter90",204,2),
        new ZES_Data("parameter91",206,2),
        new ZES_Data("parameter92",208,2),
        new ZES_Data("parameter93",210,2),
        new ZES_Data("parameter94",212,2),
        new ZES_Data("parameter95",214,2),
        new ZES_Data("parameter96",216,2),
        new ZES_Data("parameter97",218,2),
        new ZES_Data("parameter98",220,2),
        new ZES_Data("parameter99",222,2),
        new ZES_Data("parameter100",224,2),
        new ZES_Data("parameter101",226,2),
        new ZES_Data("parameter102",228,2),
        new ZES_Data("parameter103",220,2),
        new ZES_Data("parameter104",232,2),
        new ZES_Data("parameter105",234,2),
        new ZES_Data("parameter106",236,2),
        new ZES_Data("parameter107",238,2),
        new ZES_Data("parameter108",240,2),
        new ZES_Data("parameter109",242,2),
        new ZES_Data("parameter110",244,2),
        new ZES_Data("parameter111",246,2),
        new ZES_Data("parameter112",248,2),
        new ZES_Data("parameter113",250,2),
        new ZES_Data("parameter114",252,2),
        new ZES_Data("parameter115",254,2),
        new ZES_Data("parameter116",256,2),
        new ZES_Data("parameter117",258,2),
        new ZES_Data("parameter118",260,2),
        new ZES_Data("parameter119",262,2),
        new ZES_Data("parameter120",264,2),
        new ZES_Data("parameter121",266,2),
        new ZES_Data("parameter122",268,2),
        new ZES_Data("parameter123",270,2),
        new ZES_Data("parameter124",272,2),
        new ZES_Data("parameter125",274,2),
        new ZES_Data("parameter126",276,2),
        new ZES_Data("parameter127",278,2),
        new ZES_Data("parameter128",280,2),
        new ZES_Data("parameter129",282,2),
        new ZES_Data("parameter130",284,2),
        new ZES_Data("parameter131",286,2),
        new ZES_Data("parameter132",288,2),
        new ZES_Data("parameter133",290,2),
        new ZES_Data("parameter134",292,2),
        new ZES_Data("parameter135",294,2),
        new ZES_Data("parameter136",296,2),
        new ZES_Data("parameter137",298,2),
        new ZES_Data("parameter138",300,2),
        new ZES_Data("parameter139",302,2),
        new ZES_Data("parameter140",304,2),
        new ZES_Data("parameter141",306,2),
        new ZES_Data("parameter142",308,2),
        new ZES_Data("parameter143",310,2),
        new ZES_Data("parameter144",312,2),
        new ZES_Data("parameter145",314,2),
        new ZES_Data("parameter146",316,2),
        new ZES_Data("parameter147",318,2),
        new ZES_Data("parameter148",320,2),
        new ZES_Data("parameter149",322,2),
        new ZES_Data("parameter150",324,2),
        new ZES_Data("parameter151",326,2),
        new ZES_Data("parameter152",328,2),
        new ZES_Data("parameter153",330,2),
        new ZES_Data("parameter154",332,2),
        new ZES_Data("parameter155",334,2),
        new ZES_Data("parameter156",336,2),
        new ZES_Data("parameter157",338,2),
        new ZES_Data("parameter158",340,2),
        new ZES_Data("parameter159",342,2),
        new ZES_Data("parameter160",344,2),
        new ZES_Data("parameter161",346,2),
        new ZES_Data("parameter162",348,2),
        new ZES_Data("parameter163",350,2),
        new ZES_Data("parameter164",352,2),
        new ZES_Data("parameter165",354,2),
        new ZES_Data("parameter166",356,2),
        new ZES_Data("parameter167",358,2),
        new ZES_Data("parameter168",360,2),
        new ZES_Data("parameter169",362,2),
        new ZES_Data("parameter170",364,2),
        new ZES_Data("parameter171",366,2),
        new ZES_Data("parameter172",368,2),
        new ZES_Data("parameter173",370,2),
        new ZES_Data("parameter174",372,2),
        new ZES_Data("parameter175",374,2),
        new ZES_Data("parameter176",376,2),
        new ZES_Data("parameter177",378,2),
        new ZES_Data("parameter178",380,2),
        new ZES_Data("parameter179",382,2),
        new ZES_Data("parameter180",384,2),
        new ZES_Data("parameter181",386,2),
        new ZES_Data("parameter182",388,2),
        new ZES_Data("parameter183",390,2),
        new ZES_Data("parameter184",392,2),
        new ZES_Data("parameter185",394,2),
        new ZES_Data("parameter186",396,2),
        new ZES_Data("parameter187",398,2),
        new ZES_Data("parameter188",400,2),
        new ZES_Data("parameter189",402,2),
        new ZES_Data("parameter190",404,2),
        new ZES_Data("parameter191",406,2),
        new ZES_Data("parameter192",408,2),
        new ZES_Data("parameter193",410,2),
        new ZES_Data("parameter194",412,2),
        new ZES_Data("parameter195",414,2),
        new ZES_Data("parameter196",416,2),
        new ZES_Data("parameter197",418,2),
        new ZES_Data("parameter198",420,2),
        new ZES_Data("parameter199",422,2),
    };
    private static final String ZES_gv_Type = "2";
    private static final String ZES_gv_tableName = "pms_real_data_2";

    public ZES_Type2(long timestamp, byte[] bytes, String ictNumber)
    {
        super(timestamp, bytes, ictNumber);
    }

    @Override
    public ZES_Type2 ZES_saveRealTime()
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
