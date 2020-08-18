package com.current.sync;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Administrator
 * ReentrantLock and Condition
 * 精确叫醒指定线程
 */
public class ReentrantLockAndCondition {
	
	private final  ArrayList<Object> list = new ArrayList<Object>();
	
	Lock lock = new ReentrantLock();
	
	private Condition consumer = lock.newCondition();
	
	private Condition producer = lock.newCondition();
	
	private int MAX_ELEMENT_COUNT = 10;//容器最大元素个数
	
    protected int count = 0; //容器当前元素个数
	
    //加入元素，即生产
	public void put(Object o){
		
		//解释while的作用：
		//while使得当当前的线程释放对象锁，并在重新获得锁后能够再次判断是否
		//容器的元素个数达到了最大值，决定是否继续执行或者再次await释放锁
		//如果此处用if，那么当线程得到锁之后，直接从【位置标号1】开始执行，在其他线程
		//添加元素后，该获得锁的线程又put一个元素，导致出错
		
		try{
			lock.lock();
			while(list.size() == MAX_ELEMENT_COUNT){
				try {
					producer.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			this.list.add(o);//位置标号1
			System.out.println(Thread.currentThread().getName()+" put element");
			count++;
			consumer.signalAll();//通知消费者可以进行消费	
			
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
		
	}
	
	//或者元素，即消费
	public Object get(){
		Object ele = null;
		
		try{
			lock.lock();
			while(list.size() == 0){//容器为空，消费者等待
				try {
					consumer.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println(Thread.currentThread().getName()+" get element");
			ele = list.remove(0);
			count--;
			producer.signalAll(); //通知生产者生产
			
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
		return ele;
	}
	
	
	public static void main(String[] args) {
		
		WaitAndNotify wan = new WaitAndNotify();
		for(int i=0 ; i< 10; i++){//共10个消费者线程
			new Thread(()->{
				for(int k=0; k<5; k++) wan.get();//每个消费者线程一次性消费5个
			},"consumer "+i).start();
		}
		
		for(int j=0 ; j< 2; j++){//共两个生产者线程
			new Thread(()->{
				for(int k=0; k<30;k++) wan.put(new String(k+""));//每个生产者线程，一次生产30个
				
			},"producer "+j).start();
		}
		
	}
}
