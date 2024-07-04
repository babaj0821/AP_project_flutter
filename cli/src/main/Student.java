package main;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Student {
    private String studentId;
    private List<Course> enrollmentCourses;
    List<Assignment>assignments;
    private int numberOfCourses;
    private int numberOfEnrollmentUnits;
    private double totalAverageGrade;
    private double currentSemesterAverage;
    private String password;
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Student(String studentId) throws Exception {
        this.studentId = studentId;
        this.enrollmentCourses = new ArrayList<>();
        assignments = new ArrayList<>();
        this.numberOfCourses = 0;
        this.numberOfEnrollmentUnits = 0;
        this.totalAverageGrade = 0.0;
        this.currentSemesterAverage = 0.0;
    }
    public void addassignment(Assignment a){
        assignments.add(a);
    }
    public List<Assignment> getAssignments() {
        return assignments;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void addCourse(Course courseName) throws Exception{
        if (courseName == null) throw new NullPointerException("this course is empty");

        if (enrollmentCourses.contains(courseName)) {
            System.out.println("you have already signed up in this course");
            return;
        }
        enrollmentCourses.add(courseName);
        numberOfCourses++;
    }

    public void removeCourse(Course courseName) throws Exception{
        if (courseName == null) throw new NullPointerException("this course is empty");

        if (enrollmentCourses.contains(courseName)) {
            enrollmentCourses.remove(courseName);
            numberOfCourses--;
        }else{
            System.out.println("you have not added this course to your courses");
        }
    }

    public void printEnrollmentCourses() {
        for (Course course : enrollmentCourses) {
            System.out.println(course.getCourseName());
        }
    }

    public void printTotalAverage() {
        System.out.println(totalAverageGrade);
    }

    public void printNumberOfEnrollmentUnits() {
        System.out.println(numberOfEnrollmentUnits);
    }

    public String getStudentId() {
        return studentId;
    }

    public double findTotalAvg() {
        double sum = 0.0;
        int len = 0;
        for (Course course : enrollmentCourses) {
            for (Map.Entry<Student, Double> entry : course.getGrades().entrySet()) {
                if (entry.getKey() == this) {
                    sum += entry.getValue();
                    len++;
                }
            }
        }
        if (len == 0)throw new ArithmeticException("you are not in any course");

        this.totalAverageGrade = len == 0 ? 0 : (double) sum / len;
        return totalAverageGrade;
    }
    public List<Course> getEnrollmentCourses() {
        return enrollmentCourses;
    }
    public void removeAssignment(String name){
        String[] name2 = name.split(":");
        for(int i = 0 ; i < assignments.size() ; i++){
            if (assignments.get(i).getAssignmentName().equals(name2[1]) &&
                assignments.get(i).getCourseName().getCourseName().equals(name2[0])) {
                    assignments.get(i).setHasbeendone(true);
                break;
            }
        }
    }
    public String bestgrade(){
        double max = 0.0;
        if (enrollmentCourses.size() == 0) {
            return "0.0";
        }
        String namecourse = "";
        for(int i = 0 ; i < enrollmentCourses.size() ; i++){
            double grade = enrollmentCourses.get(i).grade(this);
            if (max < grade) {
                max = grade;
                namecourse = enrollmentCourses.get(i).getCourseName();
            }
        }
        return namecourse+":"+ max;
    }
    public String worstgrade(){
        double min = 20;
        String namecourse = "";
        if (enrollmentCourses.size() == 0) {
            return "0.0";
        }
        for(int i = 0 ; i < enrollmentCourses.size() ; i++){
            double grade = enrollmentCourses.get(i).grade(this);
            if (min > grade) {
                min = grade;
                namecourse = enrollmentCourses.get(i).getCourseName();

            }
        }
        return namecourse +":"+ min;
    }
}
