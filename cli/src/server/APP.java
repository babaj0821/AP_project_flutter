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
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost" , 8888);
        BufferedWriter bf = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
        bf.write("login-402243039-13751383");
        bf.newLine();
        bf.flush();
        Thread.sleep(5000);
        bf.write("course-2");
        bf.newLine();
        bf.flush();
        Thread.sleep(5000);
        bf.write("exit");
        bf.newLine();
        bf.flush();


    }
}
