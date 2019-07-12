package org.jeecg.modules.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Installation;
import org.jeecg.modules.system.service.InstallationService;
import org.jeecg.modules.system.vo.InstallationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
@Slf4j
@Api(tags="设备开关权限表")
@RestController
@RequestMapping("/demo/installation")
public class InstallationController {
    @Autowired
    private InstallationService installationService;

    @AutoLog(value = "设备开关权限表-分页列表查询")
    @ApiOperation(value="设备开关权限表-分页列表查询", notes="设备开关权限表-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<InstallationVO>> queryPageList(InstallationVO installationVO,
                                                       @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                       @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                       HttpServletRequest req) {
        Result<IPage<InstallationVO>> result = new Result<IPage<InstallationVO>>();
        QueryWrapper<InstallationVO> queryWrapper = QueryGenerator.initQueryWrapper(installationVO, req.getParameterMap());
        Page<InstallationVO> page = new Page<InstallationVO>(pageNo, pageSize);
        IPage<InstallationVO> pageList = installationService.findSelect(page,installationVO.getClientId());
        System.out.println(installationVO+"-------------------------------");
        result.setSuccess(true);
        result.setResult(pageList);
        return result;
    }
    /**
     *  编辑
     * @param commodity
     * @return
     */
    @AutoLog(value = "设备关系-编辑")
    @ApiOperation(value="商品表-编辑", notes="商品表-编辑")
    @PutMapping(value = "/updateopen")
    public Result<InstallationVO> edit(@RequestBody InstallationVO commodity) {
        Result<InstallationVO> result = new Result<InstallationVO>();
        Installation commodityEntity = installationService.getById(commodity.getOpenId());
        if(commodityEntity==null) {
            result.error500("未找到对应实体");
        }else {
            if(commodity.getOpen()==0){
                commodityEntity.setOpen(1);
            }else{
                commodityEntity.setOpen(0);
            }
            System.out.println(commodityEntity);
            boolean ok = installationService.Updeteopen(commodityEntity);
            //TODO 返回false说明什么？
            if(ok) {
                result.success("修改成功!");
            }else{
                result.success("修改失败!");
            }
        }
        return result;
    }
    /**
     *   通过id删除
     * @param openId
     * @return
     */
    @AutoLog(value = "客户表-通过id删除")
    @ApiOperation(value="客户表-通过id删除", notes="客户表-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<Installation> delete(@RequestParam(name="id",required=true) String openId) {
        Result<Installation> result = new Result<Installation>();
        Installation installation = installationService.getById(openId);
        System.out.println(openId+"*********************************");
        if(installation==null) {
            result.error500("未找到对应实体");
        }else {
            boolean ok = installationService.removeById(openId);
            if(ok) {
                result.success("删除成功!");
            }
        }
        return result;
    }
}
