import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
    private Socket Socket;
    private BufferedReader input;
    private BufferedWriter output;
    private String username;
    client(Socket socket , String username){
        try{
            this.Socket = socket;
            this.username = username;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }catch(Exception e){
            System.out.println(e);
            closeeveything(socket, input, output);
        }
    }
    public void sendmessage(){
        try{
            output.write(username);
            output.newLine();
            output.flush();

            Scanner scanner = new Scanner(System.in);
            while(Socket.isConnected()){
                String message = scanner.nextLine();
                output.write(username + " : " + message);
                output.newLine();
                output.flush();


            }
        }catch(Exception e){
            closeeveything(Socket, input, output);
        }
    }
    public void listenmessage(){
        new Thread(new Runnable(){
            @Override
            public void run(){
                String messagegroup;
                while (Socket.isConnected()) {
                    try {
                        messagegroup = input.readLine();
                        System.out.println(messagegroup);
                    } catch (Exception e) {
                        closeeveything(Socket, input, output);
                    }
                }
            }
        }).start();
    }
    public void closeeveything(Socket socket , BufferedReader input , BufferedWriter output){
        try {
            if (input != null) {
                input.close();
            }
            if(output != null){
                output.close();
            } 
            if(socket != null){
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter your username for chat");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost" , 8888);
        client client = new client(socket, username);
        client.listenmessage();
        client.sendmessage();

    }
}
