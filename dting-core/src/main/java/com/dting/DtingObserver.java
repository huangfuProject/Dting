package com.dting;

/**
 * 观察者
 *
 * @author huangfu
 * @date 2022年10月14日17:19:16
 */
public abstract class DtingObserver<T extends Subject> {

    /**
     * 监听的数据发生了变更
     *
     * @param data 数据
     */
    public void update(Subject data) {
        doUpdate((T) data);
    }

    /**
     * 开始通知观察者发生变更
     *
     * @param data 变更的数据
     */
    public abstract void doUpdate(T data);

    @Override
    public abstract int hashCode();

    @Override
    public abstract boolean equals(Object obj);
}

