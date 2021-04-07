package ua.com.foxminded.labs.task1.anagram;

import java.util.Arrays;

public class Anagram {

    private static Anagram instance;

    public static Anagram getInstance(){
        if(instance == null){
            instance = new Anagram();
        }
        return instance;
    }

    public StringBuilder createAnagram(String text)  {
        if(text == null){
            throw new IllegalArgumentException("incoming data is Null, insert text");
        } else {
            StringBuilder result = new StringBuilder();
            String[] words = text.split(" ");

            for (int i = 0; i < words.length; i++) {
                StringBuilder resultWord = reverseWord(words[i].toCharArray());
                resultWord = insertSymbolInWord(resultWord, words[i].toCharArray());
                result.append(resultWord);
                if (i + 1 != words.length) {
                    result.append(" ");
                }
            }
            if (words.length >= 1) {
                return result;
            } else return result.append(text);
        }
    }

    private StringBuilder insertSymbolInWord(StringBuilder reverseWord, char[] originalWord){
        StringBuilder resultWord = new StringBuilder(reverseWord);

        for (int counter = 0; counter < originalWord.length; counter++) {
            if (!Character.isLetter(originalWord[counter])) {
                resultWord.insert(counter, originalWord[counter]);
            }
        }
        return resultWord;
    }

    private StringBuilder reverseWord(char[] word){
        StringBuilder tempWord = new StringBuilder(Arrays.toString(word));
        StringBuilder resultWord = new StringBuilder();

        for (int counter = 0; counter < tempWord.length(); counter++) {
            if (Character.isLetter(tempWord.charAt(counter))) {
                resultWord.append(tempWord.charAt(counter));
            }
        }
        resultWord = resultWord.reverse();
        return resultWord;
    }
}
