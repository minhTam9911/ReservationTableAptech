package com.bookingtable.configurations;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.bookingtable.servicies.IAccountService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public BCryptPasswordEncoder encoderBcrypt() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public IAccountService accountService;

   @Autowired
    public void configGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(accountService);
    }



 
 
 @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.cors(i -> i.disable()).csrf(i -> i.disable()).authorizeHttpRequests(auth -> {
                    auth.requestMatchers(
                            "assets/customer/css/**",
                            "assets/customer/image/**",
                            "assets/customer/js/**",
                            "assets/customer/scss/**",
                            "assets/customer/vendors/**",
                            "assets/customer/fonts/**",
                            "assets/system/css/**",
                            "assets/system/img/**",
                            
                            "assets/system/js/**",
                            "assets/system/scss/bootstrap/scss/**",
                            "assets/system/scss/bootstrap/scss/forms/**",
                            "assets/system/scss/bootstrap/scss/helpers/**",
                            "assets/system/scss/bootstrap/scss/mixins/**",
                            "assets/system/scss/bootstrap/scss/utilities/**",
                            "assets/system/scss/bootstrap/scss/vendor/**",
                            "assets/system/lib/chart/**",
                            "assets/system/lib/easing/**",
                            "assets/system/lib/waypoints/**",
                            "assets/system/lib/owlcarousel/**",
                            "assets/system/lib/tempusdominus/js/**",
                            "assets/system/lib/owlcarousel/assets/**",
                            "assets/system/lib/tempusdominus/css/**",
                            "assets/**",
                            "uploads/**",
                            "/login", "/register","/accessDenied",
                            "/",
                            "/restaurant/index",
                            "/account/accessDenied",
                            "/customer/**",
                            "/customer/home/index",
                            "/customer/aboutUs",
                            "/customer/dinnerTables",
                            "/customer/profile",
                            "/account/check-otp", "/verify-code","/reset-password", "/forgot-password",
                            "/verify/submit",
                            "/forgot-password/submit",
                            "/reset-password/save").permitAll();
                    auth.requestMatchers("/admin/panel/**").hasAnyRole("ADMIN");
                    auth.requestMatchers("/admin/panel/role/**").hasAnyRole("ADMIN");
                    auth.requestMatchers("/admin/panel/role/**").hasAnyRole("ADMIN");
                    auth.requestMatchers("/admin/panel/role/**").permitAll();
                    	auth.requestMatchers("/admin/panel/**").permitAll();
                    //auth.requestMatchers("/partner/**").hasAnyRole("PARTNER");
                    auth.requestMatchers("/staff/**").hasAnyRole("STAFF");
                    auth.requestMatchers("/staff/reservationAgent/**").hasAnyRole("STAFF");
                    auth.requestMatchers(HttpMethod.POST, "/staff/reservationAgent/create/save").hasAnyRole("STAFF");
                    auth.requestMatchers("/receptionist/**").hasAnyRole("RECEPTIONIST");
                    auth.requestMatchers("/partner/**").hasAnyRole("PARTNER");
                    auth.requestMatchers("/partner/restaurant/**").hasAnyRole("PARTNER");
                    auth.requestMatchers("/account/profile", "/account/changePass-save",
                            "/account/change-password", "/account/edit-avatar", "/account/edit-profile")
                            .hasAnyRole("STAFF", "ADMIN", "PARTNER", "RECEPTIONIST", "CUSTOMER");

                }).logout(logout ->
                        logout.logoutUrl("/logout").logoutSuccessUrl("/login")
                )
                .formLogin(login -> {
                    login.loginPage("/login").loginProcessingUrl("/login/process").usernameParameter("username")
                            .passwordParameter("password").successHandler(new AuthenticationSuccessHandler() {

                                @Override
                                public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                                    Authentication authentication) throws IOException, ServletException {
                                    Map<String, String> urls = new HashMap<>();
                                    urls.put("ROLE_ADMIN", "/admin/panel/index");
                                    urls.put("ROLE_STAFF", "/staff/index");
                                    urls.put("ROLE_PARTNER", "/partner/index");
                                    urls.put("ROLE_RECEPTIONIST", "/receptionist/index");
                                    String url = "";
                                    for (GrantedAuthority role : authentication.getAuthorities()) {
                                        if (urls.containsKey(role.getAuthority())) {
                                            url = urls.get(role.getAuthority());
                                            break;
                                        }
                                    }
                                    response.sendRedirect(url);
                                }
                            }).failureUrl("/login?error=usernotfound");

                }).exceptionHandling(ex -> {
                    ex.accessDeniedPage("/accessDenied");
                })
                .build();
    }

}
