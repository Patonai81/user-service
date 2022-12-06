package hu.webuni.userservice;

import hu.webuni.security.SecurityConfig;
import hu.webuni.security.service.KeyManagerService;
import hu.webuni.userservice.model.AppUser;
import hu.webuni.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@SpringBootApplication(scanBasePackageClasses = {UserServiceApplication.class, SecurityConfig.class})
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    KeyManagerService keyManagerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AppUser admin = AppUser.builder().userName("admin").email("admin@admin.hu")
                .password(passwordEncoder.encode("admin")).roles(Set.of("admin","customer")).build();

        userRepository.save(admin);
    }
}
