package PPRV.MUSOR;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckUser extends Mysql {
    public boolean checkUser(String name, String password) throws SQLException, NoSuchAlgorithmException {

        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        boolean check = false;

        try {
            preparedStatement = con.prepareStatement("SELECT login, pw FROM admins");
            rs = preparedStatement.executeQuery();

            // Check return data
            if (rs.next()) {
                if (name.equals(rs.getString("login"))) {
                    if ((name.equals(rs.getString("pw")))) {
                        check = true;
                    } else {
                        check = false;
                    }
                } else {
                    check = false;
                }
            }
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException se) {
            }
            if (preparedStatement != null) try {
                preparedStatement.close();
            } catch (SQLException se) {
            }
            if (con != null) try {
                con.close();
            } catch (SQLException se) {
            }
        }
        return check;
    }


}