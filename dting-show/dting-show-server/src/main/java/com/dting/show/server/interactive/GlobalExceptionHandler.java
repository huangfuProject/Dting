package com.dting.show.server.interactive;

import com.dting.exceptions.IException;
import com.dting.show.server.interactive.codes.CurrencyRequestEnum;
import com.dting.show.server.result.ResponseResult;
import com.dting.utils.DtingLogUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author huangfu
 * @date 2022年11月1日18:25:24
 */
@Slf4j
@SuppressWarnings("all")
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 针对自定义异常的拦截实现
     *
     * @param e 异常信息
     * @return 包装好后的数据
     */
    @ExceptionHandler(IException.class)
    public ResponseResult iExceptionHandler(IException e) {
        log.error(e.getExceptionCode().defaultMessage());
        log.error(DtingLogUtil.messageRead(e));
        return ResponseResult.error(CurrencyRequestEnum.REQUEST_ERROR.getCode(), e.getExceptionCode().getMessage());
    }

    /**
     * 针对全局异常的拦截实现
     *
     * @param e 异常信息
     * @return 包装好后的数据
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception e) {
        log.error(DtingLogUtil.messageRead(e, true));
        return ResponseResult.error(CurrencyRequestEnum.REQUEST_ERROR.getCode(), DtingLogUtil.CONVENTION_LOG);
    }
}
