package Cut;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Cut {
    private final String range;
    private final Boolean charOrWorld;
    private final String inputFileName;
    private String outputFileName;

    public Cut(String range, Boolean charOrWorld, String inName, String outName) {
        this.charOrWorld = charOrWorld;
        this.range = range;
        this.inputFileName = inName;
        this.outputFileName = outName;
    }

    private static int[] rangeParse(String range) {
        if (!Pattern.matches("[0-9]+-", range) && !Pattern.matches("-[0-9]+", range) &&
                !Pattern.matches("[0-9]+-[0-9]+", range))
            throw new IllegalArgumentException("Uncorrect range");
        Matcher matcher = Pattern.compile("[0-9]+").matcher(range);
        int[] out = new int[]{-1, -1};
        int i = 0;
        if (Pattern.matches("-[0-9]+", range)) i++;
        while (matcher.find()) {
            out[i] = Integer.parseInt(matcher.group(0));
            i++;
        }
        return out;
    }

    private static int[] findChar(String line, int[] range) {
        int count = 0;
        char[] lineChar = line.toCharArray();
        int[] rangeChar = new int[]{-1, -1};
        boolean one = false;
        if (lineChar[0] != ' ') {count++;}
        for (int i = 0; i < line.length() - 2; i++ ) {
            if (count == range[1]) { rangeChar[1] = i; }
            if (count == range[0] && !one) {rangeChar[0] = i; one = true;}
            if (lineChar[i] == ' ' && lineChar[i+1] != ' ') {count++;}
        }
        return rangeChar;
    }

    private void changeDirectory() {
        boolean change = true;
        int i = 1;
        while(change) {
            File dir = new File(outputFileName);
            if(dir.exists()) {
                if (i == 1)outputFileName = outputFileName.substring(0, outputFileName.length() - 4);
                else if (i < 10) outputFileName = outputFileName.substring(0, outputFileName.length() - 7);
                else outputFileName = outputFileName.substring(0, outputFileName.length() - 8);
                outputFileName += "("+ i +").txt";
                i++;
            } else change = false;
        }
        System.out.println(outputFileName);
    }

    public void cutFile() {
        int[] rangeNow = rangeParse(range);
        changeDirectory();
        new File(outputFileName);
        try (BufferedReader in = new BufferedReader(new FileReader(inputFileName))) {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(outputFileName))){
                String line;
                boolean forN = false;
                while((line = in.readLine()) != null){
                    if (forN) out.write("\n");
                    forN = true;
                    if (charOrWorld) {
                        if (rangeNow[0] == -1){
                            if (line.length() > rangeNow[1]) out.write(line, 0, rangeNow[1]);
                            else out.write(line);
                        }
                        else if(rangeNow[1] == -1) {
                            if (line.length() > rangeNow[0])  out.write(line, rangeNow[0],line.length() - rangeNow[0]);
                        }
                        else {
                            if (line.length() > rangeNow[1])  out.write(line, rangeNow[0],rangeNow[1] - rangeNow[0]);
                            else if (line.length() > rangeNow[0])  out.write(line.substring(rangeNow[0]));
                        }
                    } else {
                        int[] rangeWorld = findChar(line, rangeNow);
                        if (rangeWorld[0] == -1 && rangeWorld[1] == -1) out.write("");
                        else if (rangeWorld[0] == -1) out.write(line, 0, rangeWorld[1]);
                        else if (rangeWorld[1] == -1 ) out.write(line, rangeWorld[0], line.length() - rangeWorld[0]);
                        else out.write(line, rangeWorld[0], rangeWorld[1] - rangeWorld[0]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
