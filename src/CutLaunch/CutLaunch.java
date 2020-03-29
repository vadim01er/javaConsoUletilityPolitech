package CutLaunch;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
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
    private boolean worldInd;

    @Option(name = "-o", metaVar = "OutputName", usage = "Output file name")
    private String outName;

    @Argument(metaVar = "InputName", usage = "Input file name")
    private String inName;

    private static int[] rangeParse(String range) {
        if (!Pattern.matches("[0-9]+-", range) && !Pattern.matches("-[0-9]+", range) &&
                !Pattern.matches("[0-9]+-[0-9]+", range))
            throw new IllegalArgumentException("");
        Matcher matcher = Pattern.compile("\\d+").matcher(range);
        int[] out = new int[]{-1, -1};
        int i = 0;
        if (Pattern.matches("-[0-9]+", range)) i++;
        while (matcher.find()) {
            out[i] = Integer.parseInt(matcher.group());
            i++;
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
            if (charInd == worldInd) {
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

        switch (args.length) {
            case 5: {
                new File(outName);
                try (BufferedReader in = new BufferedReader(new FileReader(inName))) {
                    try (BufferedWriter out = new BufferedWriter(new FileWriter(outName))) {
                        cut.cutInAndOutFile(in, out);
                    }
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.err.println("error with outputFile or inputFile");
                }
                break;
            }
            case 4: {
                new File(outName);
                try (BufferedWriter out = new BufferedWriter(new FileWriter(outName))) {
                    cut.cutOutputFile(new Scanner(System.in), out);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.err.println("error with outputFile");
                }
                break;
            }
            case 3: {
                try (BufferedReader in = new BufferedReader(new FileReader(inName))) {
                    cut.cutInputFile(in);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                    System.err.println("error with inputFile");
                }
                break;
            }
            case 2: {
                cut.cutCMD(new Scanner(System.in));
                break;
            }
        }
    }



}