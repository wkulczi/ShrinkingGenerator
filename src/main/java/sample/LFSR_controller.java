package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import java.io.IOException;

public class LFSR_controller {

    private PaneController father;

    LFSR_controller(PaneController aThis) {
        father = aThis;
    }

    private FileChooser fileChooser;

    @FXML
    void initialize() {
        LFSR_OUTPUT.setWrapText(true);
        fileChooser = new FileChooser();
    }
    public void setTextArea(String msg){
        LFSR_OUTPUT.setText(msg);
    }
    @FXML
    private Pane pane;

    @FXML
    private Button Update;

    @FXML
    private Button ImportFile;

    @FXML
    private TextArea LFSR_OUTPUT;

    @FXML
    private Button Translate;

    @FXML
    void import_file(MouseEvent event) throws IOException {
        Utility.importFile(fileChooser,LFSR_OUTPUT);
        Update.setDisable(true);
        father.setLSFR1(LFSR_OUTPUT.getText());
    }

    @FXML
    void translate(MouseEvent event) {
        Update.setDisable(false);
        String buffer = LFSR_OUTPUT.getText();
        LFSR_OUTPUT.setText(Utility.stringToBinaryString(buffer));
    }

    @FXML
    void update(MouseEvent event) {
        father.setLSFR1(LFSR_OUTPUT.getText());
    }


}
