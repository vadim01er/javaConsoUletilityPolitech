package CutLaunch;


import java.io.*;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Cut.Cut;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class CutLaunch {

    @Option(name = "-c", usage = "Char indent")
    private boolean charInd;

    @Option(name = "-w", usage = "World indent", forbids = {"-c"})
    private boolean wordInd;

    @Option(name = "-o", metaVar = "OutputName", usage = "Output file name")
    private String outName;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String inName;

    private static int[] rangeParse(String range) {
        if (!Pattern.matches("[0-9]+-", range) && !Pattern.matches("-[0-9]+", range) &&
                !Pattern.matches("[0-9]+-[0-9]+", range)) throw new IllegalArgumentException("");
        Matcher matcher = Pattern.compile("([0-9]+)?-([0-9]+)?").matcher(range);
        int[] out = new int[]{-1, -1};
        if (matcher.find()) {
            out[0] = matcher.group(1) != null ? Integer.parseInt(matcher.group(1)) : -1;
            out[1] = matcher.group(2) != null ? Integer.parseInt(matcher.group(2)) : -1;
        }
        return out;
    }

    public static void main(String[] args) {
        new CutLaunch().launch(args);
    }


    private void launch(String[] args) {
        CmdLineParser parser = new CmdLineParser(this);
        int[] range;
        try {
            parser.parseArgument(Arrays.copyOf(args, args.length - 1));
            range = rangeParse(args[args.length - 1]);
            if (charInd == wordInd) {
                System.err.println("java -jar croppedFile.jar [-c|-w] [-o ofile] [file] range");
                parser.printUsage(System.err);
                return;
            }
        } catch (CmdLineException e) {
            System.err.println("java -jar cut.jar [-c|-w] [-o ofile] [file] range");
            parser.printUsage(System.err);
            return;
        }

        Cut cut = new Cut(range[0], range[1], charInd);

        if (outName != null && inName != null) {
            new File(outName);
            try (BufferedReader in = new BufferedReader(new FileReader(inName))) {
                try (BufferedWriter out = new BufferedWriter(new FileWriter(outName))) {
                    cut.cutInputFile(in, out);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.err.println("error with outputFile or inputFile");
            }
        } else if (outName != null) {
            new File(outName);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
               try (BufferedWriter out = new BufferedWriter(new FileWriter(outName))){
                   cut.cutInputFile(in, out);
               }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.err.println("error with outputFile");
            }
        } else if (inName != null) {
            try (BufferedReader in = new BufferedReader(new FileReader(inName))) {
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out))){
                    cut.cutInputFile(in, out);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.err.println("error with inputFile");
            }
        } else {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))){
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));) {
                    cut.cutInputFile(in, out);
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                System.err.println("error CMD, you don't enter a words");
            }
        }
    }
}