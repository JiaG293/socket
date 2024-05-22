package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Student")
public class Student extends Person implements Serializable{
	
	private static final long serialVersionUID = -6911244287824585294L;
	@Column(name = "enrollmentDate")
	private LocalDateTime enrollmentDate;


	public Student() {
		super();
	}

	public Student(String firstName, String lastName, LocalDateTime enrollmentDate ) {
		super(firstName, lastName);
		this.enrollmentDate = enrollmentDate;
	}

	public LocalDateTime getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDateTime enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}



}
