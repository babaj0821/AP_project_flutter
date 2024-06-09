package server;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.net.*;
import java.util.*;
import main.*;

public class server {
    private ServerSocket server;

    public server(ServerSocket server) {
        this.server = server;
    }
    public void startServer() {
        try {
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("A new student has connected");
                ClientHandler clientHandler = new ClientHandler(client);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (Exception e) {
            closeServer();
        }
    }

    public void closeServer() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8888);
        List<Student>s = Admin.getStudents();
        System.out.println(s);
        server server = new server(serverSocket);
        server.startServer();
    }
}

class ClientHandler implements Runnable {
    private static List<Student> students = Admin.getStudents(); // Assuming Admin.getStudents() returns a list of students
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
            System.out.println("Server: connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int checkPasswordAndUsername(String password, String username) throws Exception {
        List<Student>s = Admin.getStudents();
        for (int i = 0 ; i < s.size() ; i++) {
            if (password.equals(s.get(i).getPassword()) && username.equals(s.get(i).getStudentId())) {
                return 1; // Successful login
            } else if (password.equals(s.get(i).getPassword())) {
                return 2; // Password correct, username incorrect
            } else if (username.equals(s.get(i).getStudentId())) {
                return 3; // Username correct, password incorrect
            }
        }
        return 4;
    }

    @Override
    public void run() {
        String order = "";
            try {
                System.out.println("waiting");
                order = input();
                System.out.println(students);
                System.out.println(Admin.getAdmin());
                System.out.println("get");
                String[] data = order.split("-");
                if (data[0].equals("s")) {
                    Student s = new Student(data[1]);
                    s.setPassword(data[2]);
                    output("1"); // Assuming 's' is for some other command
                } else if (data[0].equals("l")) {
                    int code = checkPasswordAndUsername(data[1], data[2]);
                    output(String.valueOf(code));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        closeConnection();
    }

    public String input() {
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

    public void output(String code) {
        try {
            System.out.println(code);
            output.writeBytes(code); // Then send the actual message
            output.flush();
            System.out.println("Response sent");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void closeConnection() {
        try {
            if (input != null) input.close();
            if (output != null) output.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
