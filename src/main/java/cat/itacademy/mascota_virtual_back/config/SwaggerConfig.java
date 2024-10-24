package cat.itacademy.mascota_virtual_back.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")))
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
                                .name("GNU General Public License")
                                .url("https://www.gnu.org/licenses/gpl-3.0.html")));
    }
}


