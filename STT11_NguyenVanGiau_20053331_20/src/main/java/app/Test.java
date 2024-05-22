package app;

import dao.CourseDAO;
import dao.EntityManagerFactoryUtil;
import entities.Course;
import jakarta.persistence.EntityManager;

public class Test {

	public static void main(String[] args) {

		EntityManagerFactoryUtil mangerFactoryUtil = new EntityManagerFactoryUtil();
		EntityManager entityManager = mangerFactoryUtil.getEnManager();

		
		//tìm join bảng
//		StudentDAO studentDAO = new StudentDAO(entityManager);
//
//		Object[] studentInfo = studentDAO.findStudentInformationById(1);
//
//        // Kiểm tra nếu kết quả trả về không rỗng, truy cập vào các phần tử của mảng để lấy thông tin tên
//        if (studentInfo != null && studentInfo.length >= 2) {
//            String firstName = (String) studentInfo[0];
//            String lastName = (String) studentInfo[1];
//            LocalDateTime enrollmentDate = (LocalDateTime) studentInfo[2];
//            
//            System.out.println("Student Name: " + firstName + " " + lastName );
//            System.out.println("Enrollment date: " + enrollmentDate);
//        } else {
//            System.out.println("Student not found");
//        }
        
		//tìm 1 trường hợp
        CourseDAO courseDAO = new CourseDAO(entityManager);
//        Course course = courseDAO.findById(1);
//        
//		if (course != null) {
//			System.out.println(course.getTittle());
//			System.out.println(course.getCredits());
//		} else {
//			System.out.println("Student not found");
//		}
        
        // Tìm 2 trường hợp
//        List<Course> courses = courseDAO.findByTitleAndCredits("TTDN", 3);
//        if (!courses.isEmpty()) {
//            System.out.println("List of courses found:");
//            for (Course course : courses) {
//                System.out.println("Course ID: " + course.getId() + ", Title: " + course.getTittle() + ", Credits: " + course.getCredits());
//            }
//        } else {
//            System.out.println("No courses found with the given title and credits.");
//        }
        
        //thêm 1 course
        Course course = new Course(4, "KTMT");
//        courseDAO.addCourse(course);
        courseDAO.updateCourse(3,"GDTC",2);
        
	}
}
