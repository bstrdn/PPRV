package PPRV;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControllerTESTchief {

    @FXML
    private TableView tvTEST;
    private ObservableList<BasePatient> data;
    public static Connection conn;
    @FXML
    TableColumn<BasePatient, String> colId;
    @FXML
    TableColumn<BasePatient, String> colUserName;
    @FXML
    TableColumn<BasePatient, String> colAge;
    @FXML
    TableColumn<BasePatient, String> colLo;


    @FXML
    private void initialize() throws Exception {


        colId.setCellValueFactory(
                new PropertyValueFactory<BasePatient, String>("userId"));
        colUserName.setCellValueFactory(
                new PropertyValueFactory<BasePatient, String>("userName"));
        colAge.setCellValueFactory(new PropertyValueFactory<BasePatient, String>("userAge"));
        colLo.setCellValueFactory(
                new PropertyValueFactory<BasePatient, String>("userLO"));

//        colId.setCellValueFactory(cellData -> cellData.getValue().colId());

        ConH2.Conn();
        buildData();
    }


    public void buildData() throws SQLException, ClassNotFoundException {
        data = FXCollections.observableArrayList();
        String SQL = "SELECT * FROM PATIENT";
        ResultSet rs = ConH2.conn.createStatement().executeQuery(SQL);

        while (rs.next()) {
            BasePatient cm = new BasePatient();
            cm.userId.set(rs.getInt("IDPATIENT"));
            System.out.println(rs.getInt("IDPATIENT"));
            cm.userName.set(rs.getString("NAME"));
            cm.userAge.set(rs.getString("AGE"));
            cm.userLO.set(rs.getString("LASTOPERATION"));
            data.add(cm);
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
                            setTextFill(Color.CHOCOLATE);
                            setStyle("-fx-background-color: yellow");
                        } else {
                            setTextFill(Color.BLACK);
                            setStyle("");
                        }
                    }
                }
            };
        });











        tvTEST.setItems(data);
//        tvTEST.setStyle("-fx-background-color: tomato;");
//        colAge.setStyle("-fx-background-color: yellow");

    }
}

