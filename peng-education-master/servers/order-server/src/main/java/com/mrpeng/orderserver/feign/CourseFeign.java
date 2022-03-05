package com.mrpeng.orderserver.feign;

import com.mrpeng.dto.CourseDetailsDto;
import com.mrpeng.pojo.R;
import com.mrpeng.vo.CourseDetailsVo;
import com.mrpeng.vo.CourseInfoVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("edu-server")
public interface CourseFeign {

    @GetMapping("/eduserver/edu-course/getCourseDetails/{id}")
    public CourseDetailsDto getCourse(@PathVariable("id")String courseId);

    @GetMapping("/eduserver/edu-course/addBuyCount/{courseId}")
    void addBuyCount(@PathVariable("courseId")String courseId);

}
