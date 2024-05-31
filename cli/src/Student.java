import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class Student {
    private String studentId;
    private List<Course> enrollmentCourses;
    private int numberOfCourses;
    private int numberOfEnrollmentUnits;
    private double totalAverageGrade;
    private double currentSemesterAverage;
    private String password;
    private String username;

    public Student(String studentId) throws Exception {
        this.studentId = studentId;
        this.enrollmentCourses = new ArrayList<>();
        this.numberOfCourses = 0;
        this.numberOfEnrollmentUnits = 0;
        this.totalAverageGrade = 0.0;
        this.currentSemesterAverage = 0.0;
        Admin a = Admin.getAdmin();
        a.addstudenttolist(this);
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

    public void findTotalAvg() {
        int sum = 0;
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
    }
    public List<Course> getEnrollmentCourses() {
        return enrollmentCourses;
    }
}
