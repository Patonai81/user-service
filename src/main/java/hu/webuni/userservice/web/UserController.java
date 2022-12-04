package hu.webuni.userservice.web;

import hu.webuni.userservice.dto.UserDTO;
import hu.webuni.userservice.mapper.UserMapper;
import hu.webuni.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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


    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO){
        if (null == userDTO){
            return ResponseEntity.badRequest().build();
        }else {
         log.info("User has been cussessfully createdd!");
         return ResponseEntity.ok( userMapper.toUserDTO(userService.createUser(userMapper.toUser(userDTO))));
        }
    }
}
