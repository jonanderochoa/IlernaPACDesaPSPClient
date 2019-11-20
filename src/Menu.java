import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Clase que genera el menu, valida que el valor introducido sea valido e intercambia informacion con
 * el Server utilizando la clase Client
 *
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
        try {
            initMenu();
        } catch (IOException e) {
            System.out.println(Constantes.ERROR_CONNECT);
        }
    }

    /**
     * Metodo que muestra el menu
     */
    public void initMenu() throws IOException {
        boolean valor = true;
        do {
            try {
                // Hace que se pruebe la conexion una unica vez
                if(valor){
                    connectServer();
                    valor = false;
                }
                Utils.showMenu();                        // Muestra el menu
                selection = getSelection();             // Recoge la seleccion

            } catch (IOException e) {
                System.out.println(Constantes.ERROR_CONNECT);
                break;
            } catch (InputMismatchException e){
                String mensaje = (e.getMessage() == null)? "" : e.getMessage();
                System.out.println(Constantes.ERROR_INVALID_VALUE + mensaje);
            }
        } while (selection != MAX);
        cli.closeSocket();
    }

    //MENU
    /**
     * Metodo encargado de seleccionar la accion en funcion de la seleccion del menu
     * @param input
     * @throws IOException
     */
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
            case "5":
                Utils.goodbye();
                break;
            default:
                System.out.println("\nError en la seleccion");
        }
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
        input.nextLine();           // Para evitar el error del nextInt

        // Si el valor es valido lo manda al servidor
        if(validateSelection(selection)){
            sendOptionServer(selection);
        }
        return selection;
    }

    /**
     * Valida que la seleccion sea valida. Entre 1 y 5 incluidos
     * @param selection Valor introducido
     * @return boolean (true si es valido)
     */
    public boolean validateSelection(int selection){
        boolean valid = false;

        if(selection < MIN || selection > MAX){
            throw new InputMismatchException(Constantes.ERROR_VALID_VALUES);
        }else{
            valid = true;
        }
        return valid;
    }

    // SERVER
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

    // ACCIONES
    /**
     * Metodo que se encarga del envio de los datos de una tortuga para su creacion en el servidor.
     * Se intercambian mensajes para introducir el nombre y dorsal de la misma
     * @throws IOException
     */
    private void newTurtle() throws IOException {
        System.out.println(cli.receiveFromServer());    // Recibe la peticion de nombre
        cli.sendToServer(input.nextLine());             // Envia el nombre
        System.out.println(cli.receiveFromServer());    // Recibe la peticion de dorsal
        cli.sendToServer(input.nextLine());             // Envia el dorsal
        System.out.println(cli.receiveFromServer());    // Recibe el mensaje de creado
    }

    /**
     * Recibe la pregunta del server de que tortuga eliminar. Se le envia la tortuga que se desea eliminar
     * y recibimos confirmacion de borrado
     * @throws IOException
     */
    private void deleteTurtle() throws IOException {
        System.out.println(cli.receiveFromServer());    // Recibe la peticion del index a borrar
        cli.sendToServer(input.nextLine());             // Envia el index
        System.out.println(cli.receiveFromServer());    // Recibe la confirmacion de borrado
    }

    /**
     * Muestra por consola el listado de tortugas que contiene el servidor
     * @throws IOException
     */
    private void showTurtleList() throws IOException {
        System.out.println("Listado de tortugas: ");
        System.out.println("--------------------------------------");
        System.out.println(cli.receiveFromServer());
        System.out.println("--------------------------------------");
    }

    /**
     * Muestra el ganador de la carrera que se ha generado en el servidor
     * @throws IOException
     */
    private void startRace() throws IOException {
        System.out.println(cli.receiveFromServer());    // Recibe el ganador
    }

}
