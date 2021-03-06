package xalbrech.exercises.starwars.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import xalbrech.exercises.starwars.crawler.mapping.ApiEndpointList;
import xalbrech.exercises.starwars.crawler.mapping.ApiResult;
import xalbrech.exercises.starwars.crawler.mapping.ApiResultClassFactory;
import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;

/**
 * Traverses the API and populates the search index with data found
 */
@Component
public class ApiCrawler {

    private static final Logger log = LoggerFactory.getLogger(ApiCrawler.class);

    @Autowired
    private SearchIndex searchIndex;

    @Autowired
    private RestTemplate restTemplate;

    public void crawl() {

        ApiEndpointList endpointList = restTemplate
                .getForEntity("https://swapi.dev/api/", ApiEndpointList.class)
                .getBody();

        endpointList
                .keySet()
                .stream()
                .forEach(endpointName -> {
                    URI nextUrl = endpointList.get(endpointName);
                    Class<? extends ApiResult> objectResultClass = ApiResultClassFactory.getResultForEndpointName(endpointName);
                    if (objectResultClass != null) {
                        do {
                            try {
                                ResponseEntity<? extends ApiResult> response = restTemplate.getForEntity(nextUrl, objectResultClass);
                                response.getBody().populateSearchIndexWithResults(searchIndex);
                                nextUrl = response.getBody().getNext();
                            } catch (Exception e) {
                                log.error("Error while populating search index with API objects name {}. Skipping.", endpointName);
                                log.error("Exception {}", e);
                            }
                        } while (nextUrl != null);
                    } else {
                        log.error("No mapping class found for object name {}. Skipping.", endpointName);
                    }
                });


    }

}