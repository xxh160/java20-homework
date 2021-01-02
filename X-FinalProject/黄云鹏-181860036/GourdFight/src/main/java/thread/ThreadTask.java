package thread;

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