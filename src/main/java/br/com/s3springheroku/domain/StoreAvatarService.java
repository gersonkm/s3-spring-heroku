package br.com.s3springheroku.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StoreAvatarService {

    @Autowired
    private AvatarRepository avatarRepository;

    public void storeAvatar(String userId, byte[] image, String contentType) {
        Avatar avatar = new Avatar(image, AvatarImageFormat.ofMediaTypeString(contentType));
        avatarRepository.store(userId, avatar);
    }

}
