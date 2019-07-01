package com.atguigu.mvcapp.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * JDbc操作的工具类
 */

public class JdbcUtils {


    /**
     * 释放connection;
     * @param connection
     */
    public static void releaseConnection(Connection connection) {

        try {
            if (connection != null) {
                connection.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DataSource dataSource = null;


   static {

       //数据源只能被创建一次
       dataSource = new ComboPooledDataSource("mvcapp");

   }

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() throws SQLException {
      return dataSource.getConnection();


    }

}
