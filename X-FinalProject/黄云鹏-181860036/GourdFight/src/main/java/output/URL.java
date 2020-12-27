package output;

import java.io.File;

public class URL { // URL转化类，提供简单的将包路径转化成URL的功能

	public static String toURL(String filePath){
		File file = new File(filePath);
		return file.toURI().toString();
	}
}
