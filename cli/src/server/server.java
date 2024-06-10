package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;
import java.util.*;
import main.*;
import database.*;

public class server {
    private ServerSocket server;

    public server(ServerSocket server) {
        this.server = server;
    }
    public void startServer() {
        try {
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("A new student has connected");
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            closeServer();
        }
    }

    public void closeServer() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        List<Student>s = Admin.getStudents();
        System.out.println(s);
        server server = new server(serverSocket);
        server.startServer();
    }
}

class ClientHandler implements Runnable {
    private static List<Student> students = new ArrayList<>();
    private static List<Teacher> teachers = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<Assignment> assignments = new ArrayList<>();
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            databasehandler.readdata();
            teachers = databasehandler.getTeachers();
            courses = databasehandler.getCourses();
            students = databasehandler.getStudents();
            assignments = databasehandler.getAssignments();
            System.out.println("Server: connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int checkPasswordAndUsername(String password, String username) throws Exception {
        for (int i = 0 ; i < students.size() ; i++) {
            if (password.equals(students.get(i).getPassword()) && username.equals(students.get(i).getStudentId())) {
                return 1; // Successful login
            } else if (password.equals(students.get(i).getPassword())) {
                return 2; // Password correct, username incorrect
            } else if (username.equals(students.get(i).getStudentId())) {
                return 3; // Username correct, password incorrect
            }
            System.out.println(students.get(i).getPassword());
            System.out.println(students.get(i).getStudentId());
        }
        return 4;
    }
    public Student findStudent(String studentID){
        for(int i  = 0 ; i < students.size() ; i++){
            if (studentID.equals(students.get(i).getStudentId())) {
                return students.get(i);
            }
        }
        return null;
    }
    public String returncourse(String studentID , String codecourse){
        List<Course>studetncourses = findStudent(studentID).getEnrollmentCourses();
        for(int i = 0 ; i < studetncourses.size() ; i++){
            if (studetncourses.get(i).getCodecourse().equals(codecourse)) {
                Course c = studetncourses.get(i);  
                System.out.println("course_details-"+ c.getCourseName() + "-" + c.getTeacher().getName() + " " + c.getTeacher().getSurname() +"-"+ c.getNumberOfUnits() + "-"  + c.getAssignments().size() + "-" + c.findTopStudent());
                return "course_details-"+ c.getCourseName() + "-" + c.getTeacher().getName() + " " + c.getTeacher().getSurname()+ "-"+ c.getNumberOfUnits() + "-"  + c.getAssignments().size() + "-" + c.findTopStudent();
            }
        }
        return "wrong";
    } 

    @Override
    public void run() {
        String order = "";
            try {
                System.out.println("waiting");
                order = input();
                System.out.println(order);
                System.out.println(order);
                System.out.println(Admin.getAdmin());
                System.out.println("get");
                String[] data = order.split("-");
                System.out.println(data[1]);
                switch (data[1]) {
                    case "sign":
                        Student s = new Student(data[2]);
                        s.setPassword(data[3]);
                        students.add(s);
                        output("1");
                        break;
                    case "login":
                        output(String.valueOf(checkPasswordAndUsername(data[3], data[2])));
                        break;
                    case "course":
                        output(returncourse(data[0] , data[2]));
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        databasehandler.writeassignemnt(assignments);
        databasehandler.writecourse(courses);
        databasehandler.writestudent(students);
        databasehandler.writeteacher(teachers);
        closeConnection();
    }

    public synchronized String input() {
        StringBuilder order = new StringBuilder();
        try {
            int character = input.read();
            while (character!= -1) {
                if (character == '\u0000') {
                    break;
                }
                order.append((char) character);
                character = input.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Received: " + order);
        return order.toString();
    }

    public void output(String code) {
        try {
            System.out.println(code);
            output.writeBytes(code); // Then send the actual message
            output.flush();
            System.out.println("Response sent" + code);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
