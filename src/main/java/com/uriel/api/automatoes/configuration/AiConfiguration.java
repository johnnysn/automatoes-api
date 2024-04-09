package com.uriel.api.automatoes.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.ai")
@Getter
@Setter
public class AiConfiguration {

    private String chatModel;

    private Float chatTemperature;

}
