package br.com.s3springheroku.infrastructure.aws.s3;

import br.com.s3springheroku.infrastructure.aws.commons.CloudObjectDownloaderException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.IOException;

@Component
public class SimpleS3ObjectDownloader {

    @Autowired
    private AmazonS3 amazonS3;

    public SimpleS3Object makeDownload(String bucketName, @Nullable String dir, String key) throws CloudObjectDownloaderException {
        String fullKey = generateFullkey(dir, key);
        S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, fullKey));
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
