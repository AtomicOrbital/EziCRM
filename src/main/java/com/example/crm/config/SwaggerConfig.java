package com.example.crm.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Configuration
@EnableOpenApi
public class SwaggerConfig {
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.OAS_30)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.crm.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .consumes(Set.of("multipart/form-data")) // API được định nghĩa để chấp nhận multipart/form-data
                .produces(Set.of("application/json")); // Định nghĩa loại content API trả về
        }

    private ApiKey apiKey() {
        return new ApiKey("Authorization","Authorization","header");
    }


        private ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("My REST API")
                    .description("Some custom description of API.")
                    .termsOfServiceUrl("API TOS")
                    .license("License of API")
                    .licenseUrl("API license URL")
                    .version("1.0.0")
                    .contact(new Contact("Your Name", "yourwebsite.com", "myeaddress@company.com"))
                    .build();
        }

        private SecurityContext securityContext() {
            return SecurityContext.builder()
                    .securityReferences(defaultAuth())
                    .build();
        }

        private List<SecurityReference> defaultAuth() {
            AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
            AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
            authorizationScopes[0] = authorizationScope;
            return Arrays.asList(new SecurityReference("Authorization", authorizationScopes));
        }
    }