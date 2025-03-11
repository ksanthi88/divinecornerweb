package com.santhi.divinecornerweb.configuration;

import com.santhi.divinecornerweb.model.User;
import com.santhi.divinecornerweb.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.atomic.AtomicReference;

@Configuration
public class SecurityConfig {
    private final UserRepository userRepository;

    // ✅ Inject UserRepository into SecurityConfig
    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/home", "/products", "/about", "/contact", "/cart", "/users/register", "/users/login", "/css/**", "/js/**", "/images/**").permitAll() // Public pages
                        .requestMatchers("/admin/**").hasRole("ADMIN") // Restrict admin pages
                        .requestMatchers("/user/**").hasRole("USER") // Restrict user pages
                        .anyRequest().authenticated() // All other pages require login
                )
                .formLogin(login -> login
                        .loginPage("/users/login") // Custom login page
                        .loginProcessingUrl("/users/login") // Spring Security login handler
                        .successHandler((request, response, authentication) -> {
                            AtomicReference<String> redirectUrl = new AtomicReference<>("/home"); // Default redirect

                            authentication.getAuthorities().forEach(grantedAuthority -> {
                                if (grantedAuthority.getAuthority().equals("ROLE_ADMIN")) {
                                    redirectUrl.set("/admin/dashboard"); // Redirect admins to dashboard
                                }
                            });

                            response.sendRedirect(request.getContextPath() + redirectUrl);
                        })
                        .failureUrl("/users/login?error=true") // Redirect back to login on failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/home")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable()); // ✅ TEMPORARILY DISABLE CSRF
        return http.build();
    }
    // ✅ Fix: Injecting `userRepository` properly into the lambda function
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            User user = userRepository.findByEmail(email);

            if(user == null ) { throw new UsernameNotFoundException("User not found: " + email);}

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getEmail()) // ✅ Ensure correct field is used
                    .password(user.getPassword()) // ✅ Ensure password is already hashed
                    .roles(user.getRole().toString()) // ✅ Convert role to Spring Security format
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}