package com.pdfen.spring_example.config;

import com.pdfen.spring_example.PDFEnClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@Slf4j
@Configuration
public class AppConfiguration {

    @Value("${filePath}")
    private String filePath;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public CommandLineRunner run(PDFEnClient pdfEnClient) {
        return args -> {
            File file = new File(filePath);
            if (file.exists()) {
                log.info("Converting {} to PDF", file.getName());
                pdfEnClient.convert(filePath);
            } else {
                log.error("File not found: {}", file.getAbsoluteFile());
            }
        };
    }
}
