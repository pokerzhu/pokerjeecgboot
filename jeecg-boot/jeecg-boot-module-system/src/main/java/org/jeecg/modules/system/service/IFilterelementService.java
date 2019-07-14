package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.system.entity.Filterelement;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * @Description: 滤芯表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
public interface IFilterelementService extends IService<Filterelement> {
    /**
     * 添加图片
     * @param name
     * @param url
     * @param mimeType
     * @param Suffix
     * @param Created
     * @param Size
     * @return
     */
    String insertImg(String name, String url, String mimeType,
                     String Suffix, String Created, long Size);

    /**
     * 条件查询该类型能够添加的滤芯
     * @param typeId
     * @return
     */
    IPage<Filterelement> selectbytypeId(String typeId, Page page);
}
