package ua.com.foxminded.labs.task1;

import ua.com.foxminded.labs.task1.anagram.Anagram;
import java.util.Scanner;

public class AnagramApp {
    public static void main(String[] args) {
        Anagram anagram = Anagram.getInstance();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            System.out.println("Type text, or 'exit' to stop:");
            String text = scanner.nextLine();

            if (text.trim().equals("exit")) {
              break;
            }
            System.out.println("\nAnagrams word \n" + anagram.createAnagram(text));
        }
    }
}
