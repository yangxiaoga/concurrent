package com.current.sync;

/**
 * @author Administrator
 * synchronized
 * synchronized锁定的是一个对象
 */
public class Sync {
	private int count = 10;
	private Object lock = new Object();
	
	public void execute(){
		synchronized(lock){
			count--;
			System.out.println(Thread.currentThread().getName()+" count:"+count);
		}
	}
	

}
