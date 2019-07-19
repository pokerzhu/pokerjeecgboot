package org.jeecg.modules.system.controller;

import java.util.*;
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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.modules.system.entity.Equipment;
import org.jeecg.modules.system.entity.FilterelementReplace;
import org.jeecg.modules.system.entity.Installation;
import org.jeecg.modules.system.service.*;
import org.jeecg.modules.system.vo.CommodityVO;
import org.jeecg.modules.system.vo.EquipmentVO;
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
 * @Description: 设备表
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Slf4j
@Api(tags="设备表")
@RestController
@RequestMapping("/demo/equipment")
public class EquipmentController {
	 @Autowired
	 private IEquipmentService equipmentService;
	 @Autowired
	 private InstallationService installationService ;
	 @Autowired
	 private IRelationshipService iRelationshipService ;
	 @Autowired
	 private IFilterelementReplaceService iFilterelementReplaceService ;
     @Autowired
     private ISysUserService iSysUserService ;

	 @AutoLog(value = "设备表-分页列表查询")
	@ApiOperation(value="设备表-分页列表查询", notes="设备表-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<EquipmentVO>> queryPageList(EquipmentVO equipmentVO,
                                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                    HttpServletRequest req) {
		Result<IPage<EquipmentVO>> result = new Result<IPage<EquipmentVO>>();
		QueryWrapper<EquipmentVO> queryWrapper = QueryGenerator.initQueryWrapper(equipmentVO, req.getParameterMap());
		Page<EquipmentVO> page = new Page<EquipmentVO>(pageNo,pageSize);
		IPage<EquipmentVO> pageList = equipmentService.Equfindselect(page);
		result.setSuccess(true);
		result.setResult(pageList);
		return result;
	}

     @AutoLog(value = "设备详情查询")
     @ApiOperation(value="设备详情查询", notes="设备详情查询")
     @GetMapping(value = "/equipmentlist")
     public Result<IPage<EquipmentVO>>equipmentList(String equipmentId,
                                                    @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                    @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                    HttpServletRequest req) {
         Result<IPage<EquipmentVO>> result = new Result<IPage<EquipmentVO>>();
         Page<EquipmentVO> page = new Page<EquipmentVO>(pageNo,pageSize);
         IPage<EquipmentVO> pageList = equipmentService.SelectByEquId(page,equipmentId);
         result.setSuccess(true);
         result.setResult(pageList);
         return result;
     }
	/**
	  *   添加
	 * @param equipment
	 * @return
	 */
	@AutoLog(value = "设备表-添加")
	@ApiOperation(value="设备表-添加", notes="设备表-添加")
	@PostMapping(value = "/add")
	public Result<Equipment> add(@RequestBody Equipment equipment) {
		Result<Equipment> result = new Result<Equipment>();
		try {
			LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
			String userId =  iSysUserService.setUser(sysUser.getId());
			equipment.setUserId(userId);
			equipment.setCreateBy(sysUser.getRealname());
			equipment.setUpdateBy(sysUser.getRealname());
			equipmentService.save(equipment);
			result.success("添加成功！");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			result.error500("操作失败");
		}
		return result;
	}

	 /**
	  * 编辑
	  *
	  * @param equipment
	  * @return
	  */
	 @AutoLog(value = "设备表-编辑")
	 @ApiOperation(value = "设备表-编辑", notes = "设备表-编辑")
	 @PutMapping(value = "/edit")
	 public Result<Equipment> edit(@RequestBody Equipment equipment) {
		 Result<Equipment> result = new Result<Equipment>();
		 Equipment equipmentEntity = equipmentService.getById(equipment.getEquipmentId());
		 System.out.println(equipmentEntity);
		 if (equipmentEntity == null) {
			 result.error500("未找到对应实体");
		 } else {
			 equipment.setUpdateTime(new Date());
			 boolean ok = equipmentService.UpdEquipment(equipment);
			 //TODO 返回false说明什么？
			 if (ok) {
				 result.success("修改成功!");
			 }
		 }
		 return result;
	 }


	 /**
	  * 回收
	  *
	  * @param equipment
	  * @return
	  */
	 @AutoLog(value = "设备表-回收与安装")
	 @ApiOperation(value = "设备表-回收与安装", notes = "设备表-回收与安装")
	 @PutMapping(value = "/editA")
	 public Result<Equipment> editA(@RequestBody Equipment equipment) {
		 Result<Equipment> result = new Result<Equipment>();
		 Equipment equipmentEntity = equipmentService.getById(equipment.getEquipmentId());
		 LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
		 try {
			 if (equipmentEntity == null) {
				 result.error500("未找到对应实体");
			 } else {
				 boolean ok = equipmentService.UpdEquipmentClient(equipment);//修改设备客户信息
				 //TODO 返回false说明什么？
				 if (ok) {
					 System.out.println(equipment.getClientId());
					 if(equipment.getClientId()==null){//判断是否有返回客户id，没有则跳安装
						 installationService.DeleteInstall(equipment.getEquipmentId());
						 iFilterelementReplaceService.DelEquipmentId(equipment.getEquipmentId());
						 System.out.println("撤回");
					 }else{
					 	//创建开关权限表对象
						 Installation installation=new Installation();
						 installation.setOpenId(UUID.randomUUID().toString());//生成id
						 installation.setClientId(equipment.getClientId());//客户id
						 installation.setEquipmentId(equipment.getEquipmentId());//设备id
						 installation.setCreateBy(sysUser.getRealname());//创建人
						 installation.setUpdateBy(sysUser.getRealname());//更新人
						 boolean a = installationService.EquAdd(installation);//新增设备客户权限记录
						 //根据商品id得到类型对饮的滤芯id集合
						 List<String> LXids = iRelationshipService.selectLXid(equipment.getCommodityId());
						 List<FilterelementReplace> list = new ArrayList<>();//保存滤芯安装记录表对象
						 for (int i = 0; i < LXids.size(); i++) {
							 FilterelementReplace relationship = new FilterelementReplace();//初始化对象
							 relationship.setRecordId(UUID.randomUUID().toString());
							 relationship.setEquipmentId(equipment.getEquipmentId());
							 relationship.setFilterelementId(LXids.get(i));
							 relationship.setCreateBy(sysUser.getRealname());
							 relationship.setUpdateBy(sysUser.getRealname());
							 list.add(relationship);
						 }
						 //新增设备对应的滤芯，生成滤芯安装记录表
						 boolean bo =  iFilterelementReplaceService.insertByfilterelementid(list);
						 if(bo){
							 result.success("操作成功!");
						 }else{
							 result.error500("操作失败");
						 }
					 }
					 System.out.println(equipment.getEquipmentId()+"************");
					 result.success("操作成功!");
				 }
			 }
		 }catch (Exception e){
			 log.error(e.getMessage(),e);
			 result.error500("操作失败");
		 }
		 return result;
	 }

	 /**
	  *   通过id删除
	  * @param equipmentId
	  * @return
	  */
	 @AutoLog(value = "设备表-通过id删除")
	 @ApiOperation(value="设备表-通过id删除", notes="设备表-通过id删除")
	 @DeleteMapping(value = "/delete")
	 public Result<Equipment> delete(@RequestParam(name="id",required=true) String equipmentId) {
		 Result<Equipment> result = new Result<Equipment>();
		 Equipment equipment = equipmentService.getById(equipmentId);
		 if(equipment==null) {
			 result.error500("未找到对应实体");
		 }else {
             Integer integer = equipmentService.EquipmentById(equipmentId);
             if (integer>0){
                 result.error500("该设备已被用户安装,不能删除");
             }else{
                 boolean ok = equipmentService.removeById(equipmentId);
                 if(ok) {
                     result.success("删除成功!");
                 }
             }
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
		 QueryWrapper<Equipment> queryWrapper = null;
		 try {
			 String paramsStr = request.getParameter("paramsStr");
			 if (oConvertUtils.isNotEmpty(paramsStr)) {
				 String deString = URLDecoder.decode(paramsStr, "UTF-8");
				 Equipment equipment = JSON.parseObject(deString, Equipment.class);
				 queryWrapper = QueryGenerator.initQueryWrapper(equipment, request.getParameterMap());
			 }
		 } catch (UnsupportedEncodingException e) {
			 e.printStackTrace();
		 }

		 //Step.2 AutoPoi 导出Excel
		 ModelAndView mv = new ModelAndView(new JeecgEntityExcelView());
		 List<Equipment> pageList = equipmentService.list(queryWrapper);
		 //导出文件名称
		 mv.addObject(NormalExcelConstants.FILE_NAME, "设备表列表");
		 mv.addObject(NormalExcelConstants.CLASS, Equipment.class);
		 mv.addObject(NormalExcelConstants.PARAMS, new ExportParams("设备表列表数据", "导出人:Jeecg", "导出信息"));
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
         LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
         //获取部门id
            String equId= iSysUserService.setUser(sysUser.getId());
		 for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			 MultipartFile file = entity.getValue();// 获取上传文件对象
			 ImportParams params = new ImportParams();
			 params.setTitleRows(2);
			 params.setHeadRows(1);
			 params.setNeedSave(true);
			 try {
				 List<Equipment> listEquipments = ExcelImportUtil.importExcel(file.getInputStream(),Equipment.class, params);
				 System.out.println(Equipment.class+"****************");
				 for (Equipment equipmentExcel : listEquipments) {
				     //获得当前登录人的部门id
				     equipmentExcel.setUserId(equId);
					 equipmentService.save(equipmentExcel);

				 }
				 return Result.ok("文件导入成功！数据行数:" + listEquipments.size());
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


     @AutoLog(value = "模糊查询")
     @ApiOperation(value="模糊查询", notes="模糊查询")
     @GetMapping(value = "/likeSelect")
     public Result<IPage<EquipmentVO>> likeSelect(EquipmentVO equipmentVO,
                                                     @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                                     @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                                     HttpServletRequest req) {
         Result<IPage<EquipmentVO>> result = new Result<IPage<EquipmentVO>>();
         QueryWrapper<EquipmentVO> queryWrapper = QueryGenerator.initQueryWrapper(equipmentVO, req.getParameterMap());
         Page<EquipmentVO> page = new Page<EquipmentVO>(pageNo, pageSize);
         IPage<EquipmentVO> pageList = equipmentService.likeEquipment(page,equipmentVO.getEquipmentId(),equipmentVO.getIds(),equipmentVO.getEnabled());
         System.out.println(equipmentVO.getEquipmentId()+"888888888888888");
         result.setSuccess(true);
         result.setResult(pageList);
         return result;
     }

//     @AutoLog(value = "模糊查询")
//     @ApiOperation(value="模糊查询", notes="模糊查询")
//     @GetMapping(value = "/likeSelectIds")
//     public Result<IPage<EquipmentVO>> likeSelectIds(EquipmentVO equipmentVO,
//                                                  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//                                                  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//                                                  HttpServletRequest req) {
//         Result<IPage<EquipmentVO>> result = new Result<IPage<EquipmentVO>>();
//         QueryWrapper<EquipmentVO> queryWrapper = QueryGenerator.initQueryWrapper(equipmentVO, req.getParameterMap());
//         Page<EquipmentVO> page = new Page<EquipmentVO>(pageNo, pageSize);
//         IPage<EquipmentVO> pageList = equipmentService.likeEquipmentIds(page,equipmentVO.getIds());
//         System.out.println(equipmentVO.getEquipmentId()+"888888888888888");
//         result.setSuccess(true);
//         result.setResult(pageList);
//         return result;
//     }
}
