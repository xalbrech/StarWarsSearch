package xalbrech.exercises.starwars.controller;

import java.net.URI;
import java.util.Collection;

public class SearchResult {

    private String searchTerm;
    private Collection<URI> searchResults;

    public SearchResult(String searchTerm, Collection<URI> searchResults) {
        this.searchTerm = searchTerm;
        this.searchResults = searchResults;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public Collection<URI> getSearchResults() {
        return searchResults;
    }
}
