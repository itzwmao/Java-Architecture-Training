package com.hsbc.tuling.classloader;

/**
 * 类懒加载，使用才加载
 * 加载->验证->准备->初始化->使用->销毁
 */
public class LazyLoad {
    static{
        System.out.println("I am load LazyLoad!");
    }
    public static void main(String[] args){
        B b = null;
        new A();
    }
}
class A{
    static {
        System.out.println("I am load A!");
    }
    public A(){
        System.out.println("I am init class A!");
    }
}

class B{
    static {
        System.out.println("I am load B!");
    }
    public B(){
        System.out.println("I am init class B!");
    }
}
//out put:
//I am load LazyLoad!
//I am load A!
//I am init class A!