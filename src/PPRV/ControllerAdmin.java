package PPRV;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class ControllerAdmin {
    @FXML private ObservableList<ObservableList> data;
    @FXML private ObservableList<BasePatient> data2;
    @FXML private TextField tfLogin;
    @FXML private TextField tfPass;
    @FXML private TextField tfFIO;
    @FXML TableColumn<BasePatient, String> colId;
    @FXML TableColumn<BasePatient, String> colUserName;
    @FXML TableColumn<BasePatient, String> colAge;
    @FXML TableColumn<BasePatient, String> colLo;
    @FXML private ChoiceBox choiceBox;
    @FXML private TableView tvTEST;
    private int idRol;
    public static int UserID;
    public static Statement statmt;






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


        data = FXCollections.observableArrayList();
        ArrayList<String> oper = new ArrayList<>();
        String SQL = "SELECT * from ROLE";
        //ResultSet
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
        while (rs.next()) {
            oper.add(rs.getString("TITLE"));
        }
        choiceBox.setItems(FXCollections.observableArrayList(
                oper)
        );
        choiceBox.getSelectionModel().select(0);

        //Достаем id Роли
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                idRol =(Integer) number2;
            }
        });

        buildData1();
    }









    public void buildData1() throws SQLException, ClassNotFoundException {
        data2 = FXCollections.observableArrayList();
        String SQL = "SELECT USERS.ID, FIO, LOGIN,  TITLE\n" +
                     "FROM USERS, ROLE\n" +
                     "WHERE USERS.ROLE=ROLE.ID";
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);

        while (rs.next()) {
            BasePatient cm = new BasePatient();
            cm.userId.set(rs.getInt("ID"));
          //  System.out.println(rs.getInt("IDPATIENT"));
            cm.userName.set(rs.getString("FIO"));
            cm.userAge.set(rs.getString("LOGIN"));

            cm.userLO.set(rs.getString("TITLE"));
            data2.add(cm);
        }
        tvTEST.setItems(data2);
        tvTEST.getSelectionModel().select(0);
    }








    public void onClick(ActionEvent actionEvent) throws Exception {
        Object source = actionEvent.getSource();
        if(!(source instanceof Button)) { return;}
        Button clickedButton = (Button) source;
        ConH2.Conn();
        statmt = ConH2.conn.createStatement();
        BasePatient selItem2 = (BasePatient) tvTEST.getSelectionModel().getSelectedItem();
        UserID = selItem2.userId.getValue();
        switch (clickedButton.getId()) {
            case  "btnLoad":
        //        loadPatient();
                break;
            case "btnDel":
                delUser();
                break;
            case "btnView":
                System.out.println(UserID);
                new Main.InfoPatient(selItem2.userName.getValue(), selItem2.userId.getValue());
                break;
            case "btnAdd":
                addUser();
                break;
        }
    }


    private void delUser() throws Exception {
        if (UserID == 1) {
            new Main.info("Учетную запись администратора нельзя удалять.");
        } else {
            String sql = "DELETE FROM USERS WHERE ID = " + UserID;
            statmt.executeUpdate(sql);
            data2.clear();
            buildData1();
        }
    }

   private void addUser() throws Exception {
        boolean newUser = true;
       ResultSet rs = ConH2.conn.createStatement().executeQuery("SELECT * from USERS");

       if (tfLogin.getText().equals("") || tfPass.getText().equals("") || tfFIO.getText().equals("")) {
           System.out.println("ПУСТО");
           newUser = false;
           new Main.info("Заполните все поля.");
       }
       else {
           while (rs.next()) {
               if (tfLogin.getText().equals(rs.getString("LOGIN"))) {
                   newUser = false;
                   new Main.info("Пользователь с таким логином уже создан.");
               }
           }
       }


           if (newUser) {
               ConH2.Conn();
               String sql = "INSERT INTO USERS VALUES (" + null + ", '" + tfLogin.getText() + "', '" + tfPass.getText() + "', '" + (idRol + 1) + "', '" + tfFIO.getText() + "')";
               statmt.executeUpdate(sql);
               data2.clear();
               buildData1();
           }
    }

}
