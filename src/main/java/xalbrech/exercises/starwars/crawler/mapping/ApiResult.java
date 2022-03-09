package xalbrech.exercises.starwars.crawler.mapping;

import java.net.URL;
import java.util.Collection;

/**
 * Mapping of the result of an SW API call. Maps the common fields of the response
 * that search needs (next url and the array of results).
 * @param <T> Type of the result, to be specd in extension classes.
 */
public abstract class ApiResult<T> {

    private URL next;
    private Collection<T> results;

    public void setNext(URL next) {
        this.next = next;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

    public URL getNext() {
        return next;
    }

    public Collection<T> getResults() {
        return results;
    }

}
