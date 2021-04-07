package ua.com.foxminded.labs.task4;

import ua.com.foxminded.labs.task4.division.Division;

public class DivisionApp {

    public static void main(String[] args) {
        Division division = new Division();
        Division division2 = new Division();
        Division division3 = new Division();

        System.out.println(division.getDivision(5555, 5));
        System.out.println(division3.getDivision(-12121212, 12));
        System.out.println(division2.getDivision(2147483647, 1));

    }
}
