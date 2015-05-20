package br.com.s3springheroku.infrastructure.aws.config;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonSdkProviderFactory {

    @Autowired
    private AwsConfig awsConfig;

    @Bean
    public AmazonS3 createS3Client() {
        AmazonS3Client amazonS3Client = new AmazonS3Client(new EnvironmentVariableCredentialsProvider().getCredentials());
        amazonS3Client.setRegion(Region.getRegion(Regions.fromName(awsConfig.getRegionName())));
        return amazonS3Client;
    }
}
