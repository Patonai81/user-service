package hu.webuni.userservice.repository;

import hu.webuni.userservice.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<AppUser,Long> {
    UserDetails findByUserName(String userName);
}
