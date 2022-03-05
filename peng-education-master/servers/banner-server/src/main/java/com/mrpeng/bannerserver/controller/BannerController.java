package com.mrpeng.bannerserver.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mrpeng.bannerserver.pojo.CrmBanner;
import com.mrpeng.bannerserver.service.CrmBannerService;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.R;
import com.mrpeng.vo.BannerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private CrmBannerService crmBannerService;
    @GetMapping("demo")
    public R dome(){
        return R.ok();
    }
    @GetMapping("findById/{id}")
    @ApiOperation("根据id查询轮播图信息")
    public R findById(@PathVariable("id") String id){
        System.out.println(132);
        CrmBanner result = crmBannerService.getById(id);
        return R.ok().data("banner",result);
    }

    @ApiOperation("查询前几名广告")
    @GetMapping("indexShowBanner")
    public R indexShowBanner(){
        List<CrmBanner> banners = crmBannerService.indexShowBanner();
        return R.ok().data("banners",banners);
    }



    @DeleteMapping("deleteById/{id}")
    @ApiOperation("根据id逻辑删除轮播图信息")
    public R deleteById(@ApiParam(value = "n你好") @PathVariable("id") String id){
        try{
            boolean result = crmBannerService.removeById(id);
            return R.ok().message("删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
            throw new NormalException("服务器错误！");
        }
    }

    @PostMapping("save")
    @ApiOperation("添加轮播图信息")
    public R save(@RequestBody CrmBanner banner){
        boolean result = crmBannerService.save(banner);
        return R.ok().message("添加成功！");
    }

    @PutMapping("update")
    @ApiOperation("修改轮播图信息")
    public R update(@RequestBody CrmBanner banner){
        boolean result = crmBannerService.updateById(banner);
        return R.ok().message("修改成功！");
    }


    @PostMapping("/findByConditions/{current}/{limit}")
    @ApiOperation("模糊分页查询轮播图信息")
    public R findByConditions(@PathVariable("current")Integer current,
                              @PathVariable("limit")Integer limit,
                              @RequestBody(required = false) BannerVo conditions){
        Page<CrmBanner> page = crmBannerService.pageBannerByConditions(current, limit, conditions);
        return R.ok().data("rows",page.getRecords()).data("totals",page.getTotal());

    }

    @ApiOperation("修改广告状态")
    @PutMapping("updateStatus/{id}/{status}")
    public R updateStatus(@PathVariable("id")String id,@PathVariable("status")String status){
        UpdateWrapper<CrmBanner> wrapper =new UpdateWrapper<>();
        wrapper.set("status",status);
        wrapper.eq("id",id);
        boolean update = crmBannerService.update(wrapper);
        if(update){
            return R.ok().message("修改成功！");
        }
        return R.error().message("修改失败！");
    }
}
