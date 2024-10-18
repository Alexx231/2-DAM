package juego;

import java.util.Scanner;
import java.util.Random;

public class juegodepalitos {
    private static final Scanner scanner = new Scanner(System.in);
    private static int palitos = 21;
    private static char simboloPalito = '&';

    public static void main(String[] args) {
        while (true) {
            mostrarMenuPrincipal();
        }
    }

    private static void mostrarMenuPrincipal() {
        System.out.println("==================================");
        System.out.println("          MENÚ PRINCIPAL          ");
        System.out.println("==================================");
        System.out.println("1. Jugar");
        System.out.println("2. Controles/Ayuda");
        System.out.println("3. Salir");
        System.out.println("==================================");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();

        switch (opcion) {
            case 1:
                mostrarSubmenuJugar();
                break;
            case 2:
                mostrarControlesYAyuda();
                break;
            case 3:
                System.out.println("Gracias por jugar. ¡Adiós!");
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }
    }

    private static void mostrarSubmenuJugar() {
        System.out.println("==================================");
        System.out.println("          SUBMENÚ JUGAR           ");
        System.out.println("==================================");
        System.out.println("1. Jugador vs. Jugador");
        System.out.println("2. Jugador vs. AI");
        System.out.println("3. Cambiar símbolo de los palitos");
        System.out.println("4. Volver al Menú Principal");
        System.out.println("==================================");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); 

        switch (opcion) {
            case 1:
                jugar(false);
                break;
            case 2:
                jugar(true);
                break;
            case 3:
                cambiarSimboloPalitos();
                break;
            case 4:
                return;
            default:
                System.out.println("Opción no válida. Intente de nuevo.");
        }
    }

    private static void mostrarControlesYAyuda() {
        System.out.println("==================================");
        System.out.println("        CONTROLES Y AYUDA         ");
        System.out.println("==================================");
        System.out.println("1. El juego consiste en tachar palitos (de 1 a 4) por turnos teniendo 21 palitos en total al inicio de la partida.");
        System.out.println("2. El jugador o máquina que tache el último palito, pierde.");
        System.out.println("3. Puede reiniciar el juego pulsando 'R' y volver al menú principal pulsando 'M'.");
        System.out.println("4. En el submenú de 'Jugar', puede cambiar el símbolo que representa a los palitos.");
        System.out.println("==================================");
        System.out.print("Presione Enter para volver al Menú Principal...");
        scanner.nextLine();
    }

    private static void jugar(boolean contraAI) {
        palitos = 21;
        Random random = new Random();
        int turno;

        if (contraAI) {
            turno = random.nextInt(2) + 1;
        } else {
            turno = 1;
        }
        
        while (palitos > 0) {
            mostrarPalitos();
            if (contraAI) {
                if (turno == 1) {
                    System.out.println("Turno del Jugador 1:");
                    int palitosARemover = obtenerPalitosARemover();
                    palitos -= palitosARemover;
                    if (palitos <= 0) {
                        System.out.println("Jugador 1 pierde.");
                        break;
                    }
                    turno = 2; // AI
                } else {
                    System.out.println("Turno de la AI:\n");
                    int palitosARemover = calcularMovimientoAI();
                    System.out.println("AI remueve " + palitosARemover + " palitos.");
                    palitos -= palitosARemover;
                    if (palitos <= 0) {
                        System.out.println("AI pierde.");
                        break;
                    }
                    turno = 1; // Jugador 1
                }
            } else {
                System.out.println("Turno del Jugador " + turno + ":");
                int palitosARemover = obtenerPalitosARemover();
                palitos -= palitosARemover;
                if (palitos <= 0) {
                    System.out.println("Jugador " + turno + " pierde.");
                    break;
                }

                turno = (turno % 3) + 1;
            }
        }

        System.out.println("\n¿Desea reiniciar el juego? (R) o volver al menú principal (M): ");
        char opcion = scanner.next().charAt(0);
        if (opcion == 'R' || opcion == 'r') {
        	System.out.println("\n");
            jugar(contraAI);
        } else if (opcion == 'M' || opcion == 'm') {
        	mostrarMenuPrincipal();
        } else {
        	System.out.println("\nOpcion Invalida\n");
        	System.exit(0);
        }
    }

    private static void mostrarPalitos() {
        for (int i = 0; i < palitos; i++) {
            System.out.print(simboloPalito);
            if ((i + 1) % 5 == 0) {
                System.out.print("\n");
            }
        }
        System.out.println(" \n(" + palitos + " palitos restantes)\n");
    }

    private static int obtenerPalitosARemover() {
        int palitosARemover;
        while (true) {
            System.out.print("\nIngrese el número de palitos a remover (1-4): ");
            palitosARemover = scanner.nextInt();
            if (palitosARemover >= 1 && palitosARemover <= 4) {
                break;
            } else {
                System.out.println("\nNúmero inválido. Intente de nuevo.");
            }
        }
        return palitosARemover;
    }

    private static void cambiarSimboloPalitos() {
        System.out.print("\nIngrese el nuevo símbolo para los palitos: ");
        simboloPalito = scanner.next().charAt(0);
        System.out.println("\nSímbolo de los palitos cambiado a: " + simboloPalito);
    }

    private static int calcularMovimientoAI() {
        int palitosARemover = palitos % 5;
        if (palitosARemover == 0) {
            palitosARemover = 1;
        }
        return palitosARemover;
    }
}