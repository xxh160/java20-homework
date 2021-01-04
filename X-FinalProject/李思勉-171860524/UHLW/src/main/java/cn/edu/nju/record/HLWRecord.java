package cn.edu.nju.record;
import cn.edu.nju.field.BattleField;
import cn.edu.nju.main.Controller;
import cn.edu.nju.main.MainPart;
import cn.edu.nju.role.Creature;
import cn.edu.nju.role.HuLuWa;
import cn.edu.nju.role.Scorp;
import cn.edu.nju.role.Snake;
import java.io.*;
//TODO: 还需要把战场信息+生物信息写下来
//TODO: 注意，每次播放录像都需要对生物类和葫芦娃类重新初始化
//格式：(HULUWA1) HP MP ATK AP AD MD SP MAXHP MAXMP MAXSP x y [bufftype buffround buffeffect]
public class HLWRecord {
    static Controller controller;
    static String saveFilePath;
    static PrintWriter out;
    static BufferedReader in;
    private static MainPart mainPart;
    public static void startRecord(){
        File dir = new File("record");
        if(!dir.exists()){
            dir.mkdir();
        }
        int index = dir.listFiles().length;
        saveFilePath = "record/"+index+".sav";
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriter(saveFilePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        controller.setRecordMode(true);
        Snake snake = controller.getSnake();
        write(snake.getStatus());
        Scorp scorp = controller.getScorp();
        write(scorp.getStatus());
        HuLuWa[] huLuWas = controller.getHuluwaList();
        for(HuLuWa huLuWa:huLuWas){
            write(huLuWa.getStatus());
        }
    }

    public static void startPlay(String path){
        try {
            in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            Snake snake = controller.getSnake();
            Scorp scorp = controller.getScorp();
            HuLuWa[] huLuWas = controller.getHuluwaList();
            BattleField<Creature> battleField = controller.getBattleField();
            snake.init();
            scorp.init();
            snake.setStatus(battleField,in.readLine());
            scorp.setStatus(battleField,in.readLine());

            for(HuLuWa huLuWa:huLuWas){
                huLuWa.init();
                huLuWa.setStatus(battleField,in.readLine());
                if(!huLuWa.isAir()){
                    controller.setChoosedCreature(huLuWa);
                    controller.setChoosedEnemy(huLuWa);
                }
            }
            controller.startReplay();
            playNextCommand();

        } catch (IOException e) {
            mainPart.showAlert("错误","文件路径不存在！");
            e.printStackTrace();
        }


    }
    public static void playNextCommand(){
        String command = null;
        try {
            command = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(command!=null&&!command.equals("null")){
            controller.parseCommand(command);
        }
        else{
            controller.gameOver("完");
        }
    }
    public static void endRecord(){
        if(out!=null){
            out.close();
            controller.setRecordMode(false);
        }
    }
    public static void write(String message){
        out.println(message);
    }
    public static void setController(Controller controller){
        HLWRecord.controller = controller;
    }
    public static void setMainPart(MainPart main) {
        HLWRecord.mainPart = main;
    }


}
