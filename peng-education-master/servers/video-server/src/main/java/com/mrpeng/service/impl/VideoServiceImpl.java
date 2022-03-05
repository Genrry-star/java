package com.mrpeng.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadFileStreamRequest;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadFileStreamResponse;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.mrpeng.config.InitVodConfig;
import com.mrpeng.exception.NormalException;
import com.mrpeng.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class VideoServiceImpl implements VideoService {
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Override
    public String uploadVideo(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0, fileName.lastIndexOf("."));
        UploadStreamRequest request = null;
        UploadStreamResponse response=null;
        try {
            request = new UploadStreamRequest(accessKeyId, accessKeySecret, title, fileName, file.getInputStream());

            UploadVideoImpl uploader = new UploadVideoImpl();
            response= uploader.uploadStream(request);
            System.out.print("RequestId=" + response.getRequestId() + "\n");  //请求视频点播服务的请求ID

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (response.isSuccess()) {
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                return response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                System.out.print("VideoId=" + response.getVideoId() + "\n");
                System.out.print("ErrorCode=" + response.getCode() + "\n");
                System.out.print("ErrorMessage=" + response.getMessage() + "\n");
                return response.getVideoId();
            }
        }
    }



    @Override
    public void deleteByVideoId(String id) {
        //初始化对象
        DefaultAcsClient client =InitVodConfig.initVodClient(accessKeyId,accessKeySecret);
        DeleteVideoRequest videoRequest =new DeleteVideoRequest();
        //设置需要删除的视频id
        videoRequest.setVideoIds(id);
        try {
            DeleteVideoResponse videoResponse =client.getAcsResponse(videoRequest);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new NormalException("删除失败");
        }
    }

    @Override
    public String videoPlayAuth(String id) {
        DefaultAcsClient client =InitVodConfig.initVodClient(accessKeyId,accessKeySecret);
        GetVideoPlayAuthRequest request =new GetVideoPlayAuthRequest();
        request.setVideoId(id);

        try {
            GetVideoPlayAuthResponse response =client.getAcsResponse(request);
            return response.getPlayAuth();
        } catch (ClientException e) {
            throw new NormalException("获取凭证失败");
        }

    }


}
