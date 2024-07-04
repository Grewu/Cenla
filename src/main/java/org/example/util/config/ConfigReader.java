package org.example.util.config;

import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

public class ConfigReader {
    public static BigDecimal getConfiguredInterestRate() {
        try (InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream("config.yml")) {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(inputStream);
            String interestRateString = String.valueOf(data.get("interestRate"));
            return new BigDecimal(interestRateString);
        } catch (IOException e) {
            throw new RuntimeException("Error reading application.yml", e);
        }
    }
}
