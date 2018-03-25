package PPRV;

import PPRV.MUSOR.JavaFX_TestTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Integer.parseInt;


public class ControllerInfoPatietn {
    private int idPatient;
    @FXML private Label label12;
    @FXML Text Text1, Text2, Text3;
    @FXML private ObservableList<ObservableList> data;
    @FXML void Load () {}
    @FXML private TableView tvAn;
    @FXML private Button btnDelAn;
    public static Statement statmt;
    public static int idAn;
    private String[] colName = {"№Иссл.", "№ПАЦ", "Дата иссл.","sdf","Рост","Вес","Кровь","sdf","sdf","sdf"};


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




    buildData();
    }



    public void onClick(ActionEvent actionEvent) throws Exception {
        Object source = actionEvent.getSource();
        //если нажата не кнопка - выход из метода
        if(!(source instanceof Button)) { return;}
        Button clickedButton = (Button) source;

        switch (clickedButton.getId()) {
            case  "btnBack":
                clickedButton.getScene().getWindow().hide();
                break;
            case "btnDelAn":
                btnDelAn();
                break;
//            case "btnView":
//                System.out.println(selItem.get(0));
//                new Main.InfoPatient(selItem.get(1).toString());
//                break;
        }
    }

public void btnDelAn()throws SQLException, ClassNotFoundException {
    ObservableList olAn = (ObservableList) tvAn.getSelectionModel().getSelectedItem();
    idAn = parseInt(olAn.get(0).toString());
    ConH2.Conn();
    String sql = "DELETE FROM ANALYSIS WHERE ID = " + idAn;
    statmt = ConH2.conn.createStatement();
    statmt.executeUpdate(sql);
    tvAn.getColumns().clear();
    buildData();

}


    public void buildData() throws SQLException, ClassNotFoundException {
        ConH2.Conn();
        data = FXCollections.observableArrayList();
        try{
            String SQL = "SELECT * from ANALYSIS";
            ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
            //СОЗДАНИЕ СТОЛБЦОВ ПЕРЕБОР ПО СТОЛБЦАМ ОТ X до Х
            for(int i=0 ; i<10; i++){
                //We are using non property style for making dynamic table
                final int j = i;
                //название колонок
                TableColumn col = new TableColumn(colName[i]);

                //цвет колонки
                if (i == 1) {
                    col.setStyle("-fx-background-color: green;");
                }
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

//                col.setCellFactory(column -> {
//                    return new TableCell() {
//                        @Override
//                        protected void updateItem(String text, boolean empty) {
//                            super.updateItem(text, empty);
//                            if (text == null || empty) {
//                                setText(null);
//                                setStyle("");
//                            }
//                            else {
//                                setText();
//                            }
//                        }
//                    }
//                });


                tvAn.getColumns().addAll(col);
//                tvAn.getStylesheets().add("my.css");
                System.out.println("Column ["+i+"] ");
            }
            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column

                    row.add(rs.getString(i));


                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            tvAn.setItems(data);


        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

}