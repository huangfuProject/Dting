package com.dting;

import cn.hutool.core.collection.CollectionUtil;
import com.dting.utils.DtingReflectionUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 被观察者
 *
 * @author huangfu
 * @date 2022年10月14日17:20:49
 */
public abstract class Subject {
    /**
     * 复制写数组的集合
     */
    private final static Map<String, CopyOnWriteArraySet<DtingObserver<?>>> OBSERVER_MAP = new ConcurrentHashMap<>();

    /**
     * 增加一个观察者
     *
     * @param observer 观察者
     */
    public static void attachObserver(DtingObserver<?> observer) {
        String genericClassName = genericClassName(observer);
        OBSERVER_MAP.computeIfAbsent(genericClassName, fun -> new CopyOnWriteArraySet<>()).add(observer);
    }

    /**
     * 删除一个观察者
     *
     * @param observer 观察者
     */
    public static void removeObserver(DtingObserver<?> observer) {
        String genericClassName = genericClassName(observer);
        CopyOnWriteArraySet<DtingObserver<?>> dtingObservers = OBSERVER_MAP.get(genericClassName);
        if (CollectionUtil.isNotEmpty(dtingObservers)) {
            dtingObservers.remove(observer);
        }
    }

    /**
     * 通知全部的观察者
     */
    public void noticeAllDtingObserver() {
        String name = this.getClass().getName();
        CopyOnWriteArraySet<DtingObserver<?>> dtingObservers = OBSERVER_MAP.get(name);

        if (CollectionUtil.isNotEmpty(dtingObservers)) {
            dtingObservers.forEach(dtingObserver -> {
                dtingObserver.update(this);
            });
        }
    }


    /**
     * 获取泛型的全限定名
     *
     * @param observer 观察者
     * @return 返回观察者观察的对象类型名
     */
    private static String genericClassName(DtingObserver<?> observer) {
        Class<?> genericClass = DtingReflectionUtil.getParentGenericClass(observer);
        if (genericClass == null) {
            throw new IllegalArgumentException("传入的对象上层父类没有泛型，或者父类不是一个类！");
        }
        return genericClass.getName();
    }

}


