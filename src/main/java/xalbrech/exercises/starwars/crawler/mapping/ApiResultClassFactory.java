package xalbrech.exercises.starwars.crawler.mapping;

import java.util.HashMap;
import java.util.Map;

public class ApiResultClassFactory {

    private static Map<String, Class<? extends ApiResult>> apiObjectConstructors = new HashMap<>();

    static {
        apiObjectConstructors.put("people", PeopleResult.class);
        apiObjectConstructors.put("planets", PlanetsResult.class);
    }

    public static Class<? extends ApiResult> getResultForEndpointName(String type) {
        return apiObjectConstructors.get(type);
    }

}
