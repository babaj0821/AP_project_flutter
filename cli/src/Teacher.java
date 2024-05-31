import java.nio.charset.CoderResult;
import java.util.ArrayList;
import java.util.List;

public class Teacher {
    private String name;
    private String teacherID;
    private String surname;
    private int numberOfCourses;
    private List<Course> courses;

    public Teacher(String name, String surname , String teacherID) throws Exception {
        this.name = name;
        this.surname = surname;
        this.teacherID = teacherID;
        this.numberOfCourses = 0;
        this.courses = new ArrayList<>();
        Admin a = Admin.getAdmin();
        a.addteachertolist(this);
    }
    public String getName() {
        return name;
    }
    public int getNumberOfCourses() {
        return numberOfCourses;
    }
    public String getSurname() {
        return surname;
    }
    public String getTeacherID() {
        return teacherID;
    }
    public List<Course> getCourses() {
        return courses;
    }

    public void addStudentToCourse(Student student, String courseName) throws Exception {
        Course course = findCourseByName(courseName);
        if (student == null) throw new NullPointerException("this student is wrong");
        if (course != null && !course.getStudents().contains(student)) {
            course.addStudent(student);
            student.addCourse(course);
            System.out.println("the student has been added");
            return;
        }else{
            System.out.println("can not remove course is wrong or student is already in this class");
            return;
        }
    }

    public void removeStudentFromCourse(Student student, String courseName) throws Exception {
        Course course = findCourseByName(courseName);
        if (course != null && course.getStudents().contains(student)) {
            course.removeStudent(student);
            student.removeCourse(course);
            System.out.println("has been removed");
            return;
        }else{
            System.out.println("can not remove");
            return;
        }
    }

    public void assignGradeToStudent(Student student, String courseName, double grade) throws Exception {
        Course course = findCourseByName(courseName);
        if(course == null)throw new NullPointerException("the course is wrong");
        if (student == null)throw new NullPointerException("the student is wrong");
        for(int i = 0 ; i < course.getStudents().size() ; i++){
            if (student.equals(course.getStudents().get(i))) {
                course.assignGrade(student, grade);
                System.out.println("the grade has been given");
                return;
            }
        }
        System.out.println("the studen name is wrong");
        return;
    }

    public void addCourse(Course course)throws Exception {
        if (course == null) throw new NullPointerException("please enter a course");
        for(int i = 0 ; i < courses.size() ; i++){
            if (course.equals(courses.get(i))) {
                System.out.println("this course already exist");
                return;
            }
        }
        courses.add(course);
        numberOfCourses++;
        System.out.println("the course has been added");
    }

    public void removeCourse(Course course)throws Exception {
        if (course == null) throw new NullPointerException("please enter a course");
        for(int i = 0 ; i < courses.size() ; i++){
            if (course.equals(courses.get(i))) {
                courses.remove(course);
                numberOfCourses--;
                System.out.println("has been removed");
                return;
            }
        }
        System.out.println("this course is not in your list");
        return;
    }

    private Course findCourseByName(String courseName) {
        for (Course course : courses) {
            if (course.getCourseName().equals(courseName)) {
                return course;
            }
        }
        return null;
    }
    public void giveaddassignment(Course course , Assignment assignment)throws Exception{
        if (course == null || assignment == null) throw new NullPointerException("please enter a course or assignment");

        if (courses.contains(course)) {
            course.addassignment(assignment);
            System.out.println("assignment has been given");
        }else{
            System.out.println("you can not add assignment to this course");
            return;
        }
    }
    public void removeassignment(Course course , Assignment assignment)throws Exception{
        if (course == null || assignment == null) throw new NullPointerException("please enter a course or assignment");
        
        if (courses.contains(course)) {
            course.removeAssignment(assignment);
            System.out.println("has been removed");
            return;
        }else{
            System.out.println("youu not able to access this course");
        }
    }
    public void setActive(Course course)throws Exception{
        if (course == null)throw new NullPointerException("the course is null");

        if (courses.contains(course)) {
            course.setActive(true);
            System.out.println("the course has been activated");
            return;
        }else{
            System.out.println("this course is not yours");
            return;
        }
    }
    
}
