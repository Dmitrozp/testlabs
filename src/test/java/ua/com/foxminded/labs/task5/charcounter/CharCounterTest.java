package ua.com.foxminded.labs.task5.charcounter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CharCounterTest {

    private static CharCounter charCounter = new CharCounter();

    @Test
    void process_ShouldThrowException_WhenInputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{charCounter.run(null); });
    }

    @Test
    void process_ShouldReturnCalculated_WhenInputSpace(){
            String expected = "Calculate character in \" \"\n" +
                    "\" \" - 1\n";
            Assertions.assertEquals(expected, charCounter.run(" "));
    }
    @Test
    void process_ShouldReturnCalculated_WhenInputNumber(){
        String expected = "Calculate character in \"5\"\n" +
                "\"5\" - 1\n";
        Assertions.assertEquals(expected, charCounter.run("5").toString());
    }
    @Test
    void process_ShouldReturnCalculated_WhenInputOneLetter(){
        String expected = "Calculate character in \"d\"\n" +
                "\"d\" - 1\n";
        Assertions.assertEquals(expected, charCounter.run("d").toString());
    }
    @Test
    void process_ShouldReturnCalculated_WhenInputTheSameLetterInLowerAndUpperCase(){
        String expected = "Calculate character in \"aA\"\n" +
                "\"a\" - 1\n" +
                "\"A\" - 1\n";
        Assertions.assertEquals(expected, charCounter.run("aA").toString());
    }
    @Test
    void process_ShouldReturnCalculated_WhenInputAnySymbol(){
        String expected = "Calculate character in \"~!@#$%^&*()_+{}:<>?,./;'[]\"\n" +
                "\"~\" - 1\n" +
                "\"!\" - 1\n" +
                "\"@\" - 1\n" +
                "\"#\" - 1\n" +
                "\"$\" - 1\n" +
                "\"%\" - 1\n" +
                "\"^\" - 1\n" +
                "\"&\" - 1\n" +
                "\"*\" - 1\n" +
                "\"(\" - 1\n" +
                "\")\" - 1\n" +
                "\"_\" - 1\n" +
                "\"+\" - 1\n" +
                "\"{\" - 1\n" +
                "\"}\" - 1\n" +
                "\":\" - 1\n" +
                "\"<\" - 1\n" +
                "\">\" - 1\n" +
                "\"?\" - 1\n" +
                "\",\" - 1\n" +
                "\".\" - 1\n" +
                "\"/\" - 1\n" +
                "\";\" - 1\n" +
                "\"'\" - 1\n" +
                "\"[\" - 1\n" +
                "\"]\" - 1\n";
        Assertions.assertEquals(expected, charCounter.run("~!@#$%^&*()_+{}:<>?,./;'[]"));
    }

}
