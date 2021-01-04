package tool.log;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import javafx.scene.input.KeyCode;

/*
    FileInputStream fis = new FileInputStream(file);
    InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
    BufferedReader br = new BufferedReader(isr);
    FileOutputStream fos = new FileOutputStream(newFilePath);
    OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
    BufferedWriter bw = new BufferedWriter(osw);

*/
public class LogWriter {
    BufferedWriter out;

    public LogWriter(String filePath) {
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath)));
            out.write("THIS IS LOG CREATE BY GMC.");
            out.newLine();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public synchronized void write(int index, KeyCode key) {
        try {
            Long time = new Date().getTime();
            out.write(time.toString());
            out.newLine();
            out.write(index);
            out.write(key.ordinal());
            out.newLine();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

    }

    public void close() {
        try {
            out.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}