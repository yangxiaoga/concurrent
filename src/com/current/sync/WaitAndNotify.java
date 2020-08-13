package com.current.sync;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author Administrator
 * wait和notify关键字
 * wait释放锁，notify不释放锁
 * 替代方案，CountDownLatch，初始值为1，！=5时，await(),==5时，countDown()
 */
public class WaitAndNotify {
	
	
	public static void main(String[] args) {
		List<Object> eles = new ArrayList<Object>();
		final Object lock = new Object();
		
		new Thread(()->{
			synchronized(lock){
				System.out.println("thread2����");
				if (eles.size() != 5){
					try {
						lock.wait();//������5���ȴ����ͷ���
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				System.out.println("thread2����");
				lock.notify();//֪ͨthread1����ִ���ˣ��߳̽���,���ͷ���
			}
			
		}).start();
		
		
		new Thread(()->{
			synchronized(lock){
				System.out.println("thread1����");
				for(int i =0; i<10; i++){
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					eles.add(new Object());
					if (eles.size() == 5){//����5��֪ͨthread2
						lock.notify();
						try {
							//����notify���ͷ�����thread2Ҫִ����Ҫthread1ִ��wait����
							lock.wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				System.out.println("thread1����");
			}
		}).start();
	}
}
