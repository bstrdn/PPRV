package PPRV;

import PPRV.MUSOR.Window2;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controller  implements Initializable {

    @FXML private Text actiontarget;
    @FXML private TextField TF1;
    @FXML private Button btn1;
    @FXML PasswordField PW1;
    String login;
    String password;
    static int id;

    @FXML protected void handleSubmitButtonAction(ActionEvent event) throws SQLException, ClassNotFoundException, IOException {
       login = TF1.getText();
       password = PW1.getText();
        int role = ConH2.LogIn(login, password);

       if(role == 1) {
           btn1.getScene().getWindow().hide();
           try {
               new Main.Admin();
           }
           catch (Exception e) {
               e.printStackTrace();
           }
       }
       else if (role == 2) {
           btn1.getScene().getWindow().hide();
           try {
               new Main.Chief();
           }
           catch (Exception e) {
               e.printStackTrace();
           }
       }
       else if (role == 3) {
           btn1.getScene().getWindow().hide();
           try {
               new Main.Doctor();
           }
           catch (Exception e) {
               e.printStackTrace();
           }
       }
        else {
           actiontarget.setText("Не правильный логин или пароль!");
       }



    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
//    public void goToWindow2(ActionEvent event) {
//        try {
//            new Window2();
//        }
//        catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}