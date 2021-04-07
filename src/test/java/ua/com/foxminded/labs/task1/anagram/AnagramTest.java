package ua.com.foxminded.labs.task1.anagram;

import ua.com.foxminded.labs.task1.anagram.Anagram;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class AnagramTest {

    private static Anagram anagram;

    @BeforeAll
    static void setup(){
        anagram = new Anagram();
    }

    @Test
    void process_ShouldThrowException_WhenInputIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class,
                ()->{anagram.createAnagram(null); });
    }
    @Test
    void process_ShouldReturnEmptyString_WhenInputIsEmptyString(){
        String expected = "";
        String actual = new String(anagram.createAnagram(""));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldNotReverseAnyNumbers_WhenInputOnlyNumbers(){
        String expected = "1234567890";
        String actual = new String(anagram.createAnagram("1234567890"));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldNotReverseAnySymbol_WhenInputContainsOnlySymbol(){
        String expected = "~!@#$%^&*()_+{}:<>?,./;'[]";
        String actual = new String(anagram.createAnagram("~!@#$%^&*()_+{}:<>?,./;'[]"));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnOneSpaceBar_WhenInputOneSpaceBar(){
        String expected = " ";
        String actual = new String(anagram.createAnagram(" "));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnSomeSpaceBar_WhenInputSomeSpaceBar(){
        String expected = "     ";
        String actual = new String(anagram.createAnagram("     "));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnOneLetter_WhenInputOneLetter(){
        String expected = "a";
        String actual = new String(anagram.createAnagram("a"));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnSomeLetter_WhenInputSomeLetter(){
        String expected = "aaaaaa";
        String actual = new String(anagram.createAnagram("aaaaaa"));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnReverseWordInLowerCase_WhenInputWordInLowerCase(){
        String expected = "olleh";
        String actual = new String(anagram.createAnagram("hello"));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnWordInLowerUpperCase_WhenInputWordInLowerUpperCase(){
        String expected = "AAAaaaAAAaaa";
        String actual = new String(anagram.createAnagram("aaaAAAaaaAAA"));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnSymbol_WhenInputSymbol(){
        String expected = "!@#$%^&*()";
        String actual = new String(anagram.createAnagram("!@#$%^&*()"));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnReverceWordWithLettersAndSymbols_WhenInputWordWithLettersAndSymbols(){
        String expected = "olleh123";
        String actual = new String(anagram.createAnagram("hello123"));

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void process_ShouldReturnReverceTextWithLettersAndSymbols_WhenInputTextWithLettersAndSymbols(){
        String expected = "olleh123 dlrow321 dna 456";
        String actual = new String(anagram.createAnagram("hello123 world321 and 456"));

        Assertions.assertEquals(expected, actual);
    }
}
