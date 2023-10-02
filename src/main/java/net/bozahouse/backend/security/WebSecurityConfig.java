package net.bozahouse.backend.security;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bozahouse.backend.security.jwt.JwtAuthenticationFilter;
import net.bozahouse.backend.utils.UtilsApp;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Slf4j
public class WebSecurityConfig {

  private final JwtAuthenticationFilter jwtAuthFilter;
  private final AuthenticationProvider authenticationProvider;
  private final Environment environment;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    ControllerVariables controllerVariables = new ControllerVariables();
    http.csrf(AbstractHttpConfigurer::disable);

    if(!UtilsApp.isProdEnv(environment)){
      log.info("swagger-ui");
      http.authorizeHttpRequests(auth -> auth
              .requestMatchers(controllerVariables.getDevAntPatterns())
              .permitAll());
    }

    http.authorizeHttpRequests(auth -> auth
            .requestMatchers(controllerVariables.getPublicAntPatterns())
            .permitAll());

    http.authorizeHttpRequests(auth -> auth
            .requestMatchers(controllerVariables.getUserAntPatterns())
            .hasAnyRole(controllerVariables.getUser(),controllerVariables.getEditor(),controllerVariables.getAdmin()));

    http.authorizeHttpRequests(auth -> auth
            .requestMatchers(controllerVariables.getEditorAntPatterns())
            .hasAnyRole(controllerVariables.getEditor(),controllerVariables.getAdmin()));

    http.authorizeHttpRequests(auth -> auth
            .requestMatchers(controllerVariables.getAdminAntPatterns())
            .hasAnyRole(controllerVariables.getAdmin()));

    http.authorizeHttpRequests(auth -> auth.anyRequest().authenticated());

    http.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    http.authenticationProvider(authenticationProvider);
    http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }




}
