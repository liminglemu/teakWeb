package com.teak.blog.service.serviceImpl;

import com.aliyun.oss.OSS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.teak.blog.mapper.FileMapper;
import com.teak.blog.model.FileModel;
import com.teak.blog.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

/**
 * Created with: IntelliJ IDEA
 *
 * @Author: li zheng
 * @Date: 2025/3/1 01:17
 * @Project: teakWeb
 * @File: FileServiceImpl.java
 * @Description:
 */
@Service
@Slf4j
public class FileServiceImpl extends ServiceImpl<FileMapper, FileModel> implements FileService {
    private final FileMapper fileMapper;
    private final OSS ossClient;
    @Value("${oss.endpoint}")
    private String endpoint;
    @Value("${oss.bucketName}")
    private String bucketName;
    @Value("${oss.expireSeconds}")
    private int expireSeconds;
    public FileServiceImpl(FileMapper fileMapper, OSS ossClient) {
        this.fileMapper = fileMapper;
        this.ossClient = ossClient;
    }

    /**
     * 智能文件上传（自动重名处理）
     *
     * @param userId
     * @param file      上传文件
     * @param directory 存储目录
     * @return 文件访问URL
     */
    @Transactional(rollbackFor = Exception.class)
    public FileModel uploadFile(Long userId, MultipartFile file, String directory) {
        // 生成唯一文件名（带原始扩展名）
        String originalName = file.getOriginalFilename();
        String fileExtension = originalName.substring(originalName.lastIndexOf("."));
        String uniqueFileName = generateUniqueFileName(originalName, fileExtension);

        String objectKey = directory + "/" + uniqueFileName;

        try (InputStream inputStream = file.getInputStream()) {
            // 检查文件是否存在（可选，根据业务需求）
            boolean exists = ossClient.doesObjectExist(bucketName, objectKey);
            if (exists) {
                objectKey = handleDuplicateFile(objectKey, fileExtension);
            }

            // 上传文件
            ossClient.putObject(bucketName, objectKey, inputStream);

            // 生成访问URL（可根据需要设置过期时间）
            String fileUrl = generateFileUrl(objectKey);

            FileModel fileModel = new FileModel();
            fileModel.setFileUrl(fileUrl);
            fileModel.setUserId(userId);
            fileModel.setTitle(originalName);
            fileModel.setFileFormat(fileExtension);

            fileMapper.insert(fileModel);

            return fileModel;
        } catch (Exception e) {
            log.error("文件上传失败: {}", e.getMessage());
            throw new RuntimeException("文件上传失败", e);
        }
    }

    /**
     * 生成防重文件名（时间戳+UUID）
     */
    private String generateUniqueFileName(String originalName, String extension) {
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 8);
        return String.format("%s_%s%s",
                originalName.replace(extension, ""),
                timestamp + "_" + uuid,
                extension);
    }

    /**
     * 处理重复文件名（添加版本后缀）
     */
    private String handleDuplicateFile(String objectKey, String extension) {
        int version = 1;
        String newKey;
        do {
            newKey = objectKey.replace(extension,
                    String.format("_v%d%s", version++, extension));
        } while (ossClient.doesObjectExist(bucketName, newKey));
        return newKey;
    }

    /**
     * 生成访问URL（建议使用HTTPS）
     */
    private String generateFileUrl(String objectKey) {
        Date expiration = new Date(System.currentTimeMillis() + expireSeconds * 1000L);
        URL url = ossClient.generatePresignedUrl(bucketName, objectKey, expiration);
        return url.toString();
        /*return String.format("https://%s.%s/%s",
                bucketName,
                endpoint.replace("https://", ""),
                objectKey);*/
    }

    /**
     * 安全删除文件（保留历史版本）
     *
     * @param objectKey  文件路径
     * @param archiveDir 归档目录（如 deleted/）
     */
    public void safeDelete(String objectKey, String archiveDir) {
        if (!ossClient.doesObjectExist(bucketName, objectKey)) {
            return;
        }

        // 生成归档文件名
        String archivedKey = archiveDir + "/" +
                LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + "_" +
                objectKey.substring(objectKey.lastIndexOf("/") + 1);

        // 复制到归档目录
        ossClient.copyObject(bucketName, objectKey, bucketName, archivedKey);

        // 删除原文件（根据业务需求决定是否删除）
        // ossClient.deleteObject(bucketName, objectKey);
    }
}
