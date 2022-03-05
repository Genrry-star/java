package com.mrpeng.orderserver.schedule;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrpeng.orderserver.service.OrderService;
import com.mrpeng.orderserver.service.PayLogService;
import com.mrpeng.orderserver.service.StatisticsService;
import com.mrpeng.pojo.Statistics;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.core.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.Map;


@Configuration
@EnableScheduling
@Slf4j
public class ScheduleTask {

    @Autowired
    private PayLogService payLogService;
    @Autowired
    private StatisticsService statisticsService;
    @Scheduled(cron = "0 0 1 * * ?")
    public void statisticsTask(){
        Map data = payLogService.getStatisticsData(LocalDate.now().plusDays(-1).toString());
        if(data.get("total")!=null){
            Statistics statistics =new Statistics();
            statistics.setPayTotal(Double.parseDouble(data.get("total").toString()));
            statistics.setBuyCount(Integer.parseInt(data.get("buy_count").toString()));
            statisticsService.save(statistics);
        }else{
            log.info("今天没有人买呢");
        }
    }

}
