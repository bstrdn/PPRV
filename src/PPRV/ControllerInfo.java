package PPRV;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ControllerInfo {
    @FXML private Label attention;

    @FXML
    private void initialize() throws Exception {
    attention.setText(Main.attention);
    }


}
