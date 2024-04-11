package com.uriel.api.automatoes.automation.functions;

import java.util.Map;
import java.util.function.Function;

public class WeatherFunction implements Function<WeatherFunction.Request, WeatherFunction.Response> {
    private final Map<String, Float> temps = Map.of(
            "juiz de fora", 34f,
            "são paulo", 28f,
            "new york", 14f,
            "rio de janeiro", 47f
    );

    @Override
    public Response apply(Request request) {
        return new Response(temps.getOrDefault(request.city.trim().toLowerCase(), 0f));
    }

    public record Request(String city, int days) {}

    public record Response(float temperature) {}
}
