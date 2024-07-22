package com.vanlang.hobby_station;

import com.vanlang.hobby_station.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService());
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(@NotNull HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/css/**", "/js/**", "/image/**", "/", "/oauth/**", "/register", "/error", "/product/**", "/shop", "/cart", "/search")
                        .permitAll() // Cho phép truy cập không cần xác thực.
                        .requestMatchers("/admin/**", "/api/admin/**", "api/orders/**", "/js/adminProducts", "/js/dashboard")
                        .hasAnyAuthority("ADMIN") // Chỉ cho phép ADMIN truy cập.   
                        .requestMatchers("/api/**")
                        .permitAll() // API mở cho mọi người dùng.
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )
                .formLogin(formLogin -> formLogin
                .loginPage("/login") // Trang đăng nhập.
                .loginProcessingUrl("/login") // URL xử lý đăng nhập.
                .defaultSuccessUrl("/") // Trang sau đăng nhập thành công.
                .failureUrl("/login?error") // Trang đăng nhập thất bại.
                .permitAll()
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("vanlang")
                        .rememberMeCookieName("vanlang")
                        .tokenValiditySeconds(24 * 60 * 60)
                        .userDetailsService(userDetailsService())
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedPage("/403")
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1)
                        .expiredUrl("/login")
                )
                .httpBasic(httpBasic -> httpBasic
                        .realmName("vanlang")
                )
                .build();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities();
            for (var grantedAuthority : authorities) {
                if (grantedAuthority.getAuthority().equals("ADMIN")) {
                    response.sendRedirect("/admin");
                    return;
                }
            }
            response.sendRedirect("/");
        };
    }
}
