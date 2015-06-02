package br.com.s3springheroku.presentation;

import br.com.s3springheroku.domain.StoreAvatarService;
import br.com.s3springheroku.infrastructure.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class S3UploadController {

    @Autowired
    private StoreAvatarService storeAvatarService;

    @RequestMapping(value="/upload", method=RequestMethod.POST)
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal LoggedUser loggedUser) throws Exception {
        if (file.isEmpty()) {
            return "Ops!!";
        }
        storeAvatarService.storeAvatar(loggedUser.getEmail(), file.getBytes(), file.getContentType());
        return "index";
    }
}
