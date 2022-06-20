import java.rmi.server.ObjID;

/*
 * @Author: ls02 <2877455773@qq.com>
 * @Date: 2022-06-15 20:53:54
 * @LastEditors: ls02 <2877455773@qq.com>
 * @LastEditTime: 2022-06-15 22:47:12
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
 *                               ���ޱ���            ����BUG
 */

 class Queue {
    private int[] arr = new int[1000];
    private int size = 0;
    private int head = 0;
    private int tail = 0;
    private Object lock = new Object();

    public void put(int val) {
        synchronized(lock) {
            while (size == arr.length) {
                lock.wait();
        }
    
            arr[tail] = val;
            tail++;
            tail %= arr.length;
            size++;
        }

        lock.notify();
    }

    public int pop() {
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
    public static void main(String[] args) {
        Queue q = new Queue();
        //�����ߺ�
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    System.out.println("������:" + i + "������");

                    try {
                        q.put(i);
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread1.start();

        //������
        Thread thread2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    q.pop();
                }
            }
        };

        thread2.start();

        thread1.join();
        thread2.join();
    }
}
