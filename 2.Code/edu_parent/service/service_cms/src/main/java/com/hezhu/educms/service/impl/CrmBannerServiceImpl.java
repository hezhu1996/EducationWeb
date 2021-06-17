package com.hezhu.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hezhu.educms.entity.CrmBanner;
import com.hezhu.educms.mapper.CrmBannerMapper;
import com.hezhu.educms.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


import javax.swing.*;
import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-03-06
 */


@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    //查询所有banner
    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        //根据id降序排列，查询前2条记录
        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //last方法：拼接sql语句
        wrapper.last("limit 2");
        //所有banner：wrapper条件
        List<CrmBanner> crmBanners = baseMapper.selectList(wrapper);
        return crmBanners;
    }
}
