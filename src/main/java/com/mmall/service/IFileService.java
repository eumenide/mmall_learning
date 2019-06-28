package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: eumes
 * @date: 2019/6/27
 **/
public interface IFileService {

    String upload(MultipartFile file, String path);

}
