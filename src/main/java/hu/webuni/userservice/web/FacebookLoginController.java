package hu.webuni.userservice.web;

import hu.webuni.userservice.service.CommunityLoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FacebookLoginController {

 private final CommunityLoginService communityLoginService;

    @RequestMapping("/fbLoginSuccess")
    public String onFacebookLoginSuccess(Map<String,Object> model, OAuth2AuthenticationToken oAuth2AuthenticationToken
    , @AuthenticationPrincipal OAuth2User user){
       String token = communityLoginService.extractToken(oAuth2AuthenticationToken);
       model.put("fullName",user.getAttribute("name"));
       model.put("token",token);
       return "home";
    }
}
