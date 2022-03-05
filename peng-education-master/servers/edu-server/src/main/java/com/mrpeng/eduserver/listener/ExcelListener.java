package com.mrpeng.eduserver.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrpeng.eduserver.mapper.EduSubjectMapper;
import com.mrpeng.pojo.EduSubject;
import com.mrpeng.pojo.ExcelData;

public class ExcelListener extends AnalysisEventListener<ExcelData> {
    private EduSubjectMapper eduSubjectMapper;
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        EduSubject oneSubject =isExist(excelData.getOneSubject(),"0");
        //判断一级课题是否存在
        if(oneSubject==null){
            oneSubject =new EduSubject();
            oneSubject.setTitle(excelData.getOneSubject());
            oneSubject.setParentId("0");
            oneSubject.setSort(0);
            eduSubjectMapper.insert(oneSubject);
        }
        EduSubject twoSubject=isExist(excelData.getTwoSubject(),oneSubject.getId());
        //判断二级课题是否存在
        if(twoSubject ==null){
            twoSubject=new EduSubject();
            twoSubject.setTitle(excelData.getTwoSubject());
            twoSubject.setParentId(oneSubject.getId());
            twoSubject.setSort(1);
            eduSubjectMapper.insert(twoSubject);
        }

    }
    //获取EduSubjectMapper对象，可以进行数据持久化操作
    public ExcelListener(EduSubjectMapper eduSubjectMapper){
        this.eduSubjectMapper =eduSubjectMapper;
    }
    //判断一级课程是否已经存在
    private EduSubject isExist(String Subject,String parentId){
        QueryWrapper wrapper =new QueryWrapper<>();
        wrapper.eq("title",Subject);
        wrapper.eq("parent_id",parentId);
        EduSubject one = eduSubjectMapper.selectOne(wrapper);
        return one;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
