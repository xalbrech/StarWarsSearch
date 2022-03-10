package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;

/**
 * Mapping of the result of an SW API call. Maps the common fields of the response
 * that search needs (next url and the array of results).
 * @param <T> Type of the result, to be specd in extension classes.
 */
public abstract class ApiResult<T extends ApiObject> {

    private URI next;
    private Collection<T> results;

    public URI getNext() {
        return next;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

    public void setNext(URI next) {
        this.next = next;
    }

    public Collection<T> getResults() {
        return results;
    }

    public void populateSearchIndexWithResults(SearchIndex searchIndex) {
        getResults().stream().forEach(o -> o.populateSearchIndex(searchIndex));
    }

}
