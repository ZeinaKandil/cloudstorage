package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public String[] getAllFiles(Integer userId) {
        return fileMapper.getAllFiles(userId);
    }

    public void addFile(MultipartFile multipartFile, String userName) throws IOException {
        InputStream fis = multipartFile.getInputStream();
        ByteArrayOutputStream bfs = new ByteArrayOutputStream();
        byte[] data = new byte[1024];
        int count;
        while ((count = fis.read(data, 0, data.length)) != -1) {
            bfs.write(data, 0, count);
        }
        bfs.flush();
        byte[] fileData = bfs.toByteArray();

        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId = userMapper.getUser(userName).getUserid();
        File file = new File(0, fileName, contentType, fileSize, userId, fileData);
        fileMapper.insert(file);
    }

    public File getFile(String fileName) {
        return fileMapper.getFile(fileName);
    }

    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }
}
