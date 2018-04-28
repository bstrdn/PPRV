package PPRV;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class db {

//    static void connect() throws SQLException, ClassNotFoundException {
//        ConH2.Conn();
//        ConH2.CreateDB();
//        //  conn.WriteDB();
//        ConH2.ReadDB();
//        ConH2.CloseDB();
//    }
//
//
//    static int LogIn (String li, String pw) throws SQLException, ClassNotFoundException {
//        ConH2.Conn();
//        int b = 0;
//        boolean isUserExists = false;
//        try (PreparedStatement ps = ConH2.conn.prepareStatement("SELECT `ROLE`, `ID` FROM `USERS` WHERE `LOGIN` = ? AND `PASSWORD` = ?")) {
//            ps.setString(1, li);
//            ps.setString(2, pw);
//            try (ResultSet rs = ps.executeQuery()) {
//                if (rs.next()) {
//                    b = rs.getInt("ROLE");
//
//                    isUserExists = true;
//                }
//            }
//        }
//        if (!isUserExists) {
//            b = 0;
//        }
//return b;
//    }
}