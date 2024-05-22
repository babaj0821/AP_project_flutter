import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) throws Exception{
        Scanner input = new Scanner(System.in);

        System.out.println("ٌWelcome\nso who are you? Admin/Teacher/exit");
        String identity = input.next();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        while (!identity.equals("exit")) {
            if (identity.equals("Admin")) {
                String command = "";
                while (!command.equals("9")) {
                    Admin admin = new Admin();
                    System.out.println("1.removeStudentFromCourse\n2.removeTeacherfromcourse\n3.addStudenttocourse\n4.setTeachertocourse");
                    System.out.println("5.addAssignmenttocourse\n6.removeAssignment\n7.removeCourse\n8.newCourse\n9.quit");
                    command = input.next();
                    System.out.flush();
                    switch (command) {
                        case "1":
                            System.out.println("enter studentID:");
                            String studentID = input.next();
                            System.out.println("enter codeofcourse:");
                            String codeofcourse =  input.next();
                            admin.removeStudentFromCourse(studentID, codeofcourse);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;
                        case "2":
                            System.out.println("enter name:");
                            String name = input.next();
                            System.out.println("enter surname:");
                            String surname = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            admin.removeTeacherfromcourse(name , surname ,codeofcourse);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;
                        case "3":
                            System.out.println("enter studentID:");
                            studentID = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            admin.addStudenttocourse(studentID, codeofcourse);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;
                        case "4":
                            System.out.println("enter name:");
                            name = input.next();
                            System.out.println("enter surname:");
                            surname = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            admin.setTeachertocourse(name , surname ,codeofcourse);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;
                        case "5":
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.println("enter Assignmentt:");
                            String name_Assignmentt = input.next();
                            admin.addAssignmenttocourse(codeofcourse, name_Assignmentt);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;
                        case "6":
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            System.out.println("enter Assignmentt:");
                            name_Assignmentt = input.next();
                            admin.removeAssignment(codeofcourse, name_Assignmentt);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;
                        case "7":
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            admin.removeCourse(codeofcourse);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;
                        case "8":
                            System.out.println("enter the units:");
                            int unit = input.nextInt();
                            System.out.println("enter the name of course:");
                            String name_course = input.next();
                            System.out.println("enter of teacher name:");
                            name = input.next();
                            System.out.println("enter of teacher surname:");
                            surname = input.next();
                            System.out.println("enter the examinationDate:");
                            String examinationDate = input.next();
                            System.out.println("enter codeofcourse:");
                            codeofcourse =  input.next();
                            admin.newCourse(name_course,name , surname, unit, examinationDate,codeofcourse);
                            System.out.print("\033[H\033[2J");
                            System.out.flush();
                            break;
                        default:
                            break;
                    }
                }
                
            }else{

            }
            System.out.println("so who are you? Admin/Teacher/exit");
            identity = input.next();
        }


    }

    }
