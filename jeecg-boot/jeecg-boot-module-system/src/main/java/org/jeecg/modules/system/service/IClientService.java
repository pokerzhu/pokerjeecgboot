package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Equipment;

import java.util.List;

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

    /**
     * 查询该用户名下的设备
     * @param clientId
     * @return
     */
    IPage<Equipment> SelectByEquId(Page page,@Param("clientId")String clientId);
}
