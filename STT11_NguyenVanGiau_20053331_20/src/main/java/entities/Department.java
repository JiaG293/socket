package entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "administrator")
	private int administrator;
	
	@Column(name = "budget")
	private double budget;

	@Override
	public String toString() {
		return "Department{" +
				"id=" + id +
				", administrator=" + administrator +
				", budget=" + budget +
				'}';
	}
}
