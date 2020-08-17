package com.hsbc.tuling.classloader;

import java.util.*;

/**
 * 类加载器
 * 1. 引导类加载器
 * 2. 扩展类加载器
 * 3. 应用来加载器
 * 4. 自定义类加载器
 */
public class ClassLoader {

    public static void main(String[] args){
        Properties properties = System.getProperties();
        properties.forEach((key, value) -> {
            System.out.println("Key:"+key+" Value:"+value);
        });
        System.out.println(ClassLoader.class.getClassLoader());
        System.out.println(ClassLoader.class.getClassLoader().getParent());
        System.out.println(Math.class.getClassLoader());
    }
}

//sun.misc.Launcher$AppClassLoader@18b4aac2  应用类加载器
//sun.misc.Launcher$ExtClassLoader@5b6f7412     扩展类加载器
//null  引导类加载器