package br.com.s3springheroku.presentation;

import br.com.s3springheroku.domain.Avatar;
import br.com.s3springheroku.domain.AvatarImageFormat;
import com.google.common.io.ByteStreams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.io.IOException;

@Configuration
public class DefaultAvatarService {

    @Value("${avatar.default-image.path}")
    private Resource defaultImagePath;

    @Value("${avatar.default-image.format}")
    private String defaultImageFormat;

    @Bean
    public Avatar create() {
        try {
            return new Avatar(ByteStreams.toByteArray(defaultImagePath.getInputStream()), AvatarImageFormat.valueOf(defaultImageFormat));
        }
        catch (IOException e) {
            throw new RuntimeException("Não foi possível recuperar a imagem padrão.");
        }
    }

}
