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
   // private int idPatient;
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
    public static int idSelected;
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

                idSelected = data2.get(((TablePosition) selectedCells.get(0)).getRow()).studyId.getValue();

             //   System.out.println(idSelected);
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
    colIdPatient.setCellValueFactory(new PropertyValueFactory<Study, String>("studyIdName"));
    colDate.setCellValueFactory(new PropertyValueFactory<Study, String>("studyDate"));
    colA1.setCellValueFactory(new PropertyValueFactory<Study, String>("studyA1"));
    colA2.setCellValueFactory(new PropertyValueFactory<Study, String>("studyA2"));
    colA3.setCellValueFactory(new PropertyValueFactory<Study, String>("studyA3"));
    colB1.setCellValueFactory(new PropertyValueFactory<Study, String>("studyB1"));
    colC1.setCellValueFactory(new PropertyValueFactory<Study, String>("studyC1"));
    colC2.setCellValueFactory(new PropertyValueFactory<Study, String>("studyC2"));
    colD1.setCellValueFactory(new PropertyValueFactory<Study, String>("studyD1"));








        //idPatient = JavaFX_TestTableView.idPatien;
       // idPatient = ControllerChief.idPatient;
        label12.setText(Main.namePatient);
//        text1.setText("111111111111");

    //ConH2.Conn();
    //data = FXCollections.observableArrayList();
    String SQL = "SELECT * from RESULT WHERE ID = " + 3;
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

    //ПОСТРОЕНИЕ ЛИСТВЬЮ
    public void buildLV(int a) throws SQLException, ClassNotFoundException {
        System.out.println(a);
        ConH2.Conn();
        String SQL = "SELECT * FROM PPRV_ANALISIS WHERE ID_STUDY =" + a;
        String SQL3 = "SELECT * FROM PPRV_ANALISIS_RESULT WHERE ID_STUDY =" + a;
        System.out.println(a);
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);
        ResultSet rs3 = ConH2.conn.createStatement().executeQuery(SQL3);
        rs.next();
//        System.out.println(rs.getString("DATE"));
        lvPatient.getItems().clear();
        String SQL2 = "SELECT * FROM COMMENT WHERE ID =" + a;
        ResultSet rs2 = ConH2.conn.createStatement().executeQuery(SQL2);
        rs2.next();
        rs3.next();

        for (int i = 2; i <= rs3.getMetaData().getColumnCount(); i++ ) {
         rs3.getString(i);

            System.out.println("НАЗВАНИЕ СТОЛБЦОВ " + rs3.getMetaData().getColumnName(i));
            System.out.println("НАЗВАНИЕ СТОЛБЦОВ " + rs.getInt(i+2));
            System.out.println("НАЗВАНИЕ ________ " + rs3.getString(i));
        }



//        lvPatient.getItems().add(0, rs2.getString("A1"));
//        lvPatient.getItems().add(1, rs2.getString("A2"));
//        lvPatient.getItems().add(2, rs2.getString("A3"));
//        lvPatient.getItems().add(3, rs2.getString("A4"));
//        lvPatient.getItems().add(4, rs2.getString("B1"));
//        lvPatient.getItems().add(5, rs2.getString("С1"));
//        lvPatient.getItems().add(6, rs2.getString("C2"));
//        lvPatient.getItems().add(7, rs2.getString("D1"));



      //  lvPatient.getItems().add(0,"Возраст: " + rs.getString("A1") + " " +pprv.analyses[4]);
       // lvPatient.getItems().add(2, "Вес: " + rs.getString("A2"));
//        lvPatient.getItems().add("Рост: " + rs.getString("A3"));
//        lvPatient.getItems().add("Анализ крови на тромбоциты: " + rs.getString("B1"));
//        lvPatient.getItems().add("Время свертываеости крови: " + rs.getString("C1"));
//        lvPatient.getItems().add("Длительность кровотечения: " + rs.getString("C2"));
//        lvPatient.getItems().add("Группа крови и резус фактор: " + rs.getString("D1"));

        lvPatient.setCellFactory(lv -> new ListCell<String>() {
            @Override
            protected void updateItem (String item, boolean empty) {
                super.updateItem(item, empty);
                System.out.println(item);
                try {
                    if (item.contains("Замечаний нет.")) {
                        setText(item);
                    }
                    else if (item.contains("Нельзя") || item.contains("женщинам" ) || item.contains("запрещено" )) {
                        setText(item);
                        setStyle("-fx-background-color: red");
                    }
                    else if (item.contains("на усмотрение")) {
                        setText(item);
                        setStyle("-fx-background-color: yellow");
                    }
                    else if (true)
                        setText(item);

                }
                catch (Exception a) {
                    System.out.println(a);
                }
            }
        });
       // lvPatient.getItems().add(lvAn);
       // lvPatient.getItems().add("dsfsdf");
    }

//ВЫВОД АНАЛИЗОВ

    public void buildData1() throws SQLException, ClassNotFoundException {
        data2 = FXCollections.observableArrayList();
      //  String SQL = "SELECT * FROM ANALYSIS WHERE ID =" + Main.idPatient;
        String SQL2 = "SELECT ID_STUDY,FIO, DATE_ANALISIS,A1,A2,A3\n" +
                "FROM PPRV_ANALISIS,PPRV_PATIENT\n" +
                "WHERE PPRV_ANALISIS.ID_PATIENT = PPRV_PATIENT.ID_PATIENT AND PPRV_PATIENT.ID_PATIENT =" + Main.idPatient;
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL2);

        while (rs.next()) {
            Study cm = new Study();
            cm.studyId.set(rs.getInt("ID_STUDY"));
            cm.studyIdName.set(rs.getString("FIO"));
            cm.studyDate.set(rs.getString("DATE_ANALISIS"));
            cm.studyA1.set(rs.getString("A1"));
            cm.studyA2.set(rs.getString("A2"));
            cm.studyA3.set(rs.getString("A3"));
//            cm.studyB1.set(rs.getString("B1"));
//            cm.studyC1.set(rs.getString("C1"));
//            cm.studyC2.set(rs.getString("C2"));
//            cm.studyD1.set(rs.getString("D1"));
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
                        if (item.equals("175")) {
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
}