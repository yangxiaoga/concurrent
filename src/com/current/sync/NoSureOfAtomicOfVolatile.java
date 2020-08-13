package com.current.sync;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 *	volatile能保证可见性，但不能保证原子性
 *	原因：线程在拿的时候是准确的，但是由于加完之后往回写的过程中不再
 *	重新读取，而是直接写回去，则产生问题，如线程1，拿出100，加1，写回去是101，
 *	线程2拿出100，加1，也写回去101，导致数据出错
 *
 *	可以再方法上加synchronized解决问题
 *	或者不使用volatile,而是在操作时，使用Atomicxxx类实现，替代x++
 *	但是两个原子性操作的中间不一定是原子性的
 *	如：AtomicInteger count = new  AtomicInteger(0); 
 *     if(count.get() <1000){count.incrementAndGet()}
 */
public class NoSureOfAtomicOfVolatile {
	volatile int count = 0;
	void execute(){
		for(int i=0; i<10000; i++){
			count++;
		}
	}
	
	public static void main(String[] args) {
		NoSureOfAtomicOfVolatile n = new NoSureOfAtomicOfVolatile();
		List<Thread> threads = new ArrayList<Thread>();
		for(int i=0; i<10; i++){
			threads.add(new Thread(()->n.execute(),"thread:"+i));
		}
		
		threads.forEach(t -> t.start());
		
		threads.forEach(t->{
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		System.out.println(n.count);
	}
	
}
