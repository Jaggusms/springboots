package com.example.s3.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

@Service
public class S3Service {

    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // 1. CREATE BUCKET
    public String createBucket(String bucketName) {

        CreateBucketRequest request =
                CreateBucketRequest.builder()
                        .bucket(bucketName)
                        .createBucketConfiguration(
                                CreateBucketConfiguration.builder()
                                        .locationConstraint(
                                                BucketLocationConstraint.AP_SOUTH_1
                                        )
                                        .build()
                        )
                        .build();

        s3Client.createBucket(request);

        return "Bucket created: " + bucketName;
    }

    // 2. UPLOAD FILE
    public String uploadFile(
            String bucketName,
            MultipartFile file) throws IOException {

        String key = file.getOriginalFilename();

        PutObjectRequest request =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType(file.getContentType())
                        .build();

        s3Client.putObject(
                request,
                RequestBody.fromInputStream(
                        file.getInputStream(),
                        file.getSize()
                )
        );

        return "File uploaded successfully: " + key;
    }

    // 3. DOWNLOAD FILE
    public byte[] downloadFile(
            String bucketName,
            String key) {

        GetObjectRequest request =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

        ResponseBytes<GetObjectResponse> response =
                s3Client.getObjectAsBytes(request);

        return response.asByteArray();
    }

    // 4. LIST FILES
    public List<String> listFiles(String bucketName) {

        ListObjectsV2Request request =
                ListObjectsV2Request.builder()
                        .bucket(bucketName)
                        .build();

        ListObjectsV2Response response =
                s3Client.listObjectsV2(request);

        return response.contents()
                .stream()
                .map(S3Object::key)
                .toList();
    }

    // 5. DELETE FILE
    public String deleteFile(
            String bucketName,
            String key) {

        DeleteObjectRequest request =
                DeleteObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

        s3Client.deleteObject(request);

        return "File deleted: " + key;
    }

    // 6. GET FILE DETAILS
    public HeadObjectResponse getFileDetails(
            String bucketName,
            String key) {

        HeadObjectRequest request =
                HeadObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .build();

        return s3Client.headObject(request);
    }

    // 7. COPY FILE
    public String copyFile(
            String bucketName,
            String sourceKey,
            String destinationKey) {

        CopyObjectRequest request =
                CopyObjectRequest.builder()
                        .sourceBucket(bucketName)
                        .sourceKey(sourceKey)
                        .destinationBucket(bucketName)
                        .destinationKey(destinationKey)
                        .build();

        s3Client.copyObject(request);

        return "File copied to: " + destinationKey;
    }

    // 8. DELETE BUCKET
    public String deleteBucket(String bucketName) {

        DeleteBucketRequest request =
                DeleteBucketRequest.builder()
                        .bucket(bucketName)
                        .build();

        s3Client.deleteBucket(request);

        return "Bucket deleted: " + bucketName;
    }
}