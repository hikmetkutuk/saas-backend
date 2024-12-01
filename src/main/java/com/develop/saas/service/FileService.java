package com.develop.saas.service;

import com.develop.saas.util.AwsCloudUtil;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class FileService {
    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }

    @Value("${s3.bucket}")
    private String AWS_BUCKET;

    @Value("${s3.access-key}")
    private String AWS_ACCESS_KEY;

    @Value("${s3.secret-key}")
    private String AWS_SECRET_KEY;

    @Async
    public void uploadFileToS3(MultipartFile data) {
        try {
            AwsCloudUtil awsCloudUtil = new AwsCloudUtil();
            awsCloudUtil.uploadFileToS3(
                    data.getOriginalFilename(), data.getBytes(), AWS_ACCESS_KEY, AWS_SECRET_KEY, AWS_BUCKET);
            log.info("File {} uploaded successfully", data.getOriginalFilename());
            CompletableFuture.completedFuture(ResponseEntity.ok("File uploaded successfully"));
        } catch (IOException e) {
            log.error("Error uploading file to S3: {}", e.getMessage());
            throw new RuntimeException("Error uploading file to S3: " + e.getMessage());
        }
    }
}
