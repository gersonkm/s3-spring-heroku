package br.com.s3springheroku.domain;

import br.com.s3springheroku.infrastructure.aws.commons.CloudObjectNotFoundException;
import br.com.s3springheroku.infrastructure.aws.s3.SimpleS3Object;
import br.com.s3springheroku.infrastructure.aws.s3.SimpleS3ObjectDownloader;
import br.com.s3springheroku.infrastructure.aws.s3.SimpleS3ObjectLister;
import br.com.s3springheroku.infrastructure.aws.s3.SimpleS3ObjectUploader;
import com.google.common.io.ByteSource;
import com.google.common.net.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class AvatarRepository {

    private final String BUCKET = "company-file-server";
    private final String KEY_PREFIX = "profile-pictures/";

    @Autowired private SimpleS3ObjectUploader s3Uploader;
    @Autowired private SimpleS3ObjectDownloader s3Downloader;
    @Autowired private SimpleS3ObjectLister s3Lister;

    public String store(String userId, Avatar avatar) {
        String key = generateKey(userId);
        s3Uploader.makeUpload(BUCKET, key, ByteSource.wrap(avatar.getImage()), avatar.getImageFormat().getMediaType());
        return key;
    }

    public @Nullable Avatar getByUserEmail(String userId) {
        try {
            SimpleS3Object object = s3Downloader.makeDownload(BUCKET, KEY_PREFIX, userId);
            return new Avatar(object.getContent(), AvatarImageFormat.ofMediaType(parseMediaType(object.getMimeType())));
        }
        catch (CloudObjectNotFoundException e) {
            return null;
        }
    }

    private String generateKey(String userId) {
        return KEY_PREFIX + userId;
    }

    private MediaType parseMediaType(@Nullable String mediaType) {
        return (mediaType != null) ? MediaType.parse(mediaType) : null;
    }

    public List<Avatar> getAll() {
        List<SimpleS3Object> objects = s3Lister.list(BUCKET, KEY_PREFIX);
        return objects.stream()
                .map(object ->
                                new Avatar(
                                        object.getContent(),
                                        AvatarImageFormat.ofMediaType(parseMediaType(object.getMimeType()))
                                )
                )
                .collect(Collectors.toList());
    }


}
