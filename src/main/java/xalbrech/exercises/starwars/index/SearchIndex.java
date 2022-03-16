package xalbrech.exercises.starwars.index;

import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Maintain the search index and provide lookups on it ({@link #search(String)}.
 */
@Component
public class SearchIndex {

    // The index itself is a multi-map, maintaining a set of URLs for each keyword.
    private Map<String, Set<URI>> index = new HashMap<>();

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
            return Collections.emptySet();
        }

        String[] words = phrase.split(",");
        return Stream.concat(Stream.of(phrase), Stream.of(words))
                .map(String::trim)
                .map(index::get)
                .filter(Objects::nonNull)
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

}
