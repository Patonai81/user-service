package hu.webuni.userservice.web;

import hu.webuni.security.service.JwtService;
import hu.webuni.userservice.dto.LoginDTO;
import hu.webuni.userservice.dto.UserDTO;
import hu.webuni.userservice.mapper.UserMapper;
import hu.webuni.userservice.service.CommunityLoginService;
import hu.webuni.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/user")
@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final CommunityLoginService communityLoginService;



    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        if (null == userDTO){
            return ResponseEntity.badRequest().build();
        }else {
         log.info("User has been cussessfully created!");
         return ResponseEntity.ok( userMapper.toUserDTO(userService.createUser(userMapper.toUser(userDTO))));
        }
    }


    @PostMapping("/login")
    public String login(@RequestBody LoginDTO loginDTO) {
        log.debug(loginDTO.toString());
        UserDetails principal = null;

        if (null != loginDTO.getFbToken()) {
            principal = communityLoginService.getUserDetailsFromFBToken(loginDTO.getFbToken());
        } else {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
            principal = (UserDetails) authentication.getPrincipal();
        }
        return jwtService.createJwTToken(principal);
    }

}
