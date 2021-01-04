package thread;

/**
 * 
 * @author 黄云鹏
 * @version	2021.12.28
 * @inherit 
 * @functions 线程接口
 * @properties
 * @methods  
 * 		handle(): 线程工作方法
 */

public interface ThreadHandler { // 线程任务处理接口，提供具体任务给线程任务类调用执行
	
	public void handle(); // 线程

}
