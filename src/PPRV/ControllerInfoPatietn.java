package PPRV;

import PPRV.MUSOR.JavaFX_TestTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;


public class ControllerInfoPatietn {
    private int idPatient;
    @FXML private Label label12;
    @FXML Text Text1, Text2, Text3;
    @FXML private ObservableList<ObservableList> data;
    @FXML void Load () {}
    @FXML private TableView tvAn;
    @FXML private Button btnDelAn;


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
//                delPatient();
                break;
//            case "btnView":
//                System.out.println(selItem.get(0));
//                new Main.InfoPatient(selItem.get(1).toString());
//                break;
        }
    }

public void btnDelAn()throws SQLException, ClassNotFoundException {
    ConH2.Conn();


}


    public void buildData() throws SQLException, ClassNotFoundException {
        ConH2.Conn();
        data = FXCollections.observableArrayList();
        try{
            //    String SQL = "SELECT * from PATIENT WHERE AGE = 2";
            String SQL = "SELECT * from ANALYSIS";
            ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            //for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
            //СОЗДАНИЕ СТОЛБЦОВ ПЕРЕБОР ПО СТОЛБЦАМ ОТ 0 до Х
            for(int i=0 ; i<10; i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                tvAn.getColumns().addAll(col);
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