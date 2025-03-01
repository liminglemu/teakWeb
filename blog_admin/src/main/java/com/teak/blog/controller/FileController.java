package com.teak.blog.controller;

import com.teak.blog.model.FileModel;
import com.teak.blog.result.GlobalResult;
import com.teak.blog.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/1 01:16
 * @Project: teakWeb
 * @File: FileController.java
 * @Description:
 */
@RestController
@RequestMapping("/api/file")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload/{userId}")
    public ResponseEntity<Map<String, FileModel>> uploadFile(@PathVariable Long userId,
                                                             @RequestBody MultipartFile file,
                                                             @RequestParam(value = "directory", defaultValue = "uploads") String directory) {

        FileModel fileModel = fileService.uploadFile(userId, file, directory);
        return ResponseEntity.ok(Collections.singletonMap("fileModel", fileModel));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFile(
            @RequestParam String fileUrl,
            @RequestParam(defaultValue = "deleted") String archiveDir) {

        String objectKey = extractObjectKeyFromUrl(fileUrl);
        fileService.safeDelete(objectKey, archiveDir);
        return ResponseEntity.noContent().build();
    }

    private String extractObjectKeyFromUrl(String fileUrl) {
        try {
            URL url = new URL(fileUrl);
            return url.getPath().substring(1); // 去除路径前的斜杠
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("无效的文件URL");
        }
    }
}
