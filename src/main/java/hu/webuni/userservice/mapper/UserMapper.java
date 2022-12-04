package hu.webuni.userservice.mapper;

import hu.webuni.userservice.dto.UserDTO;
import hu.webuni.userservice.model.AppUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "userName")
    UserDTO toUserDTO(AppUser appUser);

    AppUser toUser(UserDTO userDTO);

}
