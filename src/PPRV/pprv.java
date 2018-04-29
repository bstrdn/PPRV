package PPRV;

public class pprv {
   public static String[] analyses = new String[12];


    pprv(String[] analyzes) {
      if (analyzes[11].equals("1")) {
            analyses[4] = Integer.parseInt(analyzes[4]) < 16 ? "Операцию нельзя проводить в таком возрасте." : "Замечаний нет.";
            analyses[5] = Integer.parseInt(analyzes[5]) < 30 && Integer.parseInt(analyzes[5]) > 100 ? "Вес пациента отклонен от нормы, решение должен принять главный врач." : "Замечаний нет.";
            analyses[5] = Integer.parseInt(analyzes[5]) < 30 && Integer.parseInt(analyzes[5]) > 100 ? "Вес пациента отклонен от нормы, решение должен принять главный врач." : "Замечаний нет.";



            System.out.println(analyses[4] + analyses[5]);

      }


    }
}
