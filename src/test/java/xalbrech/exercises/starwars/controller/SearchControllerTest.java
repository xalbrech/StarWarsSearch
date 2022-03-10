package xalbrech.exercises.starwars.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class SearchControllerTest {

    @MockBean
    SearchIndex searchIndex;

    @Autowired
    SearchController searchController;

    @Mock
    SearchResult searchResult;

    @Test
    public void searchReachesSearchIndex() throws MalformedURLException {
        Collection<URI> searchIndexReply = Arrays.asList(URI.create("http://test/url"));
        when(searchIndex.search("test")).thenReturn(searchIndexReply);

        SearchResult result = searchController.search("test");
        verify(searchIndex).search("test");

        assertEquals(searchIndexReply, result.getSearchResults());
        assertEquals("test", result.getSearchTerm());
    }

}