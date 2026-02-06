package com.zes.device.models;

import com.zes.device.ZES_SQLGenerator;
import com.zes.device.config.ZES_MysqlConfig;

import java.sql.*;

public class ZES_Type1 extends ZES_TypeMysqlDB
{
    private static ZES_Data[] ZES_gv_DATA_MAP =
    {
        new ZES_Data("flag",20, 4),
        new ZES_Data("load_160_l_rd",24, 2),
        new ZES_Data("load_160_r_rd",26, 2),
        new ZES_Data("load_160_l_ton",28, 2),
        new ZES_Data("load_160_r_ton",30, 2),
        new ZES_Data("load_161_l_rd",32, 2),
        new ZES_Data("load_161_r_rd",34, 2),
        new ZES_Data("load_161_l_ton",36, 2),
        new ZES_Data("load_161_r_ton",38, 2),
        new ZES_Data("load_162_l_rd",40, 2),
        new ZES_Data("load_162_r_rd",42, 2),
        new ZES_Data("load_162_l_ton",44, 2),
        new ZES_Data("load_162_r_ton",46, 2),
        new ZES_Data("load_163_l_rd",48, 2),
        new ZES_Data("load_163_r_rd",50, 2),
        new ZES_Data("load_163_l_ton",52, 2),
        new ZES_Data("load_163_r_ton",54, 2),
        new ZES_Data("load_164_l_rd",56, 2),
        new ZES_Data("load_164_r_rd",58, 2),
        new ZES_Data("load_164_l_ton",60, 2),
        new ZES_Data("load_164_r_ton",62, 2),
        new ZES_Data("load_165_l_rd",64, 2),
        new ZES_Data("load_165_r_rd",66, 2),
        new ZES_Data("load_165_l_ton",68, 2),
        new ZES_Data("load_165_r_ton",70, 2),
        new ZES_Data("load_166_l_rd",72, 2),
        new ZES_Data("load_166_r_rd",74, 2),
        new ZES_Data("load_166_l_ton",76, 2),
        new ZES_Data("load_166_r_ton",78, 2),
        new ZES_Data("load_167_l_rd",80, 2),
        new ZES_Data("load_167_r_rd",82, 2),
        new ZES_Data("load_167_l_ton",84, 2),
        new ZES_Data("load_167_r_ton",86, 2),
        new ZES_Data("load_168_l_rd",88, 2),
        new ZES_Data("load_168_r_rd",90, 2),
        new ZES_Data("load_168_l_ton",92, 2),
        new ZES_Data("load_168_r_ton",94, 2),
        new ZES_Data("load_169_l_rd",96, 2),
        new ZES_Data("load_169_r_rd",98, 2),
        new ZES_Data("load_169_l_ton",100, 2),
        new ZES_Data("load_169_r_ton",102, 2),
        new ZES_Data("load_170_l_rd",104, 2),
        new ZES_Data("load_170_r_rd",106, 2),
        new ZES_Data("load_170_l_ton",108, 2),
        new ZES_Data("load_170_r_ton",110, 2),
        new ZES_Data("load_171_l_rd",112, 2),
        new ZES_Data("load_171_r_rd",114, 2),
        new ZES_Data("load_171_l_ton",116, 2),
        new ZES_Data("load_171_r_ton",118, 2),
        new ZES_Data("load_172_l_rd",120, 2),
        new ZES_Data("load_172_r_rd",122, 2),
        new ZES_Data("load_172_l_ton",124, 2),
        new ZES_Data("load_172_r_ton",126, 2),
        new ZES_Data("load_173_l_rd",128, 2),
        new ZES_Data("load_173_r_rd",130, 2),
        new ZES_Data("load_173_l_ton",132, 2),
        new ZES_Data("load_173_r_ton",134, 2),
        new ZES_Data("load_174_l_rd",136, 2),
        new ZES_Data("load_174_r_rd",138, 2),
        new ZES_Data("load_174_l_ton",140, 2),
        new ZES_Data("load_174_r_ton",142, 2),
        new ZES_Data("load_175_l_rd",144, 2),
        new ZES_Data("load_175_r_rd",146, 2),
        new ZES_Data("load_175_l_ton",148, 2),
        new ZES_Data("load_175_r_ton",150, 2),
        new ZES_Data("load_176_l_rd",152, 2),
        new ZES_Data("load_176_r_rd",154, 2),
        new ZES_Data("load_176_l_ton",156, 2),
        new ZES_Data("load_176_r_ton",158, 2),
        new ZES_Data("load_177_l_rd",160, 2),
        new ZES_Data("load_177_r_rd",162, 2),
        new ZES_Data("load_177_l_ton",164, 2),
        new ZES_Data("load_177_r_ton",166, 2),
        new ZES_Data("load_178_l_rd",168, 2),
        new ZES_Data("load_178_r_rd",170, 2),
        new ZES_Data("load_178_l_ton",172, 2),
        new ZES_Data("load_178_r_ton",174, 2),
        new ZES_Data("load_179_l_rd",176, 2),
        new ZES_Data("load_179_r_rd",178, 2),
        new ZES_Data("load_179_l_ton",180, 2),
        new ZES_Data("load_179_r_ton",182, 2),
        new ZES_Data("load_180_l_rd",184, 2),
        new ZES_Data("load_180_r_rd",186, 2),
        new ZES_Data("load_180_l_ton",188, 2),
        new ZES_Data("load_180_r_ton",190, 2),
        new ZES_Data("load_181_l_rd",192, 2),
        new ZES_Data("load_181_r_rd",194, 2),
        new ZES_Data("load_181_l_ton",196, 2),
        new ZES_Data("load_181_r_ton",198, 2),
        new ZES_Data("load_182_l_rd",200, 2),
        new ZES_Data("load_182_r_rd",202, 2),
        new ZES_Data("load_182_l_ton",204, 2),
        new ZES_Data("load_182_r_ton",206, 2),
        new ZES_Data("load_183_l_rd",208, 2),
        new ZES_Data("load_183_r_rd",210, 2),
        new ZES_Data("load_183_l_ton",212, 2),
        new ZES_Data("load_183_r_ton",214, 2),
        new ZES_Data("load_184_l_rd",216, 2),
        new ZES_Data("load_184_r_rd",218, 2),
        new ZES_Data("load_184_l_ton",220, 2),
        new ZES_Data("load_184_r_ton",222, 2),
        new ZES_Data("load_185_l_rd",224, 2),
        new ZES_Data("load_185_r_rd",226, 2),
        new ZES_Data("load_185_l_ton",228, 2),
        new ZES_Data("load_185_r_ton",230, 2),
        new ZES_Data("load_186_l_rd",232, 2),
        new ZES_Data("load_186_r_rd",234, 2),
        new ZES_Data("load_186_l_ton",236, 2),
        new ZES_Data("load_186_r_ton",238, 2),
        new ZES_Data("load_187_l_rd",240, 2),
        new ZES_Data("load_187_r_rd",242, 2),
        new ZES_Data("load_187_l_ton",244, 2),
        new ZES_Data("load_187_r_ton",246, 2),
        new ZES_Data("load_188_l_rd",248, 2),
        new ZES_Data("load_188_r_rd",250, 2),
        new ZES_Data("load_188_l_ton",252, 2),
        new ZES_Data("load_188_r_ton",254, 2),
        new ZES_Data("load_189_l_rd",256, 2),
        new ZES_Data("load_189_r_rd",258, 2),
        new ZES_Data("load_189_l_ton",260, 2),
        new ZES_Data("load_189_r_ton",262, 2),
        new ZES_Data("load_190_l_rd",264, 2),
        new ZES_Data("load_190_r_rd",266, 2),
        new ZES_Data("load_190_l_ton",268, 2),
        new ZES_Data("load_190_r_ton",270, 2),
        new ZES_Data("load_191_l_rd",272, 2),
        new ZES_Data("load_191_r_rd",274, 2),
        new ZES_Data("load_191_l_ton",276, 2),
        new ZES_Data("load_191_r_ton",278, 2),
        new ZES_Data("load_192_l_rd",280, 2),
        new ZES_Data("load_192_r_rd",282, 2),
        new ZES_Data("load_192_l_ton",284, 2),
        new ZES_Data("load_192_r_ton",286, 2),
        new ZES_Data("load_193_l_rd",288, 2),
        new ZES_Data("load_193_r_rd",290, 2),
        new ZES_Data("load_193_l_ton",292, 2),
        new ZES_Data("load_193_r_ton",294, 2),
        new ZES_Data("load_194_l_rd",296, 2),
        new ZES_Data("load_194_r_rd",298, 2),
        new ZES_Data("load_194_l_ton",300, 2),
        new ZES_Data("load_194_r_ton",302, 2),
        new ZES_Data("load_195_l_rd",304, 2),
        new ZES_Data("load_195_r_rd",306, 2),
        new ZES_Data("load_195_l_ton",308, 2),
        new ZES_Data("load_195_r_ton",310, 2),
        new ZES_Data("load_196_l_rd",312, 2),
        new ZES_Data("load_196_r_rd",314, 2),
        new ZES_Data("load_196_l_ton",316, 2),
        new ZES_Data("load_196_r_ton",318, 2),
        new ZES_Data("load_197_l_rd",320, 2),
        new ZES_Data("load_197_r_rd",322, 2),
        new ZES_Data("load_197_l_ton",324, 2),
        new ZES_Data("load_197_r_ton",326, 2),
        new ZES_Data("load_198_l_rd",328, 2),
        new ZES_Data("load_198_r_rd",330, 2),
        new ZES_Data("load_198_l_ton",332, 2),
        new ZES_Data("load_198_r_ton",334, 2),
        new ZES_Data("load_199_l_rd",336, 2),
        new ZES_Data("load_199_r_rd",338, 2),
        new ZES_Data("load_199_l_ton",340, 2),
        new ZES_Data("load_199_r_ton",342, 2),
        new ZES_Data("load_200_l_rd",344, 2),
        new ZES_Data("load_200_r_rd",346, 2),
        new ZES_Data("load_200_l_ton",348, 2),
        new ZES_Data("load_200_r_ton",350, 2),
        new ZES_Data("load_energy",352, 4)
    };
    private static final String ZES_gv_Type = "1";
    private static final String ZES_gv_tableName = "pms_real_data_1";

    public ZES_Type1(long timestamp, byte[] bytes, String ictNumber)
    {
        super(timestamp, bytes, ictNumber);
    }

    @Override
    public ZES_Type1 ZES_saveRealTime()
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
