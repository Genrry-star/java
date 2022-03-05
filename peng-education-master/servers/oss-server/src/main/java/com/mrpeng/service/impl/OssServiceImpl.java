package com.mrpeng.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.mrpeng.exception.NormalException;
import com.mrpeng.pojo.R;
import com.mrpeng.service.OssService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    //通过Spring容器直接注入所需要的参数信息
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;
    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Override
    public String upload(MultipartFile file) {
// Endpoint以杭州为例，其它Region请按实际情况填写。
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        InputStream inputStream;
// 创建OSSClient实例。

        String fileName=file.getOriginalFilename();
        String currentDate= new DateTime().toString("yyyy/MM/dd");
        //文件后缀
        String fileSuffix =fileName.substring(fileName.lastIndexOf("."));
        //生成新的文件名
        fileName = currentDate+"/"+UUID.randomUUID().toString().replace("-","")+fileSuffix;
        //创建Oss对象，上传文件少不了它
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        try{
            // 获取去上传文件流。
            inputStream = file.getInputStream();
        }catch (Exception ex){
            ex.printStackTrace();
            throw new NormalException("上传文件异常");
        }
        ossClient.putObject(bucketName,fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
//        https://peng-edu.oss-cn-beijing.aliyuncs.com/%E5%82%BB%E5%A6%B9%E5%A6%B91.jpg
        //根据需求返回需要的文件地址
        String path ="https://"+bucketName+"."+endpoint+"/"+fileName;
        return path;
    }
}
