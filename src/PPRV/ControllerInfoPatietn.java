package PPRV;

import PPRV.MUSOR.JavaFX_TestTableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.sql.ResultSet;


public class ControllerInfoPatietn {
    private int idPatient;
    @FXML
    private Label label12;
    @FXML Text Text1, Text2, Text3;
    @FXML private ObservableList<ObservableList> data;
    @FXML void Load () {}



@FXML
    private void initialize() throws Exception {
        //idPatient = JavaFX_TestTableView.idPatien;
        idPatient = ControllerChief.idPatient;
        label12.setText(String.valueOf(idPatient));
//        text1.setText("111111111111");

    //ConH2.Conn();
    //data = FXCollections.observableArrayList();
    String SQL = "SELECT * from RESULT WHERE ID = 2";
    ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
    //String ddd= ConH2.conn.createStatement().executeQuery(SQL).getString("RES");
    //System.out.println(ddd);
    //System.out.println(rs.getString("NAME"));
   rs.next();
   Text2.setText(rs.getString("RES"));
   Text3.setText(rs.getString("COMMENT"));
    }
}