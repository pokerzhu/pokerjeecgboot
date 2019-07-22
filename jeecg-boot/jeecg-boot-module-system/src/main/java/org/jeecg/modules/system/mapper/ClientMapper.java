package org.jeecg.modules.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Equipment;

import java.util.List;

import java.util.List;

/**
 * @Description: 客户表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 * @name: hehao
 */
public interface ClientMapper extends BaseMapper<Client> {
//    /**
//     * 更新
//     * @param client
//     */
//    Boolean ClientUpd(Client client);

    /**
     * 查询该设备关联的用户
     * @param equipmentId
     * @return
     */
    @Select("SELECT count(1) FROM installation_open WHERE client_id=#{clientId}")
    Integer ClientById(@Param("clientId") String clientId);

    /**
     * 根据手机号判断客户登陆
     * @param
     * @return
     */
    @Select("select client_id,client_name,phone,address FROM client WHERE phone = #{phone}")
    Client ClientByphone(@Param("phone") String phone);

    /**
     * 查询该用户名下的设备
     * @param clientId
     * @return
     */
    @Select("SELECT equipment_id FROM equipment WHERE client_id=#{clientId}")
    List<Equipment> SelectByEquId(@Param("clientId")String clientId);

    /**
     * 判断手机号码的唯一验证
     * @param phone
     * @return
     */
    @Select("select * from client WHERE phone = #{phone}")
    Client countphone(String phone);
}
