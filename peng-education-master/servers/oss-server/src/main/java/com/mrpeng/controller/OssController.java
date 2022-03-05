package com.mrpeng.controller;

import com.mrpeng.pojo.R;
import com.mrpeng.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("oss")
@Api(tags = "上传文件的api")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("upload")
    @ApiOperation(value = "上传文件")
    public R fileUpload(MultipartFile file){
        String path = ossService.upload(file);
        return R.ok().data("path",path).message("上传成功！");
    }

}
