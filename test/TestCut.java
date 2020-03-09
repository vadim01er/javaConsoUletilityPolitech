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

        CutLaunch.main("-w -o fortest\\testOut\\out fortest\\in 1-2".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out fortest\\in -3".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out fortest\\in 1-".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out fortest\\in 2-".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out fortest\\in 7-".split(" "));
        CutLaunch.main("-w -o fortest\\testOut\\out fortest\\in 3-7".split(" "));
        CutLaunch.main("-c -o fortest\\testOut\\out fortest\\in 4-12".split(" "));
        CutLaunch.main("-c -o fortest\\testOut\\out fortest\\in -12".split(" "));
        CutLaunch.main("-c -o fortest\\testOut\\out fortest\\in 4-".split(" "));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest", "fortest\\testOut\\out"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(1)", "fortest\\testOut\\out(1)"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(2)", "fortest\\testOut\\out(2)"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(3)", "fortest\\testOut\\out(3)"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(4)", "fortest\\testOut\\out(4)"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(5)", "fortest\\testOut\\out(5)"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(6)", "fortest\\testOut\\out(6)"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(7)", "fortest\\testOut\\out(7)"));
        Assert.assertTrue(equalsFile("fortest\\testTrue\\outTest(8)", "fortest\\testOut\\out(8)"));
    }

}
