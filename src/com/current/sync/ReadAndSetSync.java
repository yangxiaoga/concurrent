package com.current.sync;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * 写加锁，读不加锁产生的问题
 * 非同步的方法执行，由于同步方法的耗时操作，可以得不到同步方法设定的最新的结果
 * 产生脏读的现象，业务代码只对写加了锁，未对读加锁
 */
public class ReadAndSetSync {
	String name;
	double balance;
	
	public synchronized void set(String name, double balance){
		this.name = name;
		try{
			//��ʱ����
			Thread.sleep(2000);
		} catch(InterruptedException e){
			e.printStackTrace();
		}
		this.balance = balance;
	}
	
	public double getBalance(String name){
		return balance;
	}
	
	public static void main(String[] args) {
		ReadAndSetSync account = new ReadAndSetSync();
		new Thread(()->account.set("zhangsan",100.0)).start();
		
		try{
			TimeUnit.SECONDS.sleep(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//ֵΪ0
		System.out.println(account.getBalance("zhangsan"));
		
		
		try{
			TimeUnit.SECONDS.sleep(2);
		}catch(Exception e){
			e.printStackTrace();
		}
		//ֵΪ100
		System.out.println(account.getBalance("zhangsan"));
		
	}
}














