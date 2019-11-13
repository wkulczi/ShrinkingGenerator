package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class PaneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Rectangle LFSR1_RECT;

    @FXML
    private Rectangle LFSR2_RECT;

    @FXML
    private Text LFSR1_LABEL;

    @FXML
    private Text LSFR2_LABEL;

    @FXML
    private Button RUN_BUTTON;

    @FXML
    private Button CYCLE_BUTTON;

    @FXML
    private Text OUTPUT_LABEL;

    @FXML
    private Button STEP_BUTTON;

    @FXML
    private TextField XOR_POS1;

    @FXML
    private TextField XOR_POS2;

    @FXML
    private TextField CYCLES_INPUT;

    @FXML
    private Text ONES_LFSR2;

    @FXML
    private Text ZEROES_LFSR2;

    @FXML
    private TextField SIZE_LFSR2;

    @FXML
    private Button FILL_LFSR2;

    @FXML
    private Text ONES_LSFR1;

    @FXML
    private Text ZEROES_LFSR1;

    @FXML
    private TextField SIZE_LFSR1;

    @FXML
    private Button FILL_LFSR1;

    @FXML
    void show_lfsr1(MouseEvent event) {

    }

    @FXML
    void show_output(MouseEvent event) {

    }

    @FXML
    void initialize() {
        
    }
}
