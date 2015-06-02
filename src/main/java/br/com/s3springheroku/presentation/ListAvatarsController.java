package br.com.s3springheroku.presentation;

import br.com.s3springheroku.domain.User;
import br.com.s3springheroku.domain.UserRepository;
import br.com.s3springheroku.infrastructure.security.LoggedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping("/avatars-listing")
public class ListAvatarsController {

    @Autowired UserRepository userRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String index(@AuthenticationPrincipal LoggedUser loggedUser, ModelMap modelMap) {

        List<User> users = userRepository.findAll();

        modelMap.addAttribute("users", users);
        modelMap.addAttribute("loggedUser", loggedUser);

        return "profilePicture/avatars";
    }
}

