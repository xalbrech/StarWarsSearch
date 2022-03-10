package xalbrech.exercises.starwars.crawler.mapping;

import java.util.HashMap;
import java.util.Map;

public class ApiResultClassFactory {

    private static Map<String, Class<? extends ApiResult>> apiObjectConstructors = new HashMap<>();

    static {
        apiObjectConstructors.put("people", PeopleResult.class);
        apiObjectConstructors.put("planets", PlanetsResult.class);
        apiObjectConstructors.put("species", SpeciesResult.class);
        apiObjectConstructors.put("vehicles", VehiclesResult.class);
        apiObjectConstructors.put("starships", StarshipsResult.class);
        apiObjectConstructors.put("films", FilmsResult.class);
    }

    public static Class<? extends ApiResult> getResultForEndpointName(String type) {
        return apiObjectConstructors.get(type);
    }

}
