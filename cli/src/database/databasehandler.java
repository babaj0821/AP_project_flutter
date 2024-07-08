package database;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import main.Admin;
import main.Assignment;
import main.Course;
import main.Student;
import main.Teacher;

public class databasehandler {
    private static List<Student> students;
    private static List<Teacher> teachers;
    private static List<Course> courses;
    private static List<Assignment> assignments ;
    public static List<Assignment> getAssignments() {
        return assignments;
    }
    public static List<Course> getCourses() {
        return courses;
    }
    public static List<Student> getStudents() {
        return students;
    }
    public static List<Teacher> getTeachers() {
        return teachers;
    }
    public static void addassignment(Assignment a){
        if (a == null) throw new NullPointerException();
        for(int i = 0 ; i < a.getCourseName().getAssignments().size() ; i++){
            if (a.getCourseName().getAssignments().get(i).getAssignmentName().equals(a.getAssignmentName())) {
                System.out.println("exists");
            }
        }
        assignments.add(a);
        System.out.println("added");
    }
    public static void readdata() throws Exception{
        teachers = readteacher();
        Admin.setTeachers(teachers);
        courses = readcourse();
        Admin.setCourses(courses);
        assignments = readassignment();
        Admin.setAssignments(assignments);
        students = readsudent();
        Admin.setStudents(students);
    }
    public static void writedata(){
        writeassignemnt(assignments);
        writecourse(courses);
        writestudent(students);
        writeteacher(teachers);
    }
    public static Course findcourse(String code){
        for(int i = 0 ; i < courses.size() ; i++){
            if (courses.get(i).getCodecourse().equals(code)) {
                return courses.get(i);
            }
        }
        return null;
    }
     public static List<Student> readsudent() throws Exception {
        try {
            File file = new File("C:\\Users\\USER\\Desktop\\cli\\cli\\src\\database\\"+ "student.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String data = "";
            List<Student>students  = new ArrayList<>();
            while (true) {
                data = br.readLine();
                if (data == null)
                    break;
                String[] student = data.split("/");
                Student s = new Student(student[1]);
                s.setName(student[0]);
                if (student[2].equals(null)) {
                    s.setBirth(null);
                }else{
                    DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
                    try {
                        LocalDate localDate = LocalDate.parse(student[2], formatter);
                        System.out.println(localDate);
                        s.setBirth(localDate);
                    } catch (DateTimeParseException e) {
                        e.printStackTrace();
                    }
                }
                if (student[3] == null) {
                    s.setPassword(null);
                } else {
                    s.setPassword(student[3]);
                }
                for (int i =4; i < student.length; i = i + 2) {
                    Course c = findcourse(student[i]);
                    c.assignGrade(s, Double.parseDouble(student[i + 1]));
                    s.addCourse(c);
                    c.setActive(true);
                    System.out.println(c.getAssignments());
                    c.addStudent(s);
                    c.setActive(false);
                }
                data = br.readLine();
                if (data == null) {
                    students.add(s);
                    continue;
                }
                String[] a = data.split("/");
                if (a.length == 1) {
                    students.add(s);
                    continue;
                }
                for(int i = 0 ; i < a.length ; i = i + 3){
                    Assignment b = findAssignment(a[i] , a[i + 1]);
                    if (b != null) {
                        if (a[i + 2].equals("false")) {
                            b.setHasbeendone(false);
                        }else{
                            b.setHasbeendone(true);
                        }
                        s.addassignment(b);
                    }
                }
                students.add(s);
            }
            br.close();
            return students;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }
    public static Assignment findAssignment(String codecorse , String name) throws Exception{
        for(int i = 0 ; i < assignments.size() ; i++){
            if (assignments.get(i).getAssignmentName().equals(name) && 
            assignments.get(i).getCourseName().getCodecourse().equals(codecorse)) {
                return new Assignment(assignments.get(i).getCourseName(), assignments.get(i).getAssignmentName(), assignments.get(i).getDeadline()
                , assignments.get(i).getAssignmentinfo());
            }
        }
        return null;
        
    }

    public static List<Teacher> readteacher() throws Exception {
        try {
            File file = new File("C:\\Users\\USER\\Desktop\\cli\\cli\\src\\database\\" + "teacher.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String data = "";
            List<Teacher>teachers = new ArrayList<>();
            while (true) {
                data = br.readLine();
                if (data == null)
                    break;
                String[] teacher = data.split("/");
                Teacher t = new Teacher(teacher[0], teacher[1], teacher[2]);
                teachers.add(t);
            }
            br.close();
            return teachers;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static List<Course> readcourse() throws Exception {
        try {
            File file = new File("C:\\Users\\USER\\Desktop\\cli\\cli\\src\\database\\"+"course.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String data = "";
            List<Course> courses = new ArrayList<>();
            while (true) {
                data = br.readLine();
                if (data == null)
                    break;
                String[] course = data.split("/");
                Teacher teacher = Admin.findtaecherObj(course[1]);
                Course c = new Course(course[0], teacher, Integer.parseInt(course[2]), course[3], course[4]);
                c.setDayhour(course[5]);
                teacher.addCourse(c);
                courses.add(c);
            }
            br.close();
            return courses;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static List<Assignment> readassignment() throws Exception {
        try {
            File file = new File("C:\\Users\\USER\\Desktop\\cli\\cli\\src\\database\\"+"assignemnt.txt");
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            String data = "";
            List<Assignment>assignments = new ArrayList<>();
            while (true) {
                data = br.readLine();
                if (data == null)
                    break;
                String[] assignment = data.split("/");
                Course c = findcourse(assignment[0]);
                Assignment a = new Assignment(c,assignment[1], LocalDateTime.parse(assignment[2]) , assignment[3]);
                c.addassignment(a);
                assignments.add(a);
            }
            br.close();
            return assignments;
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

    public static void writestudent(List<Student>s) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\USER\\Desktop\\cli\\cli\\src\\database\\"+"student.txt"))) {
            List<Student> students = s;
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                List<Course> c = student.getEnrollmentCourses();
                List<Assignment> a = student.getAssignments();
                if (c == null) {
                    writer.write(student.getName() + "/" + student.getStudentId() + "/" + student.getBirth());
                }else {
                    writer.write(student.getName() + "/" + student.getStudentId() + "/" 
                    +student.getBirth() +"/"+student.getPassword() + "/");
                    for (int j = 0; j < c.size(); j++) {
                        if (j == c.size() - 1) {
                            writer.write(c.get(j).getCodecourse() + "/" + c.get(j).grade(student) + "/"); 
                            break;
                        }
                        writer.write(c.get(j).getCodecourse() + "/" + c.get(j).grade(student) + "/");
                    }
                    writer.newLine();
                    for(int j  = 0 ; j < a.size() ; j++){
                        writer.write(a.get(j).getCourseName().getCodecourse() + "/" + a.get(j).getAssignmentName() + "/" + a.get(j).getHasbeendone() + "/");
                    }
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void writeteacher(List<Teacher>t) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\USER\\Desktop\\cli\\cli\\src\\database\\"+"teacher.txt"))) {
            List<Teacher> teachers = t;
            for (int i = 0; i < teachers.size(); i++) {
                Teacher teacher = teachers.get(i);
                if (i == teachers.size() - 1) {
                    writer.write(teacher.getName() + "/" + teacher.getSurname() + "/" + teacher.getTeacherID());
                    break;
                }

                writer.write(teachers.get(i).getName() + "/" +
                        teachers.get(i).getSurname() + "/" + teachers.get(i).getTeacherID() + "\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void writeassignemnt(List<Assignment>a) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\USER\\Desktop\\cli\\cli\\src\\database\\"+"assignemnt.txt"))) {
            List<Assignment> assignments = a;
            for (int i = 0; i < assignments.size(); i++) {
                Assignment assignment = assignments.get(i);
                if (i == assignments.size() - 1) {
                    writer.write(assignment.getCourseName().getCodecourse() + "/" +
                            assignment.getAssignmentName() + "/" + assignment.getDeadline() + "/" + assignment.getAssignmentinfo());
                    break;
                }
                writer.write(assignment.getCourseName().getCodecourse() + "/" +
                        assignment.getAssignmentName() + "/" + assignment.getDeadline()  +"/"+ assignment.getAssignmentinfo()+ "\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void writecourse(List<Course>c) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\USER\\Desktop\\cli\\cli\\src\\database\\"+"course.txt"))) {
            List<Course> courses = c;
            for (int i = 0; i < courses.size(); i++) {
                Course course = courses.get(i);
                if (i == courses.size() - 1) {
                    writer.write(course.getCourseName() + "/" + course.getTeacher().getTeacherID() + "/"
                            + course.getNumberOfUnits() + "/" +
                            course.getExaminationDate() + "/" + course.getCodecourse() +
                            "/" + course.getDayhour());
                    break;
                }
                writer.write(course.getCourseName() + "/" + course.getTeacher().getTeacherID() + "/"
                        + course.getNumberOfUnits() + "/" +
                        course.getExaminationDate() + "/" + course.getCodecourse() +"/"+course.getDayhour()+ "\n");
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
