package itmo.localpiper.iblab1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import itmo.localpiper.iblab1.service.JwtService;

@Configuration
public class JwtServiceConfig {

    @Bean
    JwtService jwtService(@Value("${app.jwt.secret}") String secret,
                                 @Value("${app.jwt.expiration-ms}") long expirationMs) {
        return JwtService.create(secret, expirationMs);
    }
}
