package hu.webuni.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

@EnableWebSecurity
@Configuration
public class UserServiceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;


  //  @Autowired
 //   JwtFilter jwtFilter;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/fbLoginSuccess", true);;
    }


    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //  http.httpBasic()
        http.csrf().disable()
                //OAUTH miatt ki kell kapcsolni
                //   .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //   .and()
                .authorizeRequests()
                .antMatchers("/services/**").permitAll()
                .antMatchers("/oauth2/**").permitAll()
                .antMatchers("/fbLoginSuccess").permitAll()
                .antMatchers("/topic/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/login/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/teacher/**").hasAnyAuthority("USER", "ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .defaultSuccessUrl("/fbLoginSuccess", true);
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
      //http://localhost:8083/szabi/login/oauth2/fbLoginSuccess
       // http://localhost:8083/szabi/login/oauth2/googleLoginSuccess
    }
*/

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
