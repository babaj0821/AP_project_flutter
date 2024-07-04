package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private BufferedWriter out;

    ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            this.input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
        }
        return 4;
    }
    public boolean usernamechecker(String username){
        for(int i = 0 ; i < students.size() ; i ++){
            Student student = students.get(i);
            if (student.getStudentId().equals(username)) {
                return false; // username is recurrent
            }
        }
        return true; //there is no problem
    }
    public boolean passwordchecker(String password , String username){
        // Check if password contains the username
        Pattern pattern1 = Pattern.compile(username);
        Matcher matcher1 = pattern1.matcher(password);
        boolean cond1 = matcher1.find();
    
        // Check if password matches the regex for uppercase, lowercase, and minimum length
        Pattern pattern2 = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z]).{8,}$");
        Matcher matcher2 = pattern2.matcher(password);
        boolean cond2 = matcher2.find();
    
        // Return true if password does not contain username and matches the regex
        return !cond1 && cond2;
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
                int coursea = 0;
                Student s = findStudent(studentID);  
                for(int j = 0 ; j < s.getAssignments().size() ; j++){
                    if (s.getAssignments().get(j).getCourseName().getCodecourse().equals(codecourse) && !(s.getAssignments().get(j).getHasbeendone())) {
                        coursea++;
                    }
                }
                System.out.println("course_details-"+ c.getCourseName() + "-" + c.getTeacher().getName() + " " + c.getTeacher().getSurname() +"-"+ c.getNumberOfUnits() + "-"  + coursea + "-" + c.findTopStudent());
                return "course_details-"+ c.getCourseName() + "-" + c.getTeacher().getName() + " " + c.getTeacher().getSurname()+ "-"+ c.getNumberOfUnits() + "-"  + coursea + "-" + c.findTopStudent();
            }
        }
        return "wrong";
    } 
    public String givetask(String studentID){
        // taskes needed to be sent to the app
        List<Assignment> s = findStudent(studentID).getAssignments();
        StringBuilder data = new StringBuilder();
        for(int i = 0 ; i < s.size() ; i ++){
            if (!s.get(i).getHasbeendone()) {
                data.append(s.get(i).getCourseName().getCourseName() +":"+ s.get(i).getAssignmentName() + "/" + s.get(i).getDeadline().toString() + ",");

            }

        }
        String result = data.toString().substring(0 , data.length());

        return result;

    }
    public void completedTask(String studentID ,String nametask){
        Student s = findStudent(studentID);
        s.removeAssignment(nametask);
    }
    public String profiledata(String studentID){
        System.out.println(students);
        Student s = findStudent(studentID);
        StringBuilder data = new StringBuilder();
        if(s.getName().equals(null)){
            data.append("guest" +"-"+"student" +"-"+ studentID + "-1402_1403" +
            "-" + 0 + "-" + 0 + "-" + s.getPassword());
            return data.toString();
        }else if(!s.getName().equals(null) && s.getEnrollmentCourses().size() == 0){
            data.append(s.getName() +"-"+"student" +"-"+ studentID + "-1402_1403" +
            "-" + 0 + "-" + 0 + "-" + s.getPassword());
            return data.toString();
        }
        data.append(s.getName() +"-"+"student" +"-"+ studentID + "-1402_1403");
        int numunit = 0;
        for(int i = 0 ; i < s.getEnrollmentCourses().size() ; i++){
            numunit+= s.getEnrollmentCourses().get(i).getNumberOfUnits();
        }
        data.append("-" + numunit + "-" + s.findTotalAvg() + "-" + s.getPassword());
        return data.toString();
    }
    public String updatepassword(String studentID  ,String passeord){
        Student s = findStudent(studentID);
        if (passwordchecker(passeord, studentID)) {
            s.setPassword(passeord);
            return "password changed";
        }else{
            return "password is wrong";
        }
    }
    public String summary(String studentID){
        Student s = findStudent(studentID);
        int homeworkdone = 0;
        int homeworkisnotdone = 0;
        for(int i = 0 ; i < s.getAssignments().size() ; i++){
            if (s.getAssignments().get(i).getHasbeendone()) {
                homeworkdone++;
            }else{
                homeworkisnotdone++;
            }
        }
        StringBuilder data = new StringBuilder();
        int exams = s.getEnrollmentCourses().size();
        data.append("summary:" + homeworkdone + "-" + homeworkisnotdone + "-" + exams +"-" + s.bestgrade() + "-" + s.worstgrade());
        return data.toString();
    }
    public String homeworkdone(String studentID){
        Student s = findStudent(studentID);
        StringBuilder data = new StringBuilder();
        data.append("done:");
        for(int i = 0 ; i < s.getAssignments().size() ; i++){
            if ((s.getAssignments().get(i)).getHasbeendone()) {
                data.append(s.getAssignments().get(i).getCourseName().getCourseName() + ":" +
                s.getAssignments().get(i).getAssignmentName() + ":" + "done" + "-");
            }
        }
        String result = data.toString().substring(0, data.length() - 1);
        return result;
    }
    public String homeworknotdone(String studentID){
        Student s = findStudent(studentID);
        StringBuilder data = new StringBuilder();
        data.append("notdone:");
        for(int i = 0 ; i < s.getAssignments().size() ; i++){
            if (!(s.getAssignments().get(i)).getHasbeendone()) {
                data.append(s.getAssignments().get(i).getCourseName().getCourseName() + ":" +
                s.getAssignments().get(i).getAssignmentName() + ":" + "notdone" + "-");
            }
        }
        String result = data.toString().substring(0, data.length() - 1);
        return result;
    }
    @Override
    public void run() {
        String order = "";
            try {
                System.out.println("waiting");
                order = input();
                System.out.println(order);
                String[] data = order.split("-");
                System.out.println(data[1]);
                switch (data[1]) {
                    case "sign":
                        if (usernamechecker(data[2]) && passwordchecker(data[3], data[2]) ) {
                            Student s = new Student(data[2]);
                            s.setPassword(data[3]);
                            s.setName(data[4]);
                            // addin the student to data base
                            students.add(s);
                            System.out.println(students);
                            databasehandler.writeassignemnt(assignments);
                            databasehandler.writecourse(courses);
                            databasehandler.writestudent(students);
                            databasehandler.writeteacher(teachers);
                            output("1");
                            break;
                        }else{
                            output("2");
                            break;
                        }
                    case "login":
                        output(String.valueOf(checkPasswordAndUsername(data[3], data[2])));
                        break;
                    case "course":
                        output(returncourse(data[0] , data[2]));
                        break;
                    case "giveTask":
                        output(givetask(data[0]));
                        break;
                    case "completedTask":
                        completedTask(data[0], data[2]);
                        break;
                    case "profile":
                        output(profiledata(data[0]));
                        break;
                    case "update_password":
                        output(updatepassword(data[0], data[2]));
                        break;
                    case "getSummary":
                        output(summary(data[0]));
                        Thread.sleep(100);
                        order = input();
                    case "getNotDoneAssignments":
                        output(homeworknotdone(data[0]));
                        Thread.sleep(100);
                        order = input();
                    case "getDoneAssignments":
                        output(homeworkdone(data[0]));
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

    public  String input() {
        StringBuilder order = new StringBuilder();
        try {
            int character = input.read();
            while (character!= -1) {
                if (character == '\u0000') {
                    break;
                }
                order.append((char) character);
                character = input.read(); // reading the message
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
            // output.writeBytes(code); // Then send the actual message
            // output.flush();
            out.write(code);
            out.newLine();
            out.flush();
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
