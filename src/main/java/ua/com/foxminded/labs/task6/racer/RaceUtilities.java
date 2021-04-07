package ua.com.foxminded.labs.task6.racer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class RaceUtilities {
	private static final String HORIZONTAL_BAR = "_";
	private static final String DASH = "-";
	private static final String SPACE_BAR = " ";
	private static final int FIRST_FIFTEEN_POSITIONS = 15;
	private static final int LENGTH_UNDERLINE = 67;
	private static final int FIRST_SYMBOL_ABBREVIATION = 0;
	private static final int LAST_SYMBOL_ABBREVIATION = 3;
	private static final int FIRST_SYMBOL_TIME = 13;
	private static final int LAST_SYMBOL_TIME = 26;
	private static final String PATTERN_ABBREVIATION = "^(([A-Z]{3})_(([a-zA-Z\\s]+))_(([a-zA-Z\\s]+)*))$";
	private static final String PATTERN_START_END_TIME = "(([A-Z]{3})(\\d{4}-\\d{2}-\\d{2})_(\\d{2}:\\d{2}:\\d{2}.\\d{3}))$";

	private RaceUtilities() {
	}

	public static StringBuilder getResultTable(List<Racer> racers) {
		StringBuilder result = new StringBuilder();
		AtomicInteger position = new AtomicInteger(1);

		racers.forEach(racer -> racer.setResultTime(calculateTimeOfBestLap(racer.getStartTime(), racer.getEndTime(), racer.getId())));

		racers.stream()
			 .sorted((racer1, racer2) -> racer1.getResultTime().compareTo(racer2.getResultTime()))
			 .forEachOrdered(r -> {
					  result.append(formatterResult(position.get(), r.getName(), r.getNameOfTeam(), r.getResultTime()));
					  if (position.get() == FIRST_FIFTEEN_POSITIONS) {
						  result.append(SPACE_BAR + repeat(LENGTH_UNDERLINE, DASH) + "\n");
					  }
					  position.getAndIncrement();
				  }
			 );
		return result;
	}

	public static List<Racer> setDatesOfRacers(File abbreviations, File startTime, File endTime) throws IOException {
		List<Racer> racers = new ArrayList<>();

		validateFile(abbreviations);
		validateFile(startTime);
		validateFile(endTime);

		setAbbreviations(validateFileDates(abbreviations, PATTERN_ABBREVIATION), racers);
		setStartTime(validateFileDates(startTime, PATTERN_START_END_TIME), racers);
		setEndTime(validateFileDates(endTime, PATTERN_START_END_TIME), racers);

		return racers;
	}

	private static LocalTime calculateTimeOfBestLap(LocalTime start, LocalTime end, String idRacer) {
		validateTime(start, end, idRacer);
		Duration timeBestLap = Duration.between(start, end);

		return LocalTime.ofNanoOfDay(timeBestLap.toNanos());
	}

	private static void setAbbreviations(File abbreviations, List<Racer> racers) throws IOException {

		try (Stream<String> stream = Files.lines(Paths.get(abbreviations.getAbsolutePath()))) {

			stream.map(stringInFile -> Arrays.asList(Arrays.copyOf(stringInFile.split(HORIZONTAL_BAR), 3)))
				 .forEach(parsedDates -> racers.add(new Racer(Optional.ofNullable(parsedDates.get(0)).orElseThrow(IllegalArgumentException::new),
					  Optional.ofNullable(parsedDates.get(1)).orElseThrow(IllegalArgumentException::new),
					  Optional.ofNullable(parsedDates.get(2)).orElseThrow(IllegalArgumentException::new))));
		}
	}

	private static void setStartTime(File startTime, List<Racer> racers) throws IOException {

		try (Stream<String> stream = Files.lines(Paths.get(startTime.getAbsolutePath()));) {

			stream.map(stringInFile -> stringInFile = stringInFile.substring(FIRST_SYMBOL_ABBREVIATION, LAST_SYMBOL_ABBREVIATION)
				 + stringInFile.substring(FIRST_SYMBOL_TIME, LAST_SYMBOL_TIME))
				 .map(stringInFile -> Arrays.asList(Arrays.copyOf(stringInFile.split(HORIZONTAL_BAR), 2)))
				 .forEach(parsedDates ->
					  racers.forEach(racer -> {
								if (parsedDates.get(0).equals(racer.getId())) {
									racer.setStartTime(Optional.ofNullable(LocalTime.parse(parsedDates.get(1))).orElseThrow(IllegalArgumentException::new));
								}
							}
					  ));
		}
	}

	private static void setEndTime(File endTime, List<Racer> racers) throws IOException {

		try (Stream<String> stream = Files.lines(Paths.get(endTime.getAbsolutePath()))) {

			stream.map(stringInFile -> stringInFile = stringInFile.substring(FIRST_SYMBOL_ABBREVIATION, LAST_SYMBOL_ABBREVIATION)
				 + stringInFile.substring(FIRST_SYMBOL_TIME, LAST_SYMBOL_TIME))
				 .map(stringInFile -> Arrays.asList(Arrays.copyOf(stringInFile.split(HORIZONTAL_BAR), 2)))
				 .forEach(parsedDates ->
					  racers.forEach(racer -> {
								if (parsedDates.get(0).equals(racer.getId())) {
									racer.setEndTime(Optional.ofNullable(LocalTime.parse(parsedDates.get(1))).orElseThrow(IllegalArgumentException::new));
								}
							}
							));
		}
	}

	private static String formatterResult(Integer position, String name, String teamName, LocalTime time) {
		StringBuilder result = new StringBuilder();
		final int lengthPosition = 3;
		final int lengthName = 20;
		final int lengthTeamName = 30;
		final int lengthTime = 15;

		result.append(repeat(lengthPosition - position.toString().length(), SPACE_BAR) + position.toString() + ".");
		result.append(name + repeat(lengthName - name.length(), SPACE_BAR) + "|");
		result.append(teamName + repeat(lengthTeamName - teamName.length(), SPACE_BAR) + "|");
		result.append(time + repeat(lengthTime - time.toString().length(), SPACE_BAR) + "\n");

		return result.toString();
	}

	private static String repeat(int length, String characters) {
		StringBuilder result = new StringBuilder();

		for (int i = 0; i < length; i++) {
			result.append(characters);
		}
		return result.toString();
	}

	private static void validateTime(LocalTime start, LocalTime end, String idRacer) {
		if (start == null || end == null || start.equals(LocalTime.parse("00:00:00.000"))
			 || end.equals(LocalTime.parse("00:00:00.000")) || start.isAfter(end)) {
			throw new IllegalArgumentException("start = " + start + " or end = " + end + " time not valid, ID Racer " + idRacer);
		}
	}

	private static void validateDate(String pattern, String text) {
		Pattern patern = Pattern.compile(pattern);
		Matcher matcher = patern.matcher(text);

		if (!matcher.matches()) {
			throw new IllegalArgumentException(text + " - not valid dates");
		}
	}

	private static File validateFileDates(File file, String pattern) throws IOException {

		try (Stream<String> stream = Files.lines(Paths.get(file.getAbsolutePath()));) {
			stream.forEach(line -> validateDate(pattern, line));
		}
		return file;
	}

	private static void validateFile(File file) throws NoSuchFileException {

		if (file.exists() == false || file == null) {
			throw new NoSuchFileException(file.getName() + " file not found");
		}
	}
}

