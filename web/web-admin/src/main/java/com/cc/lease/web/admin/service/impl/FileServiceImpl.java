package com.cc.lease.web.admin.service.impl;

import com.cc.lease.common.minio.MinioProperties;
import com.cc.lease.web.admin.service.FileService;
import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private MinioClient minioClient;
    @Autowired
    private MinioProperties minioProperties;
    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
            boolean bucketExists = minioClient.bucketExists
                    (BucketExistsArgs.builder()
                            .bucket(minioProperties.getBucketname())
                            .build());
            if(!bucketExists){
                minioClient.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(minioProperties.getBucketname()).
                                build()
                );
            minioClient.setBucketPolicy(
                    SetBucketPolicyArgs.builder()
                            .bucket(minioProperties.getBucketname())
                            .config(createBucketPolicyConfig(minioProperties.getBucketname()))
                            .build()
            );
            }
            String filename = new SimpleDateFormat("yyyyMMdd").format(new Date())+
                    "/"+ UUID.randomUUID()+"-"+file.getOriginalFilename();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(minioProperties.getBucketname())
                    .stream(file.getInputStream(),file.getSize(),-1)
                    .object(filename)
                    .contentType(file.getContentType())
                    .build());
            return String.join("/",minioProperties.getEndpoint(),minioProperties.getBucketname(),filename);
    }
    private String createBucketPolicyConfig(String bucketName) {

        return """
            {
              "Statement" : [ {
                "Action" : "s3:GetObject",
                "Effect" : "Allow",
                "Principal" : "*",
                "Resource" : "arn:aws:s3:::%s/*"
              } ],
              "Version" : "2012-10-17"
            }
            """.formatted(bucketName);
    }
}


