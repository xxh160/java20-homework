package tool.log;

import javafx.scene.input.KeyCode;

public class LogEntry {
    public long time;
    public int index;
    public KeyCode key;

    public LogEntry(long time, int index, KeyCode key) {
        this.time = time;
        this.index = index;
        this.key = key;
    }
}