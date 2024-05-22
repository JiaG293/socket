package dao;

import java.util.List;

import entities.Student;
import jakarta.persistence.EntityManager;

public class StudentDAO {

	private EntityManager entityManager;

	public StudentDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Student findById(long id) {
		return entityManager.find(Student.class, id);
	}

	// trường hợp 2 bảng lấy dữ liệu join bảng tìm bằng id
	public Object[] findStudentInformationById(long id) {
        // Sử dụng JPQL để lấy tên của Person và enrollmentDate của Student dựa trên id
        String jpql = "SELECT p.firstName, p.lastName, s.enrollmentDate FROM Person p JOIN Student s ON p.id = s.id WHERE p.id = :id";

        // Thực hiện truy vấn JPQL
        List<Object[]> results = entityManager.createQuery(jpql, Object[].class)
                                               .setParameter("id", id)
                                               .getResultList();

        // Nếu kết quả trả về không rỗng, trả về mảng Object chứa thông tin Person và enrollmentDate của Student
        if (!results.isEmpty()) {
            return results.get(0);
        } else {
            // Nếu không tìm thấy kết quả, trả về null
            return null;
        }
    }


	//
	
	

}
