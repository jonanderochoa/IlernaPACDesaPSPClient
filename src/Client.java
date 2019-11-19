import java.io.*;
import java.net.Socket;

/**
 * Clase que se encarga del socket y la comunicacion con el server
 */
public class Client {

    private final String HOST = "localhost";            // host de conexion del socket
    private final int PORT = 1234;                      // Puerto de conexion del socket
    private Socket socket;                              // Socket
    private DataInputStream messageFromServer;          // Flujo de entrada de datos
    private DataOutputStream messageToServer;           // Flujo de salida de datos
    private BufferedReader bufferFromServer;            // Buffer de datos

    public Client() throws IOException {
        socket = new Socket(HOST, PORT);
        messageFromServer = new DataInputStream(socket.getInputStream());
        messageToServer = new DataOutputStream(socket.getOutputStream());
        bufferFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    /**
     * Metodo que recibe datos del server
     * @return
     * @throws IOException
     */
    public String receiveFromServer() throws IOException {
        return messageFromServer.readUTF();
    }

    /**
     * Metodo que envia datos al server
     * @param message
     * @throws IOException
     */
    public void sendToServer(String message) throws IOException {
        messageToServer.writeUTF(message);
    }

    /**
     * Metodo que recibe un buffer del server
     * @throws IOException
     */
    public void receiveBufferServer() throws IOException {
        String mensajeDeCliente = bufferFromServer.readLine();
        while(mensajeDeCliente != null){
            System.out.println(mensajeDeCliente);
        }
    }

    /**
     * Metodo que cierra la conexion con el socket
     * @throws IOException
     */
    public void closeSocket() throws IOException {
        if(socket != null){
            socket.close();
        }
    }
}
