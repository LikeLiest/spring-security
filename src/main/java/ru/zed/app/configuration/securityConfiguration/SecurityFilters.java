package ru.zed.app.configuration.securityConfiguration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityFilters {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("LinkWorld/account").authenticated()
                        .requestMatchers("LinkWorld/**").permitAll()
                )
                .formLogin(login -> login
                        .loginProcessingUrl("LinkWorld/login")
                        .defaultSuccessUrl("LinkWorld/account", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("LinkWorld/logout")
                        .permitAll())
                .exceptionHandling(exc ->
                        exc.authenticationEntryPoint(((request, response, authException) ->
                                response.sendRedirect("/LinkWorld/login")))
                )
                .build();
    }
}
