package org.jeecg.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.system.service.EquipmentDbService;
import org.jeecg.modules.system.vo.EquipmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Api(tags="设备表")
@RestController
@RequestMapping("/demo/quipmentDb")
public class EquipmentDbController {

    @Autowired
    private EquipmentDbService equipmentDbService;
    @AutoLog(value = "设备调拨分页列表查询")
    @ApiOperation(value="设备调拨分页列表查询", notes="设备调拨分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<EquipmentVO>> EquipmentDb(EquipmentVO equipmentVO,
                                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                  HttpServletRequest req) {
        Result<IPage<EquipmentVO>> result = new Result<IPage<EquipmentVO>>();
        QueryWrapper<EquipmentVO> queryWrapper = QueryGenerator.initQueryWrapper(equipmentVO, req.getParameterMap());
        Page<EquipmentVO> page = new Page<EquipmentVO>(pageNo, pageSize);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        IPage<EquipmentVO> pageList = equipmentDbService.EquipmentDbShow(page,sysUser.getOrgCode());
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
}
