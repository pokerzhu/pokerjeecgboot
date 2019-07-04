package org.jeecg.modules.system.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.util.oConvertUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.Filterelement;
import org.jeecg.modules.system.service.IFilterelementService;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 滤芯表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
@Slf4j
@Api(tags="滤芯表")
@RestController
@RequestMapping("/demo/filterelement")
public class FilterelementController {
     @Autowired
     private IFilterelementService filterelementService;


     @Value(value = "${jeecg.path.upload}")
     private String uploadpath;

     @PostMapping(value = "/upload")
     public Result<?> upload(HttpServletRequest request, HttpServletResponse response) {
         //结果集
         Result<?> result = new Result<>();
         try {
             //上传路径
             String ctxPath = uploadpath;
             System.out.println(ctxPath);
             //文件名
             String fileName = null;
             System.out.println(fileName);
             //文件
             String bizPath = "upFiles";
             System.out.println(bizPath);
             //获得当前时间
             String nowday = new SimpleDateFormat("yyyyMMdd").format(new Date());
             //文件流
             File file = new File(ctxPath + File.separator + bizPath + File.separator + nowday);
             System.out.println(ctxPath + File.separator + bizPath + File.separator + nowday);
             if (!file.exists()) {//判断文件夹是否存在
                 file.mkdirs();// 创建文件根目录
             }
             MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
             MultipartFile mf = multipartRequest.getFile("file");// 获取上传文件对象
             String orgName = mf.getOriginalFilename();// 获取文件名
             fileName = orgName.substring(0, orgName.lastIndexOf(".")) + "_" + System.currentTimeMillis() + orgName.substring(orgName.indexOf("."));
             String savePath = file.getPath() + File.separator + fileName;
             System.out.println(savePath);
             File savefile = new File(savePath);
             FileCopyUtils.copy(mf.getBytes(), savefile);
             //获取图片路径
             String dbpath = bizPath + File.separator + nowday + File.separator + fileName;
             //获取图片名
             File fil1 = new File(dbpath);
             System.out.println(fil1.getName());
             //获取后缀
             String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
             Date date = new Date();
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
             filterelementService.insertImg(fil1.getName(), dbpath, null, suffix, dateFormat.format(date), fil1.length());
             System.out.println(dbpath);
             if (dbpath.contains("\\")) {
                 dbpath = dbpath.replace("\\", "/");
             }
             result.setMessage(dbpath);
             result.setSuccess(true);
         } catch (IOException e) {
             result.setSuccess(false);
             result.setMessage(e.getMessage());
             log.error(e.getMessage(), e);
         }
         return result;
     }
     /**
      * 分页列表查询
      *
      * @param filterelement
      * @param pageNo
      * @param pageSize
      * @param req
      * @return
      */
     @AutoLog(value = "滤芯表-分页列表查询")
     @ApiOperation(value = "滤芯表-分页列表查询", notes = "滤芯表-分页列表查询")
     @GetMapping(value = "/list")
     public Result<IPage<Filterelement>> queryPageList(Filterelement filterelement,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest req) {
         Result<IPage<Filterelement>> result = new Result<IPage<Filterelement>>();
         QueryWrapper<Filterelement> queryWrapper = QueryGenerator.initQueryWrapper(filterelement, req.getParameterMap());
         Page<Filterelement> page = new Page<Filterelement>(pageNo, pageSize);
         IPage<Filterelement> pageList = filterelementService.page(page, queryWrapper);
         result.setSuccess(true);
         result.setResult(pageList);
         return result;
     }

     /**
      * 添加
      *
      * @param filterelement
      * @return
      */
     @AutoLog(value = "滤芯表-添加")
     @ApiOperation(value = "滤芯表-添加", notes = "滤芯表-添加")
     @PostMapping(value = "/add")
     public Result<Filterelement> add(@RequestBody Filterelement filterelement) {
         Result<Filterelement> result = new Result<Filterelement>();
         try {
             filterelementService.save(filterelement);
             result.success("添加成功！");
         } catch (Exception e) {
             log.error(e.getMessage(), e);
             result.error500("操作失败");
         }
         return result;
     }

     /**
      * 编辑
      *
      * @param filterelement
      * @return
      */
     @AutoLog(value = "滤芯表-编辑")
     @ApiOperation(value = "滤芯表-编辑", notes = "滤芯表-编辑")
     @PutMapping(value = "/edit")
     public Result<Filterelement> edit(@RequestBody Filterelement filterelement) {
         Result<Filterelement> result = new Result<Filterelement>();
         Filterelement filterelementEntity = filterelementService.getById(filterelement.getFilterelementId());
         if (filterelementEntity == null) {
             result.error500("未找到对应实体");
         } else {
             boolean ok = filterelementService.updateById(filterelement);
             //TODO 返回false说明什么？
             if (ok) {
                 result.success("修改成功!");
             }
         }

         return result;
     }

     /**
      * 通过id删除
      *
      * @param id
      * @return
      */
     @AutoLog(value = "滤芯表-通过id删除")
     @ApiOperation(value = "滤芯表-通过id删除", notes = "滤芯表-通过id删除")
     @DeleteMapping(value = "/delete")
     public Result<Filterelement> delete(@RequestParam(name = "id", required = true) String id) {
         Result<Filterelement> result = new Result<Filterelement>();
         Filterelement filterelement = filterelementService.getById(id);
         if (filterelement == null) {
             result.error500("未找到对应实体");
         } else {
             boolean ok = filterelementService.removeById(id);
             if (ok) {
                 result.success("删除成功!");
             }
         }

         return result;
     }

     /**
      * 批量删除
      *
      * @param ids
      * @return
      */
     @AutoLog(value = "滤芯表-批量删除")
     @ApiOperation(value = "滤芯表-批量删除", notes = "滤芯表-批量删除")
     @DeleteMapping(value = "/deleteBatch")
     public Result<Filterelement> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
         Result<Filterelement> result = new Result<Filterelement>();
         if (ids == null || "".equals(ids.trim())) {
             result.error500("参数不识别！");
         } else {
             this.filterelementService.removeByIds(Arrays.asList(ids.split(",")));
             result.success("删除成功!");
         }
         return result;
     }

     /**
      * 通过id查询
      *
      * @param id
      * @return
      */
     @AutoLog(value = "滤芯表-通过id查询")
     @ApiOperation(value = "滤芯表-通过id查询", notes = "滤芯表-通过id查询")
     @GetMapping(value = "/queryById")
     public Result<Filterelement> queryById(@RequestParam(name = "id", required = true) String id) {
         Result<Filterelement> result = new Result<Filterelement>();
         Filterelement filterelement = filterelementService.getById(id);
         if (filterelement == null) {
             result.error500("未找到对应实体");
         } else {
             result.setResult(filterelement);
             result.setSuccess(true);
         }
         return result;
     }

     /**
      * 导出excel
      *
      * @param request
      * @param response
      */
     @RequestMapping(value = "/exportXls")
     public ModelAndView exportXls(HttpServletRequest request, HttpServletResponse response) {
         // Step.1 组装查询条件
         QueryWrapper<Filterelement> queryWrapper = null;
         try {
             String paramsStr = request.getParameter("paramsStr");
             if (oConvertUtils.isNotEmpty(paramsStr)) {
                 String deString = URLDecoder.decode(paramsStr, "UTF-8");
                 Filterelement filterelement = JSON.parseObject(deString, Filterelement.class);
                 queryWrapper = QueryGenerator.initQueryWrapper(filterelement, request.getParameterMap());
             }
         } catch (UnsupportedEncodingException e) {
             e.printStackTrace();
         }

         //Step.2 AutoPoi 导出Excel
         ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
         List<Filterelement> pageList = filterelementService.list(queryWrapper);
         //导出文件名称
         mv.addObject(NormalExcelConstants.FILE_NAME, "滤芯表列表");
         mv.addObject(NormalExcelConstants.CLASS, Filterelement.class);
         mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("滤芯表列表数据", "导出人:Jeecg", "导出信息"));
         mv.addObject(NormalExcelConstants.DATA_LIST, pageList);
         return mv;
     }

     /**
      * 通过excel导入数据
      *
      * @param request
      * @param response
      * @return
      */
     @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
     public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
         MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
         Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
         for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
             MultipartFile file = entity.getValue();// 获取上传文件对象
             ImportParams params = new ImportParams();
             params.setTitleRows(2);
             params.setHeadRows(1);
             params.setNeedSave(true);
             try {
                 List<Filterelement> listFilterelements = ExcelImportUtil.importExcel(file.getInputStream(), Filterelement.class, params);
                 for (Filterelement filterelementExcel : listFilterelements) {
                     filterelementService.save(filterelementExcel);
                 }
                 return Result.ok("文件导入成功！数据行数:" + listFilterelements.size());
             } catch (Exception e) {
                 log.error(e.getMessage(), e);
                 return Result.error("文件导入失败:" + e.getMessage());
             } finally {
                 try {
                     file.getInputStream().close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }
         return Result.ok("文件导入失败！");
     }
 }