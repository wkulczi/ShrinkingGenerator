package sample;

import javafx.beans.property.*;
import javafx.scene.text.Text;

import javax.sound.midi.Soundbank;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ShrinkingGenBack {

    public ShrinkingGenBack() {
        LFSR1_Zeroes.setValue(0);
        LFSR1_Ones.setValue(0);
        LFSR2_Zeroes.setValue(0);
        LFSR2_Ones.setValue(0);
        ArrayList<Boolean> b = new ArrayList<>(Arrays.asList(false));
        LFSR = new ArrayList<>();
        LFSR.add(b);
        LFSR.add(b);
        xorPos1 = new ArrayList<Integer>();
        xorPos2 = new ArrayList<Integer>();
    }


    //xor position 1
    private List<Integer> xorPos1;

    //xor position 2
    private List<Integer> xorPos2;

    public ArrayList<ArrayList<Boolean>> getLFSR() {
        return LFSR;
    }

    private SimpleStringProperty LFSR1_yBit = new SimpleStringProperty(this, "");
    private SimpleStringProperty LFSR2_yBit = new SimpleStringProperty(this, "");


    //lfsr1
    private ArrayList<ArrayList<Boolean>> LFSR;

    public void setLSFR(int location, ArrayList<Boolean> booleans) {
        LFSR.set(location, booleans);
        if (location == 0) {
            LFSR1_Ones.set(Collections.frequency(LFSR.get(0), true));
            LFSR1_Zeroes.set(Collections.frequency(LFSR.get(0), false));
            LFSR1_Size.set(LFSR.get(0).size());
        } else {
            LFSR2_Ones.set(Collections.frequency(LFSR.get(1), true));
            LFSR2_Zeroes.set(Collections.frequency(LFSR.get(1), false));
            LFSR2_Size.set(LFSR.get(1).size());
        }
    }
    //     - size
//      - zeroes
//      - ones
//
    //lfsr2

    //     - size
//      - zeroes
//      - ones
//
//    clock

    //    output
    private SimpleStringProperty ticksProperty = new SimpleStringProperty(this, "");
    private SimpleStringProperty cyclesProperty = new SimpleStringProperty(this, "");
    private int ticks;
    private int cycles;

    private List<Boolean> output = new ArrayList<>();

    public StringProperty getLFSR1_oBit() {
        return LFSR1_oBit;
    }

    public String showOutputAsString() {
        return Utility.setCurrentStates(output);
    }

    public List<Boolean> getOutput() {
        return output;
    }

    private StringProperty LFSR1_oBit = new SimpleStringProperty(this, "");

    public SimpleIntegerProperty getLFSR1_Size() {
        return LFSR1_Size;
    }

    public SimpleIntegerProperty getOutputSize() {
        return Output_size;
    }

    public void setLFSR1_Size(int x) {
        LFSR1_Size.set(x);
    }

    public void setOutput_size(int x) {
        LFSR1_Size.set(x);
    }

    public void setLFSR2_Size(int x) {
        LFSR2_Size.set(x);
    }

    private SimpleIntegerProperty LFSR1_Size = new SimpleIntegerProperty(this, "");
    private SimpleIntegerProperty Output_size = new SimpleIntegerProperty(this, "");

    public SimpleIntegerProperty getLFSR2_Size() {
        return LFSR2_Size;
    }

    private SimpleIntegerProperty LFSR2_Size = new SimpleIntegerProperty(this, "");

    public SimpleIntegerProperty getLFSR1_Zeroes() {
        return LFSR1_Zeroes;
    }

    private SimpleIntegerProperty LFSR1_Zeroes = new SimpleIntegerProperty(this, "");
    private SimpleIntegerProperty LFSR1_Ones = new SimpleIntegerProperty(this, "");
    private StringProperty LFSR2_oBit = new SimpleStringProperty(this, "");
    private SimpleIntegerProperty LFSR2_Ones = new SimpleIntegerProperty(this, "");
    private SimpleIntegerProperty LFSR2_Zeroes = new SimpleIntegerProperty(this, "");
    private SimpleIntegerProperty Clock_cycle = new SimpleIntegerProperty(this, "");


    public StringProperty getXorPos1_prop() {
        return XorPos1_prop;
    }

    private StringProperty XorPos1_prop = new SimpleStringProperty(this, "");

    public StringProperty getXorPos2_prop() {
        return XorPos2_prop;
    }

    private StringProperty XorPos2_prop = new SimpleStringProperty(this, "");

    public ArrayList<Boolean> generateLFSR(int size) {
        Random r = new Random();
        Boolean[] b = new Boolean[size];
        for (int i = 0; i < size; i++) {
            b[i] = r.nextBoolean();
        }
        return new ArrayList<Boolean>(Arrays.asList(b));
    }

    //boolean jakiś, że jak już wcisnąłem to fill, to znaczy, że
    //LFSR.set(0,bulbul);
    //utwórz dwie puste listy, wstaw je do LFSR. Dwa booleany, czy są set te kontenery. Jeśli były ustawione, to nie wypierdalaj ich poniżej, bo to chujowe

    public boolean[] LFSR_Set = new boolean[]{false, false};


    void prepareData() {
        if (LFSR1_Size.get()<1){
            LFSR1_Size.set(16);
        }
        if (LFSR2_Size.get()<1){
            LFSR2_Size.set(16);
        }
        if (!LFSR_Set[0]) {
            LFSR.set(0, generateLFSR(LFSR1_Size.get()));
            LFSR1_Ones.set(Collections.frequency(LFSR.get(0), true));
            LFSR1_Zeroes.set(Collections.frequency(LFSR.get(0), false));
            LFSR_Set[0]=true;
        }
        if (!LFSR_Set[1]) {
            LFSR.set(1, generateLFSR(LFSR2_Size.get()));
            LFSR2_Ones.set(Collections.frequency(LFSR.get(1), true));
            LFSR2_Zeroes.set(Collections.frequency(LFSR.get(1), false));
            LFSR_Set[1]=true;
        }
    }

    private void filterXorPos() {
        if (xorPos1.size() < 1) {
            int x = new Random().ints(1, LFSR.get(0).size()).findFirst().getAsInt();
            var randints = ThreadLocalRandom.current().ints(1, LFSR.get(0).size() - 4).distinct().limit(x).toArray();
            for (int i = 0; i < x; i++) {
                xorPos1.add(randints[i]);
            }
        }
        if (xorPos2.size() < 1) {
            int x = new Random().ints(1, LFSR.get(1).size()).findFirst().getAsInt();
            var randints = ThreadLocalRandom.current().ints(1, LFSR.get(1).size() - 4).distinct().limit(x).toArray();
            for (int i = 0; i < x; i++) {
                xorPos2.add(randints[i]);
            }
        }
        xorPos1 = xorPos1.stream().filter(integer -> integer < LFSR.get(0).size()).collect(Collectors.toList());
        xorPos2 = xorPos2.stream().filter(integer -> integer < LFSR.get(1).size()).collect(Collectors.toList());
        showXorPoss();
    }
    private void showXorPoss(){
        XorPos1_prop.setValue(prepareShowXorPos(xorPos1).toString());
        XorPos2_prop.setValue(prepareShowXorPos(xorPos2).toString());
    }

    private StringBuilder prepareShowXorPos(List<Integer> xorlist){
        StringBuilder x1 = new StringBuilder(xorlist.get(0).toString() + ",");
        for (int i = 1; i < xorlist.size()-1; i++) {
            x1.append(xorlist.get(i).toString()).append(",");
        }
        x1.append(xorlist.get(xorlist.size() - 1));
        return x1;
    }

    int parseToInt(String x) {
        var par = x.replaceAll("\\D+", "");
//        if (par.length()==0){error}
        return Integer.parseInt(par);
    }

    public void clear() {
        output = new ArrayList<>();
        xorPos1.clear();
        xorPos2.clear();
        cycles = 0;
        ticks = 0;
        LFSR.get(0).clear();
        LFSR.get(1).clear();
    }

    private int debug_ticks = 0;
    private int debug_cycles = 0;

    //                    OUTPUT_LABEL.setText(output.get(output.size()-1)?"1":"0");
    boolean debug(boolean x, boolean cy, Text LFSR1_oBit, Text LFSR2_oBit, Text OUTPUT_LABEL) {

        prepareData();
        //watch out for null
        xorPos1 = parseXORPos(XorPos1_prop);
        xorPos2 = parseXORPos(XorPos2_prop);
        filterXorPos();
        if (x) {
            debug_cycles = parseToInt(cyclesProperty.get());
            debug_ticks = parseToInt(ticksProperty.get());
            ticks = 0;
            cycles = 0;
            cyclesProperty.set(String.valueOf(cycles));
            ticksProperty.set(String.valueOf(ticks));
        }
        if (cycles < debug_cycles) {
            if (cy) {
                System.out.println("cycles");
                for (int j = 0; j < debug_ticks; j++) {
                    stepLFSRs(LFSR1_oBit, LFSR2_oBit);
                    System.out.println("step");
                    if (LFSR1_yBit.get().matches("1")) {
                        output.add(Integer.parseInt(LFSR2_yBit.get()) > 0);
                        OUTPUT_LABEL.setText(LFSR2_yBit.get());
                    }
                    ticksProperty.set(String.valueOf(j + 1));
                }
                cycles++;
                cyclesProperty.set(String.valueOf(cycles));
                if (!(cycles < debug_cycles)) {
                    return true;
                }
            } else {
                if (ticks < debug_ticks) {
                    stepLFSRs(LFSR1_oBit, LFSR2_oBit);
                    if (LFSR1_yBit.get().matches("1")) {
                        output.add(Integer.parseInt(LFSR2_yBit.get()) > 0);
                        OUTPUT_LABEL.setText(LFSR2_yBit.get());
                    }
                    ticks++;
                    ticksProperty.set(String.valueOf(ticks));
                } else {
                    cycles++;
                    cyclesProperty.set(String.valueOf(cycles));
                    if (!(cycles < debug_cycles)) {
                        return true;
                    }
                }
            }
            return false;
        } else {
            return true;
        }
    }

    void run(Text LFSR1_oBit, Text LFSR2_oBit, Text OUTPUT_LABEL) {
        prepareData();
        //watch out for null
        xorPos1 = parseXORPos(XorPos1_prop);
        xorPos2 = parseXORPos(XorPos2_prop);
        filterXorPos();
        cycles = parseToInt(cyclesProperty.get());
        ticks = parseToInt(ticksProperty.get());
        if (cycles == 0) {
            cycles = 16;
            cyclesProperty.set(Integer.toString(16));
        }
        if (ticks == 0) {
            ticks = 16;
            ticksProperty.set(Integer.toString(16));
        }
        for (int i = 0; i < cycles; i++) {
            for (int j = 0; j < ticks; j++) {
                stepLFSRs(LFSR1_oBit, LFSR2_oBit);
                if (LFSR1_yBit.get().matches("1")) {
                    output.add(Integer.parseInt(LFSR2_yBit.get()) > 0);
                    OUTPUT_LABEL.setText(LFSR2_yBit.get());
                    Output_size.set(output.size());
                }
            }
        }
        System.out.println(output);
    }

    void generateToSize(Text LFSR1_oBit, Text LFSR2_oBit, Text OUTPUT_LABEL) {
        prepareData();
        //watch out for null
        if(!(xorPos2.size()<1 && xorPos1.size()<1)){
            xorPos1 = parseXORPos(XorPos1_prop);
            xorPos2 = parseXORPos(XorPos2_prop);
        }
        filterXorPos();
        var setsize = Output_size.get();
        while (output.size() < setsize) {
            stepLFSRs(LFSR1_oBit, LFSR2_oBit);
            if (LFSR1_yBit.get().matches("1")) {
                output.add(Integer.parseInt(LFSR2_yBit.get()) > 0);
                OUTPUT_LABEL.setText(LFSR2_yBit.get());
                Output_size.set(output.size());
            }
        }
    }

    public SimpleIntegerProperty getLFSR1_Ones() {
        return LFSR1_Ones;
    }

    public SimpleIntegerProperty getLFSR2_Ones() {
        return LFSR2_Ones;
    }

    public SimpleIntegerProperty getLFSR2_Zeroes() {
        return LFSR2_Zeroes;
    }

    public void fillLfsr(int i, int size) {
        LFSR.set(i, generateLFSR(size));
    }


    //todo throw exception if empty
    private List<Integer> parseXORPos(StringProperty posProp) {
        String par;
        List<Integer> result = new ArrayList<Integer>();
        par = posProp.get().replaceAll("\\D+", " ");
        String[] arrOfStr = par.split(" ");

        for (String s : arrOfStr) {
            if (!s.equals(" ")) {
                result.add(Integer.parseInt(s));
            }
        }
        return result;
    }

    //todo przerob tamte properties na tablice properties
    public void updateCounters(int i) {
        if (i == 0) {
            LFSR1_Ones.set(Collections.frequency(LFSR.get(0), true));
            LFSR1_Zeroes.set(Collections.frequency(LFSR.get(0), false));
        } else {
            LFSR2_Ones.set(Collections.frequency(LFSR.get(1), true));
            LFSR2_Zeroes.set(Collections.frequency(LFSR.get(1), false));
        }
    }

    public SimpleStringProperty getCycles() {
        return cyclesProperty;
    }

    public SimpleStringProperty getTicks() {
        return ticksProperty;
    }

    //OH MY GOD PUT IT IN AN ARRAY FOR GOD'S SAKE
    private void stepLFSRs(Text LFSR1_oBit, Text LFSR2_oBit) {
        //xorpos >2!
        var LFSR1 = LFSR.get(0);
        var LFSR2 = LFSR.get(1);

        ArrayList<Boolean> LFSR1_XorValues = new ArrayList<>();
        ArrayList<Boolean> LFSR2_XorValues = new ArrayList<>();

        for (int x : xorPos1) {
            LFSR1_XorValues.add(LFSR1.get(x - 1));
        }
        for (int x : xorPos2) {
            LFSR2_XorValues.add(LFSR2.get(x - 1));
        }
        var LFSR1_out = LFSR1_XorValues.remove(0);
        var LFSR2_out = LFSR2_XorValues.remove(0);
        for (Boolean x : LFSR1_XorValues) LFSR1_out ^= x;
        for (Boolean x : LFSR2_XorValues) LFSR2_out ^= x;
        //Ybit- youngest bit
        //Obit- oldest bit
        LFSR1_yBit.setValue(LFSR1.remove(LFSR1.size() - 1) ? "1" : "0"); //wyswietla 1/0 zamiast true/false
        LFSR2_yBit.setValue(LFSR2.remove(LFSR2.size() - 1) ? "1" : "0");

        LFSR1_oBit.setText(LFSR1_out ? "1" : "0");
        LFSR2_oBit.setText(LFSR2_out ? "1" : "0");
        //output do drugiego okna potrzeba tutaj. jeszcze nie pytaj jak

        LFSR1.add(0, LFSR1_out);
        LFSR2.add(0, LFSR2_out);

        LFSR.set(0, LFSR1);
        LFSR.set(1, LFSR2);
    }

    public SimpleStringProperty getLFSR1_yBit() {
        return LFSR1_yBit;
    }

    public void setLFSR1_yBit(SimpleStringProperty lfsr1_yBit) {
        this.LFSR1_yBit = lfsr1_yBit;
    }

    public SimpleStringProperty getLFSR2_yBit() {
        return LFSR2_yBit;
    }

    public void setLFSR1_Ones(int x) {
        LFSR1_Ones.set(x);
    }

    public void setLFSR1_Zeroes(int x) {
        LFSR1_Zeroes.set(x);
    }

    public void setLFSR2_Ones(int x) {
        LFSR2_Ones.set(x);
    }

    public void setLFSR2_Zeroes(int x) {
        LFSR2_Zeroes.set(x);
    }

}

//methods:
//cycle
//step
//run
//fill


//save to file or output -.-
