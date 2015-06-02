package br.com.s3springheroku.presentation;

import br.com.s3springheroku.domain.Avatar;
import br.com.s3springheroku.domain.User;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Nullable;

public class UserAvatar {
    private User user;
    private Avatar avatar;

    public UserAvatar(User user, @Nullable Avatar avatar) {
        this.user = user;
        this.avatar = avatar;
    }

    public User getUser() {
        return user;
    }

    public @Nullable Avatar getAvatar() {
        return avatar;
    }

    public boolean hasAvatar() {
        return avatar != null;
    }

    public String getAvatarUrl() {
        return UriComponentsBuilder
                .fromUriString("/user/{encodedUserEmail}/avatar/")
                .buildAndExpand(user.getEncodedEmail())
                .toUriString();

    }
}
