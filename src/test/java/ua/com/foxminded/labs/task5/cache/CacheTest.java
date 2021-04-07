package ua.com.foxminded.labs.task5.cache;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class CacheTest {

    @BeforeAll
    static void setup() {
        String key1 = "Hello world!";
        StringBuilder value1 = new StringBuilder("\"Calculate character in \\\"hello world!\\\"\\n\" +\n" +
                "                \"\\\"h\\\" - 1\\n\" +\n" +
                "                \"\\\"e\\\" - 1\\n\" +\n" +
                "                \"\\\"l\\\" - 3\\n\" +\n" +
                "                \"\\\"o\\\" - 2\\n\" +\n" +
                "                \"\\\" \\\" - 1\\n\" +\n" +
                "                \"\\\"w\\\" - 1\\n\" +\n" +
                "                \"\\\"r\\\" - 1\\n\" +\n" +
                "                \"\\\"d\\\" - 1\\n\" +\n" +
                "                \"\\\"!\\\" - 1\\n\"");
        Cache.add(key1, value1.toString());

        String key2 = "aA";
        StringBuilder value2 = new StringBuilder("Calculate character in \"aA\"\n" +
                "\"a\" - 1\n" +
                "\"A\" - 1\n");
        Cache.add(key2, value2.toString());
    }

    @Test
    public void process_ShouldReturnTrue_WhenInputTextContainedInTheCache() {
        boolean expected = true;
        boolean actual = Cache.verify("Hello world!");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void process_ShouldReturnFalse_WhenInputTextMissingInTheCache() {
        boolean expected = false;
        boolean actual = Cache.verify("Hello");

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void process_ShouldReturnValue_WhenInputKey() {
        String expected = "Calculate character in \"aA\"\n" +
                "\"a\" - 1\n" +
                "\"A\" - 1\n";
        String actual = Cache.get("aA").toString();

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void process_ShouldThrowException_WhenGetInformationIfInputTextIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    Cache.get(null);
                });
    }

    @Test
    void process_ShouldThrowException_WhenAddInCacheIfInputKeyIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> {
                    Cache.add(null, new StringBuilder("value").toString());
                });
    }

    @Test
    public void process_ShouldAddInfAndReturnTrue_WhenAddNewInformation() {
        boolean expected = true;
        boolean actual = Cache.add("AAA", new StringBuilder("Calculate character in \"AAA\"\n" +
                "\"A\" - 3\n").toString());

        Assertions.assertEquals(expected, actual);
    }

}
