package br.com.s3springheroku.infrastructure.aws.s3;

import br.com.s3springheroku.infrastructure.aws.commons.CloudObjectDownloaderException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.net.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Façade (GoF) sobre a interface do SDK da AWS (Amazon Web Services) para manusear os serviços do Amazon S3
 * (Simple Storage Service) relacionados ao envio de objetos (upload).
 *
 * <p>A intenção desta classe é fornecer uma interface mais fácil para casos de uso simples.
 */
@Component
public class SimpleS3ObjectUploader {

    @Autowired
    private AmazonS3 amazonS3;

    /**
     * Faz o upload de um objeto especificando sua chave e o bucket onde ele será armazenado.
     *
     * @throws CloudObjectDownloaderException se ocorrer um problema durante o download do objeto.
     */
    public void makeUpload(String bucketName, String key, ByteSource object, MediaType contentType) {
        makeUpload(bucketName, null, key, object, contentType);
    }

    /**
     * Faz o upload de um objeto especificando sua chave, um prefixo para a sua chave (opcional) e o
     * bucket onde ele está armazenado.
     *
     * @throws CloudObjectDownloaderException se ocorrer um problema durante o download do objeto.
     */
    public void makeUpload(String bucketName, @Nullable String keyPrefix, String key, ByteSource object, MediaType contentType) throws CloudObjectDownloaderException {
        makeUpload(bucketName, keyPrefix, key, object, contentType, ImmutableMap.of());
    }

    /**
     * Faz o upload de um objeto especificando sua chave, um prefixo para a sua chave (opcional), o
     * bucket onde ele está armazenado e os metadados.
     *
     * @throws CloudObjectDownloaderException se ocorrer um problema durante o download do objeto.
     */
    public void makeUpload(String bucketName, @Nullable String keyPrefix, String key, ByteSource object, MediaType contentType, Map<String, String> metadata) throws CloudObjectDownloaderException {
        String fullKey = generateFullkey(keyPrefix, key);
        try {
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType.toString());
            objectMetadata.setUserMetadata(metadata);
            amazonS3.putObject(new PutObjectRequest(bucketName, fullKey, new ByteArrayInputStream(object.read()), objectMetadata));
        }
        catch (Exception e) {
            throw new CloudObjectDownloaderException("Ocorreu um problema durante o download do objeto.", e);
        }
    }

    private String generateFullkey(@Nullable String dir, String key) {
        if (Strings.isNullOrEmpty(dir)) {
            return key;
        }
        return dir + key;
    }

}
