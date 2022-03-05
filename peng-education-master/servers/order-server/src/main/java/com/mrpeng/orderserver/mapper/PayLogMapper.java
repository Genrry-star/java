package com.mrpeng.orderserver.mapper;

import com.mrpeng.pojo.PayLog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 支付日志表 Mapper 接口
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-25
 */
public interface PayLogMapper extends BaseMapper<PayLog> {

    Map findStatisticsData(String date);

}
