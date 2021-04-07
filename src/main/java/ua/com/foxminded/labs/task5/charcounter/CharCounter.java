package ua.com.foxminded.labs.task5.charcounter;

import ua.com.foxminded.labs.task5.cache.Cache;

import java.util.*;

public class CharCounter {

    public String run(String text) {
        String result;
        String headOfResult = text + "\n";

        result = headOfResult + Cache.addIfAbsent(text, s -> calculate(text));
        return result;
    }

    private String calculate(String text) {
        validate(text);
        StringBuilder result = new StringBuilder();
        Set<String> characters = isolateUniqueCharacters(text);

        characters.forEach(ch -> result.append(calculateCharacter(ch, text)));
        return result.toString();
    }

    private String calculateCharacter(String character, String text) {
        List<String> characters = new LinkedList<>(Arrays.asList(text.split("")));
        int countCharacter = Collections.frequency(characters, character);
        return "\"" + character + "\"" + " - " + countCharacter + "\n";
    }

    private Set<String> isolateUniqueCharacters(String text) {
        Set<String> characters = new LinkedHashSet<>();
        characters.addAll(Arrays.asList(text.split("")));
        return characters;
    }

    private String validate(String text) {
        return Optional.ofNullable(text).orElseThrow(IllegalArgumentException::new);
    }
}
