package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.Relationship;
import org.jeecg.modules.system.vo.RelationshipVO;

import java.util.List;

/**
 * @Description: 商品滤芯关联
 * @Author: jeecg-boot
 * @Date:   2019-07-05
 * @Version: V1.0
 */
public interface IRelationshipService extends IService<RelationshipVO> {
    /**
     * 根据商品id查询滤芯
     * @param id
     * @return
     */
    IPage<RelationshipVO> selectItemsByMainId(Page page, String commodityId);

}
