package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.mapper.ClientMapper;
import org.jeecg.modules.system.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper,Client> implements IClientService {
    @Autowired
    private ClientMapper clientMapper;
    @Override
    public Integer ClientById(String clientId) {
        return clientMapper.ClientById(clientId);
    }

    @Override
    public IPage<Equipment> SelectByEquId(Page page, String clientId) {
        return page.setRecords(clientMapper.SelectByEquId(clientId));
    }

    @Override
    public Client countphone(String phone) {
        return clientMapper.countphone(phone);
    }
//    @Autowired
//    private ClientMapper clientMapper;
//    @Override
//    public Boolean ClientUpd(Client client) {
//       Boolean bool=clientMapper.ClientUpd(client);
//        return bool;
//    }

    @Override
    public Client ClientByphone(String phone) { return clientMapper.ClientByphone(phone); }
}
