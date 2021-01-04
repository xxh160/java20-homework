package output;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import framework.Constants;
import framework.Framework;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import network.Packet;
import view.PlayView;
import world.EntityName;

/**
 * 
 * @author 王浩天
 * @version	2020.12.25
 * @inherit BufferedReader, FileChooser...
 * @functions 为游戏提供游戏文件记录的功能
 * @properties 
 * @methods 
 * 		openGame(): 打开游戏存档文件
 * 		saveGame(): 保存游戏到文件指定文件中
 */

public class Log { // 文件记录类，用于保存游戏记录和打开游戏记录，并在PlayView中回放
	
	public Log() {
		
	}
	
	public void saveGame() {
		// 开启文件对话框
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Save Game");
		chooser.getExtensionFilters().add(new ExtensionFilter("Game File", "*.txt"));
		
		File selectedFile = chooser.showSaveDialog(Framework.app.getStage());
		if(selectedFile == null)
			return;
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;
		try {
			// 保存文件
			PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
			ArrayList<ArrayList<Packet>> player1Record = playView.getPlayer1Record();
			ArrayList<ArrayList<Packet>> player2Record = playView.getPlayer2Record();
			
			fos = new FileOutputStream(selectedFile);
			osw = new OutputStreamWriter(fos,"UTF-8");
			bw = new BufferedWriter(osw);
			
			// 首先保存玩家角色
			String player1_name = playView.getPlayer1Name();
			String player2_name = playView.getPlayer2Name();
			bw.write(player1_name);
			bw.newLine();
			bw.write(player2_name);
			bw.newLine();
			
			// 保存player1的包队列数组
			bw.write("player1"); // 玩家1标志位
			bw.newLine();
			int round = 0;
			for(ArrayList<Packet> pktList:player1Record) {
				round++;
				String r = "round"+round; // 游戏局数标志位
				bw.write(r);
				bw.newLine();
				
				for(Packet pkt: pktList) {
					String line = pkt.send();
					bw.write(line);
				}
			}
			
			// 保存player2的包队列数组
			bw.write("player2"); // 玩家2标志位
			bw.newLine();
			round = 0;
			for(ArrayList<Packet> pktList:player2Record) {
				round++;
				String r = "round"+round; // 游戏局数标志位
				bw.write(r);
				bw.newLine();
				
				for(Packet pkt: pktList) {
					String line = pkt.send();
					bw.write(line);
				}
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				bw.close(); // 关闭数据流
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void openGame() {
		// 开启文件对话框
		FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Game");
		chooser.getExtensionFilters().add(new ExtensionFilter("Game File", "*.txt"));
		
		File selectedFile = chooser.showOpenDialog(Framework.app.getStage());
		if(selectedFile == null)
			return;
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			// 打开文件
			PlayView playView = (PlayView)Framework.app.getView(Constants.PLAY_VIEW_KEY);
			ArrayList<ArrayList<Packet>> player1Record = new ArrayList<ArrayList<Packet>>();
			ArrayList<ArrayList<Packet>> player2Record = new ArrayList<ArrayList<Packet>>();
			
			fis = new FileInputStream(selectedFile);
			isr = new InputStreamReader(fis,"UTF-8");
			br = new BufferedReader(isr);
			
			// 逐行解析
			int parsdNameCount = 0;
			String line;
			String playerTag = "";
			String roundTag = "";
			ArrayList<Packet> pktList = new ArrayList<>();
			while((line = br.readLine()) != null) {
				
				// 首先解析玩家角色
				if(parsdNameCount < 2) {
					if(parsdNameCount == 0) { // 说明玩家1的角色尚未设置
						EntityName player1_name = EntityName.find(line);
						playView.setPlayer1Name(player1_name);
						parsdNameCount++;
					}
					else if(parsdNameCount == 1) { // 说明玩家2的角色尚未设置
						EntityName player2_name = EntityName.find(line);
						playView.setPlayer2Name(player2_name);
						parsdNameCount++;
					}
					continue;
				}
				
				// 再解析包队列数组
				
				// 玩家标志位判断
				if(line.equals("player1")) {
					playerTag = "player1";
					continue;
				}
				else if(line.equals("player2")) {
					playerTag = "player2";
					continue;
				}
				
				// 游戏局数标志位判断
				if(line.equals("round1")) {
					roundTag = "round1";
					// 保存一局
					if(playerTag.equals("player1")) { 
						// 玩家1的第一局，包中为空，无需记录
					}
					else if(playerTag.equals("player2")) { 
						// 玩家2的第一局，说明玩家1最后一局记录完毕，需要保存
						player1Record.add(new ArrayList<Packet>(pktList));
						// 再清空包队列，为下一局作准备
						pktList.clear();
					}
					continue;
				}
				else if(line.equals("round2")) {
					roundTag = "round2";
					if(playerTag.equals("player1")) { 
						// 玩家1的第二局，说明玩家1的第一局记录完毕，需要保存
						player1Record.add(new ArrayList<Packet>(pktList));
						// 再清空包队列，为下一局作准备
						pktList.clear();
					}
					else if(playerTag.equals("player2")) { 
						// 玩家2的第二局，说明玩家2的第一局记录完毕，需要保存
						player2Record.add(new ArrayList<Packet>(pktList));
						// 再清空包队列，为下一局作准备
						pktList.clear();
					}
					continue;
				}
				else if(line.equals("round3")) {
					roundTag = "round3";
					if(playerTag.equals("player1")) { 
						// 玩家1的第三局，说明玩家1的第二局记录完毕，需要保存
						player1Record.add(new ArrayList<Packet>(pktList));
						// 再清空包队列，为下一局作准备
						pktList.clear();
					}
					else if(playerTag.equals("player2")) { 
						// 玩家2的第三局，说明玩家2的第二局记录完毕，需要保存
						player2Record.add(new ArrayList<Packet>(pktList));
						// 再清空包队列，为下一局作准备
						pktList.clear();
					}
					continue;
				}
				
				// 解析命令
				Packet pkt = new Packet();
				pkt.receive(line);
				pktList.add(pkt);
			}
			
			// 循环结束，玩家2的最后一局记录完毕，需要保存
			player2Record.add(new ArrayList<Packet>(pktList));
			// 再清空包队列
			pktList.clear();
			
			// 最后再将记录添加到play View中
			for(ArrayList<Packet> pl : player1Record) {
				playView.addPlayer1Record(pl);
			}
			for(ArrayList<Packet> pl : player2Record) {
				playView.addPlayer2Record(pl);
			}
			// 并开启play back模式
			playView.setPlayBack(true);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			try {
				br.close(); // 关闭数据流
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
