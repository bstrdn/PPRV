package PPRV;

import PPRV.MUSOR.JavaFX_TestTableView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static java.lang.Integer.parseInt;


public class ControllerInfoPatient {
    @FXML public ListView lvPatient;
    private int idPatient;
    @FXML private Label label12;
    @FXML Text Text1, Text2, Text3;
  //  @FXML private ObservableList<ObservableList> data;
   // @FXML void Load () {}
 //   @FXML private TableView tvAn; //старая
    @FXML private TableView tvAn1;
    @FXML private Button btnDelAn;
    public static Statement statmt;
 //   public static int idAn;
    public static int idAn2;
    //private String[] colName = {"№Иссл.", "№ПАЦ", "Дата иссл.","sdf","Рост","Вес","Кровь","sdf","sdf","sdf"};
    private ObservableList<String> lvAn = FXCollections.observableArrayList();


    @FXML private ObservableList<Study> data2;
    @FXML TableColumn<Study, String> colId;
    @FXML TableColumn<Study, String> colIdPatient;
    @FXML TableColumn<Study, String> colDate;
    @FXML TableColumn<Study, String> colA1;
    @FXML TableColumn<Study, String> colA2;
    @FXML TableColumn<Study, String> colA3;
    @FXML TableColumn<Study, String> colB1;
    @FXML TableColumn<Study, String> colC1;
    @FXML TableColumn<Study, String> colC2;
    @FXML TableColumn<Study, String> colD1;





@FXML
    private void initialize() throws Exception {

    tvAn1.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            //Check whether item is selected and set value of selected item to Label
            if(tvAn1.getSelectionModel().getSelectedItem() != null)
            {
                TableView.TableViewSelectionModel selectionModel = tvAn1.getSelectionModel();
                ObservableList selectedCells = selectionModel.getSelectedCells();
              //  TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                System.out.println("селект цел " + ((TablePosition) selectedCells.get(0)).getRow());
               // Object val = tablePosition.getTableColumn().getCellData(newValue);
              //  System.out.println("Selected Value" + val);

                int idSelected = data2.get(((TablePosition) selectedCells.get(0)).getRow()).studyId.getValue();

                try {
                    buildLV(idSelected);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    });



    colId.setCellValueFactory(new PropertyValueFactory<Study, String>("studyId"));
    colIdPatient.setCellValueFactory(new PropertyValueFactory<Study, String>("studyIdPatient"));
    colDate.setCellValueFactory(new PropertyValueFactory<Study, String>("studyDate"));
    colA1.setCellValueFactory(new PropertyValueFactory<Study, String>("studyA1"));
    colA2.setCellValueFactory(new PropertyValueFactory<Study, String>("studyA2"));
    colA3.setCellValueFactory(new PropertyValueFactory<Study, String>("studyA3"));
    colB1.setCellValueFactory(new PropertyValueFactory<Study, String>("studyB1"));
    colC1.setCellValueFactory(new PropertyValueFactory<Study, String>("studyC1"));
    colC2.setCellValueFactory(new PropertyValueFactory<Study, String>("studyC2"));
    colD1.setCellValueFactory(new PropertyValueFactory<Study, String>("studyD1"));








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




  //  buildData();
    buildData1();
    }

    public void buildLV(int a) throws SQLException, ClassNotFoundException {

        System.out.println(a);

        ConH2.Conn();
        String SQL = "SELECT * FROM ANALYSIS WHERE ID =" + a;
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
        rs.next();
        System.out.println(rs.getString("DATE"));

        lvAn.add("первый");
        lvAn.add("первый");
        lvAn.add("первый");
        lvAn.add("первый");
        lvAn.add("первый");
        lvAn.add("первый");
        lvPatient.getItems().add(lvAn);
        lvPatient.getItems().add("dsfsdf");
    }



    public void buildData1() throws SQLException, ClassNotFoundException {
        data2 = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM ANALYSIS";
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);

        while (rs.next()) {
            Study cm = new Study();
            cm.studyId.set(rs.getInt("ID"));
            cm.studyIdPatient.set(rs.getInt("IDPATIENT"));
            cm.studyDate.set(rs.getString("DATE"));
            cm.studyA1.set(rs.getString("A1"));
            cm.studyA2.set(rs.getString("A2"));
            cm.studyA3.set(rs.getString("A3"));
            cm.studyB1.set(rs.getString("B1"));
            cm.studyC1.set(rs.getString("C1"));
            cm.studyC2.set(rs.getString("C2"));
            cm.studyD1.set(rs.getString("D1"));
            data2.add(cm);
        }

        colA2.setCellFactory(column -> {
            return new TableCell<Study, String>() {
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

        tvAn1.setItems(data2);
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
    //ObservableList olAn = (ObservableList) tvAn.getSelectionModel().getSelectedItem();
    Study st = (Study) tvAn1.getSelectionModel().getSelectedItem();
   // idAn = parseInt(olAn.get(0).toString());
    idAn2 = st.studyId.getValue();
    ConH2.Conn();
    String sql = "DELETE FROM ANALYSIS WHERE ID = " + idAn2;
    statmt = ConH2.conn.createStatement();
    statmt.executeUpdate(sql);
   // tvAn.getColumns().clear();
    data2.clear();
    buildData1();

}

//старая таблица
//    public void buildData() throws SQLException, ClassNotFoundException {
//        ConH2.Conn();
//        data = FXCollections.observableArrayList();
//        try{
//            String SQL = "SELECT * from ANALYSIS";
//            ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
//            //СОЗДАНИЕ СТОЛБЦОВ ПЕРЕБОР ПО СТОЛБЦАМ ОТ X до Х
//            for(int i=0 ; i<10; i++){
//                //We are using non property style for making dynamic table
//                final int j = i;
//                //название колонок
//                TableColumn col = new TableColumn(colName[i]);
//
//                //цвет колонки
//                if (i == 1) {
//                    col.setStyle("-fx-background-color: green;");
//                }
//                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>,ObservableValue<String>>(){
//                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
//                        return new SimpleStringProperty(param.getValue().get(j).toString());
//                    }
//                });
//
////                col.setCellFactory(column -> {
////                    return new TableCell() {
////                        @Override
////                        protected void updateItem(String text, boolean empty) {
////                            super.updateItem(text, empty);
////                            if (text == null || empty) {
////                                setText(null);
////                                setStyle("");
////                            }
////                            else {
////                                setText();
////                            }
////                        }
////                    }
////                });
//
//
//                tvAn.getColumns().addAll(col);
////                tvAn.getStylesheets().add("my.css");
//                System.out.println("Column ["+i+"] ");
//            }
//            /********************************
//             * Data added to ObservableList *
//             ********************************/
//            while(rs.next()){
//                //Iterate Row
//                ObservableList<String> row = FXCollections.observableArrayList();
//                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
//                    //Iterate Column
//
//                    row.add(rs.getString(i));
//
//
//                }
//                System.out.println("Row [1] added "+row );
//                data.add(row);
//
//            }
//
//            //FINALLY ADDED TO TableView
//            tvAn.setItems(data);
//
//
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("Error on Building Data");
//        }
//    }

}