package com.hsbc.tuling.jvm;

public class JvmTest {

    //静态变量、常量和类元信息在方法区
    private static int temp = 5;
    private static final String T = "final value";

    public static void main(String[] args){
        //a,b,c 都是局部变量
        //3,4,5的算法操作在操作数栈完成
        int a = 3;
        int b = 4;
        int c = (a+b)*5;
        //方法出口，栈帧的一部分
        out(c);
    }

    private static void out(int tmp) {
        //对象的变量在栈线程的局部变量表
        JvmTest jvmTest;
        //new 出来的对象实例在堆里面
        new JvmTest();
        System.out.println(tmp);
    }
}
