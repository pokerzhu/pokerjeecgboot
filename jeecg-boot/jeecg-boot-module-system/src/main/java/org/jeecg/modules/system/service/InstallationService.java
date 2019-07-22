package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.system.entity.Filterelement;
import org.jeecg.modules.system.entity.Installation;
import org.jeecg.modules.system.vo.InstallationVO;

import java.util.List;

public interface InstallationService extends IService<Installation> {
    /**
     *
     * @param page
     * @param clientId
     * @return
     */
    IPage<InstallationVO> findSelect(Page page, String clientId);
    /**
     * 修改设备当前状态
     * @param installationVO
     * @return
     */
    boolean Updeteopen(Installation installationVO);

    /**
     * 删除
     * @param clientId
     * @return
     */
    boolean DeleteInstall(@Param("equipmentId") String equipmentId);

    /**
     * 客户设备添加
     * @param installation
     */
    boolean EquAdd(Installation installation);

}
