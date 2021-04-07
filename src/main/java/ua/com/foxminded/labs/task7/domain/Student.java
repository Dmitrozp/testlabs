package ua.com.foxminded.labs.task7.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student {
	private int studentId;
	private String firstName;
	private String lastName;
	private String group;
	private int groupId;
	private List<Course> courses;

	public Student() {}

	public Student(int studentId, String firstName, String lastName, int groupId) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groupId = groupId;
	}

	public Student(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public Student(String firstName, String lastName, String group, int groupId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.group = group;
		this.groupId = groupId;
	}

	public Student(int studentID, String firstName, String lastName, int groupId, String nameGroup) {
		this.studentId = studentID;
		this.firstName = firstName;
		this.lastName = lastName;
		this.groupId = groupId;
		this.group = nameGroup;
	}

	public Student(int studentId, String firstName, String lastName) {
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setCourse(Course course){
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		this.courses = courses;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Student student = (Student) o;
		return studentId == student.studentId &&
			 groupId == student.groupId &&
			 Objects.equals(firstName, student.firstName) &&
			 Objects.equals(lastName, student.lastName) &&
			 Objects.equals(group, student.group) &&
			 Objects.equals(courses, student.courses);
	}

	@Override
	public int hashCode() {
		return Objects.hash(studentId, firstName, lastName, group, groupId, courses);
	}

	@Override
	public String toString() {
		return
			 "Id=" + studentId +
			 ", firstName='" + firstName + '\'' +
			 ", lastName='" + lastName + '\'' +
			 ", group='" + group + '\'' +
			 ", groupId=" + groupId +
			 ", courses=" + courses;
	}
}
