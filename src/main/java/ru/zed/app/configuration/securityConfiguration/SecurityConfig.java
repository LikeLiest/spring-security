package ru.zed.app.configuration.securityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.zed.app.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/LinkWorld/auth/register", "/LinkWorld/auth/login").anonymous()
                        .requestMatchers("/all-users").hasAuthority("ADMIN")
                        .requestMatchers("/LinkWorld/cookie/**").permitAll()
                        .requestMatchers("/LinkWorld/session/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/LinkWorld/auth/login")
                        .failureUrl("/LinkWorld/auth/login?error")
                        .defaultSuccessUrl("/LinkWorld/account")
                        .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .deleteCookies("username")
                        .logoutUrl("/LinkWorld/auth/logout")
                        .logoutSuccessUrl("/LinkWorld/auth/login")
                        .permitAll())
                .exceptionHandling(exc -> exc.authenticationEntryPoint((_, response, _) ->
                                response.sendRedirect("/LinkWorld/auth/login"))
                )
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}