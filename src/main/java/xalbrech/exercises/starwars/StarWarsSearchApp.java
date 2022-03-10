package xalbrech.exercises.starwars;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import xalbrech.exercises.starwars.crawler.ApiCrawler;
import xalbrech.exercises.starwars.index.SearchIndex;

/**
 * Main app class. Provides all necessary beans + actual main method to start the app.
 */
@SpringBootApplication
public class StarWarsSearchApp {

    private static final Logger log = LoggerFactory.getLogger(StarWarsSearchApp.class);

    /*@Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }*/

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * @return SearchIndex bean that maintains the search index
     */
    @Bean
    public SearchIndex searchIndex() {
        return new SearchIndex();
    }

    /**
     * @return ApiCrawler bean - performs traversal of the API + populates the search index
     */
    @Bean
    public ApiCrawler apiCrawler() {
        return new ApiCrawler();
    }

    /**
     * At the app start, a one-off population of the search index is performed.
     */
    @Bean
    public CommandLineRunner run(ApiCrawler apiCrawler, SearchIndex searchIndex) throws Exception {
        return args -> {
            apiCrawler.crawl();
        };
    }

    public static void main(String[] args) {
        SpringApplication.run(StarWarsSearchApp.class, args);
    }

}
