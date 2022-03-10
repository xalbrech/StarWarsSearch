package xalbrech.exercises.starwars.crawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@SpringBootTest
class ApiCrawlerTest {

    @MockBean
    private SearchIndex searchIndex;

    @Autowired
    private RestTemplate restTemplate;

    private MockRestServiceServer mockServer;

    @Autowired
    private ApiCrawler crawler;

    @BeforeEach
    public void init() {
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void crawlPlanets() throws URISyntaxException, MalformedURLException {
        mockServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://swapi.dev/api/planets/")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{\n" +
                                "    \"count\": 60, \n" +
                                "    \"next\": \"https://swapi.dev/api/planets/?page=2\", \n" +
                                "    \"previous\": null, \n" +
                                "    \"results\": [\n" +
                                "        {\n" +
                                "            \"name\": \"Tatooine\", \n" +
                                "            \"residents\": [\n" +
                                "                \"https://swapi.dev/api/people/1/\", \n" +
                                "                \"https://swapi.dev/api/people/2/\", \n" +
                                "            ], \n" +
                                "            \"films\": [\n" +
                                "                \"https://swapi.dev/api/films/1/\", \n" +
                                "                \"https://swapi.dev/api/films/3/\", \n" +
                                "            ], \n" +
                                "            \"url\": \"https://swapi.dev/api/planets/1/\"\n" +
                                "        }, \n" +
                                "        {\n" +
                                "            \"name\": \"Alderaan\", \n" +
                                "            \"residents\": [\n" +
                                "                \"https://swapi.dev/api/people/5/\", \n" +
                                "                \"https://swapi.dev/api/people/68/\", \n" +
                                "            ], \n" +
                                "            \"films\": [], \n" +
                                "            \"url\": \"https://swapi.dev/api/planets/2/\"\n" +
                                "        }, \n" +
                                "        {\n" +
                                "            \"name\": \"Yavin IV\", \n" +
                                "            \"residents\": [], \n" +
                                "            \"films\": [\n" +
                                "                \"https://swapi.dev/api/films/1/\"\n" +
                                "            ], \n" +
                                "            \"url\": \"https://swapi.dev/api/planets/3/\"\n" +
                                "        }")
                );

        verify(searchIndex).addItemToIndex("Tatooine", new URL("https://swapi.dev/api/planets/1/"));
        verify(searchIndex).addItemToIndex("Tatooine", new URL("https://swapi.dev/api/people/1/"));
        verify(searchIndex).addItemToIndex("Tatooine", new URL("https://swapi.dev/api/people/2/"));
        verify(searchIndex).addItemToIndex("Tatooine", new URL("https://swapi.dev/api/films/1/"));
        verify(searchIndex).addItemToIndex("Tatooine", new URL("https://swapi.dev/api/films/3/"));

        verify(searchIndex).addItemToIndex("Alderaan", new URL("https://swapi.dev/api/planets/2/"));
        verify(searchIndex).addItemToIndex("Alderaan", new URL("https://swapi.dev/api/people/5/"));
        verify(searchIndex).addItemToIndex("Alderaan", new URL("https://swapi.dev/api/people/68/"));

        verify(searchIndex).addItemToIndex("Yavin IV", new URL("https://swapi.dev/api/planets/3/"));
        verify(searchIndex).addItemToIndex("Yavin IV", new URL("https://swapi.dev/api/films/1/"));

    }


}