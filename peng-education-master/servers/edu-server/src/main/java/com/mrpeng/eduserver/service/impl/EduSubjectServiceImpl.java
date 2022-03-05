package com.mrpeng.eduserver.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mrpeng.eduserver.listener.ExcelListener;
import com.mrpeng.pojo.EduSubject;
import com.mrpeng.eduserver.mapper.EduSubjectMapper;
import com.mrpeng.eduserver.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.ExcelData;
import com.mrpeng.pojo.R;
import com.mrpeng.vo.SubjectVo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author mrpeng
 * @since 2020-10-13
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    @Autowired
    private EduSubjectMapper eduSubjectMapper;
    @Override
    public void importExcel(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),ExcelData.class, new ExcelListener(eduSubjectMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
            throw new NormalException("导入Excel失败！");
        }
    }

    @Override
    public List<SubjectVo> getSubject() {
        QueryWrapper<EduSubject> wrapper1 =new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        List<EduSubject> parentList =eduSubjectMapper.selectList(wrapper1);
        QueryWrapper<EduSubject> wrapper2 =new QueryWrapper<>();
        wrapper2.ne("parent_id","0");
        List<EduSubject> childList =eduSubjectMapper.selectList(wrapper2);

        List<SubjectVo> oneList=new ArrayList<>();
        for (EduSubject parent : parentList) {
            SubjectVo parentSubject =new SubjectVo();
            BeanUtils.copyProperties(parent,parentSubject);
            List<SubjectVo> twoList=new ArrayList<>();
            for (EduSubject children : childList) {
                if(StringUtils.equals(parent.getId(),children.getParentId())){
                    SubjectVo childSubject =new SubjectVo();
                    BeanUtils.copyProperties(children,childSubject);
                    twoList.add(childSubject);
                }
                parentSubject.setChildren(twoList);

            }
            oneList.add(parentSubject);
        }
        return oneList;
    }


}
