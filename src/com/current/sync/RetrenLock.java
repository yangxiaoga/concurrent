package com.current.sync;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * 一个同步方法可以调用另外一个同步方法
 * 一个线程已经拥有某个对象的锁，再次申请的时候仍然能够
 * 得到该对象的锁，也就是说synchronized获得的锁是可重入的
 */
public class RetrenLock {
	synchronized void method1(){
		System.out.println("enter method1...");
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//����ͬ������method2
		method2();
		System.out.println("leave method1...");
		
	}
	
	synchronized void method2(){
		System.out.println("enter method2...");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("leave method2...");
	}
	
	public static void main(String[] args) {
		RetrenLock lock = new RetrenLock();
		new Thread(()->lock.method1(),"t1").start();
		
	}
}
