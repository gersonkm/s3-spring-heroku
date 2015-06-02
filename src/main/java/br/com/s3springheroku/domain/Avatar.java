package br.com.s3springheroku.domain;

/**
 * Representa a imagem do perfil do usuário.
 */
public class Avatar {

    private byte[] image;
    private AvatarImageFormat imageFormat;

    public Avatar(byte[] image, AvatarImageFormat imageFormat) {
        this.image = image;
        this.imageFormat = imageFormat;
    }

    public byte[] getImage() {
        return image;
    }

    public AvatarImageFormat getImageFormat() {
        return imageFormat;
    }
}
