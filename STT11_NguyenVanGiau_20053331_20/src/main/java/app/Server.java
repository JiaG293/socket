package app;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dao.CourseDAO;
import dao.EntityManagerFactoryUtil;
import dao.StudentDAO;
import entities.Course;
import entities.Student;
import jakarta.persistence.EntityManager;

public class Server {
    private static final int PORT = 6666;

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Create a thread pool to handle multiple clients
            ExecutorService executorService = Executors.newCachedThreadPool();

            // Start accepting client connections
            System.out.printf("Server listening on port " + PORT);

            while (true) {
                // Accept a client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Create a new thread for the client and submit it to the thread pool
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                executorService.execute(clientHandler);
            }
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private ObjectInputStream in;
        private ObjectOutputStream out;
        private EntityManagerFactoryUtil mangerFactoryUtil;
        private EntityManager entityManager;

        public ClientHandler(Socket clientSocket) {
            super();
            this.clientSocket = clientSocket;
            this.mangerFactoryUtil = new EntityManagerFactoryUtil();
            this.entityManager = mangerFactoryUtil.getEnManager();
        }

        private void handleGetStudentById(String studentId) throws IOException {
            // Lấy sinh viên từ cơ sở dữ liệu dựa trên id
            StudentDAO studentDAO = new StudentDAO(entityManager);
            Student student = studentDAO.findById(Long.parseLong(studentId));

            // Gửi sinh viên đã lấy được cho client
            out.writeObject(student);
            out.flush();
        }

        private void handleFindStudentInformationById(String studentId) throws IOException {
            // Lấy thông tin của sinh viên từ cơ sở dữ liệu dựa trên id
            StudentDAO studentDAO = new StudentDAO(entityManager);
            Object[] studentInfo = studentDAO.findStudentInformationById(Long.parseLong(studentId));

            // Gửi thông tin sinh viên đã lấy được cho client
            out.writeObject(studentInfo);
            out.flush();
        }


        private void handleFindCourseByTitleAndCredits(String tittle, String creditss) throws IOException {
            String title = tittle;
            int credits = Integer.parseInt(creditss);
            CourseDAO courseDAO = new CourseDAO(entityManager);
            List<Course> courses = courseDAO.findByTitleAndCredits(title, credits);
            out.writeObject(courses);
            out.flush();
        }

        @Override
        public void run() {
            try {

                // Create input and output streams for data transmission
                in = new ObjectInputStream(clientSocket.getInputStream());
                out = new ObjectOutputStream(clientSocket.getOutputStream());

                while (true) {

                    String clientData = in.readUTF();
                    System.out.println("Recive from client: " + clientData);
                    String[] items = clientData.split("&");


                    if (items.length == 0) {
                        System.out.println("Missing request from Client");
                        return;
                    }

                    switch (items[0]) {
                        case "getStudentById":
                            handleGetStudentById(items[1]);
                            break;
                        case "findStudentInformationById":
                            handleFindStudentInformationById(items[1]);
                            break;
                        case "handleFindCourseByTitleAndCredits":
                            handleFindCourseByTitleAndCredits(items[1], items[2]);
                            break;
                        case "addCourse":
                            if (items.length >= 3) {
                                String courseTitle = items[1];
                                int courseCredits = Integer.parseInt(items[2]);
                                CourseDAO courseDAO = new CourseDAO(entityManager);
                                Course newCourse = new Course(courseCredits, courseTitle);
                                courseDAO.addCourse(newCourse);
                                out.writeObject("Course added successfully");
                                out.flush();
                            } else {
                                System.out.println("Invalid parameters for addCourse");
                                out.writeObject("Failed to add course");
                                out.flush();
                            }
                            break;
                        case "deleteCourse":
                            if (items.length >= 2) {
                                long courseId = Long.parseLong(items[1]);
                                CourseDAO courseDAO = new CourseDAO(entityManager);
                                courseDAO.deleteCourse(courseId);
                                out.writeObject("Course deleted successfully");
                                out.flush();
                            } else {
                                System.out.println("Invalid parameters for deleteCourse");
                                out.writeObject("Failed to delete course");
                                out.flush();
                            }
                            break;

                        case "updateCourse":
                            if (items.length >= 4) {
                                long courseId = Long.parseLong(items[1]);
                                String newTitle = items[2];
                                int newCredits = Integer.parseInt(items[3]);
                                CourseDAO courseDAO = new CourseDAO(entityManager);
                                courseDAO.updateCourse(courseId, newTitle, newCredits);
                                out.writeUTF("Course updated successfully");
                                out.flush();
                            }
                            break;
                        default:
                            break;
                    }

                }

            } catch (IOException e) {
//				e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
                mangerFactoryUtil.closeEnManager();
                mangerFactoryUtil.closeEnManagerFactory();
            }
        }
    }
}


