package sample;

import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public final class Utility {
    private Utility() {
        throw new UnsupportedOperationException();
    }


    public static String getExtensionByStringHandling(String filename) {
        return Arrays.stream(filename.split("\\.")).reduce((a, b) -> b).orElse(null);
    }

    static byte[] readSmallBinaryFile(Path path) throws IOException {
        return Files.readAllBytes(path);
    }

    private String listToString(List<String> st) {
        StringBuilder s = new StringBuilder();
        for (String s1 : st) {
            s.append(s1);
        }
        return s.toString().replaceAll(" ", "").replaceAll(",", "").replaceAll("\\[", "").replaceAll("]", "");
    }

    static String stringToBinaryString(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        StringBuilder binary = new StringBuilder();
        for (byte aByte : bytes) {
            int val = aByte;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary.toString();
    }
    public static String setCurrentStates(List<Boolean> l){
        StringBuilder buffer = new StringBuilder();
        for (Boolean b : l) {
            buffer.append(b ?"1":"0");
        }
        return buffer.toString();
    }
    static void importFile(FileChooser fileChooser, TextArea LFSR_OUTPUT) throws IOException {
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("Text files", "*.txt"),
                new FileChooser.ExtensionFilter("Binary files", "*.bin"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            System.out.println(file.getAbsolutePath());
            if (Objects.equals(Utility.getExtensionByStringHandling(file.getName()), "txt")) {
                List<String> lines = Files.readAllLines(Paths.get(file.toURI()), StandardCharsets.UTF_8);
                LFSR_OUTPUT.setText(lines.toString().replaceAll("\\n","").replaceAll("\\[","").replaceAll("]",""));
            } else {
                byte[] binfile = Utility.readSmallBinaryFile(Path.of(file.toURI()));
                StringBuilder output = new StringBuilder();
                for (byte b : binfile) {
                    output.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
                }
                LFSR_OUTPUT.setText(String.valueOf(output));
            }
        }
    }
   public static ArrayList<Boolean> stringToBooleanList(String lsfr2_s) {
        lsfr2_s = lsfr2_s.replaceAll(" ", "").replaceAll("\\D+", "");
        ArrayList<Boolean> list = new ArrayList<>();
        for (char x : lsfr2_s.toCharArray()) {
            list.add(x == '1');
        }
        return list;
    }

    public static String fromBinaryStringToText(String zeroes_ones){
        StringBuilder result= new StringBuilder();
        for (int i = 0; i < zeroes_ones.length() ; i+=8) {
            var s = zeroes_ones.substring(i,i+8);
            int num = Integer.parseInt(s,2);
            result.append(result.toString()).append((char)num);
        }
        return result.toString();
    }

}