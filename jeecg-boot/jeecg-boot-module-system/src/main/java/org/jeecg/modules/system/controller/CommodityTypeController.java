package org.jeecg.modules.system.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import java.util.Date;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.CommodityType;
import org.jeecg.modules.system.service.ICommodityTypeService;
import org.jeecg.modules.system.service.IRelationshipService;
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
 * @Description: 商品类型
 * @Author: jeecg-boot
 * @Date:   2019-07-11
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品类型")
@RestController
@RequestMapping("/dome/commodityType")
public class CommodityTypeController {
	@Autowired
	private ICommodityTypeService commodityTypeService;
	 @Autowired
	 private IRelationshipService relationshipService;

	 /**
	  * 分页列表查询
	  * @param commodityType
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "商品类型-分页列表查询")
	 @ApiOperation(value="商品类型-分页列表查询", notes="商品类型-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<IPage<CommodityType>> queryPageList(CommodityType commodityType,
													   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													   HttpServletRequest req) {
		 Result<IPage<CommodityType>> result = new Result<IPage<CommodityType>>();
		 QueryWrapper<CommodityType> queryWrapper = QueryGenerator.initQueryWrapper(commodityType, req.getParameterMap());
		 Page<CommodityType> page = new Page<CommodityType>(pageNo, pageSize);
		 IPage<CommodityType> pageList = commodityTypeService.page(page, queryWrapper);
		 result.setSuccess(true);
		 result.setResult(pageList);
		 return result;
	 }
	 /*查询商品类型*/
	 @GetMapping(value = "/selCommodityType")
	 public List<CommodityType> selCommodityType(){
		 //获取滤芯表数据
		 List<CommodityType> records = commodityTypeService.list();
		 System.out.println(records);
		 return records;
	 }
	 /**
	  * 查询类型对应的滤芯规格
	  * @return
	  */
	 public Integer queryPageList(String typeId) {
		 Integer specification = commodityTypeService.selectspecification(typeId);
		 return specification;
	 }

	 /**
	  *   添加
	  * @param commodityType
	  * @return
	  */
	 @AutoLog(value = "商品类型-添加")
	 @ApiOperation(value="商品类型-添加", notes="商品类型-添加")
	 @PostMapping(value = "/add")
	 public Result<CommodityType> add(@RequestBody CommodityType commodityType) {
		 Result<CommodityType> result = new Result<CommodityType>();
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 try {
			 commodityType.setUpdateBy(sysUser.getRealname());
			 commodityTypeService.save(commodityType);
			 result.success("添加成功！");
		 } catch (Exception e) {
			 log.error(e.getMessage(),e);
			 result.error500("操作失败");
		 }
		 return result;
	 }

	 /**
	  *  编辑
	  * @param commodityType
	  * @return
	  */
	 @AutoLog(value = "商品类型-编辑")
	 @ApiOperation(value="商品类型-编辑", notes="商品类型-编辑")
	 @PutMapping(value = "/edit")
	 public Result<CommodityType> edit(@RequestBody CommodityType commodityType) {
		 Result<CommodityType> result = new Result<CommodityType>();
		 CommodityType commodityTypeEntity = commodityTypeService.getById(commodityType.getTypeId());
		 int count = relationshipService.selectBycommodityId(commodityType.getTypeId());//当前类型滤芯记录数
		 /*int specification = commodityTypeService.selectspecification(commodityType.getTypeId());//当前类型滤芯规格*/
			 if(commodityTypeEntity==null) {
				 result.error500("未找到对应实体");
			 }else {
				 if(count<=commodityType.getSpecification()){
					 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
					 commodityType.setUpdateBy(sysUser.getRealname());
					 commodityTypeService.updatebytypeid(commodityType);
					 result.success("修改成功!");
				 }else {
					 result.error500("更新类型滤芯规格数量不能小于现有类型滤芯数量!");
				 }
			 }
		 return result;
	 }

	 /**
	  *   通过id删除
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "商品类型-通过id删除")
	 @ApiOperation(value="商品类型-通过id删除", notes="商品类型-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<CommodityType> delete(@RequestParam(name="id",required=true) String id) {
		 Result<CommodityType> result = new Result<CommodityType>();
		 CommodityType commodityType = commodityTypeService.getById(id);

		 if(commodityType==null) {
			 result.error500("未找到对应实体");
		 }else {
			 boolean ok = commodityTypeService.removeById(id);
			 if(ok) {
				 relationshipService.deletetypeId(id);
				 result.success("删除成功!");
			 }
		 }

		 return result;
	 }

	 /**
	  *  批量删除
	  * @param ids
	  * @return
	  */
	 @AutoLog(value = "商品类型-批量删除")
	 @ApiOperation(value="商品类型-批量删除", notes="商品类型-批量删除")
	 @DeleteMapping(value = "/deleteBatch")
	 public Result<CommodityType> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		 Result<CommodityType> result = new Result<CommodityType>();
		 if(ids==null || "".equals(ids.trim())) {
			 result.error500("参数不识别！");
		 }else {
			 this.commodityTypeService.removeByIds(Arrays.asList(ids.split(",")));
			 result.success("删除成功!");
		 }
		 return result;
	 }

	 /**
	  * 通过id查询
	  * @param id
	  * @return
	  */
	 @AutoLog(value = "商品类型-通过id查询")
	 @ApiOperation(value="商品类型-通过id查询", notes="商品类型-通过id查询")
	 @GetMapping(value = "/queryById")
	 public Result<CommodityType> queryById(@RequestParam(name="id",required=true) String id) {
		 Result<CommodityType> result = new Result<CommodityType>();
		 CommodityType commodityType = commodityTypeService.getById(id);
		 if(commodityType==null) {
			 result.error500("未找到对应实体");
		 }else {
			 result.setResult(commodityType);
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
		 QueryWrapper<CommodityType> queryWrapper = null;
		 try {
			 String paramsStr = request.getParameter("paramsStr");
			 if (oConvertUtils.isNotEmpty(paramsStr)) {
				 String deString = URLDecoder.decode(paramsStr, "UTF-8");
				 CommodityType commodityType = JSON.parseObject(deString, CommodityType.class);
				 queryWrapper = QueryGenerator.initQueryWrapper(commodityType, request.getParameterMap());
			 }
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }

		 //Step.2 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 List<CommodityType> pageList = commodityTypeService.list(queryWrapper);
		 //导出文件名称
		 mv.addObject(NormalExcelConstants.FILE_NAME, "商品类型列表");
		 mv.addObject(NormalExcelConstants.CLASS, CommodityType.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("商品类型列表数据", "导出人:Jeecg", "导出信息"));
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
				 List<CommodityType> listCommodityTypes = ExcelImportUtil.importExcel(file.getInputStream(), CommodityType.class, params);
				 for (CommodityType commodityTypeExcel : listCommodityTypes) {
					 commodityTypeService.save(commodityTypeExcel);
				 }
				 return Result.ok("文件导入成功！数据行数:" + listCommodityTypes.size());
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
