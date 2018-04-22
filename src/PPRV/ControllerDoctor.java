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


public class ControllerDoctor  {

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
    @FXML    TableColumn<BasePatient, String> colId;
    @FXML    TableColumn<BasePatient, String> colUserName;
    @FXML    TableColumn<BasePatient, String> colAge;
    @FXML    TableColumn<BasePatient, String> colLo;
    File file;







    @FXML
    private void initialize() throws Exception {
        ConH2.Conn();
        colId.setCellValueFactory(
                new PropertyValueFactory<BasePatient, String>("userId"));
        colUserName.setCellValueFactory(
                new PropertyValueFactory<BasePatient, String>("userName"));
        colAge.setCellValueFactory(new PropertyValueFactory<BasePatient, String>("userAge"));
        colLo.setCellValueFactory(
                new PropertyValueFactory<BasePatient, String>("userLO"));
        //загрузка операций в выподающий список
        data = FXCollections.observableArrayList();
        ArrayList<String> oper = new ArrayList<>();
        String SQL = "SELECT * from OPERATIONS";
        //ResultSet
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
        while (rs.next()) {
            oper.add(rs.getString("NAME"));
        }
        choiceBox.setItems(FXCollections.observableArrayList(
                oper)
        );

        //перестроение списка
        buildData1();
    }

    public void buildData1() throws SQLException, ClassNotFoundException {
        data2 = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM PATIENT";
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);

        while (rs.next()) {
            BasePatient cm = new BasePatient();
            cm.userId.set(rs.getInt("IDPATIENT"));
            cm.userName.set(rs.getString("NAME"));
            cm.userAge.set(rs.getString("AGE"));
            cm.userLO.set(rs.getString("LASTOPERATION"));
            data2.add(cm);
        }

        tvTEST.setItems(data2);
    }


    public void onClick(ActionEvent actionEvent) throws Exception {

        //id операции
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
            }
        });

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
            case "btnView":
                new Main.InfoPatient(selItem2.userName.getValue(), selItem2.userId.getValue());
        }
    }

    //удаление пациента
    private void delPatient() throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM PATIENT WHERE IDPATIENT = " + idPatient2;
        statmt.executeUpdate(sql);
        data2.clear();
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

                    ResultSet rs = ConH2.conn.createStatement().executeQuery("SELECT * from PATIENT");
                    while (rs.next()) {
                        if (analyzes[0].equals(rs.getString(1))) {
                            patientNew = false;
                        }}

                    ResultSet rs2 = ConH2.conn.createStatement().executeQuery("SELECT * from ANALYSIS");
                    while (rs2.next()) {
                        if (analyzes[2].equals(rs2.getString(1))) {
                            analysisNew = false;
                        }}

                    if (patientNew) {
                        String sql = "INSERT INTO PATIENT VALUES ('" + analyzes[0] + "', '" + analyzes[1] + "', '" + analyzes[4] + "', '" + analyzes[2] + "')";
                        statmt.executeUpdate(sql);
                    }
                    if (analysisNew) {
                        String sql2 = "INSERT INTO ANALYSIS VALUES ('" + analyzes[2] +"', '" + analyzes[0] + "', '" + analyzes[3] + "', '" + analyzes[4] +"', '" + analyzes[5] + "', '" + analyzes[6] +"', '" + analyzes[7] +"', '" + analyzes[8] +"', '" + analyzes[9] +"', '" + analyzes[10] +"')";
                        statmt.executeUpdate(sql2);

                    } else {
                        System.out.println("СУЩ");
                    }

                    data2.clear();
                    buildData1();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
