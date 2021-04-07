package ua.com.foxminded.labs.task7.domain;

import java.util.List;
import java.util.Objects;

public class Course {
   private int courseId;
   private String name;
   private String description;
   private List<Student> students;

   public Course() {
   }

   public Course(String name) {
      this.name = name;
   }

   public Course(int courseId, String name, String description) {
      this.courseId = courseId;
      this.name = name;
      this.description = description;
   }

   public Course(String name, String description) {
      this.name = name;
      this.description = description;
   }

   public Course(String name, String description, List<Student> students) {
      this.name = name;
      this.description = description;
      this.students = students;
   }

   public Course(int id, String name) {
      this.setCourseId(id);
      this.setName(name);
   }

   public int getCourseId() {
      return courseId;
   }

   public void setCourseId(int courseId) {
      this.courseId = courseId;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getDescription() {
      return description;
   }

   public void setDescription(String description) {
      this.description = description;
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
      Course course = (Course) o;
      return courseId == course.courseId &&
          Objects.equals(name, course.name) &&
          Objects.equals(description, course.description) &&
          Objects.equals(students, course.students);
   }

   @Override
   public int hashCode() {
      return Objects.hash(courseId, name, description, students);
   }

   @Override
   public String toString() {
      return "course Id=" + courseId +
          ", name='" + name + '\'' +
          ", description='" + description + '\'' +
          '}';
   }
}
