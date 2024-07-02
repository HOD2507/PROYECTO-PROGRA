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

    /*
   E: 
   S: La opción seleccionada por el usuario (entero).
   1. Muestra un menú de opciones al usuario.
   2. Lee la opción ingresada por el usuario.
   3. Valida si la opción ingresada es válida (entre 1 y 4).
   4. Devuelve la opción válida seleccionada por el usuario.
*/
    
   public static int mostrarMenu(Scanner teclado) {
    int opcion;

    while (true) {
        System.out.println("Selecciona una opción:");
        System.out.println("1. Cargar fichero de entrada o solicitar ruta del archivo.");
        System.out.println("2. Generar el calendario mensual del mes Marzo.");
        System.out.println("3. Indicar un día del mes (1 al 31) para conocer qué científica se publicará ese día y su especialidad.");
        System.out.println("4. Salir de la Interfaz.");
        System.out.print("Opción: ");

        if (teclado.hasNextInt()) {
            opcion = teclado.nextInt();
            if (opcion >= 1 && opcion <= 4) {
                break;
            } else {
                System.out.println("¡Por favor, introduce una opción válida! (1, 2, 3, 4)\\n");
            }
        } else {
            System.out.println("¡Entrada no válida! Por favor, introduce un número.\\n");
            teclado.next(); // Limpiar la entrada no válida
        }
    }

    return opcion;
}


    // Método para convertir un array de Cientifica a un array de Strings

/*
   E: Array de objetos Cientifica (listaCientificas).
   S: Array de cadenas (listaStrings) con representaciones de cada Cientifica o "No hay información disponible".
   1. Inicializar listaStrings con el mismo tamaño que listaCientificas.
   2. Convertir cada objeto Cientifica a cadena.
   3. Asignar "No hay información disponible" si el objeto Cientifica es nulo.
   4. Devolver listaStrings.
*/

    
   public static String[] cientificasAStrings(Cientifica[] listaCientificas) {
String[] listaStrings = new String[listaCientificas.length];
        for (int i = 0; i < listaCientificas.length; i++) {
    Cientifica cientifica = listaCientificas[i];
        if (cientifica != null) {
        cientifica.setAnioCartel("Marzo");
        listaStrings[i] = cientifica.toString();
    } else {
        listaStrings[i] = "No hay información disponible";
    }
}

return listaStrings;


    // Método para escribir un archivo CSV

/*
   E: listaCientificas 
   S: Escribe datos en "planning_marzo.csv".
   1. Define la ruta del archivo.
   2. Abre y prepara OutputStreamWriter.
   3. Escribe encabezados de Cientifica.
   4. Escribe cada dato seguido de líneas en blanco.
   5. Muestra mensaje de éxito o maneja errores de escritura.
*/

    public static void escribirCSV(String[] listaCientificas) {
    String rutaArchivo = "src/planning_marzo.csv";
    try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(rutaArchivo))) {
        // Escribir encabezados
        writer.write(new Cientifica("Científicas", "Año nacimiento", "Fecha",
                "Bio", "Especialidad", "Cartel", "Año",
                "Otros Carteles", "Instagram", "Pais", "Wikipedia").toString());
        writer.write("\n\n");

        // Escribir datos
        for (int i = 0; i < listaCientificas.length; i++) {
            writer.write(listaCientificas[i]);
            writer.write("\n\n");
        }

        System.out.println("Archivo CSV creado exitosamente en " + rutaArchivo);
    } catch (IOException e) {
        System.out.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
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
    // Algoritmo:
    // 1. Crear un array de Cientifica con el mismo tamaño que el array de entrada.
    // 2. Iterar sobre cada elemento del array de entrada.
    // 3. Verificar que el elemento actual no sea nulo.
    // 4. Convertir la línea actual en un objeto Cientifica.
    // 5. Devolver el array de objetos Cientifica.

    public static Cientifica[] convertirAListaCientificas(String[] datos) {
    // Crear un array de Cientifica con el mismo tamaño que el array de entrada
    Cientifica[] cientificas = new Cientifica[datos.length];

    // Recorrer el array de entrada
    for (int indice = 0; indice < datos.length; indice++) {
        // Verificar que el elemento actual no sea nulo
        if (datos[indice] != null) {
            // Convertir la línea actual en un objeto Cientifica
            cientificas[indice] = convertirALineaCientifica(datos[indice]);
        }
    }

    // Devolver el array de objetos Cientifica
    return cientificas;
    }
    // Método para convertir una línea de texto a un objeto Cientifica
    // Algoritmo:
    // 1. Dividir la línea en partes utilizando el delimitador ";".
    // 2. Crear un nuevo objeto Cientifica utilizando los elementos del array partes.
    // 3. Devolver el objeto Cientifica creado.

    public static Cientifica convertirALineaCientifica(String linea) {
    // Dividir la línea en partes utilizando el delimitador ";"
    String[] partes = linea.split(";");

    // Crear un nuevo objeto Cientifica utilizando los elementos del array partes
    return new Cientifica(
        partes[0], partes[1], partes[2], partes[3], partes[4], 
        partes[5], partes[6], partes[7], partes[8], partes[9], partes[10]
        );
    }
    // Método para generar la lista de científicas para el mes de marzo
    // Algoritmo:
    // 1. Crear un array de Cientifica con capacidad para 31 elementos.
    // 2. Inicializar variables de control para el día y para verificar si se ha encontrado el primer "1-mar".
    // 3. Buscar la primera científica con fecha "1-mar" y agregarla a la lista de marzo.
    // 4. Llenar el resto de los días de marzo con científicas válidas seleccionadas aleatoriamente.
    // 5. Devolver el array de científicas para marzo.

    public static Cientifica[] obtenerListaCientificasMarzo(Cientifica[] listaCientificas) {
    Random random = new Random();
    Cientifica[] listaCientificasMarzo = new Cientifica[31];
    int dia = 0;
    boolean encontradoPrimerMarzo = false;

    // Buscar el primer "1-mar" en la lista de científicas
    for (int i = 0; i < listaCientificas.length && !encontradoPrimerMarzo; i++) {
        if (listaCientificas[i].getFecha().equals("1-mar")) {
            listaCientificasMarzo[dia] = listaCientificas[i];
            encontradoPrimerMarzo = true;
            dia++;
        }
    }

    // Seleccionar aleatoriamente científicas válidas para el resto de los días de marzo
    while (dia < 31) {
        int randomNum = random.nextInt(listaCientificas.length);
        if (esCientificaValida(listaCientificas[randomNum], listaCientificasMarzo)) {
            listaCientificasMarzo[dia] = listaCientificas[randomNum];
            dia++;
        }
    }

    return listaCientificasMarzo;
    }
    // Método para validar si una científica es válida para ser añadida a la lista de marzo
    // Algoritmo:
    // 1. Inicializar variables de control para la validación.
    // 2. Verificar si el año del cartel y el año de nacimiento son válidos.
    // 3. Verificar que la científica no tenga la misma especialidad ni fecha que las ya añadidas.
    // 4. Encontrar la última científica añadida a la lista.
    // 5. Verificar que la científica no haya nacido en el mismo siglo que la última científica añadida.
    // 6. Devolver el resultado de la validación.

    public static boolean esCientificaValida(Cientifica cientifica, Cientifica[] listaCientificas) {
    boolean esValida = true;
    boolean mismaEspecialidad = false;
    boolean encontradaUltima = false;
    Cientifica ultimaAniadida = null;

    // Verificar condiciones de validez
    if (!cientifica.getAnioCartel().equals("-") || cientifica.getAnioNacimiento().equals("-") || cientifica.getAnioNacimientoNumero() == 0) {
        esValida = false;
    }

    // Verificar especialidad y fecha
    for (int i = 0; i < listaCientificas.length && !mismaEspecialidad; i++) {
        if (listaCientificas[i] != null && (cientifica.getEspecialidad().equals(listaCientificas[i].getEspecialidad()) || cientifica.getFecha().equals(listaCientificas[i].getFecha()))) {
            esValida = false;
            mismaEspecialidad = true;
        }
    }

    // Encontrar la última científica añadida
    for (int i = listaCientificas.length - 1; i >= 0 && !encontradaUltima; i--) {
        if (listaCientificas[i] != null) {
            ultimaAniadida = listaCientificas[i];
            encontradaUltima = true;
        }
    }

    // Verificar siglo del año de nacimiento
    if (ultimaAniadida != null && ultimaAniadida.getAnioNacimientoNumero() / 100 == cientifica.getAnioNacimientoNumero() / 100) {
        esValida = false;
    }

    return esValida;
    }
}
