package output;

import java.io.File;

/**
 * 
 * @author 王浩天
 * @version	2020.12.25
 * @inherit BufferedReader, FileChooser...
 * @functions 为游戏提供游戏文件记录的功能
 * @properties 
 * @methods 
 * 		toURL(String filePath): 将文件名转化为URL格式
 * 		toPngPath(String d, String p,String f):将指定位置的图片文件拼接成完整的png文件路径
 		toMP3Path(String d, String p, String f):将指定位置的音频文件拼接成完整的mp3文件路径
 */

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
	
	public static String toGifPath(String d, String p,String f) { // 拼接成gif文件的包路径
		// d = main/test, p为包名，f为gif文件名
		String s = "src";
		String c = "/";
		String r = "resources";
		String z = ".gif";
		
		return s + c + d + c + r + c + p + c + f + z;
	}

	public static String toMP3Path(String d, String p, String f) { // 拼接成mp3文件的包路径
		// d = main/test, p为包名，f为png文件名
		String s = "src";
		String c = "/";
		String r = "resources";
		String z = ".mp3";
		
		return s + c + d + c + r + c + p + c + f + z;
	}

}
