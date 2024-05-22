package dao;

import java.util.List;

import entities.Course;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class CourseDAO {

	private EntityManager entityManager;

	public CourseDAO(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	//tìm kiếm khóa học theo id
	public Course findById(long id) {
		return entityManager.find(Course.class, id);
	}
	
	//tìm kiếm 2 trường hợp theo tittle và theo credits
	// Tìm kiếm khóa học theo tiêu đề (tittle) và số tín chỉ (credits)
    public List<Course> findByTitleAndCredits(String tittle, int credits) {
        String jpql = "SELECT c FROM Course c WHERE c.tittle = :tittle AND c.credits = :credits";
        return entityManager.createQuery(jpql, Course.class)
                            .setParameter("tittle", tittle)
                            .setParameter("credits", credits)
                            .getResultList();
    }
    
    
    public void addCourse(Course course) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(course);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Xóa khóa học
    public void deleteCourse(long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Course course = entityManager.find(Course.class, id);
            if (course != null) {
                entityManager.remove(course);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
    // Cập nhật thông tin khóa học
    public void updateCourse(long id, String newTitle, int newCredits) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Course course = entityManager.find(Course.class, id);
            if (course != null) {
                course.setTittle(newTitle);
                course.setCredits(newCredits);
                entityManager.merge(course);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
    
}
