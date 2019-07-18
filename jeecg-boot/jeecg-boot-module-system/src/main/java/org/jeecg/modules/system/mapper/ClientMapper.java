package org.jeecg.modules.system.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Client;

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

}
