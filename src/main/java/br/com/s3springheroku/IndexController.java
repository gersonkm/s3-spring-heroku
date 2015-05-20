package br.com.s3springheroku;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class IndexController {


    @RequestMapping("/")
    public String index(ModelMap modelMap) {
        modelMap.addAttribute("img1", UriComponentsBuilder.fromUriString("/s3download/a.gif").build());
        modelMap.addAttribute("img2", UriComponentsBuilder.fromUriString("/s3download/b.gif").build());
        return "index";
    }
}
