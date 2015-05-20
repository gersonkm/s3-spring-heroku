package br.com.s3springheroku.s3downloader;

/**
 * Objeto armazenado na cloud que está disponível para download.
 */
public class SimpleS3Object {

    private byte[] content;
    private String mimeType;

    public SimpleS3Object(byte[] content, String mimeType) {
        this.content = content;
        this.mimeType = mimeType;
    }

    /**
     * Retorna o conteúdo em array de bytes.
     */
    public byte[] getContent() {
        return content;
    }

    /**
     * Retorna o mime type do objeto.
     */
    public String getMimeType() {
        return mimeType;
    }
}
