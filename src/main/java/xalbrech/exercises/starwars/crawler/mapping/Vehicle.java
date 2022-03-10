package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;

public class Vehicle extends ApiObject {

    Collection<URI> pilots, films;

    public Collection<URI> getPilots() {
        return pilots;
    }

    public void setPilots(Collection<URI> pilots) {
        this.pilots = pilots;
    }

    public Collection<URI> getFilms() {
        return films;
    }

    public void setFilms(Collection<URI> films) {
        this.films = films;
    }

    @Override
    protected void addReferencesInObjectToIndex(SearchIndex searchIndex) {
        addUrlsToIndexWithObjectName(searchIndex, getPilots(), getFilms());
    }
}
