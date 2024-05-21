import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) throws Exception {
        Teacher teacher1 = new Teacher("Hamidreza", "Mahdiyani");
        Teacher teacher2 = new Teacher("Mojtaba", "Vahidi");
        Course course1 = new Course("logic circuits", teacher1, 3, "2024/09/12");
        Course course2 = new Course("AP", teacher2, 3, "2024/09/15");
        Student student1 = new Student("S001");
        Student student2 = new Student("S002");
        student1.addCourse(course1);
        student1.addCourse(course2);
        student2.addCourse(course1);
        student2.addCourse(course2);
        System.out.println("Enrollment courses of student1:");
        student1.printEnrollmentCourses();
        System.out.println("Enrollment courses of student2:");
        student2.printEnrollmentCourses();
        course1.addStudent(student1);
        course1.addStudent(student2);
        course2.addStudent(student1);
        course2.addStudent(student2);
        course1.addExercise("problemser#1");
        course2.addExercise("problemser#1");
        teacher1.addCourse(course1);
        teacher2.addCourse(course2);
        teacher1.assignGradeToStudent(student1, "logic circuits", 12);
        teacher1.assignGradeToStudent(student2, "logic circuits", 10);
        teacher2.assignGradeToStudent(student1, "AP", 19);
        teacher2.assignGradeToStudent(student2, "AP", 14);
        System.out.println("highest grade in " + course1.getCourseName());
        System.out.println(course1.findHighestGrade());
        System.out.println("highest grade in " + course2.getCourseName());
        System.out.println(course2.findHighestGrade());
        student1.findTotalAvg();
        student2.findTotalAvg();
        System.out.println("student 1 avg grade: ");
        student1.printTotalAverage();
        System.out.println("student 2 avg grade: ");
        student2.printTotalAverage();
        LocalDateTime deadline = LocalDateTime.of(2025, 4, 30, 23, 59);
        Assignment assignment = new Assignment(course2, "java project", deadline);
        System.out.println("Time left until deadline: " + assignment.timeLeftUntilDeadline());
    }
}