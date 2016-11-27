package com.pin2.pedrobino.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.inject.Inject;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class YamlConfigInitialization {
    private static final Logger LOGGER = LoggerFactory.getLogger(YamlConfigInitialization.class);

    @Inject
    private Environment springEnvironment;

    @Bean
    public ApplicationConfiguration getApplicationConfig() throws IOException {
        String environmentName = springEnvironment.getActiveProfiles().length > 0
                ? springEnvironment.getActiveProfiles()[0]
                : "local";

        try (InputStream inputStream = YamlConfigInitialization.class.getResourceAsStream("/config/" + environmentName + ".yml")) {
            if (inputStream == null) {
                throw new IOException("Profile not found: " + environmentName);
            }

            return new ApplicationConfigurationParser().parse(environmentName, inputStream);
        }
    }
}
