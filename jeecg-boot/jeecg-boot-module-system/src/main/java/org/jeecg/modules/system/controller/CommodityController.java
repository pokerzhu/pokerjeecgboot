package org.jeecg.modules.system.controller;

import java.util.ArrayList;
import java.util.Arrays;
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

import org.jeecg.modules.system.entity.Client;
import org.jeecg.modules.system.entity.Commodity;
import org.jeecg.modules.system.service.ICommodityService;
import org.jeecg.modules.system.service.ICommodityTypeService;
import org.jeecg.modules.system.vo.CommodityVO;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

 /**
 * @Description: 商品表
 * @Author: jeecg-boot
 * @Date:   2019-07-04
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品表")
@RestController
@RequestMapping("/commodity/commodity")
public class CommodityController {
	 @Autowired
	 private ICommodityService commodityService;
	 @Autowired
     private ICommodityTypeService iCommodityTypeService;
	 /**
	  * 分页列表查询
	  * @param commodity
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "商品表-分页列表查询")
	 @ApiOperation(value="商品表-分页列表查询", notes="商品表-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<IPage<CommodityVO>> queryPageList(Commodity commodity,
												   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
												   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
												   HttpServletRequest req) {
		 Result<IPage<CommodityVO>> result = new Result<IPage<CommodityVO>>();
/*		 QueryWrapper<CommodityVO> queryWrapper = QueryGenerator.initQueryWrapper(commodity, req.getParameterMap());*/
		 Page<CommodityVO> page = new Page<CommodityVO>(pageNo, pageSize);
		 IPage<CommodityVO> pageList = commodityService.selectCommodity(page);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }
     /**
      * 分页列表查询
      * @param pageSize
      * @param req
      * @return
      */
     @AutoLog(value = "模糊查询商品")
     @ApiOperation(value="模糊查询商品", notes="模糊查询商品")
     @GetMapping(value = "/likeCommodity")
     public Result<IPage<CommodityVO>> likeCommodity(CommodityVO commodityVO,
                                                     @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                     @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                     HttpServletRequest req) {
         Result<IPage<CommodityVO>> result = new Result<IPage<CommodityVO>>();
         /*		 QueryWrapper<CommodityVO> queryWrapper = QueryGenerator.initQueryWrapper(commodity, req.getParameterMap());*/
         Page<CommodityVO> page = new Page<CommodityVO>(pageNo, pageSize);
         IPage<CommodityVO> pageList = commodityService.selectLike(page,commodityVO.getCommodityName());
         result.setSuccess(true);
         result.setResult(pageList);
         return result;
     }

//     /*模糊查询商品*/
//     @GetMapping(value = "/likeCommodity")
//     public List<CommodityVO> likeCommodity(CommodityVO commodityVO){
//         //获取商品表数据
//         List<CommodityVO> records = commodityService.selectLike(commodityVO.getCommodityName());
//         System.out.println("进来了哦哦哦哦哦哦哦哦哦哦哦哦");
//         System.out.println(records);
//         return records;
//     }

	 /*查询商品*/
	 @GetMapping(value = "/selCommodity")
	 public List<Commodity> selCommodity(){
		 //获取滤芯表数据
		 List<Commodity> records = commodityService.list();
		 System.out.println(records);
		 return records;
	 }

	 /**
	  *   添加
	  * @param commodity
	  * @return
	  */
	 @AutoLog(value = "商品表-添加")
	 @ApiOperation(value="商品表-添加", notes="商品表-添加")
	 @PostMapping(value = "/add")
	 public Result<Commodity> add(@RequestBody Commodity commodity) {
		 Result<Commodity> result = new Result<Commodity>();
		 try {
			 commodityService.save(commodity);
			 result.success("添加成功！");
		 } catch (Exception e) {
			 log.error(e.getMessage(),e);
			 result.error500("操作失败");
		 }
		 return result;
	 }

	 /**
	  *  编辑
	  * @param commodity
	  * @return
	  */
	 @AutoLog(value = "商品表-编辑")
	 @ApiOperation(value="商品表-编辑", notes="商品表-编辑")
	 @PutMapping(value = "/edit")
	 public Result<Commodity> edit(@RequestBody Commodity commodity) {
		 Result<Commodity> result = new Result<Commodity>();
		 Commodity commodityEntity = commodityService.getById(commodity.getCommodityId());
		 if(commodityEntity==null) {
			 result.error500("未找到对应实体");
		 }else {
			 boolean ok = commodityService.updateById(commodity);
			 //TODO 返回false说明什么？
			 if(ok) {
				 result.success("修改成功!");
			 }
		 }

		 return result;
	 }

	 /**
	  *   通过id删除
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "商品表-通过id删除")
	 @ApiOperation(value="商品表-通过id删除", notes="商品表-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<Commodity> delete(@RequestParam(name="id",required=true) String id) {
		 Result<Commodity> result = new Result<Commodity>();
		 Commodity commodity = commodityService.getById(id);
		 if(commodity==null) {
			 result.error500("未找到对应实体");
		 }else {
             Integer integer = commodityService.EquipmentById(id);
             if (integer>0){
                 result.error500("该商品下存有设备,不能删除");
             }else {
                 boolean ok = commodityService.removeById(id);
                 if(ok) {
                     result.success("删除成功!");
                 }
             }
		 }
		 return result;
	 }

	 /**
	  *  批量删除
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "商品表-批量删除")
	 @ApiOperation(value="商品表-批量删除", notes="商品表-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<Commodity> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		 Result<Commodity> result = new Result<Commodity>();
		 if(ids==null || "".equals(ids.trim())) {
			 result.error500("参数不识别！");
		 }else {
			 this.commodityService.removeByIds(Arrays.asList(ids.split(",")));
			 result.success("删除成功!");
		 }
		 return result;
	 }

	 /**
	  * 通过id查询
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "商品表-通过id查询")
	 @ApiOperation(value="商品表-通过id查询", notes="商品表-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<Commodity> queryById(@RequestParam(name="id",required=true) String id) {
		 Result<Commodity> result = new Result<Commodity>();
		 Commodity commodity = commodityService.getById(id);
		 if(commodity==null) {
			 result.error500("未找到对应实体");
		 }else {
			 result.setResult(commodity);
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
		 QueryWrapper<Commodity> queryWrapper = null;
		 try {
			 String paramsStr = request.getParameter("paramsStr");
			 if (oConvertUtils.isNotEmpty(paramsStr)) {
				 String deString = URLDecoder.decode(paramsStr, "UTF-8");
				 Commodity commodity = JSON.parseObject(deString, Commodity.class);
				 queryWrapper = QueryGenerator.initQueryWrapper(commodity, request.getParameterMap());
			 }
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }

		 //Step.2 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 List<Commodity> pageList = commodityService.list(queryWrapper);
		 //导出文件名称
		 mv.addObject(NormalExcelConstants.FILE_NAME, "商品表列表");
		 mv.addObject(NormalExcelConstants.CLASS, Commodity.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品表列表数据", "导出人:Jeecg", "导出信息"));
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
				 List<Commodity> listCommoditys = ExcelImportUtil.importExcel(file.getInputStream(), Commodity.class, params);
				 for (Commodity commodityExcel : listCommoditys) {
					 commodityService.save(commodityExcel);
				 }
				 return Result.ok("文件导入成功！数据行数:" + listCommoditys.size());
			 } catch (Exception e) {
				 log.error(e.getMessage(),e);
				 return Result.error("文件导入失败:"+e.getMessage());
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
