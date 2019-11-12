import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private final String HOST = "localhost";
    private final int PUERTO = 1234;
    private Socket socket;

    public Client() throws IOException {
        socket = new Socket(HOST, PUERTO);
    }

    public void iniciarCliente() throws IOException {

        // Recibe del servidor
        DataInputStream entradaDelServidor = new DataInputStream(socket.getInputStream());
        System.out.println(entradaDelServidor.readUTF());

        // Envio al servidor
        DataOutputStream salidaHaciaServidor = new DataOutputStream(socket.getOutputStream());
        for(int i = 0; i < 3; i ++){
            salidaHaciaServidor.writeUTF("Este es el mensaje numero: " + i);
        }
        socket.close();

    }


}
