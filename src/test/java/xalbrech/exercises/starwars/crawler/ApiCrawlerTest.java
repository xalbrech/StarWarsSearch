package xalbrech.exercises.starwars.crawler;

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

    @Test
    public void crawlPlanets_OverMultiplePages() throws URISyntaxException, MalformedURLException {

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(URI.create("https://swapi.dev/api/")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{ " +
                                " \"planets\": \"https://swapi.dev/api/planets/\"" +
                                "} "));

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(URI.create("https://swapi.dev/api/planets/")))
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
                        requestTo(URI.create("https://swapi.dev/api/planets/?page=2")))
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

        verify(searchIndex).addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/planets/1/"));
        verify(searchIndex).addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/people/1/"));
        verify(searchIndex).addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/people/2/"));
        verify(searchIndex).addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/films/1/"));
        verify(searchIndex).addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/films/3/"));

        verify(searchIndex).addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/planets/2/"));
        verify(searchIndex).addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/people/5/"));
        verify(searchIndex).addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/people/68/"));

        verify(searchIndex).addItemToIndex("Yavin IV", URI.create("https://swapi.dev/api/planets/3/"));
        verify(searchIndex).addItemToIndex("Yavin IV", URI.create("https://swapi.dev/api/films/1/"));

        verifyNoMoreInteractions(searchIndex);
        mockRestServiceServer.verify();

    }

    @Test
    public void crawlPeopleAndPlanets() {
        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(URI.create("https://swapi.dev/api/")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{ " +
                                " \"people\": \"https://swapi.dev/api/people/\", " +
                                " \"planets\": \"https://swapi.dev/api/planets/\" " +
                                "} "));

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(URI.create("https://swapi.dev/api/people/")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{" +
                                "    \"count\": 3, " +
                                "    \"next\": null, " +
                                "    \"previous\": null, " +
                                "    \"results\": [" +
                                "        {" +
                                "            \"name\": \"Luke Skywalker\", " +
                                "            \"height\": \"172\", " +
                                "            \"mass\": \"77\", " +
                                "            \"hair_color\": \"blond\", " +
                                "            \"skin_color\": \"fair\", " +
                                "            \"eye_color\": \"blue\", " +
                                "            \"birth_year\": \"19BBY\", " +
                                "            \"gender\": \"male\", " +
                                "            \"homeworld\": \"https://swapi.dev/api/planets/1/\", " +
                                "            \"films\": [" +
                                "                \"https://swapi.dev/api/films/1/\", " +
                                "                \"https://swapi.dev/api/films/6/\"" +
                                "            ], " +
                                "            \"species\": [], " +
                                "            \"vehicles\": [], " +
                                "            \"starships\": [" +
                                "                \"https://swapi.dev/api/starships/22/\"" +
                                "            ], " +
                                "            \"url\": \"https://swapi.dev/api/people/1/\"" +
                                "        }, " +
                                "        {" +
                                "            \"name\": \"R2-D2\", " +
                                "            \"height\": \"96\", " +
                                "            \"mass\": \"32\", " +
                                "            \"hair_color\": \"n/a\", " +
                                "            \"skin_color\": \"white, blue\", " +
                                "            \"eye_color\": \"red\", " +
                                "            \"birth_year\": \"33BBY\", " +
                                "            \"gender\": \"n/a\", " +
                                "            \"homeworld\": \"https://swapi.dev/api/planets/8/\", " +
                                "            \"films\": [" +
                                "                \"https://swapi.dev/api/films/6/\"" +
                                "            ], " +
                                "            \"species\": [" +
                                "                \"https://swapi.dev/api/species/2/\"" +
                                "            ], " +
                                "            \"vehicles\": [], " +
                                "            \"starships\": [], " +
                                "            \"url\": \"https://swapi.dev/api/people/3/\"" +
                                "        } " +
                                "   ]" +
                                "}"));

        mockRestServiceServer.expect(ExpectedCount.once(),
                        requestTo(URI.create("https://swapi.dev/api/planets/")))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body("{" +
                                "    \"next\": null, " +
                                "    \"previous\": null, " +
                                "    \"results\": [" +
                                "        {" +
                                "            \"name\": \"Tatooine\", " +
                                "            \"residents\": [" +
                                "                \"https://swapi.dev/api/people/1/\", " +
                                "                \"https://swapi.dev/api/people/2/\" " +
                                "            ], " +
                                "            \"films\": [], " +
                                "            \"url\": \"https://swapi.dev/api/planets/1/\"" +
                                "        }, " +
                                "        {" +
                                "            \"name\": \"Alderaan\", " +
                                "            \"residents\": [" +
                                "                \"https://swapi.dev/api/people/1/\", " +
                                "                \"https://swapi.dev/api/people/3/\" " +
                                "            ], " +
                                "            \"films\": [], " +
                                "            \"url\": \"https://swapi.dev/api/planets/2/\"" +
                                "        } " +
                                "   ] " +
                                " }"));
        crawler.crawl();

        verify(searchIndex).addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/planets/1/"));
        verify(searchIndex).addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/people/1/"));
        verify(searchIndex).addItemToIndex("Tatooine", URI.create("https://swapi.dev/api/people/2/"));

        verify(searchIndex).addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/planets/2/"));
        verify(searchIndex).addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/people/1/"));
        verify(searchIndex).addItemToIndex("Alderaan", URI.create("https://swapi.dev/api/people/3/"));

        verify(searchIndex).addItemToIndex("Luke Skywalker", URI.create("https://swapi.dev/api/people/1/"));
        verify(searchIndex).addItemToIndex("Luke Skywalker", URI.create("https://swapi.dev/api/planets/1/"));
        verify(searchIndex).addItemToIndex("Luke Skywalker", URI.create("https://swapi.dev/api/films/1/"));
        verify(searchIndex).addItemToIndex("Luke Skywalker", URI.create("https://swapi.dev/api/starships/22/"));

        verify(searchIndex).addItemToIndex("R2-D2", URI.create("https://swapi.dev/api/people/3/"));
        verify(searchIndex).addItemToIndex("R2-D2", URI.create("https://swapi.dev/api/planets/8/"));
        verify(searchIndex).addItemToIndex("R2-D2", URI.create("https://swapi.dev/api/films/6/"));
        verify(searchIndex).addItemToIndex("R2-D2", URI.create("https://swapi.dev/api/species/2/"));

    }


}