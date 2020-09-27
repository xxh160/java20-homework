# OOAdvanced


## 设计思想


### 面向对象
代码分为三个部分：*CalabashBrothers* 、*Sort*、和 *Homework3.java*。  
*CalabashBrothers*中存放定义葫芦娃的类，每个葫芦娃都有一个判断大小的值id和名字name。

*Sort*中存放排序的类和方法。

*Homework3.java*中存放main函数入口。


![UML pic](http://www.plantuml.com/plantuml/png/ZP8_KyCm3CNtV0gDxRMT6ClGemiJ31qvhgSuRk9hR0LRfjSh-Uuug0a9VpWo9K_vwKdFtWH6pz7MMOFgYAM67Nc-P90UurZx0wgaXK5Sm0Npm1uLj_1Afe2kDjjYZJc6wexEJy_pTTPccQen1DXMv3ML7flgVEtm2xNi3CVeerULxgpOaFil2m0oBjo-hLOmRVArEXukiKC6GszFN6aVn5oc6lcAl4hMO8grVEpO6rU2Gwj5MOo0sVm7BU5B4Aml9mbApQRe0TcR8ZUH1-qIE6sbMiY9QeqE5Da6lHv7inozV-Cap1CsVn3jvtdSazKdyiSRsNjeCpa9oA-PhQS_TIizH2lgXx4Y5zX7nVkNgAEMjOPQTBodmw5NfpDRD6ucaLrlsqQx8jhw7G00.png)

### 多态
因为因为 *Orchestration* 和 *Choreography* 两者都是排序算法，故二者都可以通过继承最基本的排序算法实现。

定义接口Ssort，里面有一个待实现的sort方法，*Orchestration* 和 *Choreography* 都需要实现这个方法。
这样运行时可根据实际情况调用其中任意一个方法。

    Ssort s=new Choreography();
    Ssort s=new Orchestration();
    s.sort();

###  静态方法



    

