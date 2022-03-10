package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;

public class Species extends ApiObject {

    Collection<URI> people, films;

    public Collection<URI> getPeople() {
        return people;
    }

    public void setPeople(Collection<URI> people) {
        this.people = people;
    }

    public Collection<URI> getFilms() {
        return films;
    }

    public void setFilms(Collection<URI> films) {
        this.films = films;
    }

    @Override
    protected void addReferencesInObjectToIndex(SearchIndex searchIndex) {
        addUrlsToIndexWithObjectName(searchIndex, getPeople(), getFilms());
    }

}
