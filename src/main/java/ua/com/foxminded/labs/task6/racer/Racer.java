package ua.com.foxminded.labs.task6.racer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class Racer {
	private String id;
	private String name;
	private String nameOfTeam;
	private LocalTime startTime;
	private LocalDate startDate;
	private LocalTime endTime;
	private LocalDate endDate;
	private LocalTime resultTime;

	public Racer(String id, String name, String nameOfTeam) {
		this.id = id;
		this.name = name;
		this.nameOfTeam = nameOfTeam;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameOfTeam() {
		return nameOfTeam;
	}

	public void setNameOfTeam(String nameOfTeam) {
		this.nameOfTeam = nameOfTeam;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public LocalTime getResultTime() {
		return resultTime;
	}

	public void setResultTime(LocalTime resultTime) {
		this.resultTime = resultTime;
	}

	@Override
	public String toString() {
		return "Racer{" +
			 "id='" + id + '\'' +
			 ", name='" + name + '\'' +
			 ", nameOfTeam='" + nameOfTeam + '\'' +
			 ", startTime=" + startTime +
			 ", endTime=" + endTime +
			 ", resultTime=" + resultTime +
			 '}';
	}
}
