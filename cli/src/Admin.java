import java.util.ArrayList;
import java.util.List;

public class Admin {
    private static List<Student> students = new ArrayList<>();
    private static List<Teacher> teachers = new ArrayList<>();
    private static List<Course> courses = new ArrayList<>();
    private static List<Assignment> assignments = new ArrayList<>();
    private static List<String> exercise = new ArrayList<>();
    private static Admin instance;
    public void addstudenttolist(Student s)throws Exception{
        if (s == null) throw new NullPointerException();
        students.add(s);
    }
    public void addteachertolist(Teacher t)throws Exception{
        if (t == null) throw new NullPointerException();
        teachers.add(t);
    }
    public void addcoursetolist(Course c)throws Exception{
        if (c == null) throw new NullPointerException();
        courses.add(c);
    }
    public void addassignmenttolist(Assignment a)throws Exception{
        if (a == null) throw new NullPointerException();
        assignments.add(a);
    }

    public void removeStudentFromCourse(String studentId , String codecourse) throws Exception{
        if (studentId == null || codecourse == null) throw new NullPointerException();
        int studentnum = findstudent(studentId);
        int coursenum =findcourse(codecourse);
        if (studentnum != -1 && coursenum != -1) {
            courses.get(coursenum).removeStudent(students.get(studentnum));
            System.out.println("student has been removed");
            return;
        }else if(coursenum == -1){
            System.out.println("course doesnt exist");
        }else{
            System.out.println("student doesnt exist");
        }
    }
    public void removeTeacherfromcourse(String name, String surname , String codecourse)throws Exception{
        if (name == null || surname == null || codecourse == null)throw new NullPointerException();
        int teachernum = findteacher(name, surname);
        int coursenum = findcourse(codecourse);
        if (teachernum != -1 && coursenum != -1) {
            if (courses.get(coursenum).getTeacher().equals(teachers.get(teachernum))) {
                courses.get(coursenum).setTeacher(null);
                System.out.println("the teacher has been removed from course , please enter a new teacher");
                return;
            }else{
                System.out.println("this teacher is not responsible for this course");
            }
        }else if(coursenum == -1){
            System.out.println("course doesnt exist");
        }else{
            System.out.println("teacher doesnt exist");
        }
    }
    public void addStudenttocourse(String studentId , String codecourse) throws Exception{
        if (studentId == null || codecourse == null) throw new NullPointerException();
        int studentnum = findstudent(studentId);
        int coursenum =findcourse(codecourse);
        if (studentnum != -1 && coursenum != -1) {
            courses.get(coursenum).addStudent(students.get(studentnum));
            System.out.println("student has been added");
        }else if(coursenum == -1){
            System.out.println("course doesnt exist");
        }else{
            System.out.println("student doesnt exist");
        }

    }
    public void setTeachertocourse(String name, String surname , String codecourse){
        if (name == null || surname == null || codecourse == null)throw new NullPointerException();
        int teachernum = findteacher(name, surname);
        int coursenum = findcourse(codecourse);
        if (teachernum != -1 && coursenum != -1) {
            courses.get(coursenum).setTeacher(teachers.get(teachernum));
            System.out.println("teacher has been set as the newone");
            return;
        }else if(coursenum != -1){
            System.out.println("course doesnt exist");
        }else{
            System.out.println("teacher doesnt exist");
        }
    }
    public void addExercise(){

    }
    public void removeExercise(){

    }
    public void addAssignment(){

    }
    public void removeAssignment(){

    }
    public void removeCourse(String codecourse)throws Exception{
        if (codecourse == null)throw new NullPointerException();
        int coursenum = findcourse(codecourse);
        if (coursenum != -1) {
            courses.get(coursenum).getTeacher().removeCourse(courses.get(coursenum));
            List<Student> s = courses.get(coursenum).getStudents();
            for(int i = 0 ; i < s.size() ; i++){
                s.get(i).removeCourse(courses.get(coursenum));
            }
            courses.remove(coursenum);
            System.out.println("the course has been removed");
        }else{
            System.out.println("course doesnt exist");
        }
    }
    public void addCourse(String courseName, String name, String surname , 
    int numberOfUnits, String examinationDate , String codecourse)throws Exception{

        if (courseName == null || name == null || surname == null ||
        examinationDate == null || codecourse == null)throw new NullPointerException();

        int teachernum = findteacher(name, surname);

        if (numberOfUnits > 0 && teachernum != -1) {
            Course c = new Course(courseName, teachers.get(teachernum), numberOfUnits, examinationDate, codecourse);
            teachers.get(teachernum).addCourse(c);
            courses.add(c);
            System.out.println("the course has been added");
        }else if(numberOfUnits > 0 && teachernum == -1){
            Course c = new Course(courseName, teachers.get(teachernum), numberOfUnits, examinationDate, codecourse);
            courses.add(c);
            System.out.println("this course dosent have any teacher add one");
        }else if(numberOfUnits <= 0){
            throw new Exception("the course unit must be above 0");
        }
    }
    public int findstudent(String studentId){
        for(int i = 0 ; i < students.size() ; i++){
            if (studentId == students.get(i).getStudentId()) {
                return i;
            }
        }
        return -1;
    }
    public int findcourse(String codecourse){
        for(int i = 0 ; i < courses.size() ;i++){
            if (courses.get(i).getCodecourse().equals(codecourse)) {
                return i;
            }
        }
        return -1;
    }
    public int findteacher(String name, String surname){
        for(int i = 0 ;i < teachers.size() ; i++){
            if (teachers.get(i).getName().equals(name) && teachers.get(i).getSurname().equals(surname)) {
                return i;
            }
        }
        return -1;
    }
    public static Admin getAdmin(){
        if (instance == null) {
            instance = new Admin();
        }
        return instance;
    }
}
