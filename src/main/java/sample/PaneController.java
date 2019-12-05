package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

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
    private Text LFSR1_oBit;
    @FXML
    private Text LFSR1_yBit;

    @FXML
    private Text LFSR2_oBit;
    @FXML
    private Text LFSR2_yBit;

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
    private TextField TICKS_INPUT;

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
    private Button CLEAR_BUTTON;

    private ShrinkingGenBack generator = new ShrinkingGenBack();
    private Output_controller output_controller;

    @FXML
    void cycle(MouseEvent event) {
        System.out.println("cycle");
        CYCLES_INPUT.setDisable(true);
        TICKS_INPUT.setDisable(true);
        if (RUN_BUTTON.getText().equals("Run")) {
            RUN_BUTTON.setText("Stop");
            STEP_BUTTON.setDisable(true);
            if (generator.debug(true, true, LFSR1_oBit, LFSR2_oBit, OUTPUT_LABEL)) {
                RUN_BUTTON.setText("Run");
                CYCLES_INPUT.setDisable(false);
                TICKS_INPUT.setDisable(false);
                STEP_BUTTON.setDisable(false);
            }
        } else {
            if (generator.debug(false, true, LFSR1_oBit, LFSR2_oBit, OUTPUT_LABEL)) {
                RUN_BUTTON.setText("Run");
                CYCLES_INPUT.setDisable(false);
                TICKS_INPUT.setDisable(false);
                STEP_BUTTON.setDisable(false);
            }
        }
    }

    @FXML
    void step(MouseEvent event) {
        System.out.println("Step");
        CYCLES_INPUT.setDisable(true);
        TICKS_INPUT.setDisable(true);
        if (RUN_BUTTON.getText().equals("Run")) {
            RUN_BUTTON.setText("Stop");
            CYCLE_BUTTON.setDisable(true);
            if (generator.debug(true, false, LFSR1_oBit, LFSR2_oBit, OUTPUT_LABEL)) {
                RUN_BUTTON.setText("Run");
                CYCLES_INPUT.setDisable(false);
                TICKS_INPUT.setDisable(false);
                CYCLE_BUTTON.setDisable(false);
            }
        } else {
            if (generator.debug(false, false, LFSR1_oBit, LFSR2_oBit, OUTPUT_LABEL)) {
                RUN_BUTTON.setText("Run");
                CYCLES_INPUT.setDisable(false);
                TICKS_INPUT.setDisable(false);
                CYCLE_BUTTON.setDisable(false);
            }
        }
    }

    @FXML
    void clear(MouseEvent event) {
        LFSR1_oBit.setText("LSFR 1");
        LFSR1_yBit.setText("");
        generator.setLFSR2_Size(0);
        generator.setLFSR1_Ones(0);
        generator.setLFSR1_Zeroes(0);
        LFSR2_oBit.setText("LSFR 2");
        LFSR2_yBit.setText("");
        generator.setLFSR1_Size(0);
        generator.setLFSR2_Ones(0);
        generator.setLFSR2_Zeroes(0);
        OUTPUT_LABEL.setText("Output");
        CYCLES_INPUT.textProperty().setValue(null);
        TICKS_INPUT.textProperty().setValue(null);
        XOR_POS1.textProperty().setValue(null);
        XOR_POS2.textProperty().setValue(null);
        generator.clear();
    }

    @FXML
    void fill_lfsr1(MouseEvent event) {
        System.out.println("fill_lfsr1: " + SIZE_LFSR1.getText());
        generator.fillLfsr(0, Integer.parseInt(SIZE_LFSR1.getText()));
        generator.LFSR_Set[0] = true;
        generator.updateCounters(0);
        lfsr1_controller.setTextArea(Utility.setCurrentStates(generator.getLFSR().get(0)));

    }

    @FXML
    void fill_lfsr2(MouseEvent event) {
        System.out.println("fill_lfsr2: " + SIZE_LFSR2.getText());
        generator.fillLfsr(1, Integer.parseInt(SIZE_LFSR2.getText()));
        generator.LFSR_Set[1] = true;
        generator.updateCounters(1);
        lfsr2_controller.setTextArea(Utility.setCurrentStates(generator.getLFSR().get(1)));

    }


    //output do rozmiaru, to samo co w 2 tylko teraz w 1
    @FXML
    void help(MouseEvent event) {
        System.out.println("Help here");
    }

    @FXML
    void run(MouseEvent event) {
        if (RUN_BUTTON.getText().equals("Stop")) {
            //do stuff
            //go back to being run button
            CYCLES_INPUT.setDisable(false);
            TICKS_INPUT.setDisable(false);
            RUN_BUTTON.setText("Run");
            STEP_BUTTON.setDisable(false);
            CYCLE_BUTTON.setDisable(false);
        } else {
            //do something else
            generator.run(LFSR1_oBit, LFSR2_oBit, OUTPUT_LABEL);
        }
        //run(cycles,ticks)
        //if one is empty just fill it with value. 1 cycle & 16 ticks. simple enough
    }


    public void setLSFR2(String lsfr2_s) {
        generator.setLSFR(1, Utility.stringToBooleanList(lsfr2_s));
    }

    public void setLSFR1(String text) {
        generator.setLSFR(0, Utility.stringToBooleanList(text));
    }

    @FXML
    void show_lfsr1(MouseEvent event) {
        System.out.println("show_lfsr1");

        System.out.println(generator.getLFSR().get(0));
        lfsr1_controller.setTextArea(Utility.setCurrentStates(generator.getLFSR().get(0)));
        LFSR1_stage.show();
    }

    @FXML
    void show_lfsr2(MouseEvent event) {
        System.out.println("show_lfsr2");

        System.out.println(generator.getLFSR().get(1));
        lfsr2_controller.setTextArea(Utility.setCurrentStates(generator.getLFSR().get(1)));

        LFSR2_stage.show();
    }


    @FXML
    private TextField SIZE_OUTPUT;
    public void setSIZE_OUTPUT(int size_output){
        SIZE_OUTPUT.setText(Integer.toString(size_output));
    }
    @FXML
    private Button FILL_OUTPUT;

    @FXML
    public void fill_output() {
        if (Integer.parseInt(SIZE_OUTPUT.getText()) > 1) {
            generator.generateToSize(LFSR1_oBit, LFSR2_oBit, OUTPUT_LABEL);
        }
    }

    @FXML
    void show_output(MouseEvent event) {
        System.out.println("show output");
        System.out.println(generator.getOutput());
        output_controller.setOutputTextArea(passCurrentStates());
        OUTPUT_stage.show();
    }

    public String passCurrentStates(){
        return Utility.setCurrentStates(generator.getOutput());
    }

    private LFSR_controller lfsr1_controller;
    private LFSR2_controller lfsr2_controller;

    @FXML
    void initialize() throws IOException {

        XOR_POS1.textProperty().bindBidirectional(generator.getXorPos1_prop());
        SIZE_LFSR1.textProperty().bindBidirectional(generator.getLFSR1_Size(), new NumberStringConverter());
        ZEROES_LFSR1.textProperty().bindBidirectional(generator.getLFSR1_Zeroes(), new NumberStringConverter());
        ONES_LSFR1.textProperty().bindBidirectional(generator.getLFSR1_Ones(), new NumberStringConverter());
        LFSR1_yBit.textProperty().bindBidirectional(generator.getLFSR1_yBit());

        XOR_POS2.textProperty().bindBidirectional(generator.getXorPos2_prop());
        SIZE_LFSR2.textProperty().bindBidirectional(generator.getLFSR2_Size(), new NumberStringConverter());
        ZEROES_LFSR2.textProperty().bindBidirectional(generator.getLFSR2_Zeroes(), new NumberStringConverter());
        ONES_LFSR2.textProperty().bindBidirectional(generator.getLFSR2_Ones(), new NumberStringConverter());
        LFSR2_yBit.textProperty().bindBidirectional(generator.getLFSR2_yBit());

        SIZE_OUTPUT.textProperty().bindBidirectional(generator.getOutputSize(), new NumberStringConverter());
        CYCLES_INPUT.textProperty().bindBidirectional(generator.getCycles());
        TICKS_INPUT.textProperty().bindBidirectional(generator.getTicks());

        lfsr1_controller = new LFSR_controller(this);
        lfsr2_controller = new LFSR2_controller(this);
        output_controller = new Output_controller(this);

        createLFSR1Stage(lfsr1_controller);
        createLFSR2Stage(lfsr2_controller);
        createOutputStage(output_controller);
    }

    private Stage LFSR1_stage;
    private Stage LFSR2_stage;
    private Stage OUTPUT_stage;

    private void createLFSR2Stage(LFSR2_controller lfsr2_controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lfsr2.fxml"));
        loader.setController(lfsr2_controller);
        Parent root = loader.load();
        LFSR2_stage = new Stage();
        LFSR2_stage.setTitle("LFSR2");
        LFSR2_stage.setScene(new Scene(root));
        LFSR2_stage.setResizable(false);
//        LFSR2_stage.show();
    }

    private void createOutputStage(Output_controller output_controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/output.fxml"));
        loader.setController(output_controller);
        Parent root = loader.load();
        OUTPUT_stage = new Stage();
        OUTPUT_stage.setTitle("Output");
        OUTPUT_stage.setScene(new Scene(root));
        OUTPUT_stage.setResizable(false);
//        LFSR2_stage.show();
    }

    private void createLFSR1Stage(LFSR_controller lfsr_controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lfsr1.fxml"));
        loader.setController(lfsr_controller);
        Parent root = loader.load();
        LFSR1_stage = new Stage();
        LFSR1_stage.setTitle("LFSR1");
        LFSR1_stage.setScene(new Scene(root));
        LFSR1_stage.setResizable(false);
//        LFSR1_stage.show();
    }

    //methods:
    //popups
    //help
    //popup when click on show lfsr1/output/lfsr2
}

//uogolniony blad zeby wpisac poprawne dane