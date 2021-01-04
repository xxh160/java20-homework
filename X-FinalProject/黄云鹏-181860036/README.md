# GourdFight

### 游戏使用说明



#### 一、进入游戏

* 主界面如下：

  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104123856758.png" alt="image-20210104123856758" style="zoom:25%;" />

* 点击“New Game”之后，进入游戏模式选择界面

* 点击“Play Back”之后，弹出文件对话框，并提示用户选择一个游戏存档文件(*.txt)，选择好之后可直接进行游戏回放

* 点击“Exit”之后，退出游戏程序

***

#### 二、选择游戏模式

* 模式选择界面如下：

  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104124217038.png" alt="image-20210104124217038" style="zoom:25%;" />

* 点击“Single Mode”之后，进入单机版游戏的角色选择界面

* 点击“Net Mode”之后，进入网络版游戏的网络设置界面**（注：由于某些原因，网络版游戏有问题，请不要选择该游戏模式）**

***

#### 三、网络设置

* 网络设置界面如下：

  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104125727618.png" alt="image-20210104125727618" style="zoom:25%;" />
  >
  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104125803855.png" alt="image-20210104125803855" style="zoom:25%;" />
  >
  >

* 点击"As Server"之后，本程序即作为**独立的服务器端**开始运行（不再跳转其他界面或者响应其他操作）

* 点击"As Client"之后，会弹出要求输入服务器IP地址的输入对话框，正确输入服务器IP地址后，会弹出等待连接服务器的提示对话框，连接成功之后，"Next"按钮被激活

* 点击"Next"按钮，进入网络版的角色选择界面

***

#### 四、角色选择

* 角色选择界面如下：

  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104124525190.png" alt="image-20210104124525190" style="zoom:40%;" />
  >
  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104124555749.png" alt="image-20210104124555749" style="zoom:25%;" />
  >
  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104124702845.png" alt="image-20210104124702845" style="zoom:25%;" />

* 首先会弹出如图一所示的提示对话框，提示此时正在设置的玩家，"player1"代表玩家1（在网络版中即为本机玩家），"player2"代表玩家2（在网络版中即为联机玩家）

* 点击OK之后，如图二所示，可以通过单击左侧一列的按钮，查看各个角色的属性说明，如生命值，攻击力，以及攻击/防御招式等

* 确定好角色之后，如图三所示，双击该角色对应的按钮，会激活角色图片下方的按钮，

  * 若是单机版，则在选择完玩家1的角色后，next按钮被激活，点击next按钮即可同理选择玩家2的角色，选择好后play按钮被激活，点击play按钮即可开始游戏；
  * 若是网络版，则在选择完玩家1的角色，即本机角色后，play按钮直接被激活，若对方玩家也点击了play按钮，双方客户端通过服务器交互后，则可以开始游戏；

***

#### 五、开始游戏

* 游戏界面如下：

  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104130446193.png" alt="image-20210104130446193" style="zoom:25%;" />
  >
  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104130514591.png" alt="image-20210104130514591" style="zoom:25%;" />
  >
  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104130537111.png" alt="image-20210104130537111" style="zoom:25%;" />
  >
  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104130632813.png" alt="image-20210104130632813" style="zoom:25%;" />
  >
  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104130707009.png" alt="image-20210104130707009" style="zoom:25%;" />

* 如图一和图二所示，场景随机选择之后，玩家1和玩家2进行一对一的对战游戏，每局游戏之前会有三秒钟的倒计时，然后就可以响应用户操作开始游戏：

  * 如果是单击版，则双方玩家可通过以下敲击键盘的方式操控角色：
    * W键（玩家1）/ 上箭头（玩家2）：跳跃
    * A键（玩家1）/ 左箭头（玩家2）：向左移动
    * D键（玩家1）/ 右箭头（玩家2）：向右移动
    * S键（玩家1）/ 下箭头（玩家2）：在当前方向上冲刺
    * J键（玩家1）/ V键（玩家2）：发出近距离攻击技能
    * K键（玩家1）/ B键（玩家2）：发出防御技能
    * L键（玩家1）/ N键（玩家2）：发出远距离攻击技能
    * I键（玩家1）/ G键（玩家2）：发出必杀技能**（注：只有角色头顶的能量槽满才能发出一次）**
  * 如果是网络版，则双方玩家的操作方式均为上方玩家1的操作方式

* 游戏采取三局两胜的方式进行，每一局游戏直到一方血槽为空为止，游戏结束后即可跳转到游戏结束界面

***

#### 六、游戏结束

* 游戏结束界面如下：

  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104131858145.png" alt="image-20210104131858145" style="zoom:25%;" />

* 如图所示，游戏结束后会展示玩家1的胜负状态的图片，另外提供三个按钮可进行下一步操作

* 点击"Save Game"之后，弹出文件选择对话框，提示用户创建一个文件(*.txt)进行游戏存档

* 点击“Back To Home”之后，可以回到主界面，进入下一轮新游戏或者回放之前的游戏

* 点击“Exit”之后，退出游戏程序

***

***

### 游戏设计说明

#### 一、框架模块设计说明

* 应用模块 **(位于package app和package main中)**

  >* 控制最顶层容器以及整个程序的生命周期的模块，包括以下几个主要的类：
  >
  >* class APP：
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104133034659.png" alt="image-20210104133034659" style="zoom:40%;" />
  >
  >* class Engine:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104133135467.png" alt="image-20210104133135467" style="zoom:40%;" />
  >
  >* class Game:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104133225027.png" alt="image-20210104133225027" style="zoom:40%;" />
  >
  >* class View:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104133303008.png" alt="image-20210104133303008" style="zoom:40%;" />
  >
  >* class Main
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104140515179.png" alt="image-20210104140515179" style="zoom:50%;" />
  >  >
  >  >

* 全局框架模块**(位于packgae framework中)**

  >* 为游戏框架提供一些重要的全局静态对象/常量，方便随时调用，包括以下几个主要的类：
  >
  >* class Framework:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104134101941.png" alt="image-20210104134101941" style="zoom:50%;" />
  >
  >* class Constants
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104134244305.png" alt="image-20210104134244305" style="zoom:50%;" />
  >  >
  >  >

* 输入模块**（位于package input中）**

  >* 提供对键盘和鼠标输入的响应判断和，包括以下几个主要的类：
  >
  >* class KeyInput:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104134729131.png" alt="image-20210104134729131" style="zoom:50%;" />
  >
  >* class MouseInput:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104134910100.png" alt="image-20210104134910100" style="zoom:50%;" />

* 输出模块**（位于package output中）**

  >* 提供位于文件读写的支持，包括以下几个主要的类：
  >
  >* class Log:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104135419720.png" alt="image-20210104135419720" style="zoom:50%;" />
  >
  >* class URL
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104135737052.png" alt="image-20210104135737052" style="zoom:50%;" />
  >  >
  >  >

* 多媒体模块**（位于package media中）**

  >* 提供游戏框架对多媒体，如音频、视频（视频未实现）的播放功能，包括以下几个主要的类：
  >
  >* class Audio
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104140229293.png" alt="image-20210104140229293" style="zoom:50%;" />
  >  >
  >  >

* 世界模块**（位于pacakge world中）**

  >* 提供游戏的所有实体元素，包括游戏角色、游戏背景、血条、能量条等，包括以下几个主要的类：
  >
  >* class Entity:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104141409231.png" alt="image-20210104141409231" style="zoom:50%;" />
  >
  >* class AttackEntity/DefendEntity: （仅展示AttackEntity)
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104142348612.png" alt="image-20210104142348612" style="zoom:50%;" />
  >  >
  >  >
  >
  >* class RedBaby/OrangeBaby/.../Snake/Scorpion/..: （仅展示RedBaby)
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104141640064.png" alt="image-20210104141640064" style="zoom:50%;" />
  >
  >* class EntityState/EntityName:（仅展示EntityState）
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104143153918.png" alt="image-20210104143153918" style="zoom:50%;" />

* 网络模块：**（位于pacakge network中）**

  >* 实现服务器、客户端等类，提供对网络的支持，包括以下几个主要的类：
  >
  >* class TCPServer/TCPClient: （仅展示TCPServer）
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104143829728.png" alt="image-20210104143829728" style="zoom:50%;" />
  >  >
  >  >
  >
  >* class TCPWorker:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104143920612.png" alt="image-20210104143920612" style="zoom:50%;" />
  >
  >* class Packet:
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104144127350.png" alt="image-20210104144127350" style="zoom:50%;" />

* 多线程模块：**（位于package thread中）**

  >* 提供对多线程的支持，包括以下几个主要的类：
  >
  >* class ThreadPool/ThreadTask/ThreadHandler: （仅展示ThreadPool）
  >
  >  ><img src="/Users/strivin/Library/Application Support/typora-user-images/image-20210104144625026.png" alt="image-20210104144625026" style="zoom:50%;" />
  >  >
  >  >

***

#### 二、游戏逻辑设计说明

* 本游戏仿照4399曾经的著名对战游戏“死神 VS 火影”以及经典游戏“拳皇”的游戏逻辑进行设计

* 双方玩家分别控制一个角色，通过键盘操作，进行一对一的三局PK

* 我们认为的**游戏设计亮点**有：

  * 提供了完整的角色操作，如移动、冲刺、跳跃、攻击、防御等
  * 同时还为角色的行为提供了较为真实的物理引擎，如跳跃时的重力加速度设计、死亡时倒地的反弹设计、静止状态受到攻击时的后退设计等等
  * 提供了生动的**血槽和能量槽**，血槽即随着生命值的下降而变短；而能量槽随着时间慢慢增加，加满之后才可以发必杀技、发完之后能量槽清空，且能量槽的增长速度与必杀技的伤害呈反比，即伤害越高能量槽填满越慢，同时当玩家受到伤害后，能量槽会根据受伤值等比激增一小段，使得濒死状态下更容易发出必杀技以绝地反击
  * 为所有页面提供了**背景音效**，同时为每一个角色提供了身份板，为其每一个招式，都设计了**名称和效果音效**，使得游戏体验更好

  * 所有的非角色操作都采用点击按钮进行操控，程序使用更加简洁和方便

* 我们认为的**游戏设计不足**有：

  * 最大的不足，也是最大失误就是**网络版的实现**上出现了难以调试的未知bug，以至于网络版游戏无法完成
  * 我们网络版的实现本身是需要同时启动三个程序，一个程序作为服务器端独立启动，另外两个程序作为客户端，手动输入服务器IP后，通过服务器端交互数据并进行游戏，但是直到最后也没改正的bug是，当服务器和两个客户端都正确连接后，数据交互迟迟不发生，当在尝试关掉其中一个客户端后才勉强进行了数据交互
  * 对应上面的网络bug，我们认为可能是JavaFX的AnimateTimer线程和网络模块中的Worker线程交互逻辑设计不当造成，但无论怎么样，我们都对网络版游戏无法实现深表遗憾和歉意，**恳请老师酌情扣分**
  * 另外的不足即是，关于葫芦娃的**素材实在过于匮乏**，导致框架能力上可以实现动画，但是没有足够和合适的动画序列帧素材，最后还是放弃了，所有的动作都只能通过简单的图片切换完成

***

***

### 其他说明

* 战斗过程记录文件位于src/playback/record.txt中
* 若README.md的图片无法查看，请查看**README.pdf**