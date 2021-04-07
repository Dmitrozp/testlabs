package ua.com.foxminded.labs.task7.sqljdbc;

import ua.com.foxminded.labs.task7.domain.Course;
import ua.com.foxminded.labs.task7.domain.Group;
import ua.com.foxminded.labs.task7.domain.Student;

import java.io.*;
import java.sql.*;
import java.util.*;

public class SqlJdbcUniversity {
   private static final String COLUMN_STUDENT_ID = "student_id";
   private static final String COLUMN_STUDENT_FIRST_NAME = "first_name";
   private static final String COLUMN_STUDENT_LAST_NAME = "last_name";
   private static final String COLUMN_STUDENT_GROUP_ID = "group_id";
   private static final String COLUMN_COURSE_ID = "course_id";
   private static final String COLUMN_COURSE_NAME = "course_name";
   private static final String COLUMN_COURSE_DESCRIPTION = "course_description";
   private static final String COLUMN_GROUP_GROUP_ID = "group_id";
   private static final String COLUMN_GROUP_GROUP_NAME = "group_name";

   private SqlJdbcUniversity() {
   }

   public static List<Course> findCoursesStudyingStudentByStudentId(Connection connection, Student student) throws SQLException {
      List<Course> courses = new ArrayList<>();
      final String sqlQuery = "SELECT courses.course_name, A.course_id " +
          "FROM ( SELECT students_courses.*  " +
          "FROM students JOIN students_courses ON students.student_id = students_courses.student_id " +
          "WHERE students.student_id = ? ) AS A JOIN courses ON courses.course_id = a.course_id";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, student.getStudentId());
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
            Course course = new Course();
            course.setCourseId(resultSet.getInt(COLUMN_COURSE_ID));
            course.setName(resultSet.getString(COLUMN_COURSE_NAME));
            courses.add(course);
         }
      }
      return courses;
   }

   public static List<Course> findCoursesNotStudyingStudent(Connection connection, Student student) throws SQLException {
      List<Course> courses = new ArrayList<>();
      final String sqlQuery = "SELECT courses.course_id, courses.course_name\n" +
          "FROM (SELECT courses.course_name, A.*\n" +
          "FROM (SELECT students_courses.*\n" +
          "FROM students JOIN students_courses ON students.student_id = students_courses.student_id\n" +
          "WHERE students.student_id = ? ) AS A JOIN courses ON courses.course_id = a.course_id) AS B \n" +
          "RIGHT JOIN courses ON B.course_id = courses.course_id\n" +
          "WHERE B.course_id IS NULL";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, student.getStudentId());
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
            Course course = new Course();
            course.setCourseId(resultSet.getInt(COLUMN_COURSE_ID));
            course.setName(resultSet.getString(COLUMN_COURSE_NAME));
            courses.add(course);
         }
      }
      return courses;
   }

   public static int findGroupIdByNameGroup(Connection connection, String name) {
      int groupId = 0;
      final String sqlQuery = "SELECT group_id FROM groups WHERE group_name = ? ;";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setString(1, name);
         ResultSet resultSet = preparedStatement.executeQuery();
         if (resultSet.next()) {
            groupId = resultSet.getInt(COLUMN_STUDENT_GROUP_ID);
         }
      } catch (SQLException e) {
         System.err.println(e);
      }
      return groupId;
   }

   public static Group findGroupByNameGroup(Connection connection, String name) throws SQLException {
      Group group = new Group();
      final String sqlQuery = "SELECT * FROM groups WHERE group_name = ? ;";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setString(1, name);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()) {
            String groupName = resultSet.getString(COLUMN_GROUP_GROUP_NAME);
            int groupId = resultSet.getInt(COLUMN_GROUP_GROUP_ID);
            group.setGroupId(groupId);
            group.setName(groupName);
         }

      }
      return group;
   }

   public static int findCourseIdByNameCourse(Connection connection, String name) throws SQLException {
      int courseId = 0;
      final String sqlQuery = "SELECT course_id FROM courses WHERE course_name = ? ;";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setString(1, name);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()) {
            courseId = resultSet.getInt(COLUMN_COURSE_ID);
         }

      }
      return courseId;
   }

   public static Course findCourseByCourseId(Connection connection, int courseId) throws SQLException {
      Course course = new Course();
      final String sqlQuery = "SELECT courses.* FROM courses WHERE course_id = ?; ";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, courseId);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()) {
            course.setCourseId(resultSet.getInt(COLUMN_COURSE_ID));
            course.setName(resultSet.getString(COLUMN_COURSE_NAME));
            course.setDescription(resultSet.getString(COLUMN_COURSE_DESCRIPTION));
         }
      }
      return course;
   }

   public static int findStudentIdByFirstNameAndLastName(Connection connection, String firstName, String lastName) throws SQLException {
      final String sqlQuery = "SELECT student_id FROM students WHERE first_name = ? AND last_name = ?;";
      int studentId = 0;

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setString(1, firstName);
         preparedStatement.setString(2, lastName);

         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()) {
            studentId = resultSet.getInt(COLUMN_STUDENT_ID);
         }
      }
      return studentId;
   }

   public static List<Student> findStudyingStudents(Connection connection) throws SQLException {
      final String sqlQuery = "SELECT student_id, group_id, first_name, last_name " +
          "FROM public.students " +
          "WHERE group_id <> 0";
      List<Student> students = new ArrayList<>();

      try (Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sqlQuery)) {

         while (resultSet.next()) {
            int studentId = resultSet.getInt(COLUMN_STUDENT_ID);
            String firstName = resultSet.getString(COLUMN_STUDENT_FIRST_NAME);
            String lastName = resultSet.getString(COLUMN_STUDENT_LAST_NAME);
            students.add(new Student(studentId, firstName, lastName));
         }
      }
      return students;
   }

   public static Student findStudentByID(Connection connection, int studentId) throws SQLException {
      Student student = new Student();
      final String sqlQuery = "SELECT students.* FROM students WHERE student_id = ?; ";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, studentId);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()) {
            student.setStudentId(resultSet.getInt(COLUMN_STUDENT_ID));
            student.setFirstName(resultSet.getString(COLUMN_STUDENT_FIRST_NAME));
            student.setLastName(resultSet.getString(COLUMN_STUDENT_LAST_NAME));
            student.setGroupId(resultSet.getInt(COLUMN_STUDENT_GROUP_ID));
         }
      }
      return student;
   }

   public static Student findStudentByFirstNameAndLastName(Connection connection, String firstName, String lastName) throws SQLException {
      Student student = new Student();
      final String sqlQuery = "SELECT students.* FROM students WHERE first_name = ? AND last_name = ?; ";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

         preparedStatement.setString(1, firstName);
         preparedStatement.setString(2, lastName);

         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()) {
            student.setStudentId(resultSet.getInt(COLUMN_STUDENT_ID));
            student.setFirstName(resultSet.getString(COLUMN_STUDENT_FIRST_NAME));
            student.setLastName(resultSet.getString(COLUMN_STUDENT_LAST_NAME));
            student.setGroupId(resultSet.getInt(COLUMN_STUDENT_GROUP_ID));
         }
      }
      return student;
   }

   public static void addCourseByCourse(Connection connection, Course course) throws SQLException {

      String sqlQuery = "INSERT INTO courses(course_name, course_description) VALUES (?, ?);";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setString(1, course.getName());
         preparedStatement.setString(2, course.getDescription());
         preparedStatement.execute();
      }
   }

   public static void addStudentOnCourse(Connection connection, Student student, Course course) throws SQLException {
      final String sqlQuery = "INSERT INTO public.students_courses (student_id, course_id) VALUES (?, ?);";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, student.getStudentId());
         preparedStatement.setInt(2, course.getCourseId());
         preparedStatement.execute();
      }
   }

   public static int deleteCourseByStudentId(Connection connection, Student student, Course course) throws SQLException {
      int deleteDone = 0;
      final String sqlQuery = "DELETE FROM public.students_courses WHERE student_id = ? AND course_id = ? ;";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, student.getStudentId());
         preparedStatement.setInt(2, course.getCourseId());
         deleteDone = preparedStatement.executeUpdate();
      }
      return deleteDone;
   }

   public static void addAllCoursesForStudent(Connection connection, Student student) {
      String sqlQuery = "INSERT INTO students_courses(student_id, course_id) VALUES (?, ?);";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {

         int studentId = findStudentIdByFirstNameAndLastName(connection, student.getFirstName(), student.getLastName());

         student.getCourses().forEach(course -> {
            try {
               int courseId = findCourseIdByNameCourse(connection, course.getName());
               preparedStatement.setInt(1, studentId);
               preparedStatement.setInt(2, courseId);
               preparedStatement.execute();
            } catch (SQLException e) {
               System.err.println(e);
            }
         });
      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static void addGroup(Connection connection, Group group) {
      String sqlQuery = "INSERT INTO groups(group_name) VALUES (?);";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setString(1, group.getName());
         preparedStatement.execute();

      } catch (SQLException e) {
         System.err.println(e);
      }
   }

   public static boolean createTableByFile(Connection connection, File file, String tableName) throws IOException, SQLException {
      String sqlCreateTable;
      boolean tableCreate = false;

      try (FileReader fr = new FileReader(file);
           BufferedReader br = new BufferedReader(fr);
           Statement statement = connection.createStatement()) {

         sqlCreateTable = br.readLine();

         if (tableExist(connection, tableName)) {
            dropTable(connection, tableName);
            statement.executeUpdate(sqlCreateTable);
            tableCreate = true;
         } else {
            statement.executeUpdate(sqlCreateTable);
            tableCreate = true;
         }
      }
      return tableCreate;
   }

   public static void dropTable(Connection connection, String tableName) throws SQLException {
      String sql = "DROP TABLE " + tableName;

      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         //preparedStatement.setString(1, tableName);  TODO вставляет таблицу в скобках ''
         preparedStatement.executeUpdate();
      }
   }

   public static List<Group> findGroupsWithMinStudents(Connection connection) throws SQLException {
      List<Group> groups = new ArrayList<>();
      final String sqlQuery = "SELECT groups_students.group_name, COUNT(groups_students.student_id) AS count_students " +
          "FROM (SELECT groups.group_name, students.* " +
          "FROM groups JOIN students ON groups.group_id = students.group_id) AS groups_students " +
          "GROUP BY groups_students.group_name " +
          "HAVING COUNT(groups_students.student_id) = (SELECT MIN(min.count_students) " +
          "FROM (SELECT groups_students.group_name, COUNT(groups_students.student_id) AS count_students " +
          "FROM (SELECT groups.group_name, students.* " +
          "FROM groups JOIN students ON groups.group_id = students.group_id) AS groups_students " +
          "GROUP BY groups_students.group_name) AS min )";

      try (Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sqlQuery)) {

         while (resultSet.next()) {

            String groupName = resultSet.getString(COLUMN_GROUP_GROUP_NAME);
            int countStudents = resultSet.getInt("count_students");
            groups.add(new Group(groupName, countStudents));
         }
      }
      return groups;
   }

   public static List<Group> findGroupsWithEqualsCountOfStudents(Connection connection) throws SQLException {
      List<Group> groups = new ArrayList<>();
      final String sqlQuery = "SELECT A.group_name, A.count_students\n" +
          "FROM (SELECT groups_students.group_name, COUNT(groups_students.student_id) AS count_students\n" +
          "FROM (SELECT groups.group_name, students.*\n" +
          "\t  FROM groups JOIN students ON groups.group_id = students.group_id) AS groups_students\n" +
          "\t  GROUP BY groups_students.group_name) AS A JOIN (SELECT groups_students.group_name, COUNT(groups_students.student_id) AS count_students\n" +
          "FROM (SELECT groups.group_name, students.*\n" +
          "\t  FROM groups JOIN students ON groups.group_id = students.group_id) AS groups_students\n" +
          "\t  GROUP BY groups_students.group_name) AS B ON A.count_students=B.count_students\n" +
          "\t  WHERE A.group_name <> B.group_name";

      try (Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sqlQuery)) {

         while (resultSet.next()) {
            String groupName = resultSet.getString(COLUMN_GROUP_GROUP_NAME);
            int countStudents = resultSet.getInt("count_students");
            groups.add(new Group(groupName, countStudents));
         }
      }
      return groups;
   }

   public static List<Group> findGroupsWithCountOfStudents(Connection connection) throws SQLException {
      List<Group> groups = new ArrayList<>();
      final String sqlQuery = "SELECT groups_students.group_name, COUNT(groups_students.student_id) AS count_students " +
          "FROM (SELECT groups.group_name, students.* " +
          "FROM groups JOIN students ON groups.group_id = students.group_id) AS groups_students " +
          "GROUP BY groups_students.group_name";

      try (Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sqlQuery)) {

         while (resultSet.next()) {
            String groupName = resultSet.getString(COLUMN_GROUP_GROUP_NAME);
            int countStudents = resultSet.getInt("count_students");
            groups.add(new Group(groupName, countStudents));
         }
      }
      return groups;
   }

   public static List<Student> findStudentsStudyingInGroupByNameGroup(Connection connection, String nameGroup) throws SQLException {
      List<Student> students = new ArrayList<>();
      final String sql = "SELECT students.*, groups.* FROM students JOIN groups ON groups.group_id = students.group_id " +
          "WHERE groups.group_name = ? ;";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
         preparedStatement.setString(1, nameGroup);
         ResultSet resultSet = preparedStatement.executeQuery();

         while (resultSet.next()) {
            int studentId = resultSet.getInt(COLUMN_STUDENT_ID);
            String groupName = resultSet.getString(COLUMN_GROUP_GROUP_NAME);
            String studentName = resultSet.getString(COLUMN_STUDENT_FIRST_NAME);
            String studentLastName = resultSet.getString(COLUMN_STUDENT_LAST_NAME);
            int studentGroupId = resultSet.getInt(COLUMN_STUDENT_GROUP_ID);
            students.add(new Student(studentId, studentName, studentLastName, studentGroupId, groupName));
         }
      }
      return students;
   }

   public static Course findCourseByName(Connection connection, String name) throws SQLException {
      Course course = new Course();
      final String sqlQuery = "SELECT courses.* FROM courses WHERE course_name = ? ;";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setString(1, name);
         ResultSet resultSet = preparedStatement.executeQuery();

         if (resultSet.next()) {
            int courseId = resultSet.getInt(COLUMN_COURSE_ID);
            String courseName = resultSet.getString(COLUMN_COURSE_NAME);
            String description = resultSet.getString(COLUMN_COURSE_DESCRIPTION);
            course = new Course(courseId, courseName, description);
         }
      }
      return course;
   }

   public static List<Course> findAllCoursesInUniversity(Connection connection) throws SQLException {
      List<Course> courses = new ArrayList<>();
      final String sqlQuery = "SELECT courses.* FROM courses ;";

      try (Statement statement = connection.createStatement();
           ResultSet resultSet = statement.executeQuery(sqlQuery)) {

         while (resultSet.next()) {
            int courseId = resultSet.getInt(COLUMN_COURSE_ID);
            String courseName = resultSet.getString(COLUMN_COURSE_NAME);
            String description = resultSet.getString(COLUMN_COURSE_DESCRIPTION);
            courses.add(new Course(courseId, courseName, description));
         }

      }
      return courses;
   }

   public static boolean addStudentByStudent(Connection connection, Student student) throws SQLException {
      boolean addDone = false;
      final String sqlQuery = "INSERT INTO public.students(group_id, first_name, last_name) VALUES (?, ?, ?);";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, student.getGroupId());
         preparedStatement.setString(2, student.getFirstName());
         preparedStatement.setString(3, student.getLastName());

         preparedStatement.execute();
      }
      return addDone;
   }

   public static boolean deleteStudentByStudent(Connection connection, Student student) throws SQLException {
      boolean deleteDone = false;
      final String sqlQuery = "DELETE FROM public.students WHERE student_id = ? ;";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, student.getStudentId());

         preparedStatement.execute();
      }
      return deleteDone;
   }

   public static int deleteCoursesFromStudentByStudentId(Connection connection, Student student) throws SQLException {
      final String sqlQuery = "DELETE FROM public.students_courses WHERE student_id = ? ;";
      int deleteDone = 0;

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, student.getStudentId());

         deleteDone = preparedStatement.executeUpdate();
      }
      return deleteDone;
   }

   public static int deleteStudentFromGroup(Connection connection, Student student) throws SQLException {
      int deleteDone = 0;
      final String sqlQuery = "UPDATE public.students SET group_id=0 WHERE students.student_id = ? ;";

      try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
         preparedStatement.setInt(1, student.getStudentId());
         deleteDone = preparedStatement.executeUpdate();
      }
      return deleteDone;
   }

   private static boolean tableExist(Connection connection, String tableName) throws SQLException {
      boolean tableExists = false;

      try (ResultSet resultSet = connection.getMetaData().getTables(null, null, tableName, null)) {

         while (resultSet.next()) {
            String tName = resultSet.getString("TABLE_NAME");
            if (tName != null && tName.equals(tableName)) {
               tableExists = true;
               break;
            }
         }
      }
      return tableExists;
   }
}
