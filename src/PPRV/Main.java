package PPRV;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

//import java.awt.*;


public class Main extends Application {
  // public Stage MainStage;
   // MainStage = primaryStage;
    public static Stage window;
    public Scene sceneOne, sceneTwo;
    public static int idPatient;


    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Врач");
       // ЗАГРУЗИТЬ НАЗАД        sceneOne = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")), 600,600);
       sceneOne = new Scene(FXMLLoader.load(getClass().getResource("Chief.fxml")), 600,600);
        window.setScene(sceneOne);
        window.show();
  //      initRootLayout();

       // Label label = new Label("123");
       // Button button1 = new Button("2 scenbbbbbbbbbb");
       // button1.setOnAction(e -> window.setScene(sceneTwo));

       // VBox layout1 = new VBox(20);
       // layout1.getChildren().addAll(label, button1);

       // sceneOne = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")), 600,600);

        //sceneOne = new Scene(layout1, 200, 299);

       // Button button2 = new Button("sdfdbbbbbbbbbbbf");
       // button2.setOnAction(e -> window.setScene(sceneOne));

       // StackPane layout2 = new StackPane();
      //  layout2.getChildren().add(button2);
//        sceneTwo = new Scene(FXMLLoader.load(getClass().getResource("Admin.fxml")));
      //  sceneTwo = new Scene(layout2, 500, 500);

     //   window.setScene(sceneOne);
      //  window.setTitle("Вход");
       // window.show();

//        sceneOne = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")), 600,600);
//        Scene sceneTwo = new Scene(FXMLLoader.load(getClass().getResource("Admin.fxml")));
//        primaryStage.setScene(sceneOne);
//        primaryStage.show();


    }

  //  private void initRootLayout() throws IOException {
        //    FXMLLoader loader = new FXMLLoader();
        //      loader.setLocation(Main.class.getResource("Login.fxml"));
//        BorderPane rootLayout = (BorderPane) loader.load();

        //   Scene scene = new Scene(rootLayout);


 //   }

  //  public void initAdminLayout() throws IOException {
        //    FXMLLoader loader = new FXMLLoader();
        //      loader.setLocation(Main.class.getResource("Login.fxml"));
//        BorderPane rootLayout = (BorderPane) loader.load();

        //   Scene scene = new Scene(rootLayout);
        //try {
       // sceneTwo = new Scene(FXMLLoader.load(getClass().getResource("Admin.fxml")), 300,300);
      //  window.setScene(sceneTwo);
//    //    window.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
  //  }


    public static void main(String[] args) {
        launch(args);

    }
    public static class Admin {

        public Admin() throws Exception
        {
            Stage stage = new Stage();
            stage.setTitle("Администрирование");
            Scene sceneTwo = new Scene(FXMLLoader.load(getClass().getResource("Admin.fxml")), 600,600);
            stage.setScene(sceneTwo);
            stage.show();
        }
    }

    public static class Chief {

        public Chief() throws Exception
        {
            Stage stage = new Stage();
            stage.setTitle("Пациенты :: Главный врач");
            Scene sceneTwo = new Scene(FXMLLoader.load(getClass().getResource("Chief.fxml")), 600,600);
            stage.setScene(sceneTwo);
            stage.show();

        }
    }

    public static class Doctor {

        public Doctor() throws Exception
        {
            Stage stage = new Stage();
            stage.setTitle("Пациенты :: Доктор");
            Scene sceneTwo = new Scene(FXMLLoader.load(getClass().getResource("Admin.fxml")), 600,600);
            stage.setScene(sceneTwo);
            stage.show();
        }
    }

    public static class InfoPatient {

       // public InfoPatient(int idPatient) throws Exception
        public InfoPatient(String s) throws Exception
        {
           // Main.idPatient = idPatient;
            Stage stage = new Stage();
            stage.setTitle(s);
            Scene sceneTwo = new Scene(FXMLLoader.load(getClass().getResource("InfoPatient.fxml")), 600,600);
            stage.setScene(sceneTwo);
            stage.show();
        }
    }


}
