package com.hibernateTest.hibernateTest.configuration.oauth2;

import com.hibernateTest.hibernateTest.configuration.jwt.JwtTokenFilterConfigurer;
import com.hibernateTest.hibernateTest.service.jwtService.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.Filter;
import javax.sql.DataSource;

@Configuration
@EnableOAuth2Client
@Order(1)
public class WebGoogleOAuthConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("oauth2ClientContext")
    OAuth2ClientContext oauth2ClientContext;

    @Autowired
    private DataSource dataSource;

    @Autowired
    @Qualifier("jwtTokenProvider")
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    @Qualifier("jwtTokenFilterConfigurer")
    private JwtTokenFilterConfigurer jwtTokenFilterConfigurer;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(this.passwordEncoder())
                .usersByUsernameQuery("select username, passwd, enable from userbasic where username=?")
                .authoritiesByUsernameQuery("select username, role from user_role_basic where username=?");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and()
                .authorizeRequests()
                .antMatchers("/","/singup**", "/welcome**", "/v2/**", "/swagger**",  "/webjars/**", "/login*", "/css/**", "/img/**",  "/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().accessDeniedPage("/welcome.html")
                .and()
                .formLogin()
                .loginPage("/welcome.html").usernameParameter("username").passwordParameter("password")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main.html", true)
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .apply(jwtTokenFilterConfigurer);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId());
        tokenServices.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(tokenServices);
        return googleFilter;
    }

    @Bean
    @ConfigurationProperties("security.oauth2.client")
    public AuthorizationCodeResourceDetails google() {
        return new AuthorizationCodeResourceDetails();
    }

    @Bean
    @ConfigurationProperties("security.oauth2.resource")
    public ResourceServerProperties googleResource() {
        return new ResourceServerProperties();
    }

    @Bean
    public FilterRegistrationBean<OAuth2ClientContextFilter> oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean<OAuth2ClientContextFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(filter);
        registration.setOrder(1);
        return registration;
    }
}
