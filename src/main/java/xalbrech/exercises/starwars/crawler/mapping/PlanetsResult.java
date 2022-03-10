package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;

/**
 * Result list of planets.
 */
public class PlanetsResult extends ApiResult<Planet> {

    private void addUrlsToIndexWithKeyword(SearchIndex searchIndex, String keyword, Collection<URI> urls) {
        urls.stream().forEach(url -> searchIndex.addItemToIndex(keyword, url));
    }

    public void populateSearchIndexWithResults(SearchIndex searchIndex) {
        getResults().stream().forEach(p -> {
            String name = p.getName();
            addUrlsToIndexWithKeyword(searchIndex, name, p.getFilms());
            addUrlsToIndexWithKeyword(searchIndex, name, p.getResidents());
            searchIndex.addItemToIndex(name, p.getUrl());
        });
    }

}
