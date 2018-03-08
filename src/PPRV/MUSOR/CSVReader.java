package PPRV.MUSOR;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void main(String[] args) {

        String csvFile = "C:\\Users\\dntst\\Desktop\\проект\\test1.csv";
        String line = "";
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] analyzes = line.split(cvsSplitBy);

                System.out.println("Имя: " + analyzes[0] + " , Уникальный id: " + analyzes[1]);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}