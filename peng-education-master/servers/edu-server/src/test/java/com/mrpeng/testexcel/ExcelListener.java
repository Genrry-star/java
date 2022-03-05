package com.mrpeng.testexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<ExcelData>{
    private static final Logger logger = LoggerFactory.getLogger(ExcelListener.class);
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        logger.debug("解析一条数据："+excelData);
        System.out.println(excelData);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        logger.debug("数据解析完成！");

    }

    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }
}
