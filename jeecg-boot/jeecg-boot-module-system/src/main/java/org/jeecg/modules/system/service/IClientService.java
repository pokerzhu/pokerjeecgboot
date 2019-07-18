package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Client;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
public interface IClientService extends IService<Client> {
//    Boolean ClientUpd(Client client);
    /**
     * 查询该设备关联的用户
     * @param equipmentId
     * @return
     */
    Integer ClientById(@Param("clientId") String clientId);
}
