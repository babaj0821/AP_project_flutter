import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);
        Teacher t = new Teacher("ali", "ahmady", "40");
        Teacher t1 = new Teacher("ali", "baba", "30");

        Course c = new Course("ap", t, 3, "20tir", "2");
        Course c1 = new Course("app", t1, 4, "20tir", "1");
        
        Student s = new Student("402243039");
        Student s2 = new Student("402243036");
        Student s3 = new Student("402243037");


        System.out.println("ٌWelcome\nso who are you? Admin/Teacher/exit");
        String identity = input.next();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        while (!identity.equals("exit")) {
            if (identity.equals("Admin")) {
                String command = "";
                while (!command.equals("10")) {
                    Admin admin = new Admin();
                    System.out.println("1.removeStudentFromCourse\n2.removeTeacherfromcourse\n3.addStudenttocourse\n4.setTeachertocourse");
                    System.out.println("5.addAssignmenttocourse\n6.removeAssignment\n7.removeCourse\n8.newCourse\n9.setcourseactive\n10.quit");
                    command = input.next();
                    System.out.flush();
                    System.out.print("\033[H\033[2J");
                    switch (command) {
                        case "1":
                            System.out.println("enter studentID:");
                            String studentID = input.next();
                            System.out.println("enter codeofcourse:");
                            String codeofcourse =  input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.removeStudentFromCourse(studentID, codeofcourse);
                            break;
                        case "2":
                            System.out.println("eneter teacher Id");
                            String teacherId = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.removeTeacherfromcourse(teacherId,codeofcourse);
                            break;
                        case "3":
                            System.out.println("enter studentID:");
                            studentID = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.addStudenttocourse(studentID, codeofcourse);
                            break;
                        case "4":
                            System.out.println("eneter teacher Id");
                            teacherId = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.setTeachertocourse(teacherId ,codeofcourse);
                            break;
                        case "5":
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.println("enter Assignmentt:");
                            String name_Assignmentt = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.addAssignmenttocourse(codeofcourse, name_Assignmentt);
                            break;
                        case "6":
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.println("enter Assignmentt:");
                            name_Assignmentt = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.removeAssignment(codeofcourse, name_Assignmentt);
                            break;
                        case "7":
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.removeCourse(codeofcourse);
                            break;
                        case "8":
                            System.out.println("enter the units:");
                            int unit = input.nextInt();
                            System.out.println("enter the name of course:");
                            String name_course = input.next();
                            System.out.println("eneter teacher Id");
                            teacherId = input.next();
                            System.out.println("enter the examinationDate:");
                            String examinationDate = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.newCourse(name_course,teacherId, unit, examinationDate,codeofcourse);
                            break;
                        case "9":
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            admin.setCourseactive(codeofcourse);
                        default:
                            break;
                    }
                }
                
            }else if(identity.equals("Teacher")){
                String command = "";
                String teacherid = input.next();
                Teacher te = Admin.findtaecher(teacherid);
                while (!command.equals(identity)) {
                    System.out.println("1.addStudentToCourse\n2.removeStudentFromCourse\n3.assignGradeToStudent\n4.addCourse\n5.removeCourse");
                    System.out.print("6.findCourseByName\n7.giveaddassignment\n8.removeassignment\n9.setthecourseActive\n10.quit");

                    switch (command) {
                        case "1":
                            break;
                        case "2":
                            break;
                        case "3":
                            break;
                        case "4":
                            break;
                        case "5":
                            break;
                        case "6":
                            break;
                        case "7":
                            break;
                        case "8":
                            break;
                        case "9":
                            break;
                        default:
                            break;
                    }
                }
            }
            System.out.println("so who are you? Admin/Teacher/exit");
            identity = input.next();
        }
        input.close();
    }

    }
