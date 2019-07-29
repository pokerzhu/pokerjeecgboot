package org.jeecg.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.service.ISysUserService;
import org.jeecg.modules.system.service.SysAnalysisService;
import org.jeecg.modules.system.vo.CommodityDiagramVo;
import org.jeecg.modules.system.vo.DailyVO;
import org.jeecg.modules.system.vo.DiagramVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@Api(tags="设备表")
@RestController
@RequestMapping("/demo/sysanalysis")
public class SysAnalysisController {
    @Autowired
    private SysAnalysisService sysAnalysisService;
    @Autowired
    private ISysUserService iSysUserService;

    /*查询设备数量*/
    @GetMapping(value = "/sysanalysis")
    public Result<JSONObject> sysanalysis(){
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        Integer count = sysAnalysisService.SelectByCount();
        obj.put("count",count);
        result.setResult(obj);
        result.success("查询成功");
        System.out.println(count);
        return result;
    }

    /*查询异常设备数量*/
    @GetMapping(value = "/sysanalysisyc")
    public Result<JSONObject> sysanalysisyc(){
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        Integer count = sysAnalysisService.SelectByYcCount();
        obj.put("count",count);
        result.setResult(obj);
        result.success("查询成功");
        System.out.println(count);
        return result;
    }

    /*查询每个代理设备数量*/
    @GetMapping(value = "/sysanalysisUserId")
    public Result<List<DiagramVo>> sysanalysisUserId(){
            Result<List<DiagramVo>> result = new Result<List<DiagramVo>>();
//            LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
//            String userId =iSysUserService.setUser(sysUser.getId());
//            System.out.println(userId);
            try {
                List<DiagramVo> count = sysAnalysisService.SelectByUserId();
                result.setResult(count);
                result.success("查询成功");
                System.out.println(count);
                return result;
            }catch (Exception e){
                log.error(e.getMessage(),e);
                result.error500("操作失败");
            }
                return result;
    }
    /*查询预计下周异常设备数量*/
    @GetMapping(value = "/sysanalysisTime")
    public Result<JSONObject> sysanalysisTime(){
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        List<String> count = sysAnalysisService.SelectNextWeek();
        obj.put("count",(count.size()));
        result.setResult(obj);
        result.success("查询成功");
        System.out.println(count);
        return result;
    }


    /*查询预计下周异常设备异常时间和数量*/
    @GetMapping(value = "/sysanalysisDaily")
    public Result<List<DailyVO>> sysanalysisDaily(){
        Result<List<DailyVO>> result = new Result<List<DailyVO>>();
//        JSONObject obj = new JSONObject();
        List<DailyVO> count = sysAnalysisService.SelectDaily();
//        obj.put("count",(count.size()));
        result.setResult(count);
        result.success("查询成功");
        System.out.println(count);
        return result;
    }
    /*查询设备安装比例*/
    @GetMapping(value = "/SelectPercentage")
    public Result<JSONObject> SelectPercentage(){
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        String count = sysAnalysisService.SelectPercentage();
        obj.put("count",(count));
        result.setResult(obj);
        result.success("查询成功");
        System.out.println(count);
        return result;
    }

    /*查询商品设备数量 */
    @GetMapping(value = "/sysanalysisCommodity")
    public Result<List<CommodityDiagramVo>> sysanalysisCommodity(){
        Result<List<CommodityDiagramVo>> result = new Result<List<CommodityDiagramVo>>();
        List<CommodityDiagramVo> count = sysAnalysisService.SelectCommodity();
        System.out.println(count);
        result.setResult(count);
        result.success("查询成功");
        return result;
    }
}
