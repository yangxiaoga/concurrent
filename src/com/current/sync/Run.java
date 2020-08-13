package com.current.sync;

/**
 * @author Administrator
 * 为对象加锁
 */
public class Run implements Runnable{
	
	private int count = 10;

	@Override
	public synchronized void run() {//synchronizedԭ�Ӳ����������ٷ�
		
			count--;
			System.out.println(Thread.currentThread().getName()+" count:"+count);
	}
	
	public static void main(String[] args) {
		
		Run r = new Run();
		Thread t1 = new Thread(r,"run1");
		Thread t2 = new Thread(r,"run2");
		Thread t3 = new Thread(r,"run3");
		
		
		t1.start();
		t2.start();
		t3.start();
	}

}
