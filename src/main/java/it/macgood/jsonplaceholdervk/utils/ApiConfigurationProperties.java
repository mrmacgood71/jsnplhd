package it.macgood.jsonplaceholdervk.utils;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class ApiConfigurationProperties {

    String host;
    String postsUrl;
    String albumsUrl;
    String usersUrl;
    String webSocketEchoServerUrl;

}
