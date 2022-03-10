package xalbrech.exercises.starwars.crawler.mapping;

import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.URI;
import java.util.Collection;
import java.util.stream.Stream;

public abstract class ApiObject {
    private String name;
    private URI url;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public URI getUrl() {
        return url;
    }

    public void setUrl(URI url) {
        this.url = url;
    }

    protected void addUrlsToIndexWithObjectName(SearchIndex searchIndex, Collection<URI>... urlsArray) {
        String name = getName();
        Stream.of(urlsArray).flatMap(Collection::stream).forEach(url -> searchIndex.addItemToIndex(name, url));
    }

    protected abstract void addReferencesInObjectToIndex(SearchIndex searchIndex);

    /**
     * Populates a given instance of search index with object's data (references to other obhects)
     * to be indexed.
     * @param searchIndex
     */
    protected void populateSearchIndex(SearchIndex searchIndex) {
        searchIndex.addItemToIndex(getName(), getUrl());
        addReferencesInObjectToIndex(searchIndex);
    }
}

