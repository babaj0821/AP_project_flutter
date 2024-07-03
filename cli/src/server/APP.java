package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APP {
    public static String input(DataInputStream input) {
        StringBuilder order = new StringBuilder();
        try {
            int character;
            while ((character = input.read()) != -1) {
                if (character == '\u0000') {
                    break;
                }
                order.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Received: " + order);
        return order.toString();
    }
    public static void output(DataOutputStream output , String code) {
        try {
            System.out.println(code);
            output.writeBytes(code); // Then send the actual message
            output.flush();
            System.out.println("Response sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean passwordchecker(String password, String username) {
        // Check if password contains the username
        Pattern pattern1 = Pattern.compile(username);
        Matcher matcher1 = pattern1.matcher(password);
        boolean cond1 = matcher1.find();
    
        // Check if password matches the regex for uppercase, lowercase, and minimum length
        Pattern pattern2 = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z]).{8,}$");
        Matcher matcher2 = pattern2.matcher(password);
        boolean cond2 = matcher2.find();
    
        // Return true if password does not contain username and matches the regex
        return !cond1 && cond2;
    }
    
    public static void main(String[] args) throws Exception{
        Scanner s = new Scanner(System.in);
        String i = s.nextLine();
        System.out.println(passwordchecker(i, "iman"));


    }
}
