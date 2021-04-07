package ua.com.foxminded.labs.task7.util;

import ua.com.foxminded.labs.task7.service.Service;

import java.util.HashMap;
import java.util.Map;

public class MenuUniversity {

   private Map<Integer, Runnable> operations = new HashMap<>();

   public MenuUniversity() {
      operations.put(1, this::operation1);
      operations.put(2, this::operation2);
      operations.put(3, this::operation3);
      operations.put(4, this::operation4);
      operations.put(5, this::operation5);
      operations.put(6, this::operation6);
   }

   public void execute(Integer operation) {
      if (operation > 0 && operation < 7) {
         operations.get(operation).run();
      }
   }

   private void operation1() {
      Service.findGroupsWithLessOrEqualsStudentCount();
   }

   private void operation2() {
      Service.showStudentsByNameGroup();
   }

   private void operation3() {
      Service.addStudentByFirstAndLastName();
   }

   private void operation4() {
      Service.deleteStudentById();
   }

   private void operation5() {
      Service.addStudentOnCourse();
   }

   private void operation6() {
      Service.deleteStudentFromCourse();
   }

   public void showMenuApp() {
      System.out.println(
          "---------------------------------MENU-------------------------------------\n" +
              "1. Show all groups with less or equals student count\n" +
              "2. Show all students related to course with given name\n" +
              "3. Add new student\n" +
              "4. Delete student by STUDENT_ID\n" +
              "5. Add a student to the course (from a list)\n" +
              "6. Remove the student from one of his or her courses\n" +
              "7. Exit.\n" +
              "--------------------------------------------------------------------------");
   }
}


