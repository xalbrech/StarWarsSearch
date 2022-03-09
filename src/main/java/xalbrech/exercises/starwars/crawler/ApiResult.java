package xalbrech.exercises.starwars.crawler;

import java.net.URL;
import java.util.Collection;

public class ApiResult<T> {

    private int count;
    private URL next;
    private Collection<T> results;

    public void setCount(int count) {
        this.count = count;
    }

    public void setNext(URL next) {
        this.next = next;
    }

    public void setResults(Collection<T> results) {
        this.results = results;
    }

    public int getCount() {
        return count;
    }

    public URL getNext() {
        return next;
    }

    public Collection<T> getResults() {
        return results;
    }

}
