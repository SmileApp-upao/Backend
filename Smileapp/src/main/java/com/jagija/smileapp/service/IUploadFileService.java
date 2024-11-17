package com.jagija.smileapp.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface IUploadFileService {

    Resource load(String filename) throws MalformedURLException;

    String copy(MultipartFile multipartFile) throws IOException;

    boolean delete(String filename);
}
