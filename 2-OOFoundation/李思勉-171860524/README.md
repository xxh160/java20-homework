# 工程结构说明

## Creature类

生物类，是该项目的基础类，爷爷和葫芦娃均由该类派生而来，拥有生物号和位置两个信息

```java
int specie;
int pos;
```

## HuLuWa类

葫芦娃类，继承于生物类，生物号为1，多了两个属性“排名”和“中文排名”（方便输出），以及各种各样的函数。

```java
int rank;
String chineseRank;
void tellRank();  				   //报数
void walkTo(battleField,pos);	  	//走向战场的目标地点
int findFrontPos(battleField); 		//在战场中找到应该在自己前面的那个人的位置
```

## OldMan类

爷爷类，继承于生物类，生物号为0，多了一些函数

```java
int askRank(huLuWa); //询问指定葫芦娃的排名
void arrangePos(battleField,huLuWa);
// 在战场中为指定葫芦娃安排位置
void commandLine(battleField);
// 找到战场中的所有葫芦娃，并让他们排位一排
void countOff(battleField);
// 爷爷大喊一声“报数”！
```

## BattleField类

战场类，其中包含有一个大小为20的数组，可以安放各种生物。

## Main类

做初始化工作，当sortMode为0时进行监管排序，为1时进行协同排序

```java
void init();   //初始化
void osSort(); //监管排序
void cgSort(); //协同排序
void main();   //运行程序
```

# 初始化&程序运行

所有葫芦娃被随机分配在战场1-8格的位置，而爷爷在第0格，sortMode默认为0（进行监管排序），为1时进行协同排序

# 监管排序算法

爷爷从战场中找到每个葫芦娃，每找到一个，就询问他他的排名（老大，老二...）,并按照排名带领他与目标位置的葫芦娃交换位置，再递归的为目标地点的葫芦娃找到正确位置，若本来就在正确位置则不对该葫芦娃进行任何操作，去下一个地点找新的葫芦娃。

排序完成后，爷爷在战场中大喊一声报数，葫芦娃开始按战场格子顺序报数。

# 协同排序算法

从大娃开始，每个娃都找其前一个人在哪，并自觉地站到他的身后（大娃站在爷爷后面）

站定后爷爷面前的第一个娃开始向后报数（即按战场格子顺序报数）



