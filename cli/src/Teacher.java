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

    public void addStudentToCourse(Student student, String courseName) throws Exception {
        Course course = findCourseByName(courseName);
        course.addStudent(student);
    }

    public void removeStudentFromCourse(Student student, String courseName) throws Exception {
        Course course = findCourseByName(courseName);
        course.removeStudent(student);
    }

    public void assignGradeToStudent(Student student, String courseName, double grade) throws Exception {
        Course course = findCourseByName(courseName);
        course.assignGrade(student, grade);
    }

    public void addCourse(Course course)throws Exception {
        if (course == null) throw new NullPointerException("please enter a course");
        courses.add(course);
        numberOfCourses++;
    }

    public void removeCourse(Course course)throws Exception {
        if (course == null) throw new NullPointerException("please enter a course");
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
    public void addassignment(Course course , Assignment assignment)throws Exception{
        if (course == null || assignment == null) throw new NullPointerException("please enter a course or assignment");

        if (courses.contains(course)) {
            course.addassignment(assignment);
        }else{
            System.out.println("you can not add assignment to this course");
            return;
        }
    }
    public void removeassignment(Course course , Assignment assignment)throws Exception{
        if (course == null || assignment == null) throw new NullPointerException("please enter a course or assignment");
        
        if (courses.contains(course)) {
            course.removeAssignment(assignment);
        }else{
            System.out.println("youu not able to access this course");
        }
    }
}
