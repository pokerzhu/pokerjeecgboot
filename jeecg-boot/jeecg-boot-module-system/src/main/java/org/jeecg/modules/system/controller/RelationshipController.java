package org.jeecg.modules.system.controller;

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

import org.jeecg.modules.system.entity.Commodity;
import org.jeecg.modules.system.entity.Relationship;
import org.jeecg.modules.system.service.IRelationshipService;
import org.jeecg.modules.system.vo.RelationshipVO;
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
 * @Description: 商品滤芯关联
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
@Slf4j
@Api(tags="商品滤芯关联")
@RestController
@RequestMapping("/dome/relationship")
public class RelationshipController {
	@Autowired
	private IRelationshipService relationshipService;

	 /**
	  * 分页列表查询
	  * @param
	  * @param pageNo
	  * @param pageSize
	  * @param req
	  * @return
	  */
	 @AutoLog(value = "商品表-分页列表查询")
	 @ApiOperation(value="商品表-分页列表查询", notes="商品表-分页列表查询")
	 @GetMapping(value = "/list")
	 public Result<IPage<RelationshipVO>> queryPageList(@RequestParam(name = "commodityId") String commodityId,
													  @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
													  @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
													  HttpServletRequest req) {
		 Result<IPage<RelationshipVO>> result = new Result<IPage<RelationshipVO>>();
		 Page<RelationshipVO> page = new Page<RelationshipVO>(pageNo, pageSize);
		 if (commodityId!=null){
			 System.out.println(commodityId);
			 IPage<RelationshipVO> pageList = relationshipService.selectItemsByMainId(page,commodityId);
			 result.setSuccess(true);
			 result.setResult(pageList);
		 }else {
			 result=null;
		 }
		 return result;
	 }


}
