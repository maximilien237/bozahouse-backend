package net.bozahouse.backend.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import net.bozahouse.backend.security.jwt.AuthEntryPointJwt;
import net.bozahouse.backend.security.jwt.AuthTokenFilter;
import net.bozahouse.backend.security.services.UserDetailsServiceImpl;
import net.bozahouse.backend.utils.UtilsApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static net.bozahouse.backend.config.PasswordConfig.passwordEncoder;


@Slf4j
@AllArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private Environment environment;
  UserDetailsServiceImpl userDetailsService;
  private AuthEntryPointJwt unauthorizedHandler;

  @Bean
  public AuthTokenFilter authenticationJwtTokenFilter() {
    return new AuthTokenFilter();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.csrf().disable();

    http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);

    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    http.cors();

    http.headers().frameOptions().sameOrigin();

    if(!UtilsApp.isProdEnv(environment)){
      log.info("swagger-ui");
      http.authorizeRequests()
              .antMatchers(ControllerVariables.devAntPatterns)
              .permitAll();
    }

    http.authorizeRequests()
            .antMatchers(ControllerVariables.publicAntPatterns)
            .permitAll();

    http.authorizeRequests()
            .antMatchers(ControllerVariables.userAntPatterns)
            .hasAnyRole(ControllerVariables.USER_ROLE_NAME,ControllerVariables.EDITOR_ROLE_NAME,ControllerVariables.ADMIN_ROLE_NAME);

    http.authorizeRequests()
            .antMatchers(ControllerVariables.editorAntPatterns)
            .hasAnyRole(ControllerVariables.EDITOR_ROLE_NAME,ControllerVariables.ADMIN_ROLE_NAME);

    http.authorizeRequests()
            .antMatchers(ControllerVariables.adminAntPatterns)
            .hasAnyRole(ControllerVariables.ADMIN_ROLE_NAME);

    http.authorizeRequests()
            .anyRequest()
            .authenticated();

    http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

  }


}
