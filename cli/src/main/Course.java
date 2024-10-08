package main;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import server.server;

public class Course {
    private String courseName;
    private String codecourse;
    private Teacher teacher;
    private int numberOfUnits;
    private List<Student> students;
    private boolean isActive = false;
    private List<String> exercises;
    private List<Assignment> assignments = new ArrayList<>();
    private int numberOfExercises;
    private String examinationDate;
    private boolean hasActiveProjects;
    private int numberOfRegisteredStudents;
    private Map<Student, Double> grades;
    private String dayhour;

    public Course(String courseName, Teacher teacher, int numberOfUnits, String examinationDate, String codecourse) {
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
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Teacher getTeacher() {
        return teacher;
    }
    public String getDayhour() {
        return dayhour;
    }
    public void setDayhour(String dayhour) {
        this.dayhour = dayhour;
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

    public void addStudent(Student student) throws Exception {
        if (student == null)
            throw new NullPointerException("you have not enterd any student");
        if (isActive == false)
            throw new Exception("please activate the course");

        if (students.contains(student)) {
            System.out.println("this student has been sighend up");
            return;
        }
        students.add(student);
        numberOfRegisteredStudents++;
    }

    public void removeStudent(Student student) throws Exception {
        if (student == null)
            throw new NullPointerException("you have not enterd any student");
        if (students.contains(student)) {
            students.remove(student);
            numberOfRegisteredStudents--;
            grades.remove(student);
        } else {
            System.out.println("this student is not in this course");
        }
    }

    public void assignGrade(Student student, double grade) throws Exception {
        if (student == null)
            throw new NullPointerException("you have not eneter any student");
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
        } else {
            System.out.println("this class is empty");
            return 0.0;
        }
    }
    public  <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null; // Value not found
    }
    public String findTopStudent(){
        String id = getKeyByValue(grades, this.findHighestGrade()).getStudentId();
        return id;
    }

    public void printStudents() {
        for (Student student : students) {
            System.out.println(student.getStudentId());
        }
    }

    public void addExercise(String exercise) throws Exception {
        if (exercise == null)
            throw new NullPointerException("this exercise is empty");

        exercises.add(exercise);
        numberOfExercises++;
    }

    public void addassignment(Assignment assignment) throws Exception {
        if (assignment == null)
            throw new NullPointerException("the assignment is wrong");
        assignments.add(assignment);
    }

    public void removeAssignment(Assignment assignment) throws Exception {
        if (assignment == null)
            throw new NullPointerException("the assignment is wrong");
        if (assignments.contains(assignment)) {
            assignments.remove(assignment);
        } else {
            System.out.println("there is no such Assignment in this course");
        }
    }

    public void removeExercise(String exercise) throws Exception {
        if (exercise == null)
            throw new NullPointerException("this exercise is empty");
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

    public Double grade(Student s) {
        return grades.get(s);
    }

    public int getNumberOfUnits() {
        return numberOfUnits;
    }

    public String getExaminationDate() {
        return examinationDate;
    }

}
