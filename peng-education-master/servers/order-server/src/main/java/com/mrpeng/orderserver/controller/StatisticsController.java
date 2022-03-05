package com.mrpeng.orderserver.controller;


import com.mrpeng.orderserver.service.StatisticsService;
import com.mrpeng.pojo.R;
import com.mrpeng.vo.StatisticVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-27
 */
@RestController
@RequestMapping("/orderserver/statistics")
@Api(tags = "统计的api")
public class StatisticsController {
    @Autowired
    private StatisticsService statisticsService;
    @ApiOperation("获取统计记录")
    @PostMapping("getData")
    public R getData(@RequestBody StatisticVo vo){
        Map<String, List> data = statisticsService.findData(vo);
        return  R.ok().data("data",data);
    }

}

