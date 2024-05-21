import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {
    private String courseName;
    private Teacher teacher;
    private int numberOfUnits;
    private List<Student> students;
    private boolean isActive;
    private List<String> exercises;
    private int numberOfExercises;
    private String examinationDate;
    private boolean hasActiveProjects;
    private int numberOfRegisteredStudents;
    private Map<Student, Double> grades;

    public Course(String courseName, Teacher teacher, int numberOfUnits, String examinationDate) {
        this.courseName = courseName;
        this.teacher = teacher;
        this.numberOfUnits = numberOfUnits;
        this.students = new ArrayList<>();
        this.isActive = true;
        this.exercises = new ArrayList<>();
        this.numberOfExercises = 0;
        this.examinationDate = examinationDate;
        this.hasActiveProjects = true;
        this.numberOfRegisteredStudents = 0;
        this.grades = new HashMap<>();
    }

    public void addStudent(Student student) {
        students.add(student);
        numberOfRegisteredStudents++;
    }

    public void removeStudent(Student student) {
        students.remove(student);
        numberOfRegisteredStudents--;
        grades.remove(student);
    }

    public void assignGrade(Student student, double grade) {
        grades.put(student, grade);
    }

    public double findHighestGrade() {
        double highestGrade = 0.0;
        for (double grade : grades.values()) {
            if (grade > highestGrade) {
                highestGrade = grade;
            }
        }
        return highestGrade;
    }

    public void printStudents() {
        for (Student student : students) {
            System.out.println(student.getStudentId());
        }
    }

    public void addExercise(String exercise) {
        exercises.add(exercise);
        numberOfExercises++;
    }

    public void removeExercise(String exercise) {
        exercises.remove(exercise);
        numberOfExercises--;
    }

    public void activateCourse() {
        isActive = true;
    }

    public void deactivateCourse() {
        isActive = false;
    }

    public void activateProjects() {
        hasActiveProjects = true;
    }

    public void deactivateProjects() {
        hasActiveProjects = false;
    }

    public String getCourseName() {
        return courseName;
    }

    public Map<Student, Double> getGrades() {
        return grades;
    }
}
