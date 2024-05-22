package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import entities.Course;

public class Client {
	private static final int PORT = 6666;
	private static final Scanner SC = new Scanner(System.in);

	public static void main(String[] args) {
		try {
			Client client = new Client();
			client.startClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startClient() throws UnknownHostException, IOException {
		boolean running = true;
		while (running) {
			System.out.println("========================================");
			System.out.println("Select an option:");
			System.out.println("1. Find student information by ID");
			System.out.println("2. Find course by credits and tittle");
			System.out.println("3. Add course");
			System.out.println("4. Delete course by ID");
			System.out.println("5. Update course");
			System.out.println("0. Exit");
			System.out.println("Enter your choice: ");
			int choice = SC.nextInt();
			SC.nextLine();

			switch (choice) {
				case 1:
					System.out.print("Enter student ID: ");
					long studentId = SC.nextLong();
					findStudentInfoById(studentId);
					break;
				case 2:
					System.out.print("Enter tittle: ");
					String tittle = SC.next();
					System.out.print("Enter credits: ");
					String credits = SC.next();
					findCourse(tittle, credits);
					break;
				case 3:
					System.out.print("Enter course tittle: ");
					String courseTitle = SC.next();
					System.out.print("Enter course credits: ");
					int courseCredits = SC.nextInt();
					addCourse(courseTitle, courseCredits);
					break;
				case 4:
					System.out.print("Enter course ID to delete: ");
					long courseId = SC.nextLong();
					deleteCourse(courseId);
					break;
				case 5:
					System.out.print("Enter course ID: ");
					long updateCourseId = SC.nextLong();
					System.out.print("Enter new title: ");
					String updatedTitle = SC.next();
					System.out.print("Enter new credits: ");
					int updatedCredits = SC.nextInt();
					updateCourse(updateCourseId, updatedTitle, updatedCredits);
					break;
				case 0:
					System.out.println("Exiting client...");
					running = false;
					break;
				default:
					System.out.println("Invalid choice. Please try again.");
					break;
			}
		}
	}

	private void findStudentInfoById(Long studentId) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", PORT);
			 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

			System.out.println("Get student by id");
			out.writeUTF("findStudentInformationById&" + studentId);
			out.flush();

			Object[] student = (Object[]) in.readObject();
            System.out.println("Nhận về từ server: { name: " + student[0] + " last name: " + student[1] + " enrolDate: " + student[2] + " }");

		} catch (Exception e) {
			System.out.println("Không tìm thấy sinh viên" + e);
		}
	}

	private void findCourse(String tittle, String credits) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", PORT);
			 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

			System.out.println("Get course by id");
			out.writeUTF("handleFindCourseByTitleAndCredits&" + tittle + "&" + credits);
			out.flush();

			List<Course> courses = (List<Course>) in.readObject();

			for (Course course : courses) {
				System.out.println("Nhận về từ server: "  + course.toString());
			}

		} catch (Exception e) {
			System.out.println("Không tìm thấy khóa học hoặc dữ liệu không hợp lệ từ server.");
		}
	}

	private void addCourse(String title, int credits) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", PORT);
			 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

			System.out.println("Adding new course");
			out.writeUTF("addCourse&" + title + "&" + credits);
			out.flush();

			String response = (String) in.readObject();
			System.out.println("Nhận về từ server: " + response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void deleteCourse(long courseId) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", PORT);
			 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

			System.out.println("Deleting course");
			out.writeUTF("deleteCourse&" + courseId);
			out.flush();

			String response = (String) in.readObject();
			System.out.println("Nhận về từ server: " + response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void updateCourse(long courseId, String newTitle, int newCredits) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", PORT);
			 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

			out.writeUTF("updateCourse&" + courseId + "&" + newTitle + "&" + newCredits);
			out.flush();

			String response = in.readUTF();
			System.out.println(response);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
