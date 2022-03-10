package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;

public class Film extends ApiObject {

    private Collection<URI> characters, planets, starships, vehicles, species;

    public Collection<URI> getCharacters() {
        return characters;
    }

    public void setCharacters(Collection<URI> characters) {
        this.characters = characters;
    }

    public Collection<URI> getPlanets() {
        return planets;
    }

    public void setPlanets(Collection<URI> planets) {
        this.planets = planets;
    }

    public Collection<URI> getStarships() {
        return starships;
    }

    public void setStarships(Collection<URI> starships) {
        this.starships = starships;
    }

    public Collection<URI> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Collection<URI> vehicles) {
        this.vehicles = vehicles;
    }

    public Collection<URI> getSpecies() {
        return species;
    }

    public void setSpecies(Collection<URI> species) {
        this.species = species;
    }

    // name --> title :(
    public void setTitle(String title) {
        setName(title);
    }

    @Override
    protected void addReferencesInObjectToIndex(SearchIndex searchIndex) {
        addUrlsToIndexWithObjectName(searchIndex, getCharacters(), getPlanets(),
                            getSpecies(), getStarships(), getVehicles());
    }
}
