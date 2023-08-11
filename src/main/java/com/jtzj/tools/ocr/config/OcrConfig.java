package com.jtzj.tools.ocr.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@ConfigurationProperties(prefix = "ocr")
public class OcrConfig {
    private String dataPath;
    private String language;
}
