package ua.com.foxminded.labs.task6;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.labs.task6.racer.RaceUtilities;
import ua.com.foxminded.labs.task6.racer.Racer;
import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.List;

class RaceUtilitiesTest {
  private static File abbreviations, endTime, startTime;
  static String pathOfResources = "src/test/resources/ua/com/foxminded/labs/task6/";
  static List<Racer> racers;

  @Test
  void process_ShouldThrowException_WhenInputFileAbbreviationsWithNotValidDates() {

    abbreviations = new File(pathOfResources + "abbreviations_not_valid.txt");
    endTime = new File(pathOfResources + "end.log");
    startTime = new File(pathOfResources + "start.log");

    Assertions.assertThrows(IllegalArgumentException.class, () -> RaceUtilities.setDatesOfRacers(abbreviations, startTime, endTime));

  }
  @Test
  void process_ShouldThrowException_WhenInputFileStartTimeWithNotValidDates() {

    abbreviations = new File(pathOfResources + "abbreviations.txt");
    startTime = new File(pathOfResources + "start_not_valid.log");
    endTime = new File(pathOfResources + "end.log");

    Assertions.assertThrows(IllegalArgumentException.class, () -> RaceUtilities.setDatesOfRacers(abbreviations, startTime, endTime));

  }
  @Test
  void process_ShouldThrowException_WhenInputFileEndTimeWithNotValidDates() {

    abbreviations = new File(pathOfResources + "abbreviations.txt");
    endTime = new File(pathOfResources + "end_not_valid.log");
    startTime = new File(pathOfResources + "start.log");

    Assertions.assertThrows(IllegalArgumentException.class, () -> RaceUtilities.setDatesOfRacers(abbreviations, startTime, endTime));

  }
  @Test
  void process_ShouldReturnResult_WhenInput3FileWithValidDates() throws IOException {

    abbreviations = new File(pathOfResources + "abbreviations.txt");
    endTime = new File(pathOfResources + "end.log");
    startTime = new File(pathOfResources + "start.log");
    racers = RaceUtilities.setDatesOfRacers(abbreviations, startTime, endTime);

    StringBuilder expected = new StringBuilder("  1.Sebastian Vettel    |FERRARI                       |00:01:04.415   \n" +
        "  2.Daniel Ricciardo    |RED BULL RACING TAG HEUER     |00:01:12.013   \n" +
        "  3.Valtteri Bottas     |MERCEDES                      |00:01:12.434   \n" +
        "  4.Lewis Hamilton      |MERCEDES                      |00:01:12.460   \n" +
        "  5.Stoffel Vandoorne   |MCLAREN RENAULT               |00:01:12.463   \n" +
        "  6.Kimi Raikkonen      |FERRARI                       |00:01:12.639   \n" +
        "  7.Fernando Alonso     |MCLAREN RENAULT               |00:01:12.657   \n" +
        "  8.Sergey Sirotkin     |WILLIAMS MERCEDES             |00:01:12.706   \n" +
        "  9.Charles Leclerc     |SAUBER FERRARI                |00:01:12.829   \n" +
        " 10.Sergio Perez        |FORCE INDIA MERCEDES          |00:01:12.848   \n" +
        " 11.Romain Grosjean     |HAAS FERRARI                  |00:01:12.930   \n" +
        " 12.Pierre Gasly        |SCUDERIA TORO ROSSO HONDA     |00:01:12.941   \n" +
        " 13.Carlos Sainz        |RENAULT                       |00:01:12.950   \n" +
        " 14.Esteban Ocon        |FORCE INDIA MERCEDES          |00:01:13.028   \n" +
        " 15.Nico Hulkenberg     |RENAULT                       |00:01:13.065   \n" +
        " -------------------------------------------------------------------\n" +
        " 16.Brendon Hartley     |SCUDERIA TORO ROSSO HONDA     |00:01:13.179   \n" +
        " 17.Marcus Ericsson     |SAUBER FERRARI                |00:01:13.265   \n" +
        " 18.Lance Stroll        |WILLIAMS MERCEDES             |00:01:13.323   \n" +
        " 19.Kevin Magnussen     |HAAS FERRARI                  |00:01:13.393   \n");
    Assertions.assertEquals(expected.toString(), RaceUtilities.getResultTable(racers).toString());
    }
  @Test
  void process_ShouldThrowException_WhenInputNotExistFileAbbreviations() throws IOException {

    abbreviations = new File(pathOfResources + "abbreviationsNOTEXIST.txt");
    endTime = new File(pathOfResources + "end.log");
    startTime = new File(pathOfResources + "start.log");

    Assertions.assertThrows(NoSuchFileException.class, () -> RaceUtilities.setDatesOfRacers(abbreviations, startTime, endTime));
  }
  @Test
  void process_ShouldThrowException_WhenInputNotExistFileStartTime() throws IOException {

    abbreviations = new File(pathOfResources + "abbreviations.txt");
    startTime = new File(pathOfResources + "starttimeNOTEXIST.log");
    endTime = new File(pathOfResources + "end.log");

    Assertions.assertThrows(NoSuchFileException.class, () -> RaceUtilities.setDatesOfRacers(abbreviations, startTime, endTime));
  }
  @Test
  void process_ShouldThrowException_WhenInputNotExistFileEndTime() throws IOException {

    abbreviations = new File(pathOfResources + "abbreviations.txt");
    endTime = new File(pathOfResources + "endtimeNOTEXIST.log");
    startTime = new File(pathOfResources + "start.log");

    Assertions.assertThrows(NoSuchFileException.class, () -> RaceUtilities.setDatesOfRacers(abbreviations, startTime, endTime));
  }
}
