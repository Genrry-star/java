package com.mrpeng.orderserver.service;

import com.mrpeng.pojo.Statistics;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mrpeng.vo.StatisticVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-27
 */
public interface StatisticsService extends IService<Statistics> {
    /**
     * 根据统计条件，获取统计数据
     * @param vo
     * @return
     */
    Map<String, List> findData(StatisticVo vo);

}
