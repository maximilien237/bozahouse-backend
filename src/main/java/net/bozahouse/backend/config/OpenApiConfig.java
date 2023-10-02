package net.bozahouse.backend.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("public-apis")
                .pathsToMatch("/**")
                .pathsToExclude("/actuator/**")
                .build();
    }
    @Bean
    public GroupedOpenApi actuatorApi(){
        return GroupedOpenApi.builder()
                .group("actuators")
                .pathsToMatch("/actuator/**")
                .build();
    }


    @Bean
    public OpenAPI apiInfo(){
        return new OpenAPI()
                .info(new Info().title("Bozahouse")
                .description("job Management REST API")
                .contact(new Contact().name("Bozahouse").url("https://www.bozahouse.com").email("contact@bozahouse.com"))
                .license(new License().name("Bozahouse Terms and Conditions").url("https://www.bozahouse.com/en/terms/"))
                .version("1.0.0"))
                .externalDocs(new ExternalDocumentation().description("").url(""));
    }

/*    @Bean
    public GroupedOpenApi adminApi(){
        return GroupedOpenApi.builder()
                .group("Bozahouse-admin")
                .pathsToMatch("/admin/**")
                .addOpenApiMethodFilter(method -> method.isAnnotationPresent( AdminRestController.class))
                .build();
    }*/

}
