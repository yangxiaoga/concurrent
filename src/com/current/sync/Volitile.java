package com.current.sync;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * volitile关键字
 * 使一个变量在多个线程之间可见
 * 我们常说的堆和栈指的是主内存，而线程有自己的内存
 * 获取数据后，线程从缓冲区读取，而不是从主内存读取
 * volatile是改了之后会通知其他线程的缓冲区过期了，
 * 需要从主内存读取
 * 
 * volatile并不能保证多个线程共同修改running变量时
 * 所带来的不一致问题，也就是说volatile不能替代synchronized
 *
 */
public class Volitile {
	
	volatile boolean running = true;
	
	void execute(){
		System.out.println("---executed---");
		while(running){
			
		}
		System.out.println("finished...");
	}

	public static void main(String[] args) {
		Volitile v = new Volitile();
		new Thread(()->v.execute(),"thread1").start();
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//����״̬,����volatile�����޷����߳�ֹͣ
		v.running = false;
	}
}
