package xalbrech.exercises.starwars;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Performs the actual search of the API
 */
public class ApiSearcher {

    Map<String, URL> index = new HashMap<>();

    ApiSearcher() {
        try {
            index.put("Tatooine", new URL("https://swapi.dev/api/planets/1/"));
            index.put("Alderaan", new URL("https://swapi.dev/api/planets/2/"));
        } catch (MalformedURLException e) {
            // not good but we already know it will be refactored out
            e.printStackTrace();
        }
    }

    /**
     * Search the star wars URLs of StarWars resources found.
     *
     * @param phrase search string
     * @return Collection of URLs found based on the search phrase
     */
    public Collection<URL> search(String phrase) {

        if (phrase == null || "".equals(phrase)) {
            return Collections.emptyList();
        }

        String[] words = phrase.split("[\\s,]");
        return Stream.of(words).map(index::get).collect(Collectors.toList());

    }


}
