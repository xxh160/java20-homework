package tool.log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javafx.scene.input.KeyCode;

public class LogReader {
    BufferedReader in;

    public LogReader(String filePath) throws LogErrorException {
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String str = in.readLine();
            if (!str.equals("THIS IS LOG CREATE BY GMC.")) {
                throw new LogErrorException();
            }

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public LogEntry read() {
        long time;
        int index;
        KeyCode key;
        LogEntry ret = null;
        String str;
        try {
            str = in.readLine();
            if (str == null) {
                return null;
            }
            time = Long.parseLong(str);
            index = in.read();
            key = KeyCode.values()[in.read()];
            in.readLine();
            ret = new LogEntry(time, index, key);

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return ret;
    }

    public void close() {
        try {
            in.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}