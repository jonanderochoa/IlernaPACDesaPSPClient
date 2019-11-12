import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainClient {

    public static void main(String[] args) throws IOException {

        menu();
//        Client cli = new Client();
//        System.out.println("Iniciando cliente...");
//
//        cli.iniciarCliente();
    }

    public static void menu(){
        try {
            Scanner input;
            int selection = -1;

            do {
                input = new Scanner(System.in);
                try {

                    showMenu();
                    selection = input.nextInt();

                    if(selection >= 0 && selection <=5){
                        System.out.println("Opcion seleccionada: " + selection);
                        selector(selection);
                    }else{
                        throw new InputMismatchException(": entre 0 y 5");
                    }
                } catch (InputMismatchException e){
                    String mensaje = (e.getMessage() == null)? "" : e.getMessage();
                    System.out.println("Error, Introduce un valor valido" + mensaje);
                }
            } while (selection != 5);

        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void selector(int input){

        switch (input){
            case 1:
                System.out.println("Nueva tortuga");
                break;
            case 2:
                System.out.println("Eliminar tortuga");
                break;
            case 3:
                System.out.println("Mostrar tortugas");
                break;
            case 4:
                System.out.println("Iniciar carrera");
                break;
            case 5:
                System.out.println("Salir");
                break;
            default:
                System.out.println("Error en la seleccion");

        }
    }

    public static void showMenu(){
        System.out.println("");
        System.out.println("Selecciona una opción: ");
        System.out.println("1. Introducir nueva tortuga ");
        System.out.println("2. Eliminar tortuga ");
        System.out.println("3. Mostrar tortugas ");
        System.out.println("4. Iniciar carrera ");
        System.out.println("5. Salir.");
        System.out.println("");
    }

    public static void goodbye(){
        System.out.println("     ################################################################");
        System.out.println("    #                                                              #");
        System.out.println("   #                            GOODBYE                           #");
        System.out.println("  #                                                              #");
        System.out.println("################################################################");
    }
}
