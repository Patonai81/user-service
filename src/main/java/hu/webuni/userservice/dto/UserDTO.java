package hu.webuni.userservice.dto;

import lombok.Data;

@Data
public class UserDTO {

    private Long id;
    protected String userName;
    protected String password;
    protected String email;

}
