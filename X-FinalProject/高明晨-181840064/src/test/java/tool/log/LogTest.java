package tool.log;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Random;

import org.junit.Test;

import javafx.scene.input.KeyCode;

public class LogTest {
    @Test
    public void LogTest() {
        try {

            LogWriter out = new LogWriter("tmp");
            Random rand = new Random();
            int x = rand.nextInt(100);
            int y = rand.nextInt(100);
            out.write(x, KeyCode.A);
            out.write(y, KeyCode.B);
            out.close();
            LogReader in = new LogReader("tmp");
            LogEntry entry;
            entry = in.read();
            assertEquals(x, entry.index);
            assertEquals(KeyCode.A, entry.key);

            entry = in.read();
            in.close();
            assertEquals(KeyCode.B, entry.key);
            assertEquals(y, entry.index);
            File f = new File("tmp");
            f.delete();
        } catch (Exception e) {
            // TODO: handle exception
            assertEquals(0, 1);
        }
    }
}