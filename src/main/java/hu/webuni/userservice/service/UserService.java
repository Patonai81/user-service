package hu.webuni.userservice.service;

import hu.webuni.userservice.model.AppUser;
import hu.webuni.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AppUser createUser(AppUser appUser){
      log.info("Create user has been called");
      log.info(appUser.toString());
      setDefault(appUser);
      return userRepository.save(appUser);

    }

    @Transactional(Transactional.TxType.REQUIRED)
    protected void setDefault(AppUser appUser){
        appUser.setRoles(Set.of("customer"));
        if (null == appUser.getPassword()){
            appUser.setPassword(passwordEncoder.encode("pass"));
        }else {
            appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        }
    }


}
