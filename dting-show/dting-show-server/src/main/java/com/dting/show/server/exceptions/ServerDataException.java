package com.dting.show.server.exceptions;

import com.dting.exceptions.IException;
import com.dting.exceptions.code.IExceptionCode;

/**
 * *************************************************<br/>
 * 服务列表信息异常<br/>
 * ************************************************<br/>
 *
 * @author huangfu
 * @date 2022/12/3 14:04
 */
public class ServerDataException extends IException {
    private static final long serialVersionUID = 8373709678211494447L;

    public ServerDataException(IExceptionCode iExceptionCode) {
        super(iExceptionCode);
    }
}
