import CutLaunch.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class TestCut {

    private Boolean equalsFile(String firstNameFile, String secondNameFile) {
        try (BufferedReader first = new BufferedReader(new FileReader(firstNameFile))) {
            try (BufferedReader second = new BufferedReader(new FileReader(secondNameFile))) {
                String c;
                String m;
                while ((c = first.readLine()) != null && (m = second.readLine()) != null)
                    if (!c.equals(m)) return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Test
    public void cutLauncher() {
        File index = new File("fortest\\testOut");
        String[] entries = index.list();
        if (entries != null) {
            for (String s : entries) {
                File currentFile = new File(index.getPath(), s);
                currentFile.delete();
            }
        }
        File dir2 = new File("fortest\\testOut");
        if (dir2.delete()) System.out.println("del");
        if (dir2.mkdir()) System.out.println("new");

        CutLaunch.main("-w -o fortest\\testOut\\out.txt fortest\\in.txt 1-2".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out.txt fortest\\in.txt -3".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out.txt fortest\\in.txt 1-".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out.txt fortest\\in.txt 2-".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out.txt fortest\\in.txt 7-".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out.txt fortest\\in.txt 3-7".split(" "));
        CutLaunch.main("-c -o fortest\\testOut\\out.txt fortest\\in.txt 4-12".split(" "));
        CutLaunch.main("-c -o fortest\\testOut\\out.txt fortest\\in.txt -12".split(" "));
        CutLaunch.main("-c -o fortest\\testOut\\out.txt fortest\\in.txt 4-".split(" "));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest.txt", "fortest\\testOut\\out.txt"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(1).txt", "fortest\\testOut\\out(1).txt"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(2).txt", "fortest\\testOut\\out(2).txt"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(3).txt", "fortest\\testOut\\out(3).txt"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(4).txt", "fortest\\testOut\\out(4).txt"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(5).txt", "fortest\\testOut\\out(5).txt"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(6).txt", "fortest\\testOut\\out(6).txt"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(7).txt", "fortest\\testOut\\out(7).txt"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(8).txt", "fortest\\testOut\\out(8).txt"));
    }

}
