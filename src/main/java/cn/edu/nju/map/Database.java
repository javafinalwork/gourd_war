package cn.edu.nju.map;

import java.sql.*;

public class Database {
    public Connection dbconnection;
    public Connection getDbconnection()
    {
        String dbname="homework_1";
        String dbuser="root";
        String dbpassword="njbk";
        String url="jdbc:mysql://localhost:3306/"+dbname+"?useUnicode=true&characterEncoding=utf8";;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("连接中......");
            dbconnection=DriverManager.getConnection(url,dbuser,dbpassword);
            System.out.println("连接成功。");
        }catch (Exception e )
        {
            System.out.println("连接失败。");
            e.printStackTrace();
            e.getCause();
        }
        return dbconnection;
    }

}
