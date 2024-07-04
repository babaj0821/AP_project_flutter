
//Hi all
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.*;
import main.*;
import database.databasehandler;

import main.Admin;
import main.Assignment;
import main.Course;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        databasehandler.readdata();
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("ٌWelcome\nso who are you? Admin/Teacher/exit");
        String identity = input.next();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        while (!identity.equals("exit")) {
            if (identity.equals("Admin")) {
                String command = "";
                while (!command.equals("14")) {
                    Admin admin = new Admin();
                    System.out.println(
                            "1.removeStudentFromCourse\n2.removeTeacherfromcourse\n3.addStudenttocourse\n4.setTeachertocourse");
                    System.out.println(
                            "5.addAssignmenttocourse\n6.removeAssignment\n7.removeCourse\n8.newCourse\n9.setcourseactive\n10.addStudent\n11.removeStudent\n12.addTeacher\n13.removeTeacher\n14.quit");
                    command = input.next();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    switch (command) {
                        case "1":
                            System.out.println("enter studentID:");
                            String studentID = input.next();
                            System.out.println("enter codeofcourse:");
                            String codeofcourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.removeStudentFromCourse(studentID, codeofcourse);
                            } catch (Exception e) {
                                System.out.println(e);
                                continue;
                            }
                            break;
                        case "2":
                            System.out.println("eneter teacher Id");
                            String teacherId = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.removeTeacherfromcourse(teacherId, codeofcourse);
                            } catch (Exception e) {
                                System.out.println(e);
                                continue;
                            }
                            break;
                        case "3":
                            System.out.println("enter studentID:");
                            studentID = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.addStudenttocourse(studentID, codeofcourse);
                            } catch (Exception e) {
                                System.out.println(e);
                                continue;
                            }
                            break;
                        case "4":
                            System.out.println("eneter teacher Id");
                            teacherId = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.setTeachertocourse(teacherId, codeofcourse);
                            } catch (Exception e) {
                                System.out.println(e);
                                continue;
                            }
                            break;
                        case "5":
                            System.out.println("enter codeofcourse:");
                            codeofcourse = input.next();
                            System.out.println("enter Assignmentt:");
                            String name_Assignmentt = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.addAssignmenttocourse(codeofcourse, name_Assignmentt);
                            } catch (Exception e) {
                                System.out.println(e);
                            }
                            break;
                        case "6":
                            System.out.println("enter codeofcourse:");
                            codeofcourse = input.next();
                            System.out.println("enter Assignmentt:");
                            name_Assignmentt = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.removeAssignment(codeofcourse, name_Assignmentt);
                            } catch (Exception e) {
                                System.out.println(e);
                                continue;
                            }
                            break;
                        case "7":
                            System.out.println("enter codeofcourse:");
                            codeofcourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.removeCourse(codeofcourse);
                            } catch (Exception e) {
                                System.out.println(e);
                                continue;
                            }
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
                            codeofcourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.newCourse(name_course, teacherId, unit, examinationDate, codeofcourse);
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "9":
                            System.out.println("enter codeofcourse:");
                            codeofcourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.setCourseactive(codeofcourse);
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "10":
                            System.out.println("enter studentId");
                            String id = input.next();
                            System.out.println("enter the name of student");
                            String names = input.next();
                            System.out.println("enter the password");
                            String pass = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.addNewStudent(id ,names , pass);
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "11":
                            System.out.println("enter studentId");
                            String id1 = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.removeStudent(id1);
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "12":
                            System.out.println("enter teacherId");
                            String id2 = input.next();
                            System.out.println("enter name");
                            String name = input.next();
                            System.out.println("enter surname");
                            String surname = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.addNewTeacher(name, surname, id2);
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "13":
                            System.out.println("enter teacherId");
                            String id3 = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                admin.removeTeacher(id3);
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        default:
                            break;
                    }
                }

            } else if (identity.equals("Teacher")) {
                String command = "";
                System.out.println("enter the teacher ID:");
                String teacherid = input.next();
                Teacher te = Admin.findtaecherObj(teacherid);
                if (te == null) {
                    System.out.println("the teacher id is wrong");
                    continue;
                }
                System.out.print("\033[H\033[2J");
                System.out.flush();
                while (!command.equals("9")) {
                    System.out.println(
                            "1.addStudentToCourse\n2.removeStudentFromCourse\n3.assignGradeToStudent\n4.addCourse\n5.removeCourse");
                    System.out.print("6.giveaddassignment\n7.removeassignment\n8.setthecourseActive\n9.quit");
                    command = input.next();
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                    switch (command) {
                        case "1":
                            System.out.println("enter the student ID:");
                            String studentid = input.next();
                            System.out.println("enter the codeof course:");
                            String codecourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                te.addStudentToCourse(Admin.getAdmin().findstudentObj(studentid),
                                        Admin.getAdmin().findcourseObj(codecourse).getCourseName());
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "2":
                            System.out.println("enter the student ID:");
                            studentid = input.next();
                            System.out.println("enter the codeof course:");
                            codecourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                te.removeStudentFromCourse(Admin.getAdmin().findstudentObj(studentid),
                                        Admin.getAdmin().findcourseObj(codecourse).getCourseName());
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "3":
                            System.out.println("enter the student ID:");
                            studentid = input.next();
                            System.out.println("enter the codeofcourse:");
                            codecourse = input.next();
                            System.out.println("enter the grade:");
                            double grade = input.nextDouble();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                te.assignGradeToStudent(Admin.getAdmin().findstudentObj(studentid),
                                        Admin.getAdmin().findcourseObj(codecourse).getCourseName(), grade);
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "4":
                            System.out.println("enter the codeofcourse:");
                            codecourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                te.addCourse(Admin.getAdmin().findcourseObj(codecourse));
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "5":
                            System.out.println("enter the codeofcourse:");
                            codecourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                te.removeCourse((Admin.getAdmin().findcourseObj(codecourse)));
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "6":
                            System.out.println("entet the codeofcourse:");
                            codecourse = input.next();
                            System.out.println("the name of the assignment:");
                            String nameofassignment = input.next();
                            System.out.println("enter the deadline:");
                            String time = input.next();
                            Assignment assignment = new Assignment(Admin.getAdmin().findcourseObj(codecourse),
                                    nameofassignment, LocalDateTime.parse(time));
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                te.giveaddassignment(Admin.getAdmin().findcourseObj(codecourse), assignment);
                                databasehandler.addassignment(assignment);
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "7":
                            System.out.println("entet the codeof course:");
                            codecourse = input.next();
                            System.out.println("the name of the assignment:");
                            nameofassignment = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                te.removeassignment(Admin.getAdmin().findcourseObj(codecourse),
                                        Admin.getAdmin().findAssignmentObj(nameofassignment));
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        case "8":
                            System.out.println("enter the codeofcourse:");
                            codecourse = input.next();
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            try {
                                te.setActive(Admin.getAdmin().findcourseObj(codecourse));
                            } catch (Exception e) {
                                System.out.println(e);
                                databasehandler.writedata();
                                continue;
                            }
                            break;
                        default:
                            break;
                    }
                }
            }
            System.out.println("so who are you? Admin/Teacher/exit");
            identity = input.next();
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
        System.out.println(Admin.getAssignments());
        databasehandler.writedata();
        System.out.println("goodbye!");
        input.close();
    }

}
