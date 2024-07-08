package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.nio.Buffer;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class APP {
    public static void main(String[] args) throws Exception{
        Socket s = new Socket("localhost" , 8888);
        client d= new client(s);
        d.start();
    }
}

class client extends Thread{
    private BufferedWriter write;
    private BufferedReader read;
    private Socket s;
    client(Socket s){
        this.s = s;
        try {
            write = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            read = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    @Override
    public void run(){
        Scanner in  = new Scanner(System.in);
        String order = "";
        while (!order.equals("exit")) {
            order = "402243127-login-402243127-Iman1234";
                try {
                    write.write(order);
                    write.newLine();
                    write.flush();
                    System.out.println(read.readLine());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                System.out.println(read.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }


    }
