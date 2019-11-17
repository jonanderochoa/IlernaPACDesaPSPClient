import java.io.*;
import java.net.Socket;

public class Client {

    private final String HOST = "localhost";
    private final int PORT = 1234;
    private Socket socket;
    private DataInputStream messageFromServer;
    private DataOutputStream messageToServer;
    private BufferedReader bufferFromServer;

    public Client() throws IOException {
        socket = new Socket(HOST, PORT);
        messageFromServer = new DataInputStream(socket.getInputStream());
        messageToServer = new DataOutputStream(socket.getOutputStream());
        bufferFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String receiveFromServer() throws IOException {
        return messageFromServer.readUTF();
    }

    public void sendToServer(String message) throws IOException {
        messageToServer.writeUTF(message);
    }

    public void receiveBufferServer() throws IOException {
        String mensajeDeCliente = bufferFromServer.readLine();
        while(mensajeDeCliente != null){
            System.out.println(mensajeDeCliente);
        }
    }

    public void closeSocket() throws IOException {
        if(socket != null){
            socket.close();
        }
    }
}
