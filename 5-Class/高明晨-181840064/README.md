## 类加载器和反射
### 类加载器
MyLoader类继承自ClassLoader，重载和实现了findClass和loadClassFromFile方法。  
其中loadClassFromFile将从参数文件名中，读取Base64文本文件，并将其解码为二进制字节流，此即为对应未知类的字节码。  
本次作业文件unknown.txt中记录着原始的Base64文本。
### 反射
根据加载后得到的Class，利用Class类中的getName(),getFields(),getMethods()等方法，获得该类的一系列属性。