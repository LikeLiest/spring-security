package ru.zed.app.configuration.securityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import ru.zed.app.controller.filter.RedirectAuthenticatedUserFilter;
import ru.zed.app.service.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSessionCsrfTokenRepository httpSessionCsrfTokenRepository = new HttpSessionCsrfTokenRepository();

        return http
                .csrf(AbstractHttpConfigurer::disable
//                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
//                        .csrfTokenRepository(httpSessionCsrfTokenRepository)
//                        // Выполнить действия после успешной аутентификации
//                        .sessionAuthenticationStrategy(new CsrfAuthenticationStrategy(httpSessionCsrfTokenRepository))
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/LinkWorld/auth/**").anonymous()
                        .requestMatchers("/admin/**").hasAuthority("ADMIN")
                        .requestMatchers("/LinkWorld/error/404").permitAll()
                        .requestMatchers("/LinkWorld/session/**", "/LinkWorld/cookie/**").authenticated()
                        .anyRequest().authenticated()
                )
                .formLogin(login -> login
                        .loginPage("/LinkWorld/auth/login")
                        .failureUrl("/LinkWorld/auth/login?error")
                        .defaultSuccessUrl("/LinkWorld/user/account")
                        .permitAll()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .deleteCookies("username")
                        .logoutUrl("/LinkWorld/auth/logout")
                        .logoutSuccessUrl("/LinkWorld/auth/login")
                        .permitAll()
                )
                .exceptionHandling(exc -> exc.authenticationEntryPoint((_, response, _) ->
                        response.sendRedirect("/LinkWorld/auth/login"))
                )
                .addFilterBefore(new RedirectAuthenticatedUserFilter(), UsernamePasswordAuthenticationFilter.class)
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