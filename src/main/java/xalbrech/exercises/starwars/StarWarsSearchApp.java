package xalbrech.exercises.starwars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import xalbrech.exercises.starwars.crawler.ApiCrawler;
import xalbrech.exercises.starwars.index.SearchIndex;

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

    @Bean
    public ApiCrawler apiCrawler() {
        return new ApiCrawler();
    }

    @Bean
    public CommandLineRunner run(ApiCrawler apiCrawler, SearchIndex searchIndex) throws Exception {
        return args -> {
            apiCrawler.crawl();
            log.debug("Test call - results of search for \"Tatooine\" {}", searchIndex.search("Tatooine"));
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(StarWarsSearchApp.class, args);
    }

}
