package org.SpringBoot_GitHub.Config.ConfigSwagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

        @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("API REST Projeto Spring Boot e Git + GitHub")
                        .version("1.0.0")
                        .description("API REST para gerenciamento de Usuarios e Missões, desenvolvida como parte de um projeto de prática com Spring Boot, Git e GitHub. Esta API permite criar, listar, buscar por ID e deletar usuários, bem como associá-los a missões com diferentes graus de dificuldade."));
    }
}
