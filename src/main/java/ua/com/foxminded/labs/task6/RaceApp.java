package ua.com.foxminded.labs.task6;

import ua.com.foxminded.labs.task6.racer.Racer;
import ua.com.foxminded.labs.task6.racer.RaceUtilities;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class RaceApp {

	public static void main(String[] args) {
		String pathOfResources = "src/main/resources/ua/com/foxminded/labs/task6/racer/";

		File abbreviations = new File(pathOfResources + "abbreviations.txt");
		File endTime = new File(pathOfResources + "end.log");
		File startTime = new File(pathOfResources + "start.log");

		try {
			List<Racer> racers = RaceUtilities.setDatesOfRacers(abbreviations, startTime, endTime);
			System.out.println(RaceUtilities.getResultTable(racers));
		} catch (IOException e) {
			System.out.println(e);
		} catch (IllegalArgumentException e) {
			System.out.println(e);
		}
	}
}

