package xalbrech.exercises.starwars.index;

import java.net.URI;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Maintain the search index and provide lookups on it ({@link #search(String)}.
 */
public class SearchIndex {

    // The index itself is a multi-map, maintaining a set of URLs for each keyword.
    Map<String, Set<URI>> index = new HashMap<>();

    /**
     * Add a new item to index.
     * @param keyword keyword to find the new item at
     * @param url URL of the item
     */
    public void addItemToIndex(String keyword, URI uri) {
        index.computeIfAbsent(keyword, k -> new HashSet<>()).add(uri);
    }

    /**
     * Search the star wars URLs of StarWars resources found.
     *
     * @param phrase search string
     * @return Collection of URLs found based on the search phrase
     */
    public Collection<URI> search(String phrase) {

        if (phrase == null || "".equals(phrase)) {
            return Collections.emptyList();
        }

        String[] words = phrase.split("[\\s,]");
        return Stream.of(words)
                      .map(index::get)
                      .filter(Objects::nonNull)
                      .flatMap(Set::stream)
                      .collect(Collectors.toList());
    }

}
