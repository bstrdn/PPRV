package PPRV;

import java.sql.ResultSet;
import java.sql.SQLException;

public class pprv {
   public static String[] analyses = new String[12];


    pprv(String[] analyzes) throws SQLException, ClassNotFoundException {
        ConH2.Conn();

        // Операция 1
      if (analyzes[11].equals("1")) {
          //Возраст
          analyses[4] = "Возраст: " + analyzes[4] + ". ";
          if (Integer.parseInt(analyzes[4]) < 18 || Integer.parseInt(analyzes[4]) > 80)
              analyses[4] += "Проводить операцию на усмотрение главного врача. Возраст не соответствует норме.";
          else analyses[4] += "Замечаний нет.";

          //Вес
          analyses[5] = "Вес: " + analyzes[5] + ". ";
          if (Integer.parseInt(analyzes[5]) < 30 || Integer.parseInt(analyzes[5]) > 100)
              analyses[5] += "Проводить операцию на усмотрение главного врача. Вес не соответствует норме.";
          else analyses[5] += "Замечаний нет.";




            System.out.println(analyses[4] + analyses[5]);

      }

        String SQL = "INSERT INTO COMMENT VALUES (" + analyzes[2] + ", '" + analyses[4] + "', '" + analyses[5] + "')";
        ConH2.conn.createStatement().executeUpdate(SQL);
    }
}
