package cat.itacademy.mascota_virtual_back.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API de Mascota Virtual")
                        .version("1.0.0")
                        .description("API per gestionar mascotes virtuals, incloent crear, eliminar i actualitzar mascotes.")
                        .termsOfService("http://itacademy.cat/termes")
                        .contact(new Contact()
                                .name("Suport Mascota Virtual API")
                                .email("suport@mascotavirtual.cat")
                                .url("http://itacademy.cat"))
                        .license(new License()
                                .name("Llicència MIT")
                                .url("https://opensource.org/licenses/MIT"))
                );
    }
}

