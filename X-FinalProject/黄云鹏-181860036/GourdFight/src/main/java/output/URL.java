package output;

import java.io.File;

import javafx.scene.image.Image;

public class URL { // URL转化类，提供简单的将包路径转化成URL/Image的功能

	public static String toURL(String filePath){ // 包路径转化为URL
		File file = new File(filePath);
		return file.toURI().toString();
	}
	
	public static String toPngPath(String d, String p,String f) { // 拼接成png文件的包路径
		// d = main/test, p为包名，f为png文件名
		String s = "src";
		String c = "/";
		String r = "resources";
		String z = ".png";
		
		return s + c + d + c + r + c + p + c + f + z;
	}
}
