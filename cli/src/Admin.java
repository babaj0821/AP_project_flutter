import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    private static List<Student> students = new ArrayList<>();
    private static List<Teacher> teachers = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<Assignment> assignments = new ArrayList<>();
    private static Admin instance;

    public void addstudenttolist(Student s) throws Exception {
        if (s == null)
            throw new NullPointerException();
        students.add(s);
    }

    public void addteachertolist(Teacher t) throws Exception {
        if (t == null)
            throw new NullPointerException();
        teachers.add(t);
    }

    public void addcoursetolist(Course c) throws Exception {
        if (c == null)
            throw new NullPointerException();
        courses.add(c);
    }

    public void addassignmenttolist(Assignment a) throws Exception {
        if (a == null)
            throw new NullPointerException();
        assignments.add(a);
    }

    public void removeStudentFromCourse(String studentId, String codecourse) throws Exception {
        Student student = findstudentObj(studentId);
        Course course = findcourseObj(codecourse);
        if (student != null && course != null) {
            if (course.getStudents().contains(student)) {
                course.removeStudent(student);
                student.removeCourse(course);
                System.out.println("student has been removed");
                return;
            } else {
                System.out.println("this student is not in this class");
                return;
            }
        } else if (course == null) {
            System.out.println("course doesnt exist");
            return;
        } else {
            System.out.println("student doesnt exist");
            return;
        }
    }

    public void removeTeacherfromcourse(String teacherid, String codecourse) throws Exception {
        Teacher teacher = findtaecherObj(teacherid);
        Course course = findcourseObj(codecourse);
        if (teacher != null && course != null) {
            if (course.getTeacher().equals(teacher)) {
                course.setTeacher(null);
                teacher.removeCourse(course);
                System.out.println("the teacher has been removed from course , please enter a new teacher");
                return;
            } else {
                System.out.println("this teacher is not responsible for this course");
                return;
            }
        } else if (course == null) {
            System.out.println("course doesnt exist");
            return;
        } else {
            System.out.println("teacher doesnt exist");
            return;
        }
    }

    public void addStudenttocourse(String studentId, String codecourse) throws Exception {
        Student student = findstudentObj(studentId);
        Course course = findcourseObj(codecourse);
        if (student != null && course != null) {
            if (course.getStudents().contains(student)) {
                System.out.println("this student is already in this course");
                return;
            } else {
                course.addStudent(student);
                student.addCourse(course);
                System.out.println("student has been added");
                return;
            }
        } else if (course == null) {
            System.out.println("course doesnt exist");
            return;
        } else {
            System.out.println("student doesnt exist");
            return;
        }

    }

    public void setTeachertocourse(String teacherID, String codecourse) throws Exception {
        Teacher teacher = findtaecherObj(teacherID);
        Course course = findcourseObj(codecourse);
        if (teacher != null && course != null) {
            if (course.getTeacher() == null) {
                course.setTeacher(teacher);
                teacher.addCourse(course);
                System.out.println("teacher has been set as the newone");
                return;
            }
            if (course.getTeacher().equals(teacher)) {
                System.out.println("this teacher is already the teacher of the course");
            }
        } else if (course != null) {
            System.out.println("course doesnt exist");
            return;
        } else {
            System.out.println("teacher doesnt exist");
            return;
        }
    }

    public void addAssignmenttocourse(String coursecode, String assignmentName) throws Exception {
        if (coursecode == null || assignmentName == null)
            throw new NullPointerException();

        Assignment assignment = findAssignmentObj(assignmentName);
        Course course = findcourseObj(coursecode);

        if (assignment != null && course != null) {
            if (course.getAssignments().contains(assignment)) {
                System.out.println("this assignment is already in this course");
                return;
            } else {
                course.addassignment(assignment);
                System.out.println("the assingment has been given");
                return;
            }
        } else if (course == null) {
            System.out.println("course doesnt exist");
            return;
        } else {
            System.out.println("assingment doesnt exist");
            return;
        }
    }

    public void removeAssignment(String coursecode, String assignmentName) {

        Assignment assignment = findAssignmentObj(assignmentName);
        Course course = findcourseObj(coursecode);

        if (assignment != null && course != null) {
            if (assignment.getCourseName().equals(course)) {
                course.getAssignments().remove(assignment);
            } else {
                System.out.println("this assignment is not related to this course");
            }
        } else if (course == null) {
            System.out.println("course doesnt exist");
            return;
        } else {
            System.out.println("assingment doesnt exist");
            return;
        }

    }

    public void removeCourse(String codecourse) throws Exception {
        Course course = findcourseObj(codecourse);
        if (course != null) {
            course.getTeacher().removeCourse(course);
            List<Student> s = course.getStudents();
            for (int i = 0; i < s.size(); i++) {
                s.get(i).removeCourse(course);
            }
            courses.remove(course);
            System.out.println("the course has been removed");
        } else {
            System.out.println("course doesnt exist");
        }
    }

    public void addNewTeacher() {

    }

    public void removeTeacher() {

    }

    public void addNewStudent(String id) {
        try {
            Student student = new Student(id);
            students.add(student);

        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void removeStudent(String id) {
        // first removes student from course then from admin list
        try {
            for (int i = 0; i < courses.size(); i++) {
                List<Student> studentsOfCurrentCourse = courses.get(i).getStudents();
                for (int j = 0; j < studentsOfCurrentCourse.size(); j++) {
                    if (studentsOfCurrentCourse.get(j).getStudentId().equals(id)) {
                        removeStudentFromCourse(id, courses.get(i).getCodecourse());
                        break;
                    }
                }
            }
            Student student = findstudentObj(id);
            students.remove(student);

        } catch (Exception e) {
            e.getMessage();
        }

    }

    public void newCourse(String courseName, String teacherId,
            int numberOfUnits, String examinationDate, String codecourse) throws Exception {

        Teacher teacher = findtaecherObj(teacherId);

        if (numberOfUnits > 0 && teacher != null) {
            Course c = new Course(courseName, teacher, numberOfUnits, examinationDate, codecourse);
            teacher.addCourse(c);
            System.out.println("the course has been added");
        } else if (numberOfUnits > 0 && teacher == null) {
            Course c = new Course(courseName, null, numberOfUnits, examinationDate, codecourse);
            System.out.println("this course dosent have any teacher add one to be shown to students");
        } else if (numberOfUnits <= 0) {
            throw new Exception("the course unit must be above 0");
        }
    }

    public Student findstudentObj(String studentId) {
        for (int i = 0; i < students.size(); i++) {
            if (studentId.equals(students.get(i).getStudentId())) {
                return students.get(i);
            }
        }
        return null;
    }

    public Course findcourseObj(String codecourse) {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCodecourse().equals(codecourse)) {
                return courses.get(i);
            }
        }
        return null;
    }

    public static Teacher findtaecherObj(String teacheId) {
        for (int i = 0; i < teachers.size(); i++) {
            if (teachers.get(i).getTeacherID().equals(teacheId)) {
                return teachers.get(i);
            }
        }
        return null;
    }

    public Assignment findAssignmentObj(String assignmentName) {
        for (int i = 0; i < assignments.size(); i++) {
            if (assignments.get(i).getAssignmentName().equals(assignmentName)) {
                return assignments.get(i);
            }
        }
        return null;

    }

    public void setCourseactive(String codeofcourse) throws Exception {
        if (codeofcourse == null)
            throw new NullPointerException();
        Course course = findcourseObj(codeofcourse);
        course.setActive(true);
        return;
    }

    public static Admin getAdmin() {
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }

    public static List<Assignment> getAssignments() {
        return assignments;
    }

    public static List<Course> getCourses() {
        return courses;
    }

    public static List<Student> getStudents() {
        return students;
    }

    public static List<Teacher> getTeachers() {
        return teachers;
    }
}
