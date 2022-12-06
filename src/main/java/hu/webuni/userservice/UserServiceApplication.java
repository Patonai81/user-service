package hu.webuni.userservice;

import hu.webuni.security.SecurityConfig;
import hu.webuni.security.service.KeyManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackageClasses = {UserServiceApplication.class, SecurityConfig.class})
public class UserServiceApplication implements CommandLineRunner {

    @Autowired
    KeyManagerService keyManagerService;
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
