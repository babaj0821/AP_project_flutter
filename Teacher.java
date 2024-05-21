import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private String name;
    private String surname;
    private int numberOfCourses;
    private List<Course> courses;

    public Teacher(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.numberOfCourses = 0;
        this.courses = new ArrayList<>();
    }

    public void addStudentToCourse(Student student, String courseName) {
        Course course = findCourseByName(courseName);
        if (course != null) {
            course.addStudent(student);
        } else
            System.out.println("course not available");
    }

    public void removeStudentFromCourse(Student student, String courseName) {
        Course course = findCourseByName(courseName);
        if (course != null) {
            course.removeStudent(student);
        } else
            System.out.println("course not available");
    }

    public void assignGradeToStudent(Student student, String courseName, double grade) {
        Course course = findCourseByName(courseName);
        if (course != null) {
            course.assignGrade(student, grade);
        } else
            System.out.println("course not available");
    }

    public void addCourse(Course course) {
        courses.add(course);
        numberOfCourses++;
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        numberOfCourses--;
    }

    private Course findCourseByName(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }
}
