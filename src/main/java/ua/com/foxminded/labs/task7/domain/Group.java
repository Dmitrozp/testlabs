package ua.com.foxminded.labs.task7.domain;

import java.util.List;
import java.util.Objects;

public class Group {
   private int groupId;
   private String name;
   private int countOfStudents;
   private List<Student> students;

   public Group() {
   }

   public Group(String name) {
      this.name = name;
   }

   public Group(int groupId, String name) {
      this.groupId = groupId;
      this.name = name;
   }

   public Group(String name, int countOfStudents) {
      this.name = name;
      this.countOfStudents = countOfStudents;
   }

   public Group(List<Student> students) {
      this.students = students;
   }

   public Group(String name, int countOfStudents, List<Student> students) {
      this.name = name;
      this.countOfStudents = countOfStudents;
      this.students = students;
   }

   public Group(int id) {
      this.groupId = id;
   }

   public int getGroupId() {
      return groupId;
   }

   public void setGroupId(int groupId) {
      this.groupId = groupId;
   }

   public int getCountOfStudents() {
      return countOfStudents;
   }

   public void setCountOfStudents(int countOfStudents) {
      this.countOfStudents = countOfStudents;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public List<Student> getStudents() {
      return students;
   }

   public void setStudents(List<Student> students) {
      this.students = students;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Group group = (Group) o;
      return groupId == group.groupId &&
          countOfStudents == group.countOfStudents &&
          name.equals(group.name) &&
          Objects.equals(students, group.students);
   }

   @Override
   public int hashCode() {
      return Objects.hash(groupId, name, countOfStudents, students);
   }

   @Override
   public String toString() {
      return "Group{" +
          "groupId=" + groupId +
          ", name='" + name + '\'' +
          ", countOfStudents=" + countOfStudents +
          ", students=" + students +
          '}';
   }
}
