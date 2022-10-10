package com.dting.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常日志信息
 *
 * @author huangfu
 * @date 2022年8月18日10:19:48
 */
public class DtingLogUtil {

    /**
     * 异常堆栈兑取
     *
     * @param throwable 异常信息
     * @param detailed  是否获取详细的异常信息
     * @return 异常堆栈
     */
    public static String messageRead(Throwable throwable, boolean detailed) {
        if (throwable == null) {
            return "";
        }
        if (detailed) {
            StringWriter sw = new StringWriter();
            try (PrintWriter pw = new PrintWriter(sw)) {
                throwable.printStackTrace(pw);
                return sw.toString();
            }
        } else {
            return throwable.getMessage();
        }
    }

    /**
     * 异常堆栈兑取
     *
     * @param throwable 异常信息
     * @return 异常堆栈
     */
    public static String messageRead(Throwable throwable) {
        return DtingLogUtil.messageRead(throwable, true);
    }
}