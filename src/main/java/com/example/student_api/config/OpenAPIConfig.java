package com.example.student_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.ExternalDocumentation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI studentManagementOpenAPI() {
        Server localServer = new Server();
        localServer.setUrl("http://localhost:8080");
        localServer.setDescription("Local Development Server");

        Contact contact = new Contact();
        contact.setName("Student API Support");
        contact.setEmail("support@studentapi.com");

        Info info = new Info()
                .title("Student Management REST API")
                .version("1.0.0")
                .description("Complete REST API for managing student records with CRUD operations, pagination, sorting, and search functionality. Built with Spring Boot 3 and MySQL.")
                .contact(contact);

        ExternalDocumentation externalDocs = new ExternalDocumentation()
                .description("GitHub Repository")
                .url("https://github.com/yourusername/student-api");

        return new OpenAPI()
                .info(info)
                .servers(List.of(localServer))
                .externalDocs(externalDocs);
    }
}

