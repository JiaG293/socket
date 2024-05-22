package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Instructor")
public class Instructor extends Person implements Serializable{
	
	private static final long serialVersionUID = -3538610833682733318L;
	@Column(name="hireDate")
	private LocalDateTime hireDate;

	public Instructor() {
		super();
	}

	public Instructor(String firstName, String lastName, LocalDateTime hireDate) {
		super(firstName, lastName);
		this.setHireDate(hireDate);
	}

	public LocalDateTime getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDateTime hireDate) {
		this.hireDate = hireDate;
	}

	@Override
	public String toString() {
		return "Instructor{" +
				"hireDate=" + hireDate +
				", id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}
