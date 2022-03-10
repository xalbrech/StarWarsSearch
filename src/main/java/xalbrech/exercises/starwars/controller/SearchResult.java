package xalbrech.exercises.starwars.controller;

import java.net.URL;
import java.util.Collection;

public class SearchResult {

    private String searchTerm;
    private Collection<URL> searchResults;

    public SearchResult(String searchTerm, Collection<URL> searchResults) {
        this.searchTerm = searchTerm;
        this.searchResults = searchResults;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public Collection<URL> getSearchResults() {
        return searchResults;
    }
}
