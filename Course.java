import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {
    private String courseName;
    private Teacher teacher;
    private int numberOfUnits;
    private List<Student> students;
    private boolean isActive = false;
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
        this.exercises = new ArrayList<>();
        this.numberOfExercises = 0;
        this.examinationDate = examinationDate;
        this.hasActiveProjects = true;
        this.numberOfRegisteredStudents = 0;
        this.grades = new HashMap<>();
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void addStudent(Student student) throws Exception{
        if (student == null)throw new NullPointerException("you have not enterd any student");
        if (isActive == false) throw new Exception("please activate the course");

        if (students.contains(student)) {
            System.out.println("this student has been sighend up");
            return;
        }
        students.add(student);
        numberOfRegisteredStudents++;
    }

    public void removeStudent(Student student) throws Exception{
        if (student == null)throw new NullPointerException("you have not enterd any student");
        if (students.contains(student)) {
            students.remove(student);
            numberOfRegisteredStudents--;
            grades.remove(student);
        }else{
            System.out.println("this student is not in this course");
        }
    }

    public void assignGrade(Student student, double grade)throws Exception {
        if (student == null)throw new NullPointerException("you have not eneter any student");
        grades.put(student, grade);
    }

    public double findHighestGrade() {
        if (students.size() > 0) {
            double highestGrade = 0.0;
            for (double grade : grades.values()) {
                if (grade > highestGrade) {
                    highestGrade = grade;
                }
            }
            return highestGrade;
        }else{
            System.out.println("this class is empty");
            return 0.0;
        }
    }

    public void printStudents() {
        for (Student student : students) {
            System.out.println(student.getStudentId());
        }
    }

    public void addExercise(String exercise)throws Exception {
        if (exercise == null)throw new NullPointerException("this exercise is empty");

        exercises.add(exercise);
        numberOfExercises++;
    }

    public void removeExercise(String exercise)throws Exception {
        if (exercise == null)throw new NullPointerException("this exercise is empty");
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
