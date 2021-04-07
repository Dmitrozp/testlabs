package ua.com.foxminded.labs.task7.generator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.labs.task7.domain.Course;
import ua.com.foxminded.labs.task7.domain.Group;
import ua.com.foxminded.labs.task7.domain.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GeneratorDatesForTablesTest {

   @Test
   void setCoursesForStudents() throws IOException, SQLException {
      int expected = 0;
      int actual;

      List<Student> studentsInGroups = GeneratorDatesForTables.setStudentsInGroups(GeneratorDatesForTables.createStudents(200), GeneratorDatesForTables.createGroups(10));
      List<Student> studentsWithCourses = GeneratorDatesForTables.setCoursesForStudents(studentsInGroups, GeneratorDatesForTables.createCourses());

      List<Student> students = studentsWithCourses.stream()
          .filter(student -> student.getCourses() != null)
          .collect(Collectors.toList());

      actual = students.size();
      Assertions.assertNotEquals(expected, actual);
   }

   @Test
   void setStudentsInGroups() throws IOException, SQLException {
      int expected = 0;
      int actual;

      List<Student> studentsInGroups = GeneratorDatesForTables.setStudentsInGroups(GeneratorDatesForTables.createStudents(200), GeneratorDatesForTables.createGroups(10));

      List<Student> students = studentsInGroups.stream()
          .filter(student -> student.getGroup() != null)
          .collect(Collectors.toList());

      actual = students.size();
      Assertions.assertNotEquals(expected, actual);
   }

   @Test
   void createStudents() throws IOException {
      int countOfStudent = 200;
      int expected = countOfStudent;
      int actual;

      List<Student> students = GeneratorDatesForTables.createStudents(countOfStudent).stream()
          .filter(student -> student.getFirstName() != null)
          .filter(student -> student.getLastName() != null)
          .collect(Collectors.toList());

      actual = students.size();
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void createCourses() throws IOException {
      int countOfCOurses = 10;
      int expected = countOfCOurses;
      int actual;

      List<Course> courses = GeneratorDatesForTables.createCourses().stream()
          .filter(course -> course.getName() != null)
          .collect(Collectors.toList());

      actual = courses.size();
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void createGroups() {
      int countOfGroups = 10;
      int expected = countOfGroups;
      int actual;

      List<Group> groups = GeneratorDatesForTables.createGroups(countOfGroups).stream()
          .filter(group -> group.getName() != null)
          .collect(Collectors.toList());

      actual = groups.size();
      Assertions.assertEquals(expected, actual);
   }
}