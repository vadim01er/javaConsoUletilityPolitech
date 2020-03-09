package CutLaunch;

import java.util.Arrays;
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

    @Option(name = "-o", required = true, metaVar = "OutputName", usage = "Output file name")
    private String outName;

    @Argument(required = true,metaVar = "InputName", usage = "Input file name")
    private String inName;


    public static void main(String[] args) {
        new CutLaunch().launch(args);
    }

    private void launch(String[] args) {
        String range = args[4];
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(Arrays.copyOf(args, 4));
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("java -jar cut.jar [-c|-w] [-o ofile] [file] range");
            parser.printUsage(System.err);
            return;
        }
        Cut cut;
        if (charInd)
            cut = new Cut(range, true, inName, outName);
        else
            cut = new Cut(range, false, inName, outName);

        cut.cutFile();

        System.out.println("File cropped");
    }
 //java -jar out\artifacts\CroppedFile_jar\CroppedFile.jar -c -o C:\Users\Vadim\IdeaProjects\ght\out.txt  C:\Users\Vadim\IdeaProjects\ght\try.txt 2-6
}