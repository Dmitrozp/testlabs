package ua.com.foxminded.labs.task7;

import ua.com.foxminded.labs.task7.service.Service;
import ua.com.foxminded.labs.task7.util.MenuUniversity;

import java.util.Scanner;

public class UniversityApp {

   public static void main(String[] args) {
      Service.initAllDatesAndSetToDB();

      MenuUniversity menu = new MenuUniversity();
      int item = 0;
      int itemExit = 7;

      while (item != itemExit) {
         menu.showMenuApp();
         System.out.println("Enter your menu item");
         Scanner scanner = new Scanner(System.in);
         item = Integer.valueOf(scanner.nextLine());
         menu.execute(item);
      }
   }
}
