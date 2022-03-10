package xalbrech.exercises.starwars.index;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchIndexTest {

    private SearchIndex searcher;

    @BeforeEach
    public void setUp() throws MalformedURLException {
        searcher = new SearchIndex();
        searcher.addItemToIndex("Tatooine", new URL("https://swapi.dev/api/planets/1/"));
    }

    @Test
    public void searchNullOrEmptyPhraseShallReturnEmptyCollection() {
        assertEquals(Collections.EMPTY_LIST, searcher.search(null));
    }

    @Test
    public void searchEmptyPhraseShallReturnEmptyCollection() {
        assertEquals(Collections.EMPTY_LIST, searcher.search(""));
    }

    @Test
    public void searchNonExistentPlanetShallReturnEmptyResult() {
        assertEquals(Collections.EMPTY_LIST, searcher.search("PlanetNeverSeen"));
    }

    @Test
    public void searchTatooineShallReturnItsUrl() throws MalformedURLException {
        Collection<URL> result = searcher.search("Tatooine");
        assertThat(result, contains(new URL("https://swapi.dev/api/planets/1/")));
    }

    @Test
    public void searchAlderaanShallReturnItsUrl() throws MalformedURLException {
        searcher.addItemToIndex("Alderaan", new URL("https://swapi.dev/api/planets/2/"));
        Collection<URL> result = searcher.search("Alderaan");
        assertThat(result, contains(new URL("https://swapi.dev/api/planets/2/")));
    }

    @Test
    public void searchBothTatooineAndAlderaanShallReturnBothUrls() throws MalformedURLException {
        searcher.addItemToIndex("Alderaan", new URL("https://swapi.dev/api/planets/2/"));
        Collection<URL> result = searcher.search("Tatooine Alderaan");
        assertThat(result, containsInAnyOrder(new URL("https://swapi.dev/api/planets/1/"),
                new URL("https://swapi.dev/api/planets/2/")));
    }


    @Test
    public void oneSearchTermsMayReturnMultipleResults() throws MalformedURLException {
        searcher.addItemToIndex("Alderaan", new URL("https://swapi.dev/api/planets/2/"));
        searcher.addItemToIndex("Alderaan", new URL("https://swapi.dev/api/vehicles/3/"));
        searcher.addItemToIndex("Alderaan", new URL("https://swapi.dev/api/films/1/"));
        Collection<URL> result = searcher.search("Alderaan");
        assertThat(result, containsInAnyOrder(new URL("https://swapi.dev/api/planets/2/"),
                new URL("https://swapi.dev/api/vehicles/3/"),
                new URL("https://swapi.dev/api/films/1/")));

    }
}
