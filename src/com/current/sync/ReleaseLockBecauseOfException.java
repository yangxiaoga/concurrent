package com.current.sync;

import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * 由于出现异常导致锁的释放
 */
public class ReleaseLockBecauseOfException {
	
	int count = 0;
	
	synchronized void m(){
		System.out.println(Thread.currentThread().getName()+" count:"+count);
		
		while(true){
			count++;
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println(Thread.currentThread().getName()+" count:"+count);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (count == 5){//�˴������쳣���ͷ���
							//�����ʽ����try,catch�����쳣
				int i = 1/0;
				System.out.println(i);
			}
		}
	}
	
	public static void main(String[] args) {
		ReleaseLockBecauseOfException release = new ReleaseLockBecauseOfException();
		Thread t1 = new Thread(()->release.m(),"thread1");
		
		t1.start();
		
		try {
			TimeUnit.SECONDS.sleep(6);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		//ͨ���۲죬thread1�����쳣�󣬻��ͷ�����thread2�õ������õ�ִ��
		new Thread(()->release.m(),"thread2").start();
	}
}
