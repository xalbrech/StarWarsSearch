package xalbrech.exercises.starwars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration
public class StarWarsSearchApp {

    private static final Logger log = LoggerFactory.getLogger(StarWarsSearchApp.class);

    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public SearchIndex searchIndex() {
        return new SearchIndex();
    }

    public static void main(String[] args) {
        SpringApplication.run(StarWarsSearchApp.class, args);
    }

}
