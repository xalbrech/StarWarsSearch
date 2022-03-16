package xalbrech.exercises.starwars.index;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchIndexTest {

    private SearchIndex searchIndex;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        searchIndex = new SearchIndex();
        searchIndex.addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/planets/1/"));
    }

    @Test
    public void searchNullOrEmptyPhraseShallReturnEmptyCollection() {
        assertEquals(Collections.emptySet(), searchIndex.search(null));
    }

    @Test
    public void searchEmptyPhraseShallReturnEmptyCollection() {
        assertEquals(Collections.emptySet(), searchIndex.search(""));
    }

    @Test
    public void searchNonExistentPlanetShallReturnEmptyResult() {
        assertEquals(Collections.emptySet(), searchIndex.search("PlanetNeverSeen"));
    }

    @Test
    public void searchTatooineShallReturnItsUrl() throws MalformedURLException {
        Collection<URI> result = searchIndex.search("Tatooine");
        assertThat(result, contains(URI.create("https://swapi.dev/api/planets/1/")));
    }

    @Test
    public void searchAlderaanShallReturnItsUrl() throws MalformedURLException {
        searchIndex.addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/planets/2/"));
        Collection<URI> result = searchIndex.search("Alderaan");
        assertThat(result, contains(URI.create("https://swapi.dev/api/planets/2/")));
    }

    @Test
    public void searchBothTatooineAndAlderaanShallReturnBothUrls() throws MalformedURLException {
        searchIndex.addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/planets/2/"));
        Collection<URI> result = searchIndex.search("Tatooine, Alderaan");
        assertThat(result, containsInAnyOrder(URI.create("https://swapi.dev/api/planets/1/"),
                URI.create("https://swapi.dev/api/planets/2/")));
    }


    @Test
    public void oneSearchTermsMayReturnMultipleResults() throws MalformedURLException {
        searchIndex.addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/planets/2/"));
        searchIndex.addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/vehicles/3/"));
        searchIndex.addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/films/1/"));
        Collection<URI> result = searchIndex.search("Alderaan");
        assertThat(result, containsInAnyOrder(URI.create("https://swapi.dev/api/planets/2/"),
                URI.create("https://swapi.dev/api/vehicles/3/"),
                URI.create("https://swapi.dev/api/films/1/")));

    }
}
