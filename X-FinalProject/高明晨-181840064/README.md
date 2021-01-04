# 葫芦娃大战妖精
## 游戏介绍
### 游戏简介
本游戏是一款简单的射击游戏，玩家双方分别扮演葫芦娃和妖精的一方。葫芦娃和妖精每个阵营有6名角色，玩家将通过控制自己6名角色进行战斗，当一方角色的全部阵亡时，游戏结束。  

### 游戏操作
|  按键   | 功能  |
|  ----  | ----  |
|   W  | 上移动 |
|   S  | 下移动 |
|   A  | 左移动 |
|   D  | 右移动 |
|   J  | 普通攻击 |
|   K  | 特殊技能(需要耗蓝) |
|   H  | 切换上一个人物(人物头顶黄色标记位当前人物) |
|   L  | 切换下一个人物 |
### 角色设计
|人物|	HP|	MP|	攻击力|	子弹速度|被动技能|主动技能|
|---|---|---|---|---|---|---|
|模板|100|100|20|10| | | | |			
|大娃|100|100|30|10|攻击力提升50%|发射伤害100的子弹|
|二娃|80|100|10|20|攻击力减少50%，子弹速度x2，体力减少20%|	连续发射伤害10的子弹5枚|
|三娃|100|100|20|10|25%的几率反伤2倍伤害|发射伤害60的子弹|
|四娃|100|100|20|10|25%的几率造成双倍伤害|每行发射一枚伤害10的子弹|
|五娃|100|100|20|10|10%的几率吸血，1%的几率给对面满血，2%的几率自己满血|连续发射伤害30的子弹2枚|
|六娃|100|100|20|10|20%的几率闪避|在对手场地随机生成5枚伤害20的子弹|								
|蝗虫|100|50|10|20|MP消耗减少50%，20%几率双倍伤害|发射伤害50的子弹|
|蜈蚣|100|100|20|10|体力越少攻击力越高(2-HP/100)*20|发射伤害0-100之间随机伤害的子弹|
|蝎子|200|100|20|10|体力提高100%|连续发射伤害50的子弹两枚|
|蛇精|100|200|20|10|MP消耗增加100%|对方场地布满伤害为10的子弹|
|七娃|80|120|10|20|体力减少20%，MP消耗增加20%|发射一枚伤害100的高速子弹|
|蛤蟆|100|100|???|???|发射速度在5-15之间伤害为5-35的随机子弹|25%的概率发射一枚伤害50的子弹；25%的概率回50点血，25%的几率掉50点血（最多掉到1），25%的概率什么也不会发生|
### 游戏运行
在pom.xml所在文件夹输入

    mvn package

即可生成target目录，文件的打包结果为target中的hwx-1.0.0.jar。  
由于pom.xml中已经利用额外的插件实现了打包结果的hwx-1.0.0.jar可以直接运行。  
![pic2.png](https://raw.githubusercontent.com/waterflowing/picture/main/pic2.png)  
在运行的初始窗口中选择“作为服务器启动”，在额外启动两个该程序选择“作为客户端启动”，作为客户端启动时还需要输入服务器IP地址，默认为本地主机IP地址。当服务器检测到有两个客户端与之连接时，游戏开始，阵营为服务器随机分配。  
![pic1.png](https://raw.githubusercontent.com/waterflowing/picture/main/pic1.png)

### 存档记录与加载
服务器运行时会在当前目录自动生成当前战局的存档日志，存档日志名称形如“year_month_day_hour_minute_second.log”,例如"2021_01_03_22_25_09.log"。  
在运行的初始窗口中选择“加载存档日志”，选择存档日志即可加载已有日志。其中src/main/resource/log中包含了一个提供的存档文件"存档.log"。
![pic3.png](https://raw.githubusercontent.com/waterflowing/picture/main/pic3.png)  

---
## 实现介绍
### 总体思想
游戏部分按照MVC范式实现，将model和view分开，通信部分处理control，按照Server-Client模式设计，Client发送指令给Server，Server经过计算后将结果返回给Client。  
具体来说Client端接受用户操作指令后，发送给Server，Server根据指令操作model，根据model的结果返回view给Client,Client显示view。即Client只显示。Server只计算。
### 模块划分
按照上述思想划分出以下核心模块  
|模块|作用|
|---|---|
|game|集成了游戏中model和view|
|game.model|包含了人物模型和子弹模型|
|game.view|包含了人物视图和子弹视图|
|client|集成了客户端的操作|
|server|集成了服务器端的操作|
|tool|工具类|
|app|javaFX应用|

### 核心类介绍
#### game.model.Character
Character类实现了一个人物抽象基类，包含了人物的属性和对人物的一些操作，包括移动、攻击、特殊技能等。其中特殊技能为抽象方法，待具体子类实现。  

    abstract public void specialAttack();

具体人物(在game.model.character.*)的实现时，只需要初始化人物的属性，并实现specialAttack()方法即可。以大娃的实现为例:

    public class Dawa extends Character {
    public Dawa() {
        super(new Grid(1, 0), 100, 100, 30, 10, Color.RED, Camp.Hulu);
    }

    @Override
    public void specialAttack() {
        if (isDead() || getMP() != maxMP)
            return;
        setMP(0);
        BattleGround.bulletThreadPool.execute(
                new Bullet(this, 100, bulletSpeed, Screen.gridToRightBulletPixel(grid), bulletRadius * 2, bulletColor));
    }
}
此外Character中还抽象出了hit(自己的子弹击中敌人)，和hurt(敌人的子弹击中自己)这两个方法，以供有特殊属性的人物使用，例如六娃的20%闪避，将会重载hurt。  
#### game.model.Bullet
Bullet类实现了Runnable接口，线程行为为会每隔固定的时间向前运动，并检查是否击中敌人或到达屏幕边界。如果击中，则调用自己的hit和敌人的hurt函数。  
从而Character中的普通攻击或特殊技能都会生成相应的Bullet提交线程池运行。

#### game.view.CharacterView/BulletView/SceneView
CharacterView/BulletView/SceneView的行为均是根据自己对应的Character/Bullet/全局信息，生成对应的图像。

#### tool.connection.Message
封装了服务器和客户端之间通信的所有信息类型

#### server.Server/ServerHandler/ServerInputHandler/ServerSendMessage
Server会监听直到获得两个客户端，分别生成ServerHandler线程,每个ServerHandler会生成一个ServerInputHandler用来接收客户发送的指令并计算战场情况。当游戏开始时ServerHandler会每隔一段时间(20ms)将战场情况转换为图像发送给客户端。  
ServerSendMessage为抽象出来的一个发送消息的线程类。

#### client.Client/ClientInputHandler/ClientSendMessage
Client会连接服务器并生成ClientInputHandler线程，ClientInputHandler会将从服务器端发送收到的战场图像显示出来。  
此外Client还会将本地的键盘操作发送给服务器进行计算。
ClientSendMessage为抽象出来的一个发送消息的线程类。

#### tool.logReader/logWriter
logReader和logWriter抽象出了读写日志的操作。

#### server.LoadLog
此类与Server操作相似，不过不从Client端接收操作，而是从日志中读取操作，不将图像发送给Client而是直接显示出来。


### 测试
测试部分写了一些tool模块中的类测试，包括对Point、Grid、Pixel这些类的操作测试，Screen中Grid和Pixel互相转换测试，以及logReader和logWriter的操作。

---
## 心得体会
以上主要还是从一个比较抽象的层面描述项目实现内容的，其实代码实现部分有巨多细节要注意的地方，不经过亲自动手编写、调试是很难感受的到。现将一些较难编写调试的地方说明如下：

* 并发编程是非常大的坑，这花费了我调试时间的最大一部分。甚至有些你认为线程安全的地方恰恰没有线程安全，如java提供的如Collections.synchronizedSet()获得的集合照样可能发送并发错误(遍历时删除)。加锁是最稳的方法。
* 架构设计的抽象和解耦合非常重要，这是复杂设计时根本上影响效率的地方，本项目得益于MVC的将模型、图形和控制分离的思想，也受累于如LoadLog,ServerHandler等耦合非常深的部分。事先能准确高效的设计好架构是非常重要的。











