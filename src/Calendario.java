import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.Scanner;

public class Calendario {

    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);
        String rutaArchivo = null;
        int opcion = mostrarMenu(teclado);
        Cientifica[] listaCientificasMarzo = null;

        // Bucle principal del programa
        do {
            switch (opcion) {
                case 1:
                    teclado.nextLine(); // Consumir el salto de línea
                    System.out.print("Por favor, introduce la ruta del archivo a leer (src/listaCientificas.csv): ");
                    rutaArchivo = teclado.nextLine();
                    break;
                case 2:
                    if (rutaArchivo != null) {
                        String[] lineasCSV = leerLineasCSV(rutaArchivo); // Leer líneas del CSV
                        String[] lineasFiltradas = eliminarLineasVacias(lineasCSV); // Filtrar líneas vacías
                        Cientifica[] listaCientificas = convertirAListaCientificas(lineasFiltradas); // Convertir a objetos Cientifica
                        listaCientificasMarzo = generarListaCientificasMarzo(listaCientificas); // Generar lista de científicas para marzo
                        String[] listaMarzo = convertirCientificasAStrings(listaCientificasMarzo); // Convertir a Strings
                        escribirCSV(listaMarzo); // Escribir a archivo CSV
                    } else {
                        System.out.println("Por favor, primero introduzca una ruta de archivo");
                    }
                    break;
                case 3:
                    if (listaCientificasMarzo != null) {
                        int dia;
                        do {
                            System.out.print("Introduzca un día del mes de Marzo: ");
                            dia = teclado.nextInt();
                            if (dia < 1 || dia > 31) {
                                System.out.println("Por favor, introduce un número entero del 1 al 31");
                            }
                        } while (dia < 1 || dia > 31);

                        System.out.println("Para el día " + dia + ", se tiene previsto publicar a la siguiente científica:\n");
                        System.out.println(listaCientificasMarzo[dia - 1].mostrar());
                    } else {
                        System.out.println("El calendario no se ha generado correctamente.");
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }
            if (opcion != 4) {
                opcion = mostrarMenu(teclado);
            }
        } while (opcion != 4);
    }

    // Método para mostrar el menú y obtener la opción del usuario
    public static int mostrarMenu(Scanner teclado) {
        int opcion;
        do {
            System.out.println("1. Cargar fichero de entrada o solicitar ruta del archivo.\n" +
                    "2. Generar el calendario mensual del mes Marzo.\n" +
                    "3. Indicar un día del mes (1 al 31) para conocer qué científica se publicará ese día y su especialidad. \n" +
                    "4. Salir de la Interfaz.");
            opcion = teclado.nextInt();
            if (opcion < 1 || opcion > 4) {
                System.out.println("¡Por favor, introduce una opción válida! (1, 2, 3, 4)\n");
            }
        } while (opcion < 1 || opcion > 4);
        return opcion;
    }

    // Método para convertir un array de Cientifica a un array de Strings
    public static String[] convertirCientificasAStrings(Cientifica[] listaCientificas) {
        String[] listaStrings = new String[listaCientificas.length];
        for (int i = 0; i < listaCientificas.length; i++) {
            if (listaCientificas[i] != null) {
                listaCientificas[i].setAnioCartel("Marzo");
                listaStrings[i] = listaCientificas[i].toString();
            }
        }
        return listaStrings;
    }

    // Método para escribir un archivo CSV
    public static void escribirCSV(String[] listaCientificas) {
        String rutaArchivo = "src/planning_marzo.csv";
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(rutaArchivo), StandardCharsets.UTF_8)) {
            // Escribir encabezados
            writer.write(new Cientifica("Científicas", "Año nacimiento", "Fecha",
                    "Bio", "Especialidad", "Cartel", "Año",
                    "Otros Carteles", "Instagram", "Pais", "Wikipedia").toString());
            writer.write("\n\n");

            // Escribir datos
            for (String cientifica : listaCientificas) {
                writer.write(cientifica);
                writer.write("\n\n");
            }

            System.out.println("Archivo CSV creado exitosamente en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }

    // Método para leer el archivo CSV y separar por líneas
    public static String[] leerLineasCSV(String archivoCSV) throws IOException {
        int numeroLineas = contarLineasArchivo(archivoCSV);
        String[] lineas = new String[numeroLineas];
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            br.readLine(); // Saltar la primera línea (encabezados)
            for (int i = 0; i < numeroLineas; i++) {
                lineas[i] = br.readLine();
            }
        }
        return lineas;
    }

    // Método para contar las líneas en un archivo
    private static int contarLineasArchivo(String archivoCSV) throws IOException {
        int contador = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            while (br.readLine() != null) {
                contador++;
            }
        }
        return contador;
    }

    // Método para eliminar elementos vacíos de un array
    public static String[] eliminarLineasVacias(String[] array) {
        int elementosNoVacios = 0;
        for (String s : array) {
            if (s != null && !s.isEmpty()) {
                elementosNoVacios++;
            }
        }

        String[] arraySinVacios = new String[elementosNoVacios];
        int indice = 0;
        for (String s : array) {
            if (s != null && !s.isEmpty()) {
                arraySinVacios[indice++] = s;
            }
        }
        return arraySinVacios;
    }

    // Método para convertir un array de líneas a un array de objetos Cientifica
    public static Cientifica[] convertirAListaCientificas(String[] lineas) {
        Cientifica[] listaCientificas = new Cientifica[lineas.length];
        for (int i = 0; i < lineas.length; i++) {
            if (lineas[i] != null) {
                listaCientificas[i] = convertirLineaACientifica(lineas[i]);
            }
        }
        return listaCientificas;
    }

    // Método para convertir una línea de texto a un objeto Cientifica
    public static Cientifica convertirLineaACientifica(String linea) {
        String[] atributos = linea.split(";");
        return new Cientifica(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6], atributos[7], atributos[8], atributos[9], atributos[10]);
    }

    // Método para generar la lista de científicas para el mes de marzo
    public static Cientifica[] generarListaCientificasMarzo(Cientifica[] listaCientificas) {
        Random random = new Random();
        Cientifica[] listaCientificasMarzo = new Cientifica[31];
        int dia = 1;
        int contador = 0;
        boolean dia1;

        // Encontrar una científica que cumpla con los requisitos para el primer día de marzo
        do {
            if (listaCientificas[contador].getFecha().equals("1-mar")) {
                contador++;
                dia1 = true;
            } else {
                dia1 = false;
            }
        } while (dia1);

        int randomNum = random.nextInt(contador + 1);
        listaCientificasMarzo[0] = listaCientificas[randomNum];

        // Generar la lista para los días restantes del mes
        for (Cientifica cientifica : listaCientificas) {
            if (esCientificaValida(cientifica, listaCientificasMarzo)) {
                listaCientificasMarzo[dia] = cientifica;
                dia++;
            }
        }
        return listaCientificasMarzo;
    }

    // Método para validar si una científica es válida para ser añadida a la lista de marzo
    public static boolean esCientificaValida(Cientifica cientifica, Cientifica[] listaCientificas) {
        boolean esValida = true;
        boolean mismaEspecialidad = false;
        boolean encontradaUltima = false;
        Cientifica ultimaAniadida = null;

        // Verificar condiciones para validar la científica
        if (!cientifica.getAnioCartel().equals("-") || cientifica.getAnioNacimiento().equals("-") || cientifica.getAnioNacimientoNumero() == 0) {
            esValida = false;
        }

        for (Cientifica c : listaCientificas) {
            if (c != null && (cientifica.getEspecialidad().equals(c.getEspecialidad()) || cientifica.getFecha().equals(c.getFecha()))) {
                esValida = false;
                mismaEspecialidad = true;
            }
        }

        for (int i = listaCientificas.length - 1; i >= 0 && !encontradaUltima; i--) {
            if (listaCientificas[i] != null) {
                ultimaAniadida = listaCientificas[i];
                encontradaUltima = true;
            }
        }

        if (ultimaAniadida != null && ultimaAniadida.getAnioNacimientoNumero() / 100 == cientifica.getAnioNacimientoNumero() / 100) {
            esValida = false;
        }

        return esValida;
    }
}
