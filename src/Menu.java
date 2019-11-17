import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @project IlernaPACDesaPSPClient
 * @author: jonan on 16/11/2019
 */
public class Menu {

    private Client cli;
    private Scanner input;
    private int selection;
    private final int MIN = 1;
    private final int MAX = 5;

    public Menu(){
        initMenu();
    }

    public void initMenu() {
        boolean valor = true;
        do {
            try {
                if(valor){
                    connectServer();
                    valor = false;
                }
                showMenu();
                selection = getSelection();

//                cli.closeSocket();                  // Cerramos el socket de cliente

            } catch (IOException e) {
                System.out.println(Constantes.ERROR_CONNECT);
                break;
            } catch (InputMismatchException e){
                String mensaje = (e.getMessage() == null)? "" : e.getMessage();
                System.out.println(Constantes.ERROR_INVALID_VALUE + mensaje);
            }
        } while (selection != MAX);
        goodbye();
    }

    /**
     * Recoge la opcion seleccionada y comnprueba que el valor sea valido para enviar al servidor.
     * Si es valido lo envia.
     * @return
     * @throws IOException
     */
    public int getSelection() throws IOException {
        input = new Scanner(System.in);
        selection = input.nextInt();
        input.nextLine(); // Para evitar el error del nextInt
        if(validateSelection(selection)){
            sendOptionServer(selection);
        }
        return selection;
    }

    /**
     * Valida que la seleccion sea valida
     * @param selection
     * @return
     */
    public boolean validateSelection(int selection){
        boolean valid = false;

        if(selection < MIN || selection > MAX){
            throw new InputMismatchException(Constantes.ERROR_VALID_VALUES);
        }else if(selection != MAX) {
            valid = true;
        }
        return valid;
    }

    /**
     * Metodo que se conecta con el servidor mediante socket y recibe un mensaje
     * @throws IOException
     */
    public void connectServer() throws IOException {
        cli = new Client();
        System.out.println(cli.receiveFromServer());
        cli.sendToServer("Conexion realizada: Client <======> Server");
    }

    /**
     * Envia la opcion seleccionada al servidor
     * @param selection opcion seleccionada del menu
     * @throws IOException
     */
    public void sendOptionServer(int selection) throws IOException {
        cli.sendToServer(Integer.toString(selection));
        selector(Integer.toString(selection));
    }

    public void selector(String input) throws IOException {

        switch (input){
            case "1":
                System.out.println("\nNueva tortuga");
                newTurtle();
                break;
            case "2":
                System.out.println("\nEliminar tortuga");
                deleteTurtle();
                break;
            case "3":
                System.out.println("\nMostrar tortugas");
                showTurtleList();
                break;
            case "4":
                System.out.println("\nIniciar carrera");
                startRace();
                break;
            default:
                System.out.println("\nError en la seleccion");
        }
    }

    private void newTurtle() throws IOException {
        System.out.println(cli.receiveFromServer());    // Recibe la peticion de nombre
        cli.sendToServer(input.nextLine());             // Envia el nombre
        System.out.println(cli.receiveFromServer());    // Recibe la peticion de dorsal
        cli.sendToServer(input.nextLine());             // Envia el dorsal
        System.out.println(cli.receiveFromServer());    // Recibe el mensaje de creado
    }

    private void deleteTurtle() throws IOException {
        System.out.println(cli.receiveFromServer());    // Recibe la peticion del index a borrar
        cli.sendToServer(input.nextLine());             // Envia el index
        System.out.println(cli.receiveFromServer());    // Recibe la confirmacion de borrado
    }

    private void showTurtleList() throws IOException {
        System.out.println("Listado de tortugas: ");
        System.out.println("--------------------------------------");
//        cli.receiveBufferServer();
        System.out.println("--------------------------------------");
    }

    private void startRace() {
    }

    public void showMenu(){
        System.out.println(Constantes.MESSAGE_MENU);
    }

    public void goodbye(){
        System.out.println(Constantes.MESSAGE_GODBYE);
    }
}
