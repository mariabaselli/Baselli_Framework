package baselli.framework;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Consola {
    private static final String CLASS_NAME_PROPERTY = "clase";
    public static final String MENÚ = "Menú:";
    public static final String MENSAJE_ÉXITO = "Ha salido exitosamente.";
    public static final String MENSAJE_ERROR = "La opción seleccionada es incorrecta. Intentelo nuevamente. ";
    public static final String OPCION_SALIR = ".- Salir.";
    public static final String MENSAJE_BIENVENIDA = "Bienvenid@!";
    public static final String LABEL_ERROR_INSTANCIA = "No pude crear la instancia de Menú... ";
    public static final String SPLIT = ",";
    public static final String SPACE = " ";
    private Accion menu;
    private List<Accion> acciones = new ArrayList<>();


    public Consola(String pathConfiguracion) {
        Properties prop = new Properties();
        try (InputStream configFile = getClass().getResourceAsStream(pathConfiguracion);) {
            prop.load(configFile);
            String clases = prop.getProperty(CLASS_NAME_PROPERTY);
            String[] nombresDeClases = clases.split(SPLIT);

            for (String nombreClase : nombresDeClases) {
                Class<?> c = Class.forName(nombreClase.trim());
                menu = (Accion) c.getDeclaredConstructor().newInstance();
                acciones.add(menu);
            }
        } catch (Exception e) {
            throw new RuntimeException(LABEL_ERROR_INSTANCIA, e);
        }
    }


    public void imprimir() {
        int indice = 1;
        Scanner scanner = new Scanner(System.in);
        desplegarMenu(indice, scanner);
    }

    private void desplegarMenu(int indice, Scanner scanner) {
        int option;
        System.out.println(MENSAJE_BIENVENIDA);

        while (true) {
            System.out.println(MENÚ);
            for (Accion menu : acciones) {
                System.out.println(indice + ".- " + menu.nombreItemMenu() + menu.descripcionItemMenu());
                indice += 1;
            }
            System.out.println(indice + OPCION_SALIR);
            indice = 1;
            System.out.print("Indique a continuación su opcion del " + indice + "-" + (acciones.size() + 1) + ": ");

            option = scanner.nextInt();
            System.out.println(SPACE);
            if (opcionContinuar(option)) {
                acciones.get(option - 1).ejecutar();
            } else if (opcionSalir(option)) {
                System.out.println(MENSAJE_ÉXITO);
                break;
            } else {
                System.out.println(MENSAJE_ERROR);
            }
            System.out.println(SPACE);
        }
    }

    private boolean opcionSalir(int option) {
        return option == acciones.size() + 1;
    }

    private boolean opcionContinuar(int option) {
        return option >= 1 && option <= acciones.size();
    }
}

