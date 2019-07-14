package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Installation;
import org.jeecg.modules.system.mapper.ClientMapper;
import org.jeecg.modules.system.mapper.InstallationMapper;
import org.jeecg.modules.system.service.IClientService;
import org.jeecg.modules.system.service.InstallationService;
import org.jeecg.modules.system.vo.InstallationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InstallationServiceImpI extends ServiceImpl<InstallationMapper, Installation> implements InstallationService {
    @Autowired
    private InstallationMapper installationMapper;

    @Override
    public IPage<InstallationVO> findSelect(Page page, String clientId) {
        return page.setRecords(installationMapper.findSelect(clientId));
    }

    @Override
    public boolean Updeteopen(Installation installation) {
        return installationMapper.Updeteopen(installation);
    }

    @Override
    public boolean DeleteInstall(String equipmentId) {
        return installationMapper.DeleteInstall(equipmentId);
    }

    @Override
    public boolean EquAdd(Installation installation) {
        return installationMapper.EquAdd(installation);
    }

}
