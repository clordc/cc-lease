package com.cc.lease.common.minio;

import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan("com.cc.lease.common.minio")
@ConditionalOnProperty(name = "minio.endpoint")
public class MinioConfiguration {

    @Autowired
    private MinioProperties minioProperties;
    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder().endpoint(minioProperties.getEndpoint()).credentials(minioProperties.getAccessKey(), minioProperties.getSecretKey()).build();
    }


}
