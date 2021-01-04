package thread;

/**
 * 
 * @author 黄云鹏
 * @version	2021.12.28
 * @inherit Runnable
 * @functions 线程任务类
 * @properties exeuctor nThread
 * @methods  handler
 * 		run(): 线程工作方法
 */

public class ThreadTask implements Runnable { // 线程任务类

	ThreadHandler handler; // 线程任务处理接口，用于线程方法调用
	
	public ThreadTask(ThreadHandler handler) {
		this.handler = handler;
	}
	
	// 线程方法
	@Override
	public void run() {
		handler.handle();
	}
}