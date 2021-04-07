package ua.com.foxminded.labs.task5;

import ua.com.foxminded.labs.task5.charcounter.CharCounter;

public class CharCounterApp {

    public static void main(String[] args) {
        CharCounter charCounter = new CharCounter();

        String text1 = "hello world!";
        String text2 = "Hello World!";
        String text3 = "vjsdfl aslkdfjv ads";
        String text4 = "hello world!";
        String text5 = null;

        System.out.println(charCounter.run(text1));
        System.out.println(charCounter.run(text2));
        System.out.println(charCounter.run(text3));
        System.out.println(charCounter.run(text4));
        System.out.println(charCounter.run(text5));
    }
}
