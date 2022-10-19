package com.dting.show.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dting.show.server.entity.MessageCpuData;
import com.dting.show.server.mapper.MessageCpuDataMapper;
import com.dting.show.server.service.MessageCpuDataService;
import org.springframework.stereotype.Service;

/**
 * CPU业务操作类
 *
 * @author huangfu
 * @date 2022年10月19日14:42:55
 */
@Service
public class MessageCpuDataServiceImpl extends ServiceImpl<MessageCpuDataMapper, MessageCpuData> implements MessageCpuDataService {
}
