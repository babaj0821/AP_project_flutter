import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable{
    private static List<ClientHandler> clients = new ArrayList<>();
    private Socket Socket;
    private BufferedReader input;
    private BufferedWriter output;
    private String clientusername;
    public String getClientusername() {
        return clientusername;
    }
    public BufferedReader getInput() {
        return input;
    }
    public BufferedWriter getOutput() {
        return output;
    }
    ClientHandler(Socket socket){
        try{
            this.Socket = socket;
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            clientusername = input.readLine();
            broadcastmassege("Server: " + clientusername + " has enterd the caht!");
            clients.add(this);
        }catch(Exception e){
            closeeveything(socket, input, output);
        
        }
    }
    @Override
    public void run() {
        String messageclient;
        while (Socket.isConnected()) {
            try{
                messageclient = input.readLine();
                broadcastmassege(messageclient);
            }catch(Exception e){
                closeeveything(Socket, input, output);
                break;
            }
        }

    }
    public void broadcastmassege(String message){
        for(ClientHandler clientHandler : clients){
            try {
                if (!clientHandler.getClientusername().equals(clientusername)) {
                    clientHandler.getOutput().write(message);
                    clientHandler.getOutput().newLine();
                    clientHandler.getOutput().flush();
                }
            } catch (Exception e) {
                closeeveything(Socket, input, output);
            }
        }
    }
    public void closeeveything(Socket socket , BufferedReader input , BufferedWriter output){
        removeclienthandler();
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
    public void removeclienthandler(){
        clients.remove(this);
        broadcastmassege("server : " + clientusername + " has left the chat");
    }
}
