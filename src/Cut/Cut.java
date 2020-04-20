package Cut;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

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
        String[] word = line.split("\\s+");
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

    public void cutInputFile(BufferedReader inputFile, BufferedWriter out) throws IOException {
        String line;
        while ((line = inputFile.readLine()) != null) {
            if (isChar)
                out.write(splitC(line, rangeStart, rangeEnd));
            else
                out.write(splitW(line, rangeStart, rangeEnd));
            out.write("\n");
        }
    }

}
