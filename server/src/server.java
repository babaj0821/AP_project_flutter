import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;

public class server {
    private ServerSocket server;
    server(ServerSocket server){
        this.server = server;
    }

    public void startserver(){
        try{
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("a new client has connected");
                ClientHandler clietnhadler = new ClientHandler(client);
                Thread thread = new Thread(clietnhadler);
                thread.start();
            }
        }catch(Exception e){
            closeserver();
        }
    }
    public void closeserver(){
        try{
            if (server != null) {
                server.close();
            }
        }catch(Exception e){
            e.getStackTrace();
        }
        
    }
    public static void main(String[] args) throws Exception{
        ServerSocket serversocket = new ServerSocket(8888);
        server server = new server(serversocket);
        server.startserver();
        
    }
}

