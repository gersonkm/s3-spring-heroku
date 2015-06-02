package br.com.s3springheroku.domain;

import com.google.common.net.MediaType;

import java.util.stream.Stream;

public enum AvatarImageFormat {
    JPEG(MediaType.JPEG, "jpg"),
    PNG(MediaType.PNG, "png"),
    GIF(MediaType.GIF, "gif")
    ;

    private MediaType mediaType;
    private String extension;

    AvatarImageFormat(MediaType mediaType, String extension) {
        this.mediaType = mediaType;
        this.extension = extension;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public String getExtension() {
        return extension;
    }

    public static AvatarImageFormat ofMediaType(MediaType mediaType) {
        return Stream.of(values())
                .filter(m -> m.getMediaType().equals(mediaType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Não foi encontrado um mime type compatível com '%s'.", mediaType)));
    }

    public static AvatarImageFormat ofMediaTypeString(String mediaType) {
        return Stream.of(values())
                .filter(m -> m.getMediaType().toString().equals(mediaType))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Não foi encontrado um mime type compatível com '%s'.", mediaType)));
    }
}
