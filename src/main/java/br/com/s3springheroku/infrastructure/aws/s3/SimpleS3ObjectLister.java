package br.com.s3springheroku.infrastructure.aws.s3;

import br.com.s3springheroku.infrastructure.aws.commons.CloudObjectDownloaderException;
import br.com.s3springheroku.infrastructure.aws.commons.CloudObjectNotFoundException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Façade (GoF) sobre a interface do SDK da AWS (Amazon Web Services) para manusear os serviços do Amazon S3
 * (Simple Storage Service) relacionados à listagem de objetos.
 * <p>
 * <p>A intenção desta classe é fornecer uma interface mais fácil para casos de uso simples.
 */
@Component
public class SimpleS3ObjectLister {

    @Autowired
    private AmazonS3 amazonS3;

    public List<SimpleS3Object> list(String bucketName, @Nullable String keyPrefix) throws CloudObjectDownloaderException {
        List<SimpleS3Object> result = new ArrayList<>();
        try {
            ObjectListing objectListing = amazonS3.listObjects(new ListObjectsRequest().withBucketName(bucketName).withPrefix(keyPrefix).withDelimiter("/"));
            for (S3ObjectSummary s3ObjectSummary : filterOnlyFiles(objectListing.getObjectSummaries())) {
                S3Object s3Object = amazonS3.getObject(bucketName, s3ObjectSummary.getKey());
                result.add(
                        new SimpleS3Object(
                                ByteStreams.toByteArray(s3Object.getObjectContent()),
                                s3Object.getObjectMetadata().getContentType()
                        ));
            }
            return result;
        }
        catch (Exception e) {
            throw new CloudObjectDownloaderException("Ocorreu um problema durante o download do objeto.", e);
        }
    }

    private List<S3ObjectSummary> filterOnlyFiles(List<S3ObjectSummary> objects) {
        return objects.stream()
                .filter(o -> !o.getKey().endsWith("/"))
                .collect(Collectors.toList());
    }

}
