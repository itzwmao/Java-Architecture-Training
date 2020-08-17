package com.hsbc.tuling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TulingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TulingApplication.class, args);
    }
    public void sout(){
        System.out.println("自定义类加载器调用的方法！");
    }
}
