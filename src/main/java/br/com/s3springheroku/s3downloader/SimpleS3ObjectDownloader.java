package br.com.s3springheroku.s3downloader;

import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.auth.SystemPropertiesCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.IOException;

@Component
public class SimpleS3ObjectDownloader {

    private AmazonS3 s3;

    public SimpleS3ObjectDownloader() {
        this.s3 = setupAmazonS3Client();
    }

    private AmazonS3 setupAmazonS3Client() {
        AmazonS3 s3 = new AmazonS3Client(new EnvironmentVariableCredentialsProvider().getCredentials());
        s3.setRegion(Region.getRegion(Regions.US_EAST_1));
        return s3;
    }

    public SimpleS3Object makeDownload(String bucketName, String key) throws CloudObjectDownloaderException {
        return makeDownload(bucketName, null, key);
    }

    public SimpleS3Object makeDownload(String bucketName, @Nullable String dir, String key) throws CloudObjectDownloaderException {
        String fullKey = generateFullkey(dir, key);
        S3Object s3Object = s3.getObject(new GetObjectRequest(bucketName, fullKey));
        try {
            return new SimpleS3Object(ByteStreams.toByteArray(s3Object.getObjectContent()), s3Object.getObjectMetadata().getContentType());
        }
        catch (IOException e) {
            throw new CloudObjectDownloaderException(e);
        }
    }

    private String generateFullkey(String dir, String key) {
        if (Strings.isNullOrEmpty(dir)) {
            return key;
        }
        return dir + "/" + key;
    }

}
