# 面向葫芦娃编程

学号：181860070

姓名：裴新宇



### PlantUML 类图

![](http://www.plantuml.com/plantuml/png/bL71JiCm33stNn7jr6cCB-0mQH9mcJZiY51bKg-jQ9BAJPZGsB_ZOj9s7GV8BS_sy_Dxcyu3-M2Qj0Nz1YMgnT5HKzngGl21SA5EQPBe1hfERKcmKwhu9SbgxwcsfR9WC4An9rjU8NloiNl9dEU1zu705lVWAwI1kdFaiyVGX2Cylros2ygo-ArhwvKDHiRawwX0YZpId34-2EfxIlGvZsLNuFVm2Ck5qWv6hc9SgTMxgmlL3HFJ1LAA8q9nMTGkM9uy7CPDhyur29PBfWL2eHK7OCPyX7PM5dkYq-hkSxrMKfl3KxFJypprtdL9dEz1Ee16jMTiXxvoHHzECgXWE9kGtVmUrhPoXAuaQAiF2VTl1Cd0y4IwmiuJ-DhP_rACukNwgPphykKkjaSKxA3cnN6fuTzFgB98ODsxZ7alnfaSA8OpxM4q7wCVw-TqWxO8fla2)



### 运行入口：Main.java

main 操作过程：

- 创建七个 Huluwa 对象，调用 create_random_arr() 方法随机排序，形成队列 arr
- 创建一个 Orchestartion 对象，调用该对象的 sort() 方法对 arr 排序。
- 调用 arr_count_off() 方法输出排序结果。
- 调用 create_random_arr() 方法随机排序，形成队列 arr
- 创建一个 Choreography 对象，调用该对象的 sort() 方法对 arr 排序。
- 调用 arr_count_off() 方法输出排序结果。



### Orchestration 方法：

 调用 GrandFather 对象的 sort() 方法，直接对 arr 进行冒泡排序



### Choreography 方法：

冒泡排序，每次调用 Huluwa 对象的 compare_order() 方法比较其与相邻兄弟的顺序，然后调用 swap_order() 方法进行交换。



### 继承与多态

- 父类：Creature
- 子类：
  - Huluwa
  - GrandFather



### 接口

- 接口：SortMethod
- 成员 sort()，被类 Orchestration、Choreography 实现。



### 包

- world.sort：接口 SortMethod、类 Orchestration、Choreography 
- world.creature：类 Creature、Huluwa、GrandFather

