import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WalkerTest {
    static final float[] testMat1 = {0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f,
            0.25f, 0.25f, 0.25f, 0.25f};
    static final MarkovChain testMC1 = new MarkovChain(new FloatMatrix(testMat1, 4));

    @Test
    void saveWalkToFile_Common() {
        Walker walker = new RandomWalker(testMC1);
        String path = "_saveWalkToFile_Common_UnitTest.txt";
        try {
            File fp = new File(path);
            assertFalse(fp.exists(), "Pre-condition: file should not yet exist.");

            walker.saveWalkToFile(path);

            assertTrue(fp.exists(), "File should exist after writing to it with no IOExeception.");
            assertTrue(fp.delete(), "Expected to be able to delete file after writing to it.");
        } catch (IOException e) {
            fail("Exception occurred while trying to save walk to the file: " + path);
        }
    }

    @Test
    void saveWalkToFile_Exception() {
        Walker walker = new RandomWalker(testMC1);
        String fakePath = "foobarbaddirectory" + File.separator
                + "definitelynotarealdirectory123905" + File.separator
                + "testFile.txt";
        try {
            walker.saveWalkToFile(fakePath);
            fail("Should have failed trying to write to the fake path: " + fakePath);
        } catch (IOException e) {
            //Exception expected
        }
    }


}
