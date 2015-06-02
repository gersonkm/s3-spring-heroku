package br.com.s3springheroku.presentation;

import br.com.s3springheroku.domain.Avatar;
import br.com.s3springheroku.domain.AvatarRepository;
import com.google.common.base.MoreObjects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class GetUserAvatarController {

    @Autowired private AvatarRepository avatarRepository;
    @Autowired private Avatar defaultAvatar;

    @RequestMapping("/user/{encodedUserEmail}/cacheable-avatar")
    public ResponseEntity<byte[]> getCacheableAvatar(@PathVariable String encodedUserEmail) {
        Avatar avatar = MoreObjects.firstNonNull(avatarRepository.getByUserEmail(encodedUserEmail), defaultAvatar);
        return createResponseEntity(avatar, true);
    }

    @RequestMapping("/user/{encodedUserEmail}/avatar")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String encodedUserEmail) {
        Avatar avatar = MoreObjects.firstNonNull(avatarRepository.getByUserEmail(encodedUserEmail), defaultAvatar);
        return createResponseEntity(avatar, false);
    }

    private ResponseEntity<byte[]> createResponseEntity(Avatar avatar, boolean enableCacheControl) {
        ResponseEntity.BodyBuilder bodyBuilder = ResponseEntity.ok();

        bodyBuilder.contentType(MediaType.valueOf(avatar.getImageFormat().getMediaType().toString()));
        if (enableCacheControl) {
            bodyBuilder.cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS));
        }
        return bodyBuilder.body(avatar.getImage());
    }
}
