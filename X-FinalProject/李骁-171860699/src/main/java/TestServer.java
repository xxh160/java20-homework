import creature.*;
import view.*;
import net.*;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestServer {

    @Test
    public static void main(String[] args) {
        //try {
            GameServer server = new GameServer(28080);
            server.start();

        //} catch (Exception e) {
        //    System.out.println("测试服务器端监听出错: " + e.getMessage());
        //}
    }

}