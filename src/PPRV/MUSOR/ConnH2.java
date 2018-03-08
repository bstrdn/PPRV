package PPRV.MUSOR;
import java.sql.*;

public class ConnH2 {
    /**
     * Query that create table.
     */
    private static final String CREATE_QUERY =
            "CREATE TABLE EXAMPLE3 (GREETING VARCHAR(6), TARGET VARCHAR(6))";
    /**
     * Quaery that populates table with data.
     */
    private static final String DATA_QUERY =
            "INSERT INTO EXAMPLE3 VALUES('Привет','World')";

    /**
     * Do not construct me.
     */
    private ConnH2() {
    }

    /**
     * Entry point.    public static void CreateDB() throws ClassNotFoundException, SQLException
     {
     statmt = conn.createStatement();
     statmt.execute("CREATE TABLE if not exists 'admins' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'login' text, 'pw' text);");

     System.out.println("Таблица создана или уже существует.");
     }
     *
     * @param args Commans line args. Not used.
     */
    public static void main(final String[] args) {







        try (Connection db = DriverManager.getConnection("jdbc:h2:./src/db", "C3E*jCmT?2vpN5d", "ak~YrsT%5pX47~s")) {
            try (Statement dataQuery = db.createStatement()) {
                dataQuery.execute(CREATE_QUERY);
                dataQuery.execute(DATA_QUERY);
                System.out.println("КОННЕКТ");
            }

            try (PreparedStatement query =
                         db.prepareStatement("SELECT * FROM EXAMPLE3")) {
                ResultSet rs = query.executeQuery();
                while (rs.next()) {
                    System.out.println(String.format("%s, %s!",
                            rs.getString(1),
                            rs.getString("TARGET")));
                    System.out.println("tst");
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("Database connection failure: "
                    + ex.getMessage());
        }
    }
}
