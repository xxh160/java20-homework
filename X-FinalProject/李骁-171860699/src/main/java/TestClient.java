import net.*;

import org.junit.Test;

public class TestClient {
    @Test
    public static void main(String[] args) {
        try {
            new GameClient("localhost", 28080); // IP地址为本机，端口为8080
        } catch (Exception e) {
            System.out.println("测试客户端连接出错：" + e.getMessage());
        }
    }
}

/*
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

 
public class TestClient {
 
    @Test
	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			int result = br.read();
			System.out.println(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			boolean isTrue = br.ready();
			System.out.println("br buffer status is " + isTrue);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//		BufferedReader br2 = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			System.out.println("next!");
			String str = br.readLine();
			str = br.readLine();
			System.out.println(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}*/