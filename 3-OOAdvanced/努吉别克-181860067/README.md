# 作业：面向葫芦娃编程

## 第三次作业



### 一：类设计

##### 1：`World`类

​	初始化场景中的各个元素，包括七个葫芦娃、爷爷、以及全局接收到的指令、main函数启动世界

##### 2：`Being`类

​	为葫芦娃和爷爷的父类，数据成员为名字

##### 3：`CalabashBrothers`类

​	葫芦娃类，数据成员为该葫芦娃的等级，函数成员为返回自己的名字、获得的“排序”的思想、返回自己的等级、与另一个葫芦娃比较等级、交换位置

##### 4:`GrandFather`类

​	爷爷类，数据成员：获得的对葫芦娃排序的“智慧”、对外提供调用他的智慧的接口

##### 5：接口`Wisdom`

​	管理世界中存在的智慧即通用的思想方法的接口，比如排序、统计等等

##### 6：`Sort_orchestration类`

​	对Wisdom接口中排序思想的实现类

##### 7：`Sort_choreography`类

​	对Wisdom接口中排序思想的实现类

##### 其他：

​	使用了`ArrayList`存放葫芦娃引用，利用`import java.util.Random`生成随机数打乱葫芦娃的位置顺序

### 二：面向对象编程思想

##### 1：封装

​	封装了葫芦娃类、爷爷类、Being类、World类等，每个类的数据成员访问权限为private，封装了数据和对数据操作的方法，对外仅提供调用的接口。

##### 2：继承

​	爷爷类和葫芦娃类继承了存在类Being类。

##### 3：多态

​	待拓展，可以根据进一步要求在Being类添加一些新的方法比如走路等，葫芦娃类和爷爷类根据自己的情况比如走路的速度不同重新定义走方法，将爷爷对象或葫芦娃对象引用复制给Being类，运行时会动态绑定，根据Being引用指向 的对象类型调用各自实现的走方法。

##### 4：接口

​	Wisdom接口，Wisdom接口中有抽象Sort_Wisdom方法，`Sort_orchestration与``Sort_choreography`根据排序方法的不同实现了Sort方法。

##### 5：构造器	

​	各个定义的类都定义了构造器

```
GrandFather()
{
    name="爷爷";
    sort_calabash=new Sort_choreography();
}
    World()
    {
        brothers=new ArrayList<CalabashBrothers>();
        CalabashBrothers Eldest =new CalabashBrothers("老大 ",0);
...
    }
    Being()
    {
        name="";
    }
    Being(String its_name)
    {
        this.name=its_name;
    }
        CalabashBrothers()
    {
        rank=0;
        this.name="";
        sorting=new Sort_orchestration();
        count++;
    }
    CalabashBrothers(String name,int age)
    {
        this.name=name;
        this.rank=age;
        sorting=new Sort_orchestration();
        count++;
        System.out.println("count="+count);
    }
```

##### 6：静态变量

葫芦娃类定义了一个静态变量计数出生的葫芦娃的个数

```
static int count;
```

##### 7：静态块

初始化葫芦娃的数量

```
static {
    count=0;
}
```

##### 8：包

```
package cn.edu.nju.java;
```

##### 9：修饰符

对外公开的接口用public修饰，子类可用的接口用protected修饰，仅自己可用的方法和数据成员用private修饰



### 三：类图

![](C:\Users\admin\Desktop\PK8YTLCD.png)

### 四：结果

![](C:\Users\admin\Desktop\result.png)

<u>**注：问题的解决思路与类的设计还存在很多不足之处，希望老师能提出宝贵意见**</u>

