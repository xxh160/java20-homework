package thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool { // 线程池类
		
	private ThreadPoolExecutor executor; // 线程池执行器
	private int nThreads = 4; // 线程池中线程数量
		
	// 初始化
	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;
		executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(this.nThreads);
	}
		
	// 提交任务
	public void submit(ThreadTask task) { 
		executor.execute(task);
	}
		
	// 关闭执行器
	public void shutDown() {
		executor.shutdown();
	}
}