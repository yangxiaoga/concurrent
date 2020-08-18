package com.current.sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 *  ReentrantLock 可重入的锁
 *  ReentrantLock tryLock 尝试获取锁
 *  可打断的 lock.lockInterruptibly();
 *  ReentrantLock 可以指定为公平锁 new ReentrantLock(true)
 *  公平锁等的时间长的线程优先得到锁
 */
public class ReentrantLockPractice  {
	
	Lock lock = new ReentrantLock();
	
	void method(){
		
		try {
			boolean locked = lock.tryLock(5,TimeUnit.SECONDS);
			if(locked){
				System.out.println("method lockd...");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
			System.out.println("method unlocked");
		}

	}
	
	public static void main(String[] args) {
		ReentrantLockPractice rlp = new ReentrantLockPractice();
		new Thread(()->rlp.method(),"thread1").start();
	}
	
}
