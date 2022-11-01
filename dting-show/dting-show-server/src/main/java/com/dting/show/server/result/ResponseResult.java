package com.dting.show.server.result;

import com.dting.exceptions.code.IExceptionCode;
import com.dting.show.server.interactive.codes.CurrencyRequestEnum;
import lombok.Data;

/**
 * 全局响应结果集
 *
 * @author huangfu
 * @date 2022年11月1日18:07:29
 */
@Data
@SuppressWarnings("all")
public class ResponseResult<T> {

    /**
     * 响应码
     */
    private String code;

    /**
     * 响应信息
     */
    private String message;

    /**
     * 响应结果
     */
    private T result;

    public ResponseResult(String code, T result) {
        this.code = code;
        this.result = result;
    }

    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult() {
    }

    /**
     * 成功的数据
     *
     * @param result 结果
     * @param <T>    泛型
     * @return 包装结果
     */
    public static <T> ResponseResult<T> success(T result) {
        return new ResponseResult(CurrencyRequestEnum.REQUEST_SUCCESS.getCode(), result);
    }

    /**
     * 失败额数据
     *
     * @param iExceptionCode 错误信息
     * @return 包装好后的数据
     */
    public static <T> ResponseResult<T> error(IExceptionCode iExceptionCode) {
        return new ResponseResult(iExceptionCode.getCode(), iExceptionCode.getMessage());
    }

    /**
     * 失败额数据
     *
     * @param code    错误码
     * @param message 错误信息
     * @param <T>     泛型数据
     * @return 包装好后的数据
     */
    public static <T> ResponseResult<T> error(String code, String message) {
        return new ResponseResult(code, message);
    }
}
