package br.com.s3springheroku.s3downloader;

public class CloudObjectDownloaderException extends RuntimeException {
    public CloudObjectDownloaderException(String message) {
        super(message);
    }

    public CloudObjectDownloaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public CloudObjectDownloaderException(Throwable cause) {
        super(cause);
    }

    public CloudObjectDownloaderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
