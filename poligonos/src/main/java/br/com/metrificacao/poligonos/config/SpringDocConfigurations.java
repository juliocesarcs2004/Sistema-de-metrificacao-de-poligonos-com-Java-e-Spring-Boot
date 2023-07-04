package br.com.metrificacao.poligonos.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("API Rest Processamento de Poligonos")
                        .description("API Rest para processamento e poligonos e persitencia das métricas em um banco de dados MySQL")
                        .contact(new Contact()
                                .name("Time Backend")
                                .email("juliocesarcs2004@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://poligonos/api/licenca")));
    }

}
