package media;

import java.util.HashMap;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import output.URL;

public class Audio { // 音频播放类，用于播放游戏背景音频，以及游戏人物效果音频

	HashMap<String, AudioClip> clipMap; // 效果音频字典
	HashMap<String, Media> bgmMap; // 背景音频字典
	
	// 初始化
	public Audio() {
		clipMap = new HashMap<>();
	}
	
	public void playClip(String d, String f) { // 播放效果音频
		
		String dirStr = "main";
		String filePath = URL.toMP3Path(dirStr, d, f);
		String source = URL.toURL(filePath);
		
		AudioClip audioClip = clipMap.get(source);
		if(audioClip == null) { // 该音频文件未曾被播放过
			audioClip = new AudioClip(source);
			audioClip.play();
			clipMap.put(source, audioClip);
		}
		else { 
			if(audioClip.isPlaying()) { // 该音频文件正在播放
				return;
			}else { // 该音频文件曾被播放过
				audioClip.play();
			}
		}
	}
	
	public void playBGM() { // 播放背景音频
		
	}
	
}
