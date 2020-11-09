# 泛型

请在你现有的葫芦娃代码中使用泛型进行改写（改进），并更新Readme文件进行具体说明（用泛型改了哪些类型，为什么这样改，获得了哪些好处等）。
1. 在Sorting.java中将Sorting类改为了泛型类Sorting<T extends Creature>，而在Sorting类的数据域中的容器List p改为了List<T> p=new ArrayList<>();
这样改是因为使用泛型更符合语义，这是对于所有生物的排序，而葫芦娃只是生物中的一种，用泛型更具有普遍性和适用性。
2. 将原来实现的接口Comparator<Huluwa>，改为了Comparator<Creature>，并在泛型类Sorting类中重写了compare函数。
因为Huluwa继承了Creature类中的name属性，而泛型T的边界就是Creature，所以在泛型擦除后就是用Creature对应的Comparator来进行姓名的排序。
