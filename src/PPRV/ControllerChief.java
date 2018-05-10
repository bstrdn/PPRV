package PPRV;

        import javafx.beans.property.SimpleIntegerProperty;
        import javafx.beans.property.SimpleStringProperty;
        import javafx.beans.value.ChangeListener;
        import javafx.beans.value.ObservableValue;
        import javafx.collections.FXCollections;
        import javafx.collections.ObservableList;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.*;
        import javafx.scene.control.Button;
        import javafx.scene.control.Label;
        import javafx.scene.control.TextField;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.paint.Color;
        import javafx.scene.text.Text;
        import javafx.stage.FileChooser;
        import javafx.stage.Stage;
        import javafx.util.Callback;

        import javax.swing.*;
        import java.awt.*;
        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;
        import java.sql.*;
        import java.util.ArrayList;
        import java.util.Arrays;

        import static java.awt.geom.Arc2D.OPEN;
        import static java.lang.Integer.parseInt;
        import static java.sql.JDBCType.TIME;
        import static java.util.Calendar.DATE;
        import static javax.print.attribute.standard.PrintQuality.HIGH;
        import static javax.sound.sampled.FloatControl.Type.VOLUME;
        import static javax.sound.sampled.LineEvent.Type.CLOSE;
        import static jdk.management.resource.ResourceAccuracy.LOW;


public class ControllerChief  {

    @FXML private TableView tvTEST;
    @FXML private Label label1;
    @FXML private TableView tableview;
    @FXML private ObservableList<ObservableList> data;
    @FXML private Button btnLoad;
    @FXML private Button btnLoad2;
    @FXML private ChoiceBox choiceBox;
    @FXML private TextField tf12;
    @FXML private TextField tfFIO;
    public static Statement statmt;
    public static Connection conn;
    public static int idPatient;
    public static int idPatient2;
    String[] analyzes;
    int idOper = 0;


    @FXML private ObservableList<BasePatient> data2;
    @FXML TableColumn<BasePatient, String> colId;
    @FXML TableColumn<BasePatient, String> colUserName;
    @FXML TableColumn<BasePatient, String> colAge;
    @FXML TableColumn<BasePatient, String> colLo;
    @FXML TableColumn<BasePatient, String> colAdd;




    File file;

    @FXML
    private void initialize() throws Exception {
        ConH2.Conn();
        colId.setCellValueFactory(new PropertyValueFactory<BasePatient, String>("userId"));
        colUserName.setCellValueFactory(new PropertyValueFactory<BasePatient, String>("userName"));
        colAge.setCellValueFactory(new PropertyValueFactory<BasePatient, String>("userAge"));
        colLo.setCellValueFactory(new PropertyValueFactory<BasePatient, String>("userLO"));
        colAdd.setCellValueFactory(new PropertyValueFactory<BasePatient, String>("userAdd"));


        //загрузка операций в выподающий список
        data = FXCollections.observableArrayList();
        ArrayList<String> oper = new ArrayList<>();
            String SQL = "SELECT * from OPERATIONS";
            //ResultSet
            ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
           while (rs.next()) {
                oper.add(rs.getString("OPERATION_NAME"));
               // System.out.println(rs.getString("OPERATION_NAME"));
           }
        choiceBox.setItems(FXCollections.observableArrayList(
                oper)
        );
        choiceBox.getSelectionModel().select(0);

        //Достаем id операции
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                idOper =(Integer) number2;
            }
        });

        //перестроение списка
        buildData1();

       // test(2,1);
      //  test2(2);


    }

    public void buildData1() throws SQLException {
        data2 = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM PATIENT";
        String SQL2 = "SELECT\n" +
                "  ID_PATIENT, PPRV_PATIENT.FIO, AGE, SEX, OPERATION_NAME, USERS.FIO\n" +
                "FROM PPRV_PATIENT, OPERATIONS, USERS\n" +
                "WHERE PPRV_PATIENT.LAST_OPERATION=OPERATIONS.ID AND PPRV_PATIENT.ID_DOCTOR=USERS.ID\n" +
                "ORDER BY ID_PATIENT";
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL2);

        while (rs.next()) {
            BasePatient cm = new BasePatient();
            cm.userId.set(rs.getInt("ID_PATIENT"));
            cm.userName.set(rs.getString("PPRV_PATIENT.FIO"));
            cm.userAge.set(rs.getString("AGE"));
            cm.userLO.set(rs.getString("OPERATION_NAME"));
            cm.userAdd.set(rs.getString("USERS.FIO"));
            data2.add(cm);
        }

        colAge.setCellFactory(column -> {
            return new TableCell<BasePatient, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format date.
                        setText(item);
                        // Style all dates in March with a different color.
                        if (item.equals("2")) {
                            setTextFill(javafx.scene.paint.Color.CHOCOLATE);
                            setStyle("-fx-background-color: yellow");
                        } else {
                            setTextFill(Color.BLACK);
                            setStyle("");
                        }
                    }
                }
            };
        });

      tvTEST.setItems(data2);
    }



    public void onClick(ActionEvent actionEvent) throws Exception {

        Object source = actionEvent.getSource();
        //если нажата не кнопка - выход из метода
        if(!(source instanceof Button)) { return;}

        ConH2.Conn();
        statmt = ConH2.conn.createStatement();

        Button clickedButton = (Button) source;

        BasePatient selItem2 = (BasePatient) tvTEST.getSelectionModel().getSelectedItem();

try {

    idPatient2 = selItem2.userId.getValue();
}
catch (Exception e) {
    System.out.println("айди не получен");
}

        switch (clickedButton.getId()) {
            case  "btnLoad":
                loadPatient();
                break;
            case "btnDel":
                delPatient();
                break;
            case "btnLoad2":
                loadPatient3();
                break;
            case "btnView":
                System.out.println(idPatient2);
                new Main.InfoPatient(selItem2.userName.getValue(), selItem2.userId.getValue());
                break;
        }
    }

    //удаление пациента
    private void delPatient() throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM PPRV_PATIENT WHERE ID_PATIENT = " + idPatient2;
        statmt.executeUpdate(sql);
        data2.clear();
        buildData1();
    }


    private void loadPatient() throws SQLException, ClassNotFoundException {

        FileChooser fileChooser = new FileChooser();
        //фильтр по типу файла
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);
        //диалог открытия файла
        file = fileChooser.showOpenDialog(null);
    if (!(file == null)) {
        String csvFile = file.getPath();
        String line = "";
        String cvsSplitBy = ",";
        tf12.setText(csvFile);

    try (BufferedReader br = new BufferedReader(new FileReader(tf12.getText()))) {
        //раньше считывал каждую строчку..  //     while ((line = br.readLine()) != null) {
            // use comma as separator
            line = br.readLine(); // + ",0";
            analyzes = line.split(cvsSplitBy);

        System.out.println(analyzes[3]);
        System.out.println(analyzes[4]);
        System.out.println(analyzes[5]);
        System.out.println(analyzes[6]);

            tfFIO.setText(analyzes[1]);

            }
     catch (IOException e) {
        e.printStackTrace();
    }

    }
}

    private void loadPatient2() throws Exception {
    boolean patientNew = true;
    boolean analysisNew = true;
    if (analyzes != null) {
        analyzes[1] = tfFIO.getText();
        int idoper = idOper + 1;
        analyzes[11] = idoper + "";


    ResultSet rs = ConH2.conn.createStatement().executeQuery("SELECT * from PATIENT");
    while (rs.next()) {
        if (analyzes[0].equals(rs.getString(1))) {
            patientNew = false;
//                    System.out.println("СУЩЕСТВУЕТ");
        }}

    ResultSet rs2 = ConH2.conn.createStatement().executeQuery("SELECT * from ANALYSIS");
    while (rs2.next()) {
        if (analyzes[2].equals(rs2.getString(1))) {
            analysisNew = false;
            System.out.println("СУЩЕСТВУЕТ");
        }}



    if (patientNew) {
       // System.out.println("ID пациента: " + analyzes[0] + " , ФИО: " + analyzes[1]);
        String sql = "INSERT INTO PATIENT VALUES ('" + analyzes[0] + "', '" + analyzes[1] + "', '" + analyzes[4] + "', '" + (idOper + 1) + "', '" + Controller.id +"')";

        statmt.executeUpdate(sql);
    }
    if (analysisNew) {
//                            System.out.println("ID анализа: " + analyzes[2] + " , ID пациента: " + analyzes[0] + " А1: " + analyzes[4] + " B1: " + analyzes[5]);
        String sql2 = "INSERT INTO ANALYSIS VALUES ('" + analyzes[2] +"', '" + analyzes[0] + "', '" + analyzes[3] + "', '" + analyzes[4] +"', '" + analyzes[5] + "', '" + analyzes[6] +"', '" +  analyzes[8] +"', '" + analyzes[9] +"', '" + analyzes[10] + "', '" + analyzes[11] + "', '" + (idOper + 1) +"', '" + analyzes[7] + "')";
        statmt.executeUpdate(sql2);

        new pprv(analyzes);


    } else {
        new Main.info("Данные анализы уже загружены в систему.");
    }
    data2.clear();
    buildData1();
}
    else {
            new Main.info("Не выбран файл!");
        }
    }






    private void loadPatient3() throws Exception {
        boolean patientNew = true;
        boolean analysisNew = true;
        if (analyzes != null) {
            analyzes[1] = tfFIO.getText();
            int idoper = idOper + 1;
            //analyzes[6] = idoper + "";


            ResultSet rs = ConH2.conn.createStatement().executeQuery("SELECT * from PPRV_PATIENT");
            while (rs.next()) {
                if (analyzes[0].equals(rs.getString(1))) {
                    patientNew = false;
                    System.out.println("СУЩЕСТВУЕТ ПАЦИЕНТ");
                    break;
                }}

            ResultSet rs2 = ConH2.conn.createStatement().executeQuery("SELECT * from PPRV_ANALISIS");
            while (rs2.next()) {
                if (analyzes[3].equals(rs2.getString(1))) {
                    analysisNew = false;
                    System.out.println("СУЩЕСТВУЕТ АНАЛИЗ");
                    break;
                }}


            if (patientNew) {
                // System.out.println("ID пациента: " + analyzes[0] + " , ФИО: " + analyzes[1]);
                String sql = "INSERT INTO PPRV_PATIENT VALUES ('" + analyzes[0] + "', '" + analyzes[1] + "', '" + analyzes[5] + "', '" + analyzes[2] +"', '" + (idOper + 1) + "', '" + Controller.id +"')";
                statmt.executeUpdate(sql);
            }
            if (analysisNew) {
//                            System.out.println("ID анализа: " + analyzes[2] + " , ID пациента: " + analyzes[0] + " А1: " + analyzes[4] + " B1: " + analyzes[5]);
               // String sql2 = "INSERT INTO PPRV_ANALISIS VALUES ('" + analyzes[3] +"', '" + analyzes[4] + "', '" + analyzes[5] + "', '" + analyzes[6]  + "')";
                //statmt.executeUpdate(sql2);

                String a = "";
                for (int i = 6; i <= analyzes.length; i++) {
                    a += ",'" + analyzes[i-1] + "'";
                }
               // System.out.println("INSERT INTO PPRV_ANALISIS VALUES ('" + analyzes[3]  + "'" + a +")");
               // System.out.println(a);
                statmt.executeUpdate("INSERT INTO PPRV_ANALISIS VALUES ('" + analyzes[3]  + "','" + analyzes[0] + "','" + analyzes[4] + "'"+ a +")");

                //ОБРАБОТКА АНАЛИЗОВ
             //   new pprv(analyzes);
                test(Integer.parseInt(analyzes[3]), Integer.parseInt(analyzes[2]));


            } else {
                new Main.info("Данные анализы уже загружены в систему.");
            }
            data2.clear();
            buildData1();
        }
        else {
            new Main.info("Не выбран файл!");
        }
    }


















    //TEST
   void test (int id_study, int sex) throws SQLException, ClassNotFoundException {
       ConH2.Conn();
       Statement statement = ConH2.conn.createStatement();
       String sqlq = "INSERT INTO PPRV_ANALISIS_RESULT (ID_STUDY) VALUES (" + id_study + ")";
       statement.executeUpdate(sqlq);
       boolean r;
       String SQL_A = "SELECT * from PPRV_ANALISIS WHERE ID_STUDY =" + id_study;
       String SQL_N = "SELECT * from PPRV_ANALISIS_NORM";
       ResultSet rs_a = ConH2.conn.createStatement().executeQuery(SQL_A);
       ResultSet rs_n = ConH2.conn.createStatement().executeQuery(SQL_N);
       rs_n.next();
       rs_a.next();

       System.out.println("КОРОЧЕ " + rs_a.getInt(4));
       System.out.println("КОРОЧЕ " + rs_a.getInt(5));
       System.out.println("КОРОЧЕ " + rs_a.getInt(6));


           for (int i = 4; i <= rs_a.getMetaData().getColumnCount(); i++) {

               int study = rs_a.getInt(i);

               String columnName = rs_n.getMetaData().getColumnName(i-3);
               int[] arr = Arrays.stream(rs_n.getString(i-3).substring(0, rs_n.getString(i-3).length()).split(","))
                       .map(String::trim).mapToInt(Integer::parseInt).toArray();
               System.out.println("значение " + study);
               System.out.println("меньше " + arr[1] + " и больше " + arr[0]);
               if (sex == 0) {
                  r = study < arr[1] && study > arr[0] ? true : false;
               }
                else {
                  r = study < arr[2] && study > arr[3] ? true : false;
               }
               System.out.println("результат " + r);

               String sql2 = "UPDATE PPRV_ANALISIS_RESULT SET " + columnName +" = " + r + " WHERE ID_STUDY = " + id_study;
               statement.executeUpdate(sql2);
               }
       }










       void test2 (int id_study) throws SQLException {
           ResultSet rs = ConH2.conn.createStatement().executeQuery("SELECT * from PPRV_ANALISIS_RESULT WHERE ID_STUDY =" + id_study);
           rs.next();




           for (int i = 2; i <= rs.getMetaData().getColumnCount(); i++) {
               System.out.println(rs.getMetaData().getColumnName(i));
               System.out.println(rs.getBoolean(i));

           }
       }
   }    // endTEST


