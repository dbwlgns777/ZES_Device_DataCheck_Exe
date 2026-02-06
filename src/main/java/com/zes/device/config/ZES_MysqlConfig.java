package com.zes.device.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ZES_MysqlConfig
{
    private static final HikariConfig config = new HikariConfig("/config/hikari.properties");
    private static final HikariDataSource ds;

    static
    {
        ds = new HikariDataSource( config );
    }

    private ZES_MysqlConfig() {}
    public static Connection getConnection() throws SQLException
    {
        return ds.getConnection();
    }

}
