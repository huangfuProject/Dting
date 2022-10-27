package com.dting.sdk.test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * *************************************************<br/>
 * <br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/10/27 20:50
 */
public class TestThreadPool {

    private final static ThreadPoolExecutor test = new ThreadPoolExecutor(1,1,60L, TimeUnit.DAYS,new ArrayBlockingQueue<>(1024));

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> aClass = Class.forName("com.huangfu.TestThreadPool");
        //获取静态属性
        Field testField = aClass.getDeclaredField("test");
        //破坏其private 不可见的特性
        testField.setAccessible(true);
        //修改静态属性  final 不可修改的特性
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        int anInt = modifiersField.getInt(testField);
        //记录原值
        modifiersField.setInt(testField, testField.getModifiers() & ~Modifier.FINAL);
        ThreadPoolExecutor test111 = new ThreadPoolExecutor(10,10,60L, TimeUnit.DAYS,new ArrayBlockingQueue<>(1024));
        //修改为新值
        testField.set(null, test111);
        //恢复原址
        modifiersField.setInt(testField, anInt);
        modifiersField.setAccessible(false);
        System.out.println(test.getCorePoolSize());

    }
}
