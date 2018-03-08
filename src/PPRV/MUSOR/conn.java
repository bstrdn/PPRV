package PPRV.MUSOR;

import java.sql.*;
//import javax.crypto.Cipher;

public class conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;

    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName("org.sqlite.JDBC");
        // Class.forName ("org.h2.Driver");
        conn = DriverManager.getConnection("jdbc:sqlite:USERS.s3db");
        // conn = DriverManager.getConnection("jdbc:h2:~/db", "C3E*jCmT?2vpN5d", "ak~YrsT%5pX47~s");
        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'admins' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'pw' text);");

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        statmt.execute("INSERT INTO 'admins' ('login', 'pw') VALUES ('Petya', 123); ");
        statmt.execute("INSERT INTO 'admins' ('login', 'pw') VALUES ('Vasya', 234); ");
        statmt.execute("INSERT INTO 'admins' ('login', 'pw') VALUES ('Masha', 345); ");

        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        resSet = statmt.executeQuery("SELECT * FROM admins");

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  login = resSet.getString("login");
            String  phone = resSet.getString("pw");
            System.out.println( "ID = " + id );
            System.out.println( "login = " + login );
            System.out.println( "pw = " + phone );
            System.out.println();
        }

        System.out.println("Таблица выведена");
    }

    // --------Закрытие--------
    public static void CloseDB() throws ClassNotFoundException, SQLException
    {
        conn.close();
       // statmt.close();
        resSet.close();

        System.out.println("Соединения закрыты");
    }


}