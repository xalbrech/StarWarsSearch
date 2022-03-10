package xalbrech.exercises.starwars.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xalbrech.exercises.starwars.index.SearchIndex;

/**
 * Controller of the search rest endpoint.
 */
@RestController
public class SearchController {

    @Autowired
    private SearchIndex searchIndex;

    @GetMapping("/starwars")
    public SearchResult search(@RequestParam String term) {
        return new SearchResult(term, searchIndex.search(term));
    }
}
