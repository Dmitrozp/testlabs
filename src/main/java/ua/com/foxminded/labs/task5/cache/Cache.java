package ua.com.foxminded.labs.task5.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class Cache {
    public static Map<String, String> cache = new HashMap<>();

    public static boolean verify(String text) {
        return cache.containsKey(text);
    }

    public static String addIfAbsent(String key, Function<String, String> mappingFunction){
        return cache.computeIfAbsent(key, mappingFunction);
    }

    public static String get(String text) {
        validate(text);
        StringBuilder result = new StringBuilder();
        result.append(cache.get(text));
        return result.toString();
    }

    public static boolean add(String key, String value) {
        validate(key);
        cache.put(key, value);
        return verify(key);
    }

    public static String validate(String text){
        return Optional.ofNullable(text).orElseThrow(IllegalArgumentException::new);
    }
}
