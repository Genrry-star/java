package com.mrpeng.orderserver.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrpeng.pojo.Statistics;
import com.mrpeng.orderserver.mapper.StatisticsMapper;
import com.mrpeng.orderserver.service.StatisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrpeng.vo.StatisticVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-27
 */
@Service
public class StatisticsServiceImpl extends ServiceImpl<StatisticsMapper, Statistics> implements StatisticsService {
    @Autowired
    private  StatisticsMapper statisticsMapper;
    @Override
    public Map<String,List> findData(StatisticVo vo) {

        QueryWrapper<Statistics> wrapper =new QueryWrapper<>();
        wrapper.between("date_calculated",vo.getStart(),vo.getEnd());

        List<Statistics> list = statisticsMapper.selectList(wrapper);
        List<String> date =new ArrayList<>();
        List<Double> total=new ArrayList<>();
        Map<String,List> myData =new HashMap<>();
        for (Statistics statistics : list) {
            date.add(statistics.getDateCalculated());
            total.add(statistics.getPayTotal());
        }
        myData.put("date",date);
        myData.put("total",total);
        return myData;
    }
}
