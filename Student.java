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

    public Student(String studentId) {
        this.studentId = studentId;
        this.enrollmentCourses = new ArrayList<>();
        this.numberOfCourses = 0;
        this.numberOfEnrollmentUnits = 0;
        this.totalAverageGrade = 0.0;
        this.currentSemesterAverage = 0.0;
    }

    public void addCourse(Course courseName) {
        enrollmentCourses.add(courseName);
        numberOfCourses++;
    }

    public void removeCourse(Course courseName) {
        enrollmentCourses.remove(courseName);
        numberOfCourses--;
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
        this.totalAverageGrade = len == 0 ? 0 : (double) sum / len;
    }
}
