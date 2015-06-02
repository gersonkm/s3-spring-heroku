package br.com.s3springheroku.presentation;

import br.com.s3springheroku.infrastructure.aws.s3.SimpleS3Object;
import br.com.s3springheroku.infrastructure.aws.s3.SimpleS3ObjectDownloader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class S3DownloadController {

    @Autowired
    private SimpleS3ObjectDownloader s3Downloader;

    @RequestMapping("/s3download/{key:.+}")
    public ResponseEntity<byte[]> download(@PathVariable String key) {
        SimpleS3Object object = s3Downloader.makeDownload("company-file-server", "gerson/", key);
        return createResponseEntity(object);
    }

    private ResponseEntity<byte[]> createResponseEntity(SimpleS3Object object) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(object.getMimeType()));
        return new ResponseEntity<>(object.getContent(), headers, HttpStatus.OK);
    }
}
