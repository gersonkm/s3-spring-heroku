package br.com.s3springheroku.infrastructure.aws.s3;

import br.com.s3springheroku.infrastructure.aws.commons.CloudObjectDownloaderException;
import br.com.s3springheroku.infrastructure.aws.commons.CloudObjectNotFoundException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.google.common.base.Strings;
import com.google.common.io.ByteStreams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

/**
 * Façade (GoF) sobre a interface do SDK da AWS (Amazon Web Services) para manusear os serviços do Amazon S3
 * (Simple Storage Service) relacionados à obtenção de objetos (download).
 *
 * <p>A intenção desta classe é fornecer uma interface mais fácil para casos de uso simples.
 */
@Component
public class SimpleS3ObjectDownloader {

    @Autowired
    private AmazonS3 amazonS3;

    /**
     * Faz o download de um objeto especificando sua chave e o bucket onde ele está armazenado.
     *
     * @throws CloudObjectDownloaderException se ocorrer um problema durante o upload do objeto.
     */
    public SimpleS3Object makeDownload(String bucketName, String key) {
        return makeDownload(bucketName, null, key);
    }

    /**
     * Faz o download de um objeto especificando sua chave, um prefixo para a sua chave (opcional) e o
     * bucket onde ele está armazenado.
     *
     * @throws CloudObjectNotFoundException o objeto não for encontrado.
     * @throws CloudObjectDownloaderException se ocorrer um problema durante o download do objeto.
     */
    public SimpleS3Object makeDownload(String bucketName, @Nullable String keyPrefix, String key) throws CloudObjectDownloaderException {
        String fullKey = generateFullkey(keyPrefix, key);
        try {
            S3Object s3Object = amazonS3.getObject(new GetObjectRequest(bucketName, fullKey));
            return new SimpleS3Object(
                    ByteStreams.toByteArray(s3Object.getObjectContent()),
                    s3Object.getObjectMetadata().getContentType()
            );
        }
        catch (AmazonServiceException e) {
            String errorCode = e.getErrorCode();
            if (errorCode.equals("NoSuchKey")) {
                throw new CloudObjectNotFoundException("Não foi encontrado o objeto '" + fullKey + "'.");
            }
            throw new CloudObjectDownloaderException("Ocorreu um problema durante o download do objeto.", e);
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
