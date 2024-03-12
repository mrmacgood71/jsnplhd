package it.macgood.jsonplaceholdervk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class ApiConfigurationProperties {

    private String host;
    private String postsUrl;
    private String albumsUrl;
    private String usersUrl;

}
