package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;

public class People extends ApiObject {

    URI homeworld;
    Collection<URI> films;
    Collection<URI> species;
    Collection<URI> vehicles;
    Collection<URI> starships;

    public URI getHomeworld() {
        return homeworld;
    }

    public void setHomeworld(URI homeworld) {
        this.homeworld = homeworld;
    }

    public Collection<URI> getFilms() {
        return films;
    }

    public void setFilms(Collection<URI> films) {
        this.films = films;
    }

    public Collection<URI> getSpecies() {
        return species;
    }

    public void setSpecies(Collection<URI> species) {
        this.species = species;
    }

    public Collection<URI> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<URI> vehicles) {
        this.vehicles = vehicles;
    }

    public Collection<URI> getStarships() {
        return starships;
    }

    public void setStarships(Collection<URI> starships) {
        this.starships = starships;
    }

    @Override
    protected void addReferencesInObjectToIndex(SearchIndex searchIndex) {
        addUrlsToIndexWithObjectName(searchIndex, getFilms(), getSpecies(), getStarships(), getVehicles());
        searchIndex.addItemToIndex(getName(), getHomeworld());
    }

    @Override
    protected void populateSearchIndex(SearchIndex searchIndex) {
        super.populateSearchIndex(searchIndex);
    }
}
