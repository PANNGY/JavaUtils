package my.pan.sync;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * @author Pan
 * 读写锁
 */
public class ReadWriteLockTest {

	public static void main(String[] args) {
		final Data1 data1 = new Data1();
		for(int i=0;i<3;i++){
			new Thread(new Runnable() {			
				@Override
				public void run() {
					for(int j=0;j<5;j++){
						data1.set(new Random().nextInt(30));
					}				
				}
			}).start();
		}
		
		for(int i=0;i<3;i++){
			new Thread(new Runnable() {				
				@Override
				public void run() {
					for(int j=0;j<5;j++){
						data1.get();
					}				
				}
			}).start();
		}
		
	}
}

class Data1{
	private int data;
	private ReadWriteLock rwl = new ReentrantReadWriteLock();   
    public void set(int data){
    	//取得写锁
    	rwl.writeLock().lock();
		System.out.println(Thread.currentThread().getName()+"开始写入数据");
		try {
			Thread.sleep(2000);
			this.data = data;
			System.out.println(Thread.currentThread().getName()+"写入"+this.data);
		} catch (Exception e) {
		}finally{
			//释放写锁
			rwl.writeLock().unlock();
		}
		
	}
	
    public void get(){
    	//取得读锁
    	rwl.readLock().lock();
		System.out.println(Thread.currentThread().getName()+"开始读取数据");
		try {
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName()+"读取"+this.data);
		} catch (Exception e) {
		}finally{
			//释放读锁
			rwl.readLock().unlock();
		}

	}
}
