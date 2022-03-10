package xalbrech.exercises.starwars.crawler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import xalbrech.exercises.starwars.index.SearchIndex;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

@RestClientTest
public class ApiCrawlerTest {

    @MockBean
    private SearchIndex searchIndex;

    // Mock out CLR so that it doesn't get called. Not sure how to do this better atm (Test-specific config?)
    @MockBean
    private CommandLineRunner commandLineRunner;

    @Autowired
    private MockRestServiceServer mockRestServiceServer;

    @Autowired
    private ApiCrawler crawler;

    @BeforeEach
    public void init() throws URISyntaxException {
        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://swapi.dev/api/")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{ " +
                                " \"people\": \"https://swapi.dev/api/people/\", " +
                                " \"planets\": \"https://swapi.dev/api/planets/\", " +
                                " \"films\": \"https://swapi.dev/api/films/\" " +
                                "} "));
    }

    @Test
    public void crawlPlanets() throws URISyntaxException, MalformedURLException {

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://swapi.dev/api/planets/")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{" +
                                "    \"next\": \"https://swapi.dev/api/planets/?page=2\", " +
                                "    \"previous\": null, " +
                                "    \"results\": [" +
                                "        {" +
                                "            \"name\": \"Tatooine\", " +
                                "            \"residents\": [" +
                                "                \"https://swapi.dev/api/people/1/\", " +
                                "                \"https://swapi.dev/api/people/2/\" " +
                                "            ], " +
                                "            \"films\": [" +
                                "                \"https://swapi.dev/api/films/1/\", " +
                                "                \"https://swapi.dev/api/films/3/\" " +
                                "            ], " +
                                "            \"url\": \"https://swapi.dev/api/planets/1/\"" +
                                "        }, " +
                                "        {" +
                                "            \"name\": \"Alderaan\", " +
                                "            \"residents\": [" +
                                "                \"https://swapi.dev/api/people/5/\", " +
                                "                \"https://swapi.dev/api/people/68/\" " +
                                "            ], " +
                                "            \"films\": [], " +
                                "            \"url\": \"https://swapi.dev/api/planets/2/\"" +
                                "        } " +
                                "   ] " +
                                "}")
                );


        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(new URI("https://swapi.dev/api/planets/?page=2")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{" +
                                "    \"next\": null, " +
                                "    \"previous\": null, " +
                                "    \"results\": [" +
                                "           {" +
                                "            \"name\": \"Yavin IV\", " +
                                "            \"residents\": [], " +
                                "            \"films\": [" +
                                "                \"https://swapi.dev/api/films/1/\"" +
                                "            ], " +
                                "            \"url\": \"https://swapi.dev/api/planets/3/\"" +
                                "        }" +
                                "   ]" +
                                "}")
                );


        crawler.crawl();

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

        verifyNoMoreInteractions(searchIndex);
        mockRestServiceServer.verify();

    }

}