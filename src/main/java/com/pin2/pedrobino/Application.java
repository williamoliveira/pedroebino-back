package com.pin2.pedrobino;

import com.pin2.pedrobino.configurations.ApplicationConfiguration;
import com.pin2.pedrobino.configurations.filters.CorsFilter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.AuthenticationManagerConfiguration;
import org.springframework.boot.autoconfigure.security.FallbackWebSecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SpringBootWebSecurityConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import javax.inject.Inject;
import java.util.Properties;

@ComponentScan("com.pin2.pedrobino")
@EnableJpaRepositories("com.pin2.pedrobino")
@Configuration
@EnableAutoConfiguration(exclude = {
        AuthenticationManagerConfiguration.class,
        FallbackWebSecurityAutoConfiguration.class,
        SecurityAutoConfiguration.class,
        SpringBootWebSecurityConfiguration.class
})
public class Application {

    @Inject
    private ApplicationConfiguration configuration;

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty("org.apache.tomcat.util.http.ServerCookie.STRICT_NAMING", "false");
        properties.setProperty("org.apache.tomcat.util.http.ServerCookie.ALLOW_HTTP_SEPARATORS_IN_V0", "true");

        new SpringApplicationBuilder(Application.class)
                .properties(properties)
                .run(args);
    }

    /**
     * Keeps the session open until the end of a request. Allows us to use lazy-loading with Hibernate.
     */
    @Bean
    public OpenEntityManagerInViewFilter openEntityManagerInViewFilter() {
        return new OpenEntityManagerInViewFilter();
    }

    @Bean
    public CorsFilter apiOriginFilter() {
        return new CorsFilter();
    }
}
