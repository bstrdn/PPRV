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
        import java.sql.Connection;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.ArrayList;

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
    @FXML private ChoiceBox choiceBox;
    @FXML private TextField tf12;
    public static Statement statmt;
    public static Connection conn;
    public static int idPatient;
    public static int idPatient2;


    @FXML private ObservableList<BasePatient> data2;
//    public static Connection conn;
    @FXML TableColumn<BasePatient, String> colId;
    @FXML TableColumn<BasePatient, String> colUserName;
    @FXML TableColumn<BasePatient, String> colAge;
    @FXML TableColumn<BasePatient, String> colLo;
    @FXML TableColumn<BasePatient, String> colAdd;













    File file;

    @FXML
    private void initialize() throws Exception {
        //label1.setText("I'm a Label.");        //tableview = new TableView();//        choiceBox = new ChoiceBox(FXCollections.observableArrayList(
////                "First", "Second", "Third")      );
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
                System.out.println(rs.getString("OPERATION_NAME"));
           }
        choiceBox.setItems(FXCollections.observableArrayList(
                oper)
        );
        choiceBox.getSelectionModel().select(0);


        //перестроение списка
       // buildData();
        buildData1();
    }

    public void buildData1() throws SQLException, ClassNotFoundException {
        data2 = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM PATIENT";
        String SQL2 = "SELECT\n" +
                "  IDPATIENT, PATIENT.NAME, AGE, OPERATIONS.OPERATION_NAME, FIO\n" +
                "FROM PATIENT, OPERATIONS, USERS\n" +
                "WHERE PATIENT.LASTOPERATION=OPERATIONS.ID AND PATIENT.IDDOCTOR=USERS.ID\n" +
                "ORDER BY IDPATIENT";
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL2);

        while (rs.next()) {
            BasePatient cm = new BasePatient();
            cm.userId.set(rs.getInt("IDPATIENT"));
            cm.userName.set(rs.getString("NAME"));
            cm.userAge.set(rs.getString("AGE"));
            cm.userLO.set(rs.getString("OPERATION_NAME"));
            cm.userAdd.set(rs.getString("FIO"));
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

        //id операции
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                System.out.println(choiceBox.getItems().get((Integer) number2));
                System.out.println(number2);

            }
        });


       // String option = choiceBox.getValue().toString();
       // System.out.println(option);

        Object source = actionEvent.getSource();
        //если нажата не кнопка - выход из метода
        if(!(source instanceof Button)) { return;}

        ConH2.Conn();
        statmt = ConH2.conn.createStatement();

        Button clickedButton = (Button) source;

        //
      //  ObservableList selItem = (ObservableList) tableview.getSelectionModel().getSelectedItem();
     //   ObservableList selItem2 = (ObservableList) tvTEST.getSelectionModel().getSelectedItem();
        BasePatient selItem2 = (BasePatient) tvTEST.getSelectionModel().getSelectedItem();

try {
    //id пациента
  //  idPatient = parseInt(selItem.get(0).toString());
   // idPatient2 = parseInt(selItem2.get(0).toString());
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
            case "btnView":
              //  System.out.println(idPatient2);
                System.out.println(idPatient2);
                new Main.InfoPatient(selItem2.userName.getValue(), selItem2.userId.getValue());
//                break;
        }
    }

    //удаление пациента
    private void delPatient() throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM PATIENT WHERE IDPATIENT = " + idPatient2;
        statmt.executeUpdate(sql);
       // tableview.getColumns().clear();
       // tvTEST.getColumns().clear();
//tvTEST.refresh();
data2.clear();
      //  tableview.getColumns().clear();
      //  buildData();
        buildData1();
    }


    private void loadPatient() throws SQLException, ClassNotFoundException {
        boolean patientNew = true;
        boolean analysisNew = true;
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
        while ((line = br.readLine()) != null) {
            // use comma as separator
            String[] analyzes = line.split(cvsSplitBy);

//            if (analyzes[0].equals())

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
                System.out.println("ID пациента: " + analyzes[0] + " , ФИО: " + analyzes[1]);
                String sql = "INSERT INTO PATIENT VALUES ('" + analyzes[0] + "', '" + analyzes[1] + "', '" + analyzes[4] + "', '" + analyzes[2] + "')";
                statmt.executeUpdate(sql);
            }
                        if (analysisNew) {
//                            System.out.println("ID анализа: " + analyzes[2] + " , ID пациента: " + analyzes[0] + " А1: " + analyzes[4] + " B1: " + analyzes[5]);
                            String sql2 = "INSERT INTO ANALYSIS VALUES ('" + analyzes[2] +"', '" + analyzes[0] + "', '" + analyzes[3] + "', '" + analyzes[4] +"', '" + analyzes[5] + "', '" + analyzes[6] +"', '" + analyzes[7] +"', '" + analyzes[8] +"', '" + analyzes[9] +"', '" + analyzes[10] +"')";
                            statmt.executeUpdate(sql2);



                    } else {
                        System.out.println("СУЩ");
                    }


                    data2.clear();
                buildData1();
//            tableview.getColumns().clear();
//            buildData();
            }

    }
     catch (IOException e) {
        e.printStackTrace();
    }

    }
}

//СТАРАЯ ЗАГРУЗКА ПАЦИЕНТОВ
//    public void buildData() throws SQLException, ClassNotFoundException {
//        ConH2.Conn();
//        data = FXCollections.observableArrayList();
//        try{
//        //    String SQL = "SELECT * from PATIENT WHERE AGE = 2";
//            String SQL = "SELECT * from PATIENT";
//            ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
//            /**********************************
//             * TABLE COLUMN ADDED DYNAMICALLY *
//             **********************************/
//            //for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
//            //СОЗДАНИЕ СТОЛБЦОВ ПЕРЕБОР ПО СТОЛБЦАМ ОТ 0 до Х
//            for(int i=0 ; i<4; i++){
//                //We are using non property style for making dynamic table
//                final int j = i;
//                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
//                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
//                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
//                        return new SimpleStringProperty(param.getValue().get(j).toString());
//                    }
//                });
//                tableview.getColumns().addAll(col);
//
//                System.out.println("Column ["+i+"] ");
//            }
//
//            /********************************
//             * Data added to ObservableList *
//             ********************************/
//            while(rs.next()){
//                //Iterate Row
//                ObservableList<String> row = FXCollections.observableArrayList();
//                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
//                    //Iterate Column
//                    row.add(rs.getString(i));
//                }
//                System.out.println("Row [1] added "+row );
//                data.add(row);
//
//
//            }
//
//            //FINALLY ADDED TO TableView
//            tableview.setItems(data);
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Error on Building Data");
//        }
//    }



}
