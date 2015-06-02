package br.com.s3springheroku.infrastructure.aws.commons;

public class CloudObjectNotFoundException extends CloudObjectDownloaderException {

    public CloudObjectNotFoundException(String message) {
        super(message);
    }

    public CloudObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public CloudObjectNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
