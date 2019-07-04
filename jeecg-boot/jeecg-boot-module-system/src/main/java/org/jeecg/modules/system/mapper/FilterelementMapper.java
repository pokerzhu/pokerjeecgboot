package org.jeecg.modules.system.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.system.entity.Filterelement;

/**
 * @Description: 滤芯表
 * @Author: jeecg-boot
 * @Date:   2019-07-03
 * @Version: V1.0
 */
public interface FilterelementMapper extends BaseMapper<Filterelement> {
    /**
     *添加图片
     */
    String insertImg(String name,String url,String mimeType,
                     String Suffix,String Created,long Size);
}
