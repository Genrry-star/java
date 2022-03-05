package com.mrpeng.service;

import com.mrpeng.pojo.R;
import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    String upload(MultipartFile file);
}
