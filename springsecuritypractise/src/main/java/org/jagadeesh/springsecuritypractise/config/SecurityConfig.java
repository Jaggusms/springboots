package org.jagadeesh.springsecuritypractise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /*
     * STEP 1:
     * Configure users for authentication.
     */
    @Bean
    public UserDetailsService userDetailsService(
            PasswordEncoder passwordEncoder) {

        UserDetails normalUser = User.builder()
                .username("jagadeesh")
                .password(passwordEncoder.encode("user123"))
                .roles("USER")
                .build();

        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin123"))
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(
                normalUser,
                adminUser
        );
//        return new InMemoryUserDetailsManager(
//                normalUser,
//                adminUser,
//                anotherUser
//        );
//        return new InMemoryUserDetailsManager(normalUser);
    }

    /*
     * STEP 2:
     * Configure password encoding.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * STEP 3:
     * Configure HTTP security rules.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                // Disable CSRF for this REST API learning example.
                // Do not copy this setting blindly into production.
                .csrf(AbstractHttpConfigurer::disable)

                // Define endpoint authorization rules.
                .authorizeHttpRequests(auth -> auth

                        // Anyone can access public APIs.
                        .requestMatchers("/public/**").permitAll()

                        // Any authenticated user can access user APIs.
                        .requestMatchers("/user/**").authenticated()

                        // Only users with ROLE_ADMIN can access admin APIs.
                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        // All other requests require authentication.
                        .anyRequest().authenticated()
                )

                // Use HTTP Basic authentication.
//                .httpBasic(basic -> {});
                .httpBasic( Customizer.withDefaults());





        return http.build();
    }
}



/*

package org.jagadeesh.springsecuritypractise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http

            // CSRF
            .csrf(csrf -> csrf.disable())
            // .csrf(Customizer.withDefaults())

            // CORS
            .cors(Customizer.withDefaults())
            // .cors(cors -> cors.disable())

            // Authorization
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/user/**").authenticated()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/manager/**").hasAnyRole("ADMIN", "MANAGER")

                .requestMatchers("/api/read/**").hasAuthority("READ")
                .requestMatchers("/api/write/**").hasAnyAuthority("WRITE", "ADMIN")

                .requestMatchers("/api/**").denyAll()

                .dispatcherTypeMatchers(
                        jakarta.servlet.DispatcherType.FORWARD,
                        jakarta.servlet.DispatcherType.ERROR
                ).permitAll()

                .anyRequest().authenticated()
            )

            // HTTP Basic Authentication
            .httpBasic(Customizer.withDefaults())
            // .httpBasic(basic -> basic.realmName("MyApplication"))

            // Form Login
            .formLogin(Customizer.withDefaults())
            // .formLogin(form -> form.loginPage("/login").permitAll())

            // Logout
            .logout(Customizer.withDefaults())
            // .logout(logout -> logout.logoutUrl("/logout"))
            // .logout(logout -> logout.logoutSuccessUrl("/public/hello"))
            // .logout(logout -> logout.invalidateHttpSession(true))
            // .logout(logout -> logout.clearAuthentication(true))
            // .logout(logout -> logout.deleteCookies("JSESSIONID"))

            // Session Management
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                // .sessionCreationPolicy(SessionCreationPolicy.NEVER)
                // .sessionFixation(fixation -> fixation.migrateSession())
                // .maximumSessions(1)
            )

            // Exception Handling
            .exceptionHandling(exception -> exception
                // .authenticationEntryPoint(...)
                // .accessDeniedHandler(...)
            )

            // Request Cache
            .requestCache(Customizer.withDefaults())
            // .requestCache(cache -> cache.disable())

            // Security Headers
            .headers(headers -> headers
                // .frameOptions(frame -> frame.deny())
                // .frameOptions(frame -> frame.sameOrigin())
                // .contentTypeOptions(Customizer.withDefaults())
                // .cacheControl(Customizer.withDefaults())
                // .httpStrictTransportSecurity(hsts -> hsts
                //     .includeSubDomains(true)
                //     .maxAgeInSeconds(31536000)
                // )
                // .contentSecurityPolicy(csp -> csp
                //     .policyDirectives("default-src 'self'")
                // )
            )

            // Remember Me
            // .rememberMe(Customizer.withDefaults())
            // .rememberMe(remember -> remember
            //     .key("uniqueAndSecretKey")
            //     .tokenValiditySeconds(86400)
            // )

            // Anonymous Authentication
            .anonymous(Customizer.withDefaults())
            // .anonymous(anonymous -> anonymous.disable())

            // Request Authentication
            .anonymous(anonymous -> anonymous
                .principal("anonymousUser")
                .authorities("ROLE_ANONYMOUS")
            )

            // Custom Filters
            // .addFilterBefore(customFilter,
            //     org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            // .addFilterAfter(customFilter,
            //     org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
            // .addFilterAt(customFilter,
            //     org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)

            // OAuth2 Login
            // .oauth2Login(Customizer.withDefaults())

            // OAuth2 Resource Server / JWT
            // .oauth2ResourceServer(oauth2 -> oauth2
            //     .jwt(Customizer.withDefaults())
            // )

            // SAML2 Login
            // .saml2Login(Customizer.withDefaults())

            // Channel Security
            // .requiresChannel(channel -> channel
            //     .anyRequest().requiresSecure()
            // )

            // Authentication Provider
            // .authenticationProvider(customAuthenticationProvider)

            // Custom Authentication Manager
            // .authenticationManager(customAuthenticationManager)

            // Security Context
            .securityContext(Customizer.withDefaults())
            // .securityContext(context -> context.requireExplicitSave(true))

            // Cross-Origin Resource Sharing
            // .cors(cors -> cors.configurationSource(corsConfigurationSource))

            // Disable Security Completely (not recommended)
            // .securityMatcher("/api/**")
            // .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

            ;

        return http.build();
    }
}
 */




/*
package org.jagadeesh.springsecuritypractise.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    // First filter chain: API endpoints
    @Bean
    @Order(1)
    public SecurityFilterChain apiSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .securityMatcher("/api/**")

            .csrf(AbstractHttpConfigurer::disable)

            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/public/**").permitAll()
                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )

            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    // Second filter chain: All other endpoints
    @Bean
    @Order(2)
    public SecurityFilterChain webSecurityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
            )

            .formLogin(Customizer.withDefaults());

        return http.build();
    }
}
 */