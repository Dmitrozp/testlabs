package ua.com.foxminded.labs.task7.service;

import ua.com.foxminded.labs.task7.domain.Course;
import ua.com.foxminded.labs.task7.domain.Group;
import ua.com.foxminded.labs.task7.domain.Student;
import ua.com.foxminded.labs.task7.generator.GeneratorDatesForTables;
import ua.com.foxminded.labs.task7.sqljdbc.DBCPDataSource;
import ua.com.foxminded.labs.task7.sqljdbc.SqlJdbcUniversity;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Service {

   private Service() {
   }

   public static void createAllTables() {
      String fileStudent = "university/create_table_students.txt";
      String fileCourses = "university/create_table_courses.txt";
      String fileGroups = "university/create_table_groups.txt";
      String fileStudentsCourses = "university/create_table_studentToCourses.txt";

      final String TABLE_NAME_COURSES = "courses";
      final String TABLE_NAME_STUDENTS = "students";
      final String TABLE_NAME_GROUPS = "groups";
      final String TABLE_NAME_STUDENTS_COURSES = "students_courses";

      try (Connection connection = DBCPDataSource.getConnection()) {

         SqlJdbcUniversity.createTableByFile(connection, new File(ClassLoader.getSystemResource(fileStudent).getPath()), TABLE_NAME_STUDENTS);
         SqlJdbcUniversity.createTableByFile(connection, new File(ClassLoader.getSystemResource(fileGroups).getPath()), TABLE_NAME_GROUPS);
         SqlJdbcUniversity.createTableByFile(connection, new File(ClassLoader.getSystemResource(fileCourses).getPath()), TABLE_NAME_COURSES);
         SqlJdbcUniversity.createTableByFile(connection, new File(ClassLoader.getSystemResource(fileStudentsCourses).getPath()), TABLE_NAME_STUDENTS_COURSES);

      } catch (SQLException e) {
         System.err.println(e);
      } catch (IOException e) {
         System.err.println(e);
      }
   }

   public static void initAllDatesAndSetToDB() {
      final int COUNT_OF_STUDENTS = 200;
      final int COUNT_OF_GROUPS = 10;

      try {
         Class.forName("org.postgresql.Driver");
         System.out.println("Start Init Dates...");

         createAllTables();

         List<Group> groups = GeneratorDatesForTables.createGroups(COUNT_OF_GROUPS);
         addAllGroups(groups);

         List<Student> students = GeneratorDatesForTables.createStudents(COUNT_OF_STUDENTS);
         List<Student> studentsInGroups = GeneratorDatesForTables.setStudentsInGroups(students, groups);
         addAllStudents(studentsInGroups);

         List<Course> courses = GeneratorDatesForTables.createCourses();
         addAllCourses(courses);


         List<Student> studentsWithCourses = GeneratorDatesForTables.setCoursesForStudents(studentsInGroups, courses);
         addCoursesForStudents(studentsWithCourses);

      } catch (SQLException throwables) {
         throwables.printStackTrace();
      } catch (ClassNotFoundException e) {
         System.err.println(e);
      } catch (IOException e) {

      }
      System.out.println("Finished Init and Add Dates to DB");
   }

   public static void addCoursesForStudents(List<Student> students) {
      try (Connection connection = DBCPDataSource.getConnection()) {
         students.stream()
             .filter(student -> student.getGroupId() != 0)
             .forEach(student -> SqlJdbcUniversity.addAllCoursesForStudent(connection, student));
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static void findGroupsWithLessOrEqualsStudentCount() {
      try (Connection connection = DBCPDataSource.getConnection()) {
         System.out.println("Groups with MIN count of Students:");
         SqlJdbcUniversity.findGroupsWithMinStudents(connection).forEach(System.out::println);
         System.out.println("Groups with Equals count of Students:");
         SqlJdbcUniversity.findGroupsWithEqualsCountOfStudents(connection).forEach(System.out::println);
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static void addAllCourses(List<Course> courses) throws SQLException {
      try (Connection connection = DBCPDataSource.getConnection()) {
         courses.forEach(course -> {
            try {
               SqlJdbcUniversity.addCourseByCourse(connection, course);
            } catch (SQLException throwables) {
               throwables.printStackTrace();
            }
         });
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static void addAllGroups(List<Group> groups) {
      try (Connection connection = DBCPDataSource.getConnection()) {
         groups.forEach(group -> SqlJdbcUniversity.addGroup(connection, group));
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static void addAllStudents(List<Student> students) throws SQLException {
      try (Connection connection = DBCPDataSource.getConnection()) {
         students.forEach(student -> {
            try {
               SqlJdbcUniversity.addStudentByStudent(connection, student);
            } catch (SQLException e) {
               System.err.println(e);
            }
         });
      }
   }

   public static void showStudentsByNameGroup() {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter GROUP name");
      String groupName = scanner.nextLine();
      try (Connection connection = DBCPDataSource.getConnection()) {
         SqlJdbcUniversity.findStudentsStudyingInGroupByNameGroup(connection, groupName).forEach(System.out::println);
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static void addStudentOnCourse() {
      Scanner scanner = new Scanner(System.in);
      Course course;
      Student student;

      try (Connection connection = DBCPDataSource.getConnection()) {
         SqlJdbcUniversity.findStudyingStudents(connection).forEach(System.out::println);
         System.out.println("Enter ID Student");
         String studentId = scanner.nextLine();
         student = SqlJdbcUniversity.findStudentByID(connection, Integer.parseInt(studentId));
         System.out.println("Student is studing these courses:");
         SqlJdbcUniversity.findCoursesStudyingStudentByStudentId(connection, student).forEach(System.out::println);
         System.out.println("-----------------------------------------------------------------");
         System.out.println("What the course do you wont to add for student?");
         SqlJdbcUniversity.findCoursesNotStudyingStudent(connection, student).forEach(System.out::println);
         System.out.println("Enter ID Course");
         String courseId = scanner.nextLine();
         course = SqlJdbcUniversity.findCourseByCourseId(connection, Integer.parseInt(courseId));
         SqlJdbcUniversity.addStudentOnCourse(connection, student, course);
         System.out.println("Courses for student ID " + student.getStudentId());
         SqlJdbcUniversity.findCoursesStudyingStudentByStudentId(connection, student).forEach(System.out::println);
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static void deleteStudentFromCourse() {
      Scanner scanner = new Scanner(System.in);
      Course course;
      Student student;

      try (Connection connection = DBCPDataSource.getConnection()) {
         SqlJdbcUniversity.findStudyingStudents(connection).forEach(System.out::println);
         System.out.println("Enter ID Student");
         String studentId = scanner.nextLine();
         student = SqlJdbcUniversity.findStudentByID(connection, Integer.parseInt(studentId));
         System.out.println("Student is studing these courses:");
         SqlJdbcUniversity.findCoursesStudyingStudentByStudentId(connection, student).forEach(System.out::println);
         System.out.println("-----------------------------------------------------------------");
         System.out.println("What the course do you wont to delete for student?");
         System.out.println("Enter ID Course");
         String courseId = scanner.nextLine();
         course = SqlJdbcUniversity.findCourseByCourseId(connection, Integer.parseInt(courseId));
         SqlJdbcUniversity.deleteCourseByStudentId(connection, student, course);
         System.out.println("Courses for student ID " + student.getStudentId());
         SqlJdbcUniversity.findCoursesStudyingStudentByStudentId(connection, student).forEach(System.out::println);
      } catch (SQLException e) {
         System.err.println(e);
      }

   }

   public static void deleteStudentById() {
      Scanner scanner = new Scanner(System.in);
      Student student;

      try (Connection connection = DBCPDataSource.getConnection()) {
         SqlJdbcUniversity.findStudyingStudents(connection).forEach(System.out::println);
         System.out.println("Enter ID Student");
         String studentId = scanner.nextLine();
         student = SqlJdbcUniversity.findStudentByID(connection, Integer.parseInt(studentId));
         deleteAllDatesAboutStudent(student);

         System.out.println("Courses for student ID " + student.getStudentId());
         SqlJdbcUniversity.findCoursesStudyingStudentByStudentId(connection, student).forEach(System.out::println);
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static void addStudentByFirstAndLastName() {
      String firstName;
      String lastName;
      Scanner scanner = new Scanner(System.in);
      try (Connection connection = DBCPDataSource.getConnection()) {
         System.out.println("Enter first name of student:");
         firstName = scanner.nextLine();
         System.out.println("Enter last name of student:");
         lastName = scanner.nextLine();
         SqlJdbcUniversity.addStudentByStudent(connection, new Student(firstName, lastName));
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   private static void deleteAllDatesAboutStudent(Student student) {

      try (Connection connection = DBCPDataSource.getConnection()) {
         SqlJdbcUniversity.deleteCoursesFromStudentByStudentId(connection, student);
         SqlJdbcUniversity.deleteStudentFromGroup(connection, student);
         SqlJdbcUniversity.deleteStudentByStudent(connection, student);
         System.out.println("All Dates delete about student " + student.getFirstName() + " " + student.getLastName());
      } catch (SQLException e) {
         System.err.println(e);
      }

   }
}
