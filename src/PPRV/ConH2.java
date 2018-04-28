package PPRV;

import java.sql.*;
//import javax.crypto.Cipher;

public class ConH2 {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:./src/db";
    static final String USER = "admin";
    static final String PASS = "12345";




    // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
    public static void Conn() throws ClassNotFoundException, SQLException
    {
        conn = null;
        Class.forName (JDBC_DRIVER);
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void CreateDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        String sql =  "CREATE TABLE   USERS " + "(id INTEGER not NULL, " +
                " login VARCHAR(255), " + " password VARCHAR(255), " +
                " role INTEGER, " + " PRIMARY KEY ( id ))";
        statmt.executeUpdate(sql);

        System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void WriteDB() throws SQLException
    {
        statmt = conn.createStatement();
        String sql = "INSERT INTO USERS " + "VALUES (1, 'Admin', 'Admin', 1)";
        statmt.executeUpdate(sql);
        sql = "INSERT INTO USERS " + "VALUES (2, 'Chief ', '12345', 2)";
        statmt.executeUpdate(sql);
        sql = "INSERT INTO USERS " + "VALUES (3, 'Doctor', '12345', 3)";
        statmt.executeUpdate(sql);
        System.out.println("Таблица заполнена");
    }

    // -------- Вывод таблицы--------
    public static void ReadDB() throws ClassNotFoundException, SQLException
    {
        statmt = conn.createStatement();
        String sql = "SELECT id, login, password, role FROM users";
        resSet = statmt.executeQuery(sql);

        while(resSet.next())
        {
            int id = resSet.getInt("id");
            String  login = resSet.getString("login");
            String  password = resSet.getString("password");
            int role = resSet.getInt("role");
            System.out.println( "ID = " + id );
            System.out.println( "login = " + login );
            System.out.println( "password = " + password );
            System.out.println( "role = " + role );
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

//    public static void main(String[] args) throws SQLException, ClassNotFoundException {
//        try {
//            Conn();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//       // CreateDB();
//       // WriteDB();
//        ReadDB();
//    }



//        static void connect() throws SQLException, ClassNotFoundException {
//            ConH2.Conn();
//            ConH2.CreateDB();
//            //  conn.WriteDB();
//            ConH2.ReadDB();
//            ConH2.CloseDB();
//        }


        static int LogIn (String li, String pw) throws SQLException, ClassNotFoundException {
            ConH2.Conn();
            int b = 0;
            boolean isUserExists = false;
            try (PreparedStatement ps = ConH2.conn.prepareStatement("SELECT `ROLE`, `ID` FROM `USERS` WHERE `LOGIN` = ? AND `PASSWORD` = ?")) {
                ps.setString(1, li);
                ps.setString(2, pw);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        b = rs.getInt("ROLE");
                        Controller.id = rs.getInt("ID");
                        isUserExists = true;
                    }
                }
            }
            if (!isUserExists) {
                b = 0;
            }
            return b;
        }

}