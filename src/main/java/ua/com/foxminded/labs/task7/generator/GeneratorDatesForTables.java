package ua.com.foxminded.labs.task7.generator;

import ua.com.foxminded.labs.task7.sqljdbc.DBCPDataSource;
import ua.com.foxminded.labs.task7.sqljdbc.SqlJdbcUniversity;
import ua.com.foxminded.labs.task7.domain.Course;
import ua.com.foxminded.labs.task7.domain.Group;
import ua.com.foxminded.labs.task7.domain.Student;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GeneratorDatesForTables {

   private static final String PATH_FIRST_NAME_FOR_STUDENTS = "university/firstNames.txt";
   private static final String PATH_LAST_NAME_FOR_STUDENTS = "university/lastNames.txt";
   private static final String PATH_FOR_COURSES = "university/courses.txt";

   private GeneratorDatesForTables() {
   }

   public static List<Student> setCoursesForStudents(List<Student> students, List<Course> courses) {
      final int MIN_COURSES_FOR_STUDENT = 1;
      final int MAX_COURSES_FOR_STUDENT = 3;
      final int STUDENT_NOT_ENTRY_IN_GROUP = 0;
      final int FIRST_POSITION_COURSE_IN_LIST = 0;
      final int LAST_POSITION_COURSE_IN_LIST = 9;

      students.forEach(student -> {
         if (student.getGroupId() != STUDENT_NOT_ENTRY_IN_GROUP || student.getGroup() != null) {
            int countOfCourses = generateRandomNumber(MIN_COURSES_FOR_STUDENT, MAX_COURSES_FOR_STUDENT);
            List<Course> coursesForStudent = new ArrayList<>();
            Course firstCourse = courses.get(generateRandomNumber(FIRST_POSITION_COURSE_IN_LIST, LAST_POSITION_COURSE_IN_LIST));
            coursesForStudent.add(firstCourse);
            while (coursesForStudent.size() < countOfCourses) {
               Course course = courses.get(generateRandomNumber(FIRST_POSITION_COURSE_IN_LIST, LAST_POSITION_COURSE_IN_LIST));
               if (!checkCourseInListCourses(coursesForStudent, course.getName())) {
                  coursesForStudent.add(course);
               }
            }
            student.setCourses(coursesForStudent);
         }
      });
      return students;
   }

   public static List<Student> setStudentsInGroups(List<Student> students, List<Group> groups) throws SQLException {
      AtomicInteger countOfStudents = new AtomicInteger(students.size());
      AtomicInteger firstPositionOfStudent = new AtomicInteger(0);
      AtomicInteger countStudentsInGroup = new AtomicInteger(0);
      final int MIN_STUDENTS_IN_GROUP = 10;
      final int MAX_STUDENTS_IN_GROUP = 30;

      try (Connection connection = DBCPDataSource.getConnection()) {
         groups.forEach(group -> {
            countStudentsInGroup.set(generateRandomNumber(MIN_STUDENTS_IN_GROUP, MAX_STUDENTS_IN_GROUP));
            if (countStudentsInGroup.get() < countOfStudents.get()) {
               group.setCountOfStudents(countStudentsInGroup.get());
               students.stream().skip(firstPositionOfStudent.longValue())
                   .limit(countStudentsInGroup.get())
                   .forEach(student -> {
                      student.setGroupId(SqlJdbcUniversity.findGroupIdByNameGroup(connection, group.getName()));
                      student.setGroup(group.getName());
                   });
               countOfStudents.set(countOfStudents.get() - countStudentsInGroup.get());
            } else {
               group.setCountOfStudents(countOfStudents.get());
               countOfStudents.set(countOfStudents.get() - countOfStudents.get());
            }
            firstPositionOfStudent.set(firstPositionOfStudent.get() + countStudentsInGroup.get());
         });
      }
      return students;
   }

   public static List<Student> createStudents(int count) throws IOException {
      List<Student> students = new ArrayList<>();
      List<String> firstNames;
      List<String> lastNames;

      try (Stream<String> streamNames = Files.lines(Path.of(String.valueOf(new File(ClassLoader.getSystemResource(PATH_FIRST_NAME_FOR_STUDENTS).getPath()))));
           Stream<String> streamLastNames = Files.lines(Path.of(String.valueOf(new File(ClassLoader.getSystemResource(PATH_LAST_NAME_FOR_STUDENTS).getPath()))))) {

         firstNames = streamNames.collect(Collectors.toList());
         lastNames = streamLastNames.collect(Collectors.toList());

         for (int i = 0; i < count; i++) {
            Student student = new Student(firstNames.get(generateRandomNumber(0, firstNames.size() - 1)), lastNames.get(generateRandomNumber(0, lastNames.size() - 1)));
            students.add(student);
         }
      }
      return students;
   }

   public static List<Course> createCourses() throws IOException {
      List<Course> courses = new ArrayList<>();

      try (Stream<String> stream = Files.lines(Path.of(String.valueOf(new File(ClassLoader.getSystemResource(PATH_FOR_COURSES).getPath()))))) {

         stream.forEach(courseName -> courses.add(new Course(courseName)));

      }
      return courses;
   }

   public static List<Group> createGroups(int countGroups) {
   	final int COUNT_SYMBOL_IN_GROUP_ABBRIVIATION = 2;

      List<Group> groups = new ArrayList<>();
      String nameGroup;
      for (int i = 0; i < countGroups; i++) {
         nameGroup = generateSymbols(COUNT_SYMBOL_IN_GROUP_ABBRIVIATION) + "-" + generateRandomNumber(10, 99);
         Group group = new Group(nameGroup);
         groups.add(group);
      }
      return groups;
   }

   private static String generateSymbols(int countSymbols) {
      final int SYMBOL_A_CHARCODE = 65;
      final int SYMBOL_Z_CHARCODE = 89;

      int min = SYMBOL_A_CHARCODE;
      int max = SYMBOL_Z_CHARCODE;
      StringBuilder symbols = new StringBuilder();
      max -= min;
      for (int i = 0; i < countSymbols; i++) {
         char symbol = (char) ((Math.random() * ++max) + min);
         symbols.append(symbol);
      }
      return symbols.toString();
   }

   private static int generateRandomNumber(int start, int finish) {
      finish -= start;
      return (int) ((Math.random() * ++finish) + start);
   }

   private static boolean checkCourseInListCourses(List<Course> courses, String nameCourse) {
      AtomicBoolean teachingCourse = new AtomicBoolean(false);
      courses.forEach(course -> {
         if (course.getName().equals(nameCourse)) {
            teachingCourse.set(true);
         }
      });
      return teachingCourse.get();
   }
}
