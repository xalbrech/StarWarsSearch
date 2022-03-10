package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;

/**
 * Mapping class for results of the planet resource - https://swapi.dev/api/planets/
 * Only maps the fields the search needs: name, url, residents, films
 */
public class Planet {

    private String name;
    private URI url;
    private Collection<URI> residents;
    private Collection<URI> films;

    public void setName(String name) {
        this.name = name;
    }

    public Collection<URI> getResidents() {
        return residents;
    }

    public void setResidents(Collection<URI> residents) {
        this.residents = residents;
    }

    public void setFilms(Collection<URI> films) {
        this.films = films;
    }

    public String getName() {
        return name;
    }

    public Collection<URI> getFilms() {
        return films;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }


}
