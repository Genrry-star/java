package com.mrpeng.bannerserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.bannerserver.pojo.CrmBanner;
import com.mrpeng.bannerserver.mapper.CrmBannerMapper;
import com.mrpeng.bannerserver.service.CrmBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrpeng.vo.BannerVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-12
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner> implements CrmBannerService {

    @Autowired
    private CrmBannerMapper  crmBannerMapper;
    @Override
    public Page<CrmBanner> pageBannerByConditions(Integer current, Integer limit, BannerVo conditions) {
        Page<CrmBanner> page =new Page<>(current,limit);
        QueryWrapper<CrmBanner> wrapper =new QueryWrapper<>();
        if(conditions!=null){
            if(StringUtils.isNotEmpty(conditions.getTitle())){
                wrapper.like("title",conditions.getTitle());
            }
            if (StringUtils.isNotEmpty(conditions.getStart())){
                wrapper.ge("gmt_create",conditions.getStart());
            }
            if(StringUtils.isNotEmpty(conditions.getEnd())){
                wrapper.le("gmt_modified",conditions.getEnd());
            }
            if(conditions.getStatus()!=null){
                wrapper.eq("status",conditions.getStatus());
            }
            wrapper.orderByAsc("sort");

        }
        page = crmBannerMapper.selectPage(page, wrapper);
        return page;
    }
    @Cacheable(cacheNames = "banner",key = "'indexBanner'")
    @Override
    public List<CrmBanner> indexShowBanner() {
        QueryWrapper<CrmBanner> wrapper =new QueryWrapper<>();
        wrapper.orderByDesc("sort");
        wrapper.last("limit 4");
        List<CrmBanner> banners = crmBannerMapper.selectList(wrapper);
        return banners;
    }
}
