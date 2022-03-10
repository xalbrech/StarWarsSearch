package xalbrech.exercises.starwars.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import xalbrech.exercises.starwars.crawler.mapping.ApiEndpointList;
import xalbrech.exercises.starwars.crawler.mapping.Planet;
import xalbrech.exercises.starwars.crawler.mapping.PlanetsResult;
import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.net.URL;
import java.util.Collection;

/**
 * Traverses the API and populates the search index with data found
 */
public class ApiCrawler {

    private static final Logger log = LoggerFactory.getLogger(ApiCrawler.class);

    @Autowired
    private SearchIndex searchIndex;
    @Autowired
    private RestTemplate restTemplate;


    private void addUrlsToIndexWithKeyword(String keyword, Collection<URL> urls) {
        urls.stream().forEach(url -> searchIndex.addItemToIndex(keyword, url));
    }

    public void crawl() {

        ApiEndpointList endpointList = restTemplate
                    .getForEntity("https://swapi.dev/api/", ApiEndpointList.class)
                    .getBody();

        URI nextUrl = endpointList.get("planets");

        do {
            ResponseEntity<PlanetsResult> response = restTemplate.getForEntity(nextUrl, PlanetsResult.class);

            Collection<Planet> planets = response.getBody().getResults();
            planets.stream().forEach(p -> {
                String name = p.getName();
                addUrlsToIndexWithKeyword(name, p.getFilms());
                addUrlsToIndexWithKeyword(name, p.getResidents());
                searchIndex.addItemToIndex(name, p.getUrl());
                log.debug("Indexing planet. Name: {}, URL: {}\n films: {}\n residents: {}", name, p.getUrl(), p.getFilms(), p.getResidents());
            });
            nextUrl = response.getBody().getNext();

        } while(nextUrl != null);
    }

}