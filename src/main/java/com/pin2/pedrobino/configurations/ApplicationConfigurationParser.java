package com.pin2.pedrobino.configurations;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;

public class ApplicationConfigurationParser {

    private final ObjectMapper yamlMapper;
    private final Validator validator;

    public ApplicationConfigurationParser() {
        yamlMapper = new ObjectMapper(new YAMLFactory())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        validator = Validation.byProvider(HibernateValidator.class)
                .configure()
                .buildValidatorFactory()
                .getValidator();
    }

    public ApplicationConfiguration parse(@NotNull String profileName, @NotNull InputStream inputStream) throws IOException {
        ApplicationConfiguration config = yamlMapper.readValue(inputStream, ApplicationConfiguration.class);

        for (ConstraintViolation<ApplicationConfiguration> validationError : validator.validate(config)) {
            throw new ValidationException(String.format("Error validating environment '%s': '%s': %s",
                    profileName, validationError.getPropertyPath(), validationError.getMessage()));
        }

        return config;
    }
}
