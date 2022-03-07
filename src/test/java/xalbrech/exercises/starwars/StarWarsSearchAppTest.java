package xalbrech.exercises.starwars;

import com.fasterxml.jackson.databind.JsonNode;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@SpringBootTest(classes = StarWarsSearchApp.class)
public class StarWarsSearchAppTest {

    private static final Logger log = LoggerFactory.getLogger(StarWarsSearchAppTest.class);

    @Autowired RestTemplate restTemplate;

    @Ignore
    @Test
    public void planets() {
        ResponseEntity<JsonNode> response = restTemplate.exchange(
                    RequestEntity.get("https://swapi.dev/api/planets")
                            .accept(MediaType.APPLICATION_JSON)
                            .build(), JsonNode.class);

        Iterable<JsonNode> planetsIterable = () -> response.getBody().get("results").elements();
        Stream<JsonNode> planetsStream = StreamSupport.stream(planetsIterable.spliterator(), false);

        log.info("{}", planetsStream.map(planet -> planet.get("name")).collect(Collectors.toList()));

    }

}
