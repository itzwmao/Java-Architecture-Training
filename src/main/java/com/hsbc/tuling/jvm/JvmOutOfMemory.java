package com.hsbc.tuling.jvm;

import java.util.ArrayList;

/**
 * 模拟内存溢出
 */
public class JvmOutOfMemory {
    public static void main(String[] args) throws InterruptedException {
        ArrayList<JvmOutOfMemory> list = new ArrayList<JvmOutOfMemory>();
        while(true){
            list.add(new JvmOutOfMemory());
            Thread.sleep(10);
        }
    }
}
