package org.jeecg.modules.system.service.impl;

import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.mapper.ClientMapper;
import org.jeecg.modules.system.service.IClientService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper,Client> implements IClientService {

}
