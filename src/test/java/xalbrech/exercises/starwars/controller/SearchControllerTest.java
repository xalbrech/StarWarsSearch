package xalbrech.exercises.starwars.controller;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.util.Arrays;
import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import xalbrech.exercises.starwars.index.SearchIndex;

@SpringBootTest(classes = SearchController.class)
@EnableWebMvc
@AutoConfigureMockMvc
class SearchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private SearchIndex searchIndex;

    @Mock
    private SearchResult searchResult;

    @Test
    public void searchReachesSearchIndex() throws Exception {
        Collection<URI> searchIndexReply = Arrays.asList(URI.create("http://test/url"));
        when(searchIndex.search("test")).thenReturn(searchIndexReply);

        mockMvc.perform(get("/starwars?term=test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchTerm", is("test")))
                .andExpect(jsonPath("$.searchResults", contains("http://test/url")));

        verify(searchIndex).search("test");
    }

    @Test
    public void searchWithException() throws Exception {
        when(searchIndex.search("test")).thenThrow(new RuntimeException("test message"));

        mockMvc.perform(get("/starwars?term=test"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errorMessage", is("test message")));
    }

}