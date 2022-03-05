package com.mrpeng.service;

import org.springframework.web.multipart.MultipartFile;

public interface VideoService {
    String uploadVideo(MultipartFile file);

    void deleteByVideoId(String id);

    String videoPlayAuth(String id);

}
