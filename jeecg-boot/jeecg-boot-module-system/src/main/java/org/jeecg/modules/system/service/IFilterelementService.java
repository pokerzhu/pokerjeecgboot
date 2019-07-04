package org.jeecg.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.entity.Filterelement;

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
    String insertImg(String name,String url,String mimeType,
                     String Suffix,String Created,long Size);
}
