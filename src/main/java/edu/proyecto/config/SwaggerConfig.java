package edu.proyecto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;


@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI(){
        return new OpenAPI()
        .info(new Info().title("HTTP API ARCHIVO")
                        .description("Documentación de los métodos u operaciones del API de Archivo")
                        .version("1.0.0")
                        .contact(new Contact().email("USUARIO@EMAIL.COM")
                        .name("USUARIO")                            )
                        .license(new License().name("APACHE 2.0")
                        .url("http://www.opensource.org/licenses/apache2.html")
                                                    )
                        );
    }
}
