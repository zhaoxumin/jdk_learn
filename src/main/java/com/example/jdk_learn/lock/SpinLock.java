package com.example.jdk_learn.lock;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author xumin zhao
 * @version 1.0
 * @description SpinLock  自旋锁
 * @date 2020/8/28 14:27
 **/
public class SpinLock {

    //自旋锁（spinlock）：是指当一个线程在获取锁的时候，如果锁已经被其它线程获取，那么该线程将循环等待，
    // 然后不断的判断锁是否能够被成功获取，直到获取到锁才会退出循环。
    //
    //获取锁的线程一直处于活跃状态，但是并没有执行任何有效的任务，使用这种锁会造成busy-waiting。
    //
    //Java如何实现自旋锁？
    //
    //下面是个简单的例子：
    //
    private AtomicReference ar = new AtomicReference(  );


    public void lock(){

        Thread thread = Thread.currentThread();

        //利用CAS
        while (!ar.compareAndSet( null,thread )){

            System.out.println( thread+"锁住了哦==============" );
            //todo  DO nothing
        }
    }

    public void unlock(){
        Thread thread = Thread.currentThread();
        ar.compareAndSet( thread,null );
    }

    //ock()方法利用的CAS，当第一个线程A获取锁的时候，能够成功获取到，不会进入while循环，
    // 如果此时线程A没有释放锁，另一个线程B又来获取锁，此时由于不满足CAS，所以就会进入while循环，
    // 不断判断是否满足CAS，直到A线程调用unlock方法释放了该锁。
    //自旋锁存在的问题
    //
    //使用自旋锁会有以下一个问题：
    //
    //1. 如果某个线程持有锁的时间过长，就会导致其它等待获取锁的线程进入循环等待，消耗CPU。
    // 使用不当会造成CPU使用率极高。
    //
    //2. 上面Java实现的自旋锁不是公平的，即无法满足等待时间最长的线程优先获取锁。
    // 不公平的锁就会存在“线程饥饿”问题。
    //
    //自旋锁的优点
    //自旋锁不会使线程状态发生切换，一直处于用户态，即线程一直都是active的；不会使线程进入阻塞状态，
    // 减少了不必要的上下文切换，执行速度快
    //

    public static void main(String[] args){

    }

    @Test
    public void  reentrant(){
        SpinLock spinLock = new SpinLock();
        spinLock.lock();
        System.out.println( "锁住了哦！！！！！" );
        spinLock.lock();
        System.out.println( "锁住了哦！！！！！" );
        spinLock.unlock();
        System.out.println( "解开锁了哦！！！！！" );
        spinLock.unlock();
    }
}
