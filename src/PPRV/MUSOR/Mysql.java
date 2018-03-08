package PPRV.MUSOR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Mysql {

    protected static Connection con = null;
    private static String username = "**********";
    private static String password = "************";
    private static String url = "jdbc:mysql://*********:3306/******";

    public static void connect() throws SQLException, ClassNotFoundException {
        con = null;
        Class.forName("org.sqlite.JDBC");
        con = DriverManager.getConnection("jdbc:sqlite:USERS.s3db");
        System.out.println("База Подключена!");
    }
}