package thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 
 * @author 黄云鹏
 * @version	2021.12.28
 * @inherit 
 * @functions 线程池类，创建线程池，调度线程任务
 * @properties exeuctor nThread
 * @methods  
 * 		submit(ThreadTask task): 提交线程任务
 */

public class ThreadPool { // 线程池类
		
	private ThreadPoolExecutor executor; // 线程池执行器
	private int nThreads = 4; // 线程池中线程数量
		
	// 初始化
	public ThreadPool(int nThreads) {
		this.nThreads = nThreads;
		if(nThreads > 0)
			executor = (ThreadPoolExecutor)Executors.newFixedThreadPool(this.nThreads);
		else
			executor = (ThreadPoolExecutor)Executors.newCachedThreadPool();
	}
		
	// 提交任务
	public void submit(ThreadTask task) { 
		executor.execute(task);
	}
	
	// 判断任务是否全部完成
	public boolean isAllCompleted() {
		return executor.getCompletedTaskCount() >= executor.getTaskCount();
	}
		
	// 关闭执行器
	// 关闭执行器
	public void shutDown() {
		executor.shutdown();
	}
	

}