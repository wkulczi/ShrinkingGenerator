package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Output_controller {

    @FXML
    private Button ImportOutput;

    @FXML
    private Button ExportOutput;

    @FXML
    private TextArea OUTPUT;

    void setOutputTextArea(String text) {
        OUTPUT.setText(text);
    }

    @FXML
    private TextArea FILE_TO_XOR;

    @FXML
    private TextArea XOREDFILE;

    @FXML
    private Button TranslateToXor;

    @FXML
    private Button ImportToXor;
    @FXML
    private Button XORFILES;
    @FXML
    private Button SaveXored;

    @FXML
    private Button TO_PLAINTEXT;

    @FXML
    private Button TO_BINARY;

    @FXML
    void export_file(MouseEvent event) {
        export_F(OUTPUT);
        System.out.println("export_file");
    }

    private void export_F(TextArea textArea) {
        fileChooser.getExtensionFilters().addAll(extensionFilter1, extensionFilter2);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            saveFile(OUTPUT.getText(), file);
        }
    }

    private void saveFile(String text, File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print(text);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void import_output(MouseEvent event) throws IOException {
        Utility.importFile(fileChooser, OUTPUT);
        System.out.println("import_output");
    }

    @FXML
    void import_to_xor(MouseEvent event) throws IOException {
        Utility.importFile(fileChooser, FILE_TO_XOR);
        System.out.println("import_to_xor");
    }

    @FXML
    void save_xored(MouseEvent event) {
        export_F(XOREDFILE);
        System.out.println("save_xored");

    }

    @FXML
    void translate_to_xor(MouseEvent event) {
        System.out.println("translate_to_xor");
        FILE_TO_XOR.setText(Utility.stringToBinaryString(FILE_TO_XOR.getText()));
    }

    @FXML
    void to_binary(MouseEvent event) {
        String buffer = XOREDFILE.getText();
        XOREDFILE.setText(Utility.stringToBinaryString(buffer));
    }

    @FXML
    void to_plaintext(MouseEvent event) {
        XOREDFILE.setText(Utility.fromBinaryStringToText(XOREDFILE.getText()));
    }

    @FXML
    void xor_files(MouseEvent event) {
        var generated_values = Utility.stringToBooleanList(OUTPUT.getText());
        var to_xor = Utility.stringToBooleanList(FILE_TO_XOR.getText());
        if (generated_values.size() < to_xor.size()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Size difference");
            alert.setHeaderText("We have a problem.");
            alert.setContentText("Text to xor is longer than generated series. How do you want to proceed?");
            ButtonType buttonTypeOne = new ButtonType("Generate new series.");
            ButtonType buttonTypeTwo = new ButtonType("Wrap around key.");
            alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeTwo);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeOne) {
                father.setSIZE_OUTPUT(to_xor.size());
                father.fill_output();
                OUTPUT.setText(father.passCurrentStates());
                XOREDFILE.setText(Utility.setCurrentStates(XORed(Utility.stringToBooleanList(OUTPUT.getText()), Utility.stringToBooleanList(FILE_TO_XOR.getText()))));
            } else if (result.get() == buttonTypeTwo) {
                XOREDFILE.setText(Utility.setCurrentStates(XORed(Utility.stringToBooleanList(OUTPUT.getText()), Utility.stringToBooleanList(FILE_TO_XOR.getText()))));
            }
        }else{
            XOREDFILE.setText(Utility.setCurrentStates(XORed(Utility.stringToBooleanList(OUTPUT.getText()), Utility.stringToBooleanList(FILE_TO_XOR.getText()))));
        }
    }

    private List<Boolean> XORed(List<Boolean> y, List<Boolean> z) {
        List<Boolean> buffer = new ArrayList<>();
        for (int i = 0; i < z.size(); i++) {
            buffer.add(y.get(i % y.size()) ^ z.get(i));
        }
        return buffer;
    }

    private FileChooser.ExtensionFilter extensionFilter1;
    private FileChooser.ExtensionFilter extensionFilter2;
    private FileChooser fileChooser;

    public Output_controller(PaneController paneController) {
        this.father = paneController;
    }

    PaneController father;

    @FXML
    void initialize() {
        XOREDFILE.setWrapText(true);
        FILE_TO_XOR.setWrapText(true);
        OUTPUT.setWrapText(true);
        fileChooser = new FileChooser();
        extensionFilter1 = new FileChooser.ExtensionFilter("TXT files", "*.txt");
        extensionFilter2 = new FileChooser.ExtensionFilter("All files", "*.*");
    }

}
