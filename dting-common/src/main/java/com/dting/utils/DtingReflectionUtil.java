package com.dting.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 反射相关工具类
 *
 * @author huangfu
 * @date 2022年10月14日17:35:26
 */
public class DtingReflectionUtil {

    /**
     * 获取泛型类型
     *
     * @param sourceObj 要获取的原始类
     * @return 泛型的类型
     */
    public static Class<?> getParentGenericClass(Object sourceObj) {
        //获取bean类型的父类
        Type superclass = sourceObj.getClass().getGenericSuperclass();
        //验证 该类型是不是参数类型化   所谓的参数类型化就是带泛型的，不是就是没带
        if (superclass instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) superclass;
            return (Class<?>) parameterizedType.getActualTypeArguments()[0];
        }

        return null;
    }
}
