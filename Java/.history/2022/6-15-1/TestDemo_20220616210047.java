/*
 *                                                     __----~~~~~~~~~~~------___
 *                                    .  .   ~~//====......          __--~ ~~
 *                    -.            \_|//     |||\\  ~~~~~~::::... /~
 *                 ___-==_       _-~o~  \/    |||  \\            _/~~-
 *         __---~~~.==~||\=_    -_--~/_-~|-   |\\   \\        _/~
 *     _-~~     .=~    |  \\-_    '-~7  /-   /  ||    \      /
 *   .~       .~       |   \\ -_    /  /-   /   ||      \   /
 *  /  ____  /         |     \\ ~-_/  /|- _/   .||       \ /
 *  |~~    ~~|--~~~~--_ \     ~==-/   | \~--===~~        .\
 *           '         ~-|      /|    |-~\~~       __--~~
 *                       |-~~-_/ |    |   ~\_   _-~            /\
 *                            /  \     \__   \/~                \__
 *                        _--~ _/ | .-~~____--~-/                  ~~==.
 *                       ((->/~   '.|||' -_|    ~~-/ ,              . _||
 *                                  -_     ~\      ~~---l__i__i__i--~~_/
 *                                  _-~-__   ~)  \--______________--~~
 *                                //.-~~~-~_--~- |-------~~~~~~~~
 *                                       //.-~~~--\
 *                       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 *                               神兽保佑            永无BUG
 */

import java.util.ArrayList;

/*
 * @Author: ls02 <2877455773@qq.com>
 * @Date: 2022-06-15 20:53:54
 * @LastEditors: ls02 <2877455773@qq.com>
 * @LastEditTime: 2022-06-16 15:08:24
 * @FilePath: \Java\2022\6-15-1\TestDemo.java
 * @Description: 
 * 
 * Copyright (c) 2022 by ${ls02 <2877455773@qq.com>}, All Rights Reserved. 
 */
/*
 *                                                     __----~~~~~~~~~~~------___
 *                                    .  .   ~~//====......          __--~ ~~
 *                    -.            \_|//     |||\\  ~~~~~~::::... /~
 *                 ___-==_       _-~o~  \/    |||  \\            _/~~-
 *         __---~~~.==~||\=_    -_--~/_-~|-   |\\   \\        _/~
 *     _-~~     .=~    |  \\-_    '-~7  /-   /  ||    \      /
 *   .~       .~       |   \\ -_    /  /-   /   ||      \   /
 *  /  ____  /         |     \\ ~-_/  /|- _/   .||       \ /
 *  |~~    ~~|--~~~~--_ \     ~==-/   | \~--===~~        .\
 *           '         ~-|      /|    |-~\~~       __--~~
 *                       |-~~-_/ |    |   ~\_   _-~            /\
 *                            /  \     \__   \/~                \__
 *                        _--~ _/ | .-~~____--~-/                  ~~==.
 *                       ((->/~   '.|||' -_|    ~~-/ ,              . _||
 *                                  -_     ~\      ~~---l__i__i__i--~~_/
 *                                  _-~-__   ~)  \--______________--~~
 *                                //.-~~~-~_--~- |-------~~~~~~~~
 *                                       //.-~~~--\
 *                       ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * 
 *                               锟斤拷锟睫憋拷锟斤拷            锟斤拷锟斤拷BUG
 */

class Queue {
    private int[] arr = new int[1000];
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private Object lock = new Object();

    public void put(int val) throws InterruptedException {
        synchronized(lock) {
            while (size == arr.length) {
                lock.wait();
            }

            arr[tail] = val;
            tail++;
            tail %= arr.length;
            size++;
            lock.notify();
        }
    }

    public int pop() throws InterruptedException {
        int ret = 0;
        synchronized(lock) {
            while (size == 0) {
                lock.wait();
            }

            ret = arr[head];
            head++;
            head %= arr.length;
            size--;

            lock.notify();
        }

        return ret;
    }
}

public class TestDemo {
    static void Test1() throws InterruptedException {

    }

    static void Test2() throws InterruptedException {
        Object obj = new Object();

        synchronized (obj) {
            obj.wait();
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Queue q = new Queue();
        //鐢熶骇鑰?
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 1; i < 1000; i++) {
                    System.out.println("生产者:" + i);

                    try {
                        q.put(i);
                        Thread.sleep(50);
                    } catch (InterruptedException e) { 
                    }
                }
            }
        };

        thread1.start();

        //娑堣垂鑰?
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int ret = q.pop();
                        System.out.println("消费者A：" + ret);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread2.start();

        Thread thread3 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int ret = q.pop();
                        System.out.println("消费者B：" + ret);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread3.start();

        Thread thread4 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int ret = q.pop();
                        System.out.println("消费者C：" + ret);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread4.start();

        Thread thread5 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int ret = q.pop();
                        System.out.println("消费者D：" + ret);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();
    }
}