package br.com.s3springheroku.presentation;

import br.com.s3springheroku.domain.StoreAvatarService;
import br.com.s3springheroku.infrastructure.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
@RequestMapping("/change-avatar")
public class ChangeAvatarController {

    @Autowired
    private StoreAvatarService storeAvatarService;

    @RequestMapping(method = RequestMethod.GET)
    public String showForm(ModelMap modelMap, @AuthenticationPrincipal LoggedUser loggedUser) {
        modelMap.addAttribute("avatarUrl",
                UriComponentsBuilder
                        .fromUriString("/user/{encodedUserEmail}/avatar/")
                        .buildAndExpand(loggedUser.getEncodedEmail())
                        .toUriString());

        modelMap.addAttribute("loggedUser", loggedUser);
        return "profilePicture/update";
    }

    @RequestMapping(method= RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal LoggedUser loggedUser) throws Exception {
        storeAvatarService.storeAvatar(loggedUser.getEncodedEmail(), file.getBytes(), file.getContentType());
        return "redirect:/change-avatar";
    }

}
