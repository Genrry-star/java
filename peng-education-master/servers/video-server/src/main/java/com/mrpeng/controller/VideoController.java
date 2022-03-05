package com.mrpeng.controller;

import com.mrpeng.pojo.R;
import com.mrpeng.service.VideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Api(tags = "视频的api")
@RequestMapping("video")
public class VideoController {
    @Autowired
    public VideoService videoService;

    @PostMapping("upload")
    @ApiOperation("上传视频文件，并返回视频id")
    public R uploadFile(@ApiParam(value = "视频文件",required = true)
                                 @RequestPart("file")MultipartFile file){
        String videoId = videoService.uploadVideo(file);
        return R.ok().data("videoId",videoId);
    }


    @ApiOperation("根据视频Id删除文件")
    @DeleteMapping("deleteById/{id}")
    public R deleteById(@PathVariable("id") String id){
        videoService.deleteByVideoId(id);
        return R.ok().message("视频删除成功！！");
    }

    @ApiOperation("根据视频的Id获取视频的播放凭证")
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable("id")String id){
        String playAuth = videoService.videoPlayAuth(id);
        return R.ok().data("playAuth",playAuth);
    }


}
