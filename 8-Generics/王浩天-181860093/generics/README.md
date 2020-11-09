# 8-Generics作业说明

## 类的实现
### Human类：//基类
  public class Human<T extends Myinteger> {
	T value;//用于区分身份，能力等  
	String name;//姓名  
  Human(T i,String na);//构造函数  
  T getval();//返回val  
  String getname();//返回name  
  void change(T i,String n);//改变val,name用于交换位置  
  void show();//输出自己的val,name  
    
  class Myinteger implements Comparable<Myinteger>  
  利用Myinteger类继承Comparable接口来比较两个人的val值  
### class Calabash_Brothers<T extends Myinteger> extends Human：//继承自Human类  
  boolean compare_val(Calabash_Brothers<T> a)；//每个葫芦娃可以与其他的葫芦娃比较自己的value值  
  void swap(Calabash_Brothers<T> a)；//与其他的葫芦娃交换位置  
  
### class Grandpa<T extends Myinteger> extends Human：//继承自Human类 
  private static final int num_of_brothers;//葫芦兄弟的个数  
  static {  
    num_of_brothers=7;  
  }  
  ArrayList<Calabash_Brothers<T>> lis=new ArrayList<>();//爷爷管理着一个葫芦娃队列  
  boolean compare(Calabash_Brothers<T> a,Calabash_Brothers<T> b)；//爷爷可以比较两个葫芦娃的value值  
  void swap(Calabash_Brothers<T> a,Calabash_Brothers<T> b)；//交换两个葫芦娃的位置  
  void queue(int num,int now)；//爷爷根据自己的观察给葫芦娃调换顺序  
  void countoff() ；//爷爷让葫芦娃们从队列开始依次报出自己的value和name  

## 接口的实现  
//Orchestration和Choreography继承了Sort_method接口，重写了sort()函数以实现两种排序方法，体现出多态的特性  
//可通过重写sort()的方式实现排序算法的可替换  
### public abstract interface Sort_method<T>；  
  void sort(ArrayList<Calabash_Brothers> lis);  
  
### class Orchestration<T> implements Sort_method<T>；  
  void sort(ArrayList<Calabash_Brothers> lis); //在爷爷的指挥下，葫芦娃完成排队  
爷爷会从头开始遍历队列，当发现当前位置与葫芦娃不匹配时，会直接将该葫芦娃与他应该去的位置上的葫芦娃交换位置；  
再观察刚刚被交换的葫芦娃是否处于正确位置，如果不是，则继续交换到正确位置，一直递归下去；如果是，则再从头开始查找不处于正确位置的葫芦娃进行交换；  
当交换次数等于葫芦兄弟的次数时，说明已经完成排序；  
再令葫芦娃们从头开始报数，进行验证即可；     

### class Choreography<T> implements Sort_method<T>;  
  void sort(ArrayList<Calabash_Brothers> lis);//葫芦娃们相互协助，完成排队     
共进行六轮排序，每次从头开始，每个葫芦娃都与身后葫芦娃进行比较，如果value值大于身后的葫芦娃，则进行交换；  
六轮排序后，再通过报数进行验证；  

## main函数    
  public static void main(String[] args)；  
首先提示输入1-7（表示赤橙黄绿青蓝紫七个颜色的葫芦娃）七个数字的任意排序，代表初始队列；  
然后分别调用Orchestration和Choreography的排序方法，并分别输出排序结果；  
  输入函数：public static void input(ArrayList<Calabash_Brothers<Myinteger>> lis)  
