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

          //Рост
          analyses[6] = "Рост: " + analyzes[6] + ". ";
          if (Integer.parseInt(analyzes[6]) < 100 || Integer.parseInt(analyzes[6]) > 220)
               analyses[6] += "Проводить операцию на усмотрение главного врача. Рост не соответствует норме.";
          else analyses[6] += "Замечаний нет.";

          //Пол
          analyses[7] = "Пол: " + analyzes[7] + ". ";
          if (analyzes[7].equals("0")) {
              if (analyzes[11].equals("4") || analyzes[11].equals("10"))
                  analyses[7] += "Операция проводится только женщинам.";
              else analyses[7] += "Замечаний нет.";
          }
          else analyses[7] += "Замечаний нет.";

          //B1 – анализ крови на тромбоциты
          analyses[8] = "Показатель тромбоцитов в крови: " + analyzes[8] + "тыс./мкл. ";
          if (analyzes[7].equals("0")) {
              if (Integer.parseInt(analyzes[8]) < 180 || Integer.parseInt(analyzes[8]) > 320)
                  analyses[8] += "Операцию проводить запрещено, показатель тромбоцитов в крови отклонен от нормы.";
              else analyses[8] += "Замечаний нет.";
          }
          else {
              if (Integer.parseInt(analyzes[8]) < 150 || Integer.parseInt(analyzes[8]) > 380)
                  analyses[8] += "Операцию проводить запрещено, показатель тромбоцитов в крови отклонен от нормы.";
              else analyses[8] += "Замечаний нет.";
          }

            //С1 – время свертываемости. по методу Дуке  60-180
          analyses[9] = "Время свертываемости: " + analyzes[9] + ". ";
          if (Integer.parseInt(analyzes[9]) < 60 || Integer.parseInt(analyzes[9]) > 180)
              analyses[9] += "Операцию проводить запрещено, показатель откловен от нормы.";
          else analyses[9] += "Замечаний нет.";

          //С2 – время свертываемости. по методу Дуке  60-180
          analyses[10] = "Время свертываемости: " + analyzes[10] + ". ";
          if (Integer.parseInt(analyzes[10]) < 60 || Integer.parseInt(analyzes[10]) > 180)
              analyses[10] += "Операцию проводить запрещено, показатель откловен от нормы.";
          else analyses[10] += "Замечаний нет.";

          //D1 – Определение группы крови и резус фактора
          analyses[11] = "Группа крови: " + analyzes[11] + "Замечаний нет. ";


          System.out.println(analyses[4] + analyses[5]);

      }

        String SQL = "INSERT INTO COMMENT VALUES (" + analyzes[2] + ", '" + analyses[4] + "', '" + analyses[5] + "', '" + analyses[6] +"', '" + analyses[7] +"', '" + analyses[8] +"', '" + analyses[9] +"', '" + analyses[10] +"', '" + analyses[11] +"')";
        ConH2.conn.createStatement().executeUpdate(SQL);
    }
}
