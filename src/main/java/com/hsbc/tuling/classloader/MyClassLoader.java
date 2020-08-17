package com.hsbc.tuling.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * A. 自定义类加载器
 *  1. 继承ClassLoader
 *  2. 重写fingClass方法
 * B. 打破双亲委派机制
 *  基于自定义类加载器的基础上，重写loadClass方法，不从父类找加载了的类
 */
public class MyClassLoader extends java.lang.ClassLoader {

    private String classPath;
    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }

    private byte[] loadByte(String name) throws Exception{
        name = name.replaceAll("\\.","/");
        FileInputStream fis = new FileInputStream(classPath+ File.separator+name+".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        return data;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] data = this.loadByte(name);
            //加载类
            return defineClass(name,data,0,data.length);
        }catch (Exception e){
            e.printStackTrace();
            throw new ClassNotFoundException();
        }
    }

    /**
     * 打破双亲委派机制
     * @param name
     * @param resolve
     * @return
     * @throws ClassNotFoundException
     */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();
                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    if(name.startsWith("com.hsbc.tuling")){
                        c = findClass(name);
                    }else{
                        c = this.getParent().loadClass(name);
                    }
                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    public static void main(String[] args) throws Exception{
        MyClassLoader myClassLoader = new MyClassLoader("E:/Cache");
        Class clazz = myClassLoader.loadClass("com.hsbc.tuling.TulingApplication");
        Object obj = clazz.newInstance();
        Method method = clazz.getDeclaredMethod("sout",null);
        method.invoke(obj,null);
        /**
         * 未重载loadClass时没有打破双亲委派机制，如果TulingApplication.class仍然存在于application的classes路径下面，
         * 得到的是AppClassLoader加载的TulingApplication，如果把application的classes路径下的TulingApplication.class
         * 删除，再运行这个代码，就返回的是AppClassLoader加载的TulingApplication
         */
        System.out.println(clazz.getClassLoader());//sun.misc.Launcher$AppClassLoader@18b4aac2
        /**
         * 重载了loadClass后，打破了双亲委派机制，就使用的自定义加载器加载
         */
        System.out.println(clazz.getClassLoader());//com.hsbc.tuling.classloader.MyClassLoader@27c170f0
    }
}
