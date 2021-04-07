package ua.com.foxminded.labs.task7.sqljdbc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.com.foxminded.labs.task7.domain.Course;
import ua.com.foxminded.labs.task7.domain.Group;
import ua.com.foxminded.labs.task7.domain.Student;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class SqlJdbcUniversityTest {

   private static final String COLUMN_STUDENT_FIRST_NAME = "first_name";
   private static final String COLUMN_STUDENT_LAST_NAME = "last_name";
   private static final String COLUMN_COURSE_NAME = "course_name";
   private static final String COLUMN_COURSE_DESCRIPTION = "course_description";

   @BeforeEach
   void createAllTablesAndInitAllDates() {
      String sqlCreateTableStudents = "CREATE TABLE students(student_id SERIAL, group_id int, first_name varchar(80), last_name varchar(80), PRIMARY KEY (student_id));";
      String sqlCreateTableGroups = "CREATE TABLE groups(group_id SERIAL PRIMARY KEY, group_name varchar(80));";
      String sqlCreateTableCourses = "CREATE TABLE courses(course_id SERIAL, course_name varchar(80), course_description varchar(80), PRIMARY KEY (course_id));";
      String sqlCreateTableStudentsCourses = "CREATE TABLE students_courses(students_courses_id SERIAL PRIMARY KEY, student_id int, course_id int);";
      String sqlDropTableStudents = "DROP TABLE students";
      String sqlDropTableGroups = "DROP TABLE groups";
      String sqlDropTableCourses = "DROP TABLE courses";
      String sqlDropTableStudentsCourses = "DROP TABLE students_courses";

      try (Connection connection = DBCPDataSource.getConnectionTest();
           Statement statement = connection.createStatement()) {
         statement.execute(sqlDropTableStudents);
         statement.execute(sqlCreateTableStudents);
         statement.execute(sqlDropTableGroups);
         statement.execute(sqlCreateTableGroups);
         statement.execute(sqlDropTableCourses);
         statement.execute(sqlCreateTableCourses);
         statement.execute(sqlDropTableStudentsCourses);
         statement.execute(sqlCreateTableStudentsCourses);

         statement.execute("INSERT INTO students(first_name, last_name) VALUES ('Anton', 'Petrov');");
         statement.execute("INSERT INTO students(group_id, first_name, last_name) VALUES (1, 'Roger', 'Roger');");
         statement.execute("INSERT INTO students(group_id, first_name, last_name) VALUES (1, 'Leon', 'Leon');");
         statement.execute("INSERT INTO students(group_id, first_name, last_name) VALUES (1, 'Robert', 'Robert');");
         statement.execute("INSERT INTO students(group_id, first_name, last_name) VALUES (2, 'William', 'William');");
         statement.execute("INSERT INTO students(group_id, first_name, last_name) VALUES (2, 'Mervyn', 'Mervyn');");
         statement.execute("INSERT INTO students(group_id, first_name, last_name) VALUES (3, 'Myron', 'Myron');");
         statement.execute("INSERT INTO students(group_id, first_name, last_name) VALUES (3, 'Miron', 'Moor');");

         statement.execute("INSERT INTO courses(course_name) VALUES ('business analytics');");
         statement.execute("INSERT INTO courses(course_name) VALUES ('game development');");
         statement.execute("INSERT INTO courses(course_name) VALUES ('photography & video');");
         statement.execute("INSERT INTO courses(course_name) VALUES ('health & fitness');");
         statement.execute("INSERT INTO courses(course_name) VALUES ('music');");
         statement.execute("INSERT INTO courses(course_name) VALUES ('graphic design');");

         statement.execute("INSERT INTO students_courses(student_id, course_id) VALUES (1, '1');");
         statement.execute("INSERT INTO students_courses(student_id, course_id) VALUES (1, '2');");
         statement.execute("INSERT INTO students_courses(student_id, course_id) VALUES (1, '3');");

         statement.execute("INSERT INTO groups(group_name) VALUES ('AA-44');");
         statement.execute("INSERT INTO groups(group_name) VALUES ('AA-22');");
         statement.execute("INSERT INTO groups(group_name) VALUES ('AA-11');");
         statement.execute("INSERT INTO groups(group_name) VALUES ('AA-33');");
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   @Test
   void process_ShouldAddCourse_WhenInputIsCorrectCourseDate() {
      String name = "web development";
      String description = "web development text text text";
      String sqlQuery = "SELECT * FROM courses WHERE course_name = ? AND course_description = ?;";
      Course expected = new Course(name, description);
      Course actual = new Course();

      try (Connection connection = DBCPDataSource.getConnectionTest();
           PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         SqlJdbcUniversity.addCourseByCourse(connection, new Course(name, description));
         preparedStatement.setString(1, name);
         preparedStatement.setString(2, description);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            actual.setName(resultSet.getString(COLUMN_COURSE_NAME));
            actual.setDescription(resultSet.getString(COLUMN_COURSE_DESCRIPTION));
         }
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldAddGroup_WhenInputIsCorrectGroupDate() {
      String name = "AA-44";
      String sqlQuery = "SELECT * FROM groups WHERE group_name = ? ;";
      Group expected = new Group(name);
      Group actual = new Group();

      try (Connection connection = DBCPDataSource.getConnectionTest();
           PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         SqlJdbcUniversity.addGroup(connection, new Group(name));
         preparedStatement.setString(1, name);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            String groupName = resultSet.getString("group_name");
            actual.setName(groupName);
         }
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldAddStudent_WhenInputIsCorrectStudentDate() {
      String firstName = "Anton";
      String secondName = "Petrov";
      String sqlQuery = "SELECT * FROM students WHERE first_name = ? AND last_name = ?;";
      Student expected = new Student(firstName, secondName);
      Student actual = new Student();

      try (Connection connection = DBCPDataSource.getConnectionTest();
           PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         SqlJdbcUniversity.addStudentByStudent(connection, new Student(firstName, secondName));
         preparedStatement.setString(1, firstName);
         preparedStatement.setString(2, secondName);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            String studentFirstName = resultSet.getString(COLUMN_STUDENT_FIRST_NAME);
            String studentSecondName = resultSet.getString(COLUMN_STUDENT_LAST_NAME);
            actual.setFirstName(studentFirstName);
            actual.setLastName(studentSecondName);
         }
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindCoursesStudyingStudent_WhenInputIsCorrectStudentDate() {
      Student student = new Student(1, "Anton", "Petrov");
      List<Course> actual = new ArrayList<>();
      List<Course> expected = new ArrayList<>();
      expected.add(new Course(1, "business analytics"));
      expected.add(new Course(2, "game development"));
      expected.add(new Course(3, "photography & video"));

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findCoursesStudyingStudentByStudentId(connection, student);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindCoursesNotStudyingStudent_WhenInputIsCorrectStudentDate() {
      Student student = new Student(1, "Anton", "Petrov");
      List<Course> actual = new ArrayList<>();
      List<Course> expected = new ArrayList<>();
      expected.add(new Course(4, "health & fitness"));
      expected.add(new Course(5, "music"));
      expected.add(new Course(6, "graphic design"));

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findCoursesNotStudyingStudent(connection, student);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindGroup_WhenInputIsCorrectNameGroupDate() {
      String name = "AA-44";
      int id = 1;
      Group expected = new Group(id, name);
      Group actual = new Group();

      try (Connection connection = DBCPDataSource.getConnectionTest()){
         actual.setGroupId(SqlJdbcUniversity.findGroupIdByNameGroup(connection, name));
         actual.setName(name);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindGroupsWithMinStudents_WhenRunMethodWithCorrectDate() {
      List<Group> expected = new ArrayList<>();
      expected.add(new Group("AA-11", 2));
      expected.add(new Group("AA-22", 2));
      List<Group> actual = new ArrayList<>();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findGroupsWithMinStudents(connection);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindGroupsWithEqualsCountOfStudents_WhenInputSelectQuery() {
      List<Group> expected = new ArrayList<>();
      expected.add(new Group("AA-11", 2));
      expected.add(new Group("AA-22", 2));
      List<Group> actual = new ArrayList<>();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findGroupsWithEqualsCountOfStudents(connection);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindGroupsWithCountOfStudents_WhenInputSelectQuery() {
      List<Group> expected = new ArrayList<>();
      expected.add(new Group("AA-11", 2));
      expected.add(new Group("AA-22", 2));
      expected.add(new Group("AA-44", 3));
      List<Group> actual = new ArrayList<>();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findGroupsWithCountOfStudents(connection);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindCourseId_WhenInputIsCorrectNameCourseDate() {
      String name = "business analytics";
      int expected = 1;
      int actual = 0;

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findCourseIdByNameCourse(connection, name);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindCourse_WhenInputIsCorrectCourseDate() {
      String name = "game development";
      int id = 2;
      Course expected = new Course(id, name);
      Course actual = new Course();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findCourseByCourseId(connection, id);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindStudentId_WhenInputIsCorrectFirstNameAndLastNameDate() {
      String firstName = "William";
      String lastName = "William";
      int expected = 5;
      int actual = 0;

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findStudentIdByFirstNameAndLastName(connection, firstName, lastName);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindStudyingStudents_WhenRunMethod() {
      List<Student> expected = new ArrayList<>();
      expected.add(new Student(2, "Roger", "Roger"));
      expected.add(new Student(3, "Leon", "Leon"));
      expected.add(new Student(4, "Robert", "Robert"));
      expected.add(new Student(5, "William", "William"));
      expected.add(new Student(6, "Mervyn", "Mervyn"));
      expected.add(new Student(7, "Myron", "Myron"));
      expected.add(new Student(8, "Miron", "Moor"));
      List<Student> actual = new ArrayList<>();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findStudyingStudents(connection);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindStudent_WhenInputIsCorrectStudentId() {
      String firstName = "Roger";
      String lastName = "Roger";
      int id = 2;
      Student expected = new Student(id, firstName, lastName,1);
      Student actual = new Student();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findStudentByID(connection, id);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindStudent_WhenInputIsCorrectFirstNameAndLastName() {
      String firstName = "William";
      String lastName = "William";
      int id = 5;
      int groupId = 2;
      Student expected = new Student(id, firstName, lastName, groupId);
      Student actual = new Student();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findStudentByFirstNameAndLastName(connection, firstName, lastName);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindStudentsStudyingInGroup_WhenInputIsCorrectNameGroup() {
      String nameGroup = "AA-22";
      List<Student> expected = new ArrayList<>();
      expected.add(new Student(5, "William", "William", 2, "AA-22"));
      expected.add(new Student(6, "Mervyn", "Mervyn", 2, "AA-22"));
      List<Student> actual = new ArrayList<>();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findStudentsStudyingInGroupByNameGroup(connection, nameGroup);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindCourse_WhenInputIsCorrectName() {
      String nameCourse = "game development";
      int id = 2;
      Course expected = new Course(id, nameCourse);
      Course actual = new Course();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findCourseByName(connection, nameCourse);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldFindAllCourses_WhenRunMethod() {
      List<Course> expected = new ArrayList<>();
      expected.add(new Course(1, "business analytics"));
      expected.add(new Course(2, "game development"));
      expected.add(new Course(3, "photography & video"));
      expected.add(new Course(4, "health & fitness"));
      expected.add(new Course(5, "music"));
      expected.add(new Course(6, "graphic design"));
      List<Course> actual = new ArrayList<>();

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.findAllCoursesInUniversity(connection);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldCreateTable_WhenInputIsCorrectDatesFromFile() {
      String nameTable = "TEST";
      String fileTest = "ua/com/foxminded/labs/university/create_table_test.txt";
      String expected = nameTable;
      String actual = "";

      try (Connection connection = DBCPDataSource.getConnectionTest();
           Statement statement = connection.createStatement();) {
         SqlJdbcUniversity.createTableByFile(connection, new File(ClassLoader.getSystemResource(fileTest).getPath()), nameTable);
         ResultSet resultSet = connection.getMetaData().getTables(null, null, nameTable, null);
         while (resultSet.next()) {
            actual = resultSet.getString("TABLE_NAME");
            if (actual != null && actual.equals(nameTable)) {
               break;
            }
         }
         statement.execute("DROP TABLE TEST;");
      } catch (SQLException | IOException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldThrowException_WhenTwiceDropTheSameTable() throws SQLException {
      String nameTable = "testdrop";

      try (Connection connection = DBCPDataSource.getConnectionTest();
           Statement statement = connection.createStatement();) {
         statement.execute("CREATE TABLE " + nameTable + "(test_id SERIAL, test_name varchar(80), PRIMARY KEY (test_id));");
         SqlJdbcUniversity.dropTable(connection, nameTable);
         Assertions.assertThrows(SQLException.class,
             () -> {
                SqlJdbcUniversity.dropTable(connection, nameTable);
             });
      }
   }

   @Test
   void process_ShouldDeleteCourse_WhenInputIsCorrectStudentId() {
      int expected = 1;
      int actual = 0;
      Student student = new Student(1, "Anton", "Petrov", 1);
      Course course = new Course(2, "course", "course");

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.deleteCourseByStudentId(connection, student, course);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldDeleteCoursesFromStudent_WhenInputIsCorrectStudentId() {
      int expected = 3;
      int actual = 0;
      Student student = new Student(1, "Anton", "Petrov", 1);

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.deleteCoursesFromStudentByStudentId(connection, student);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }

   @Test
   void process_ShouldDeleteStudentFromGroup_WhenInputIsCorrectStudentDate() {
      int expected = 3;
      int actual = 0;
      Student student1 = new Student(1, "Anton", "Petrov", 1);
      Student student2 = new Student(1, "Anton", "Petrov", 1);
      Student student3 = new Student(1, "Anton", "Petrov", 1);

      try (Connection connection = DBCPDataSource.getConnectionTest()) {
         actual = SqlJdbcUniversity.deleteStudentFromGroup(connection, student1)
             + SqlJdbcUniversity.deleteStudentFromGroup(connection, student2)
             + SqlJdbcUniversity.deleteStudentFromGroup(connection, student3);
      } catch (SQLException e) {
         System.err.println(e);
      }
      Assertions.assertEquals(expected, actual);
   }
}