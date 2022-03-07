package xalbrech.exercises.starwars;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiSearcherTest {

    private ApiSearcher searcher;

    @BeforeEach
    public void setUp() {
        searcher = new ApiSearcher();
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
    public void searchTatooineShallReturnItsUrl() throws MalformedURLException {
        Collection<URL> result = searcher.search("Tatooine");
        assertThat(result, contains(new URL("https://swapi.dev/api/planets/1/")));
    }

    @Test
    public void searchAlderaanShallReturnItsUrl() throws MalformedURLException {
        Collection<URL> result = searcher.search("Alderaan");
        assertThat(result, contains(new URL("https://swapi.dev/api/planets/2/")));
    }

    @Test
    public void searchBothTatooineAndAlderaanShallReturnBothUrls() throws MalformedURLException {
        Collection<URL> result = searcher.search("Tatooine Alderaan");
        assertThat(result, containsInAnyOrder(new URL("https://swapi.dev/api/planets/1/"),
                new URL("https://swapi.dev/api/planets/2/")));
    }

}
