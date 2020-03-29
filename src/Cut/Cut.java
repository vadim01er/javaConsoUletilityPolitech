package Cut;

import java.io.*;
import java.util.Scanner;

public class Cut {
    private final int rangeStart;
    private final int rangeEnd;
    private final Boolean isChar;

    public Cut(int rangeStart, int rangeEnd, Boolean isChar) {
        this.isChar = isChar;
        this.rangeStart = rangeStart;
        this.rangeEnd = rangeEnd;
    }

    private static String splitW(String line, int rangeStart, int rangeEnd) {
        String[] word = line.split(" ");
        String[] space = line.split("\\S+");
        int[] rangeNow = new int[]{1, 0};
        StringBuilder out = new StringBuilder();
        if (rangeStart == -1) {
            rangeNow[1] = Math.min(rangeEnd, word.length);
        } else if (rangeEnd == -1) {
            if (rangeStart > word.length) return "";
            else rangeNow[0] = rangeStart;
            rangeNow[1] = word.length;
        } else {
            if (rangeStart > word.length) return "";
            else rangeNow[0] = rangeStart;
            rangeNow[1] = Math.min(rangeEnd, word.length);
        }
        for (int i = rangeNow[0] - 1; i <= rangeNow[1] - 1; i++) {
            out.append(word[i]);
            if (i != rangeNow[1] - 1) out.append(space[i + 1]);
        }
        return out.toString();
    }

    private static String splitC(String line, int rangeStart, int rangeEnd){
        StringBuilder out = new StringBuilder();
        if (rangeStart == -1) {
            if (line.length() > rangeEnd) out.append(line, 0, rangeEnd);
            else out.append(line);
        } else if (rangeEnd == -1) {
            if (line.length() > rangeStart) out.append(line, rangeStart, line.length());
        } else {
            if (line.length() > rangeEnd) out.append(line, rangeStart, rangeEnd);
            else if (line.length() > rangeStart) out.append(line.substring(rangeStart));
        }
        return out.toString();
    }

    public void cutInAndOutFile(BufferedReader inputFile, BufferedWriter outputFileName) throws IOException {
        String line;
        StringBuilder out = new StringBuilder();
        boolean forN = false;
        while ((line = inputFile.readLine()) != null) {
            if (forN) out.append("\n");
            forN = true;
            if (isChar)
                out.append(splitC(line, rangeStart, rangeEnd));
            else
                out.append(splitW(line, rangeStart, rangeEnd));
        }
        outputFileName.write(out.toString());
    }

    public void cutInputFile(BufferedReader inputFile) throws IOException {
        String line;
        StringBuilder out = new StringBuilder();
        boolean forN = false;
        while ((line = inputFile.readLine()) != null) {
            if (forN) out.append("\n");
            forN = true;
            if (isChar)
                out.append(splitC(line, rangeStart, rangeEnd));
            else
                out.append(splitW(line, rangeStart, rangeEnd));
        }
        System.out.println(out);

    }

    public void cutOutputFile(Scanner input, BufferedWriter outputFile) throws IOException {
        String line;
        StringBuilder out = new StringBuilder();
        boolean forN = false;
        while (input.hasNextLine()) {
            line = input.nextLine();
            if (forN) out.append("\n");
            forN = true;
            if (isChar)
                out.append(splitC(line, rangeStart, rangeEnd));
            else
                out.append(splitW(line, rangeStart, rangeEnd));
        }
        outputFile.write(out.toString());
    }

    public void cutCMD(Scanner input) {
        String line;
        StringBuilder out = new StringBuilder();
        boolean forN = false;
        while ((line = input.nextLine()) != null) {
            if (forN) out.append("\n");
            forN = true;
            if (isChar)
                out.append(splitC(line, rangeStart, rangeEnd));
            else
                out.append(splitW(line, rangeStart, rangeEnd));
        }
        System.out.println(out.toString());
    }
}
