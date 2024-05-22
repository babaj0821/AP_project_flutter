import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Course {
    private String courseName;
    private String codecourse;
    private Teacher teacher;
    private int numberOfUnits;
    private List<Student> students;
    private boolean isActive = false;
    private List<String> exercises;
    private List<Assignment> assignments;
    private int numberOfExercises;
    private String examinationDate;
    private boolean hasActiveProjects;
    private int numberOfRegisteredStudents;
    private Map<Student, Double> grades;

    public Course(String courseName, Teacher teacher, int numberOfUnits, String examinationDate , String codecourse) throws Exception {
        this.courseName = courseName;
        this.teacher = teacher;
        this.numberOfUnits = numberOfUnits;
        this.codecourse = codecourse;
        this.students = new ArrayList<>();
        this.exercises = new ArrayList<>();
        this.numberOfExercises = 0;
        this.examinationDate = examinationDate;
        this.hasActiveProjects = true;
        this.numberOfRegisteredStudents = 0;
        this.grades = new HashMap<>();
        Admin a = Admin.getAdmin();
        a.addcoursetolist(this);
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public Teacher getTeacher() {
        return teacher;
    }
    public List<Student> getStudents() {
        return students;
    } 
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    public String getCodecourse() {
        return codecourse;
    }
    public List<Assignment> getAssignments() {
        return assignments;
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

    public void addassignment(Assignment assignment){
        assignments.add(assignment);
    }
    public void removeAssignment(Assignment assignment){
        if (assignments.contains(assignment)) {
            assignments.remove(assignment);
        }else{
            System.out.println("there is no such Assignment in this course");
        }
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
