# README

## 运行方式

以下说明Windows系统中的运行方式：首先进入项目根目录，然后在命令行执行

```shell
javac God.java
java God input\
```

即可获得如下输出：

```shell
Class Name: Monster
Class Constructors:
Monster(java.lang.String,int,int)
Class Fields:
public java.lang.String Monster.name
public int Monster.health
public int Monster.damage
Class Methods:
public java.lang.String Monster.attack(Monster)
```

## 类加载

类加载功能主要在`CustomClassLoader`类中完成，它继承`ClassLoader`类，重载`findClass`函数并在重载函数中调用编写的`loadClassFromBase64File`函数执行文件系统中Base64编码的Java字节码文件输入。

在`God`类中，用

```java
CustomClassLoader clsLoader = new CustomClassLoader(args[0]);
Class<?> cls = clsLoader.loadClass("Monster");
```

执行自定义的类加载。

## 反射

在`God`类中，执行完类加载以后可以通过`getName`、`getDeclaredConstructors`、`getDeclaredFields`和`getDeclaredMethods`的反射机制找出对象的所有属性和方法。

## 额外说明

作业要求需要我们创建一个该类型的对象实例，但因为调用

```java
	...
	printConstructors(cls.getDeclaredConstructors());
	...

private static void printConstructors(Constructor[] constructors){
        System.out.println("Class Constructors:");
        for (Constructor constructor : constructors) {
            System.out.println(constructor);
        }
    }
```

的输出结果是

```shell
Class Constructors:
Monster(java.lang.String,int,int)
```

可以发现`Monster`对象没有无参构造函数，因此系统无法调用无参数的构造函数实例化类。所以调用

```java
Object obj = cls.newInstance();
```

语句会抛出`InstantiationException`的异常：

```shell
java.lang.InstantiationException: Monster
	at java.lang.Class.newInstance(Class.java:427)
	at God.main(God.java:20)
Caused by: java.lang.NoSuchMethodException: Monster.<init>()
	at java.lang.Class.getConstructor0(Class.java:3082)
	at java.lang.Class.newInstance(Class.java:412)
	... 1 more
```

也就是说我们无法完成“创建一个该类型的对象实例”的作业要求。