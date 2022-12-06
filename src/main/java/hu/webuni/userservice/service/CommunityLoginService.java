package hu.webuni.userservice.service;

import hu.webuni.userservice.dto.FacebookDTO;
import hu.webuni.userservice.model.AppUser;
import hu.webuni.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Service
public class CommunityLoginService {

    private final OAuth2AuthorizedClientService auth2AuthorizedClientService;

    private final UserRepository userRepository;
    private static final String FB_URL="https://graph.facebook.com/v13.0";

    public String extractToken(OAuth2AuthenticationToken oAuth2AuthenticationToken){

        String authorizedClientRegistrationId = oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
        OAuth2AuthorizedClient oAuth2AuthorizedClient = auth2AuthorizedClientService.loadAuthorizedClient(authorizedClientRegistrationId, oAuth2AuthenticationToken.getName());
        String token = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
        log.info("TOKEN IS: "+ token);
        log.info("URL IS:"+oAuth2AuthorizedClient.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUri());

        findOrCreateUserByToken(oAuth2AuthenticationToken);

        return token;
    }

    private AppUser findOrCreateUserByToken(OAuth2AuthenticationToken oAuth2AuthenticationToken) {
        AppUser byFacebookId = userRepository.findByUserName(oAuth2AuthenticationToken.getPrincipal().getName());
        if (null == byFacebookId){
            String email = oAuth2AuthenticationToken.getPrincipal().getAttribute("email");
            byFacebookId = new AppUser(email,email);
            byFacebookId.setRoles(Set.of("customer"));
            userRepository.save(byFacebookId);
        }
       return byFacebookId;
    }

    public AppUser getUserDetailsFromFBToken(String fbToken) {
          FacebookDTO facebookData = getDataFromFacebook(fbToken);
          log.info("Facebook data: ");
          log.info(facebookData.toString());
          return userRepository.findByUserName(String.valueOf(facebookData.getEmail()));
    }

    private FacebookDTO getDataFromFacebook(String fbToken){
       return  WebClient.create(FB_URL).get()
                .uri(uriBuilder -> {return uriBuilder.path("/me").queryParam("fields","email,name").build();})
                .headers(httpHeaders -> httpHeaders.setBearerAuth(fbToken))
                .retrieve()
                .bodyToMono(FacebookDTO.class)
                .block();
    }



}
