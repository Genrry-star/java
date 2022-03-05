package com.mrpeng.bannerserver.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.bannerserver.pojo.CrmBanner;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrpeng.vo.BannerVo;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-12
 */
public interface CrmBannerService extends IService<CrmBanner> {
    Page<CrmBanner> pageBannerByConditions(Integer current, Integer limit, BannerVo conditions);

    List<CrmBanner> indexShowBanner();
}
