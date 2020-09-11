package com.example.jdk_learn.lock;

import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author xumin zhao
 * @version 1.0
 * @description ReentrantSpinLock
 * @date 2020/9/1 17:19
 **/
public class ReentrantSpinLock implements Lock {

    //利用 AtomicReference来调用CAS，ar初始内存值是null
    private AtomicReference<Thread> ar = new AtomicReference<Thread>(  );

    private int lockCount=0;

    @Override
    public void lock() {
     Thread currentThread = Thread.currentThread();

     if(currentThread == ar.get()){
         lockCount++;
         return;
     }

     while (!ar.compareAndSet( null,currentThread )){

     }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        Thread cureentThread = Thread.currentThread();
        if(cureentThread == ar.get()){
            lockCount --;
        }else{
            ar.compareAndSet( cureentThread,null );
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }

    @Test
    public void testReentrant(){
        ReentrantSpinLock rsl = new ReentrantSpinLock();

        rsl.lock();
        System.out.println( "我来了！！！！" );

        //第二次
        rsl.lock();
        System.out.println( "我又来了！！！！！" );

        rsl.unlock();
        rsl.unlock();
    }

}
