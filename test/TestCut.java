import CutLaunch.*;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class TestCut {

    private Boolean equalsFile(String firstNameFile, String secundNameFile) {
        try (BufferedReader first = new BufferedReader(new FileReader(firstNameFile))) {
            try (BufferedReader second = new BufferedReader(new FileReader(secundNameFile))){
                String c;
                String m;
                while ((c = first.readLine()) != null && (m = second.readLine()) != null)
                    if (!c.equals(m)) return false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    @Test
    public void cutLauncher(){
        CutLaunch.main("-w -o fortest\\testout\\out fortest\\in 1-2".split(" "));
        CutLaunch.main("-w -o fortest\\testout\\out fortest\\in -3".split(" "));
        CutLaunch.main("-w -o fortest\\testout\\out fortest\\in 1-".split(" "));
//        CutLaunch.main("-w -o fortest\\testout\\out fortest\\in 2-".split(" "));
//        CutLaunch.main("-w -o fortest\\testout\\out fortest\\in 7-".split(" "));
//        CutLaunch.main("-w -o fortest\\testout\\out fortest\\in 1-".split(" "));
//        CutLaunch.main("-w -o fortest\\testout\\out fortest\\in 1-".split(" "));
        Assert.assertTrue(equalsFile("fortest\\outTest", "fortest\\testout\\out"));
        Assert.assertTrue(equalsFile("fortest\\outTest(1)", "fortest\\testout\\out(1)"));
        Assert.assertTrue(equalsFile("fortest\\outTest(2)", "fortest\\testout\\out(2)"));
        Boolean dir = new File("fortest\\testout").delete();
    }

}
