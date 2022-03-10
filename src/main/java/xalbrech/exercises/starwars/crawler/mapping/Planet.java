package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;

/**
 * Mapping class for results of the planet resource - https://swapi.dev/api/planets/
 * Only maps the fields the search needs: name, url, residents, films
 */
public class Planet extends ApiObject {

    private Collection<URI> residents;
    private Collection<URI> films;

    public Collection<URI> getResidents() {
        return residents;
    }

    public void setResidents(Collection<URI> residents) {
        this.residents = residents;
    }

    public void setFilms(Collection<URI> films) {
        this.films = films;
    }

    public Collection<URI> getFilms() {
        return films;
    }

    @Override
    protected void addReferencesInObjectToIndex(SearchIndex searchIndex) {
        addUrlsToIndexWithObjectName(searchIndex, getFilms(), getResidents());
    }
}
