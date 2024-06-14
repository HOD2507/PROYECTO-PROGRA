import java.io.*;
import java.util.Random;
import java.nio.charset.StandardCharsets; //Para el UTF8 (Formato)
import java.util.Scanner;

public class Calendario{

    public static void main(String[] args) throws IOException {
        Scanner teclado = new Scanner(System.in);
        String rutaArchivo = null;
        int opcion = menu(teclado);
        Cientifica[] listaCientificasMarzo = null;

        do{
            switch (opcion) {
                case 1:
                    teclado.nextLine();
                    System.out.print("Por favor, introduce la ruta del archivo a leer (src/listaCientificas.csv): ");
                    rutaArchivo = teclado.nextLine();
                    break;
                case 2:
                    if (rutaArchivo != null) {
                        String[] csvEnLineas = separarPorLineasCSV(rutaArchivo); //Separamos por líneas
                        String[] listaLineasCientificas = eliminarElementosVacios(csvEnLineas); //Borramos los elementos vacíos
                        Cientifica[] listaCientificas = listaCientificas(listaLineasCientificas); //Pasamos de Strings a Cientificas con sus atributos
                        listaCientificasMarzo = listaCientificasMarzo(listaCientificas); //Reducimos la lista a una científica por día
                        String[] listaMarzo = cientificasAStrings(listaCientificasMarzo); //Pasamos la lista a Strings de nuevo para poder escribirla en el csv
                        escribirCSV(listaMarzo); //Escribimos la lista de Strings en un nuevo fichero csv con nombre "calendarioMarzo.csv"
                    }else
                        System.out.println("Por favor, primero introduzca una ruta de archivo");
                    break;
                case 3:
                    int dia;
                    if(listaCientificasMarzo != null){
                        do{
                            System.out.print("Introduzca un día del mes de Marzo: ");
                            dia = teclado.nextInt();
                            if(dia < 1 || dia > 31){
                                System.out.println("Por favor, introduce un número entero del 1 al 31");
                            }
                        }while (dia < 1 || dia > 31);

                        System.out.println("Para el día " + dia + ", se tiene previsto publicar a la siguiente científica:\n");
                        System.out.println(listaCientificasMarzo[dia-1].mostrar());
                    }else
                        System.out.println("El calendario no se ha generado correctamente.");
                    break;
                case 4:
                    break;
            }
            if(opcion != 4){
                opcion = menu(teclado);
            }
        }while (opcion != 4);
    }
//////////////////////////////////////////////////////////////////////////////////////////////
    public static int menu(Scanner teclado){
        int opcion;

        do{
            System.out.println("1. Cargar fichero de entrada o solicitar ruta del archivo.\n" +
                    "2. Generar el calendario mensual del mes Marzo.\n" +
                    "3. Indicar un día del mes (1 al 31) para conocer qué científica se publicará ese día y su especialidad. \n" +
                    "4. Salir de la Interfaz. ");
            opcion = teclado.nextInt();
            if(opcion < 1 || opcion > 4){
                System.out.println("¡Por favor, introduce una opción válida! (1, 2, 3, 4)\n");
            }
        }while (opcion < 1 || opcion > 4);

        return opcion;
    }

    public static String[] cientificasAStrings(Cientifica[] listaCientificas){
        String[] listaStrings = new String[listaCientificas.length];
        for(int i = 0; i < listaCientificas.length; i++){
            if(listaCientificas[i] != null){
                listaCientificas[i].setAnioCartel("Marzo");
                listaStrings[i] = listaCientificas[i].toString();
            }
        }
        return listaStrings;
    }

    public static void escribirCSV(String[] listaCientificas){
        String rutaArchivo = "src/planning_marzo.csv";

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(rutaArchivo);
            OutputStreamWriter escribirCSV = new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8);

            escribirCSV.append(new Cientifica("Científicas", "Año nacimiento", "Fecha"
                    , "Bio", "Especialidad", "Cartel", "Año"
                    , "Otros Carteles", "Instagram", "Pais", "Wikipedia").toString());
            escribirCSV.append("\n\n");

            for (int i = 0; i < listaCientificas.length; i++) {
                escribirCSV.append(listaCientificas[i]);
                escribirCSV.append("\n\n");
            }

            escribirCSV.flush();
            escribirCSV.close();

            System.out.println("Archivo CSV creado exitosamente en " + rutaArchivo);
        } catch (IOException e) {
            System.err.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Método para leer el archivo CSV
    public static String[] separarPorLineasCSV(String archivoCSV) throws IOException {
        int lineasDelArchivo = contarLineasArchivo(archivo3CSV);
        String[] listaCientificas = new String[lineasDelArchivo];

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            br.readLine(); //Para que no guarde la primera línea, que es la que muestra las categorías
            for (int i = 0; i < lineasDelArchivo; i++) {
                listaCientificas[i] = br.readLine();
            }
        }

        return listaCientificas;
    }

    // Método para contar las líneas en el archivo csv p
    private static int contarLineasArchivo(String archivoCSV) throws IOException {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            while (br.readLine() != null) {
                count++;
            }
        }
        return count;
    }

    public static String[] eliminarElementosVacios(String[] arrayConHuecos) {
        // Contar cuántos elementos no están vacíos
        int elementosNoVacios = 0;
        for (int i = 0; i < arrayConHuecos.length; i++) {
            if (arrayConHuecos[i] != null && !arrayConHuecos[i].isEmpty()) {
                elementosNoVacios++;
            }
        }

        // Crear un nuevo array para almacenar los elementos no vacíos
        String[] arraySinHuecos = new String[elementosNoVacios];
        int indice = 0;
        for (int i = 0; i < arrayConHuecos.length; i++) {
            //Metemos también la condición i > 0 para que no meta la primera línea, ya que esta muestra las categorías
            if (arrayConHuecos[i] != null && !arrayConHuecos[i].isEmpty()) {
                arraySinHuecos[indice++] = arrayConHuecos[i];
            }
        }

        return arraySinHuecos;
    }

    public static Cientifica[] listaCientificas(String[] array){
        Cientifica[] listaCientificas = new Cientifica[array.length];

        for(int i = 0; i < array.length; i++){
            if(array[i] != null){
                listaCientificas[i] = lineaACientifica(array[i]);
            }
        }

        return listaCientificas;
    }

    public static Cientifica lineaACientifica(String linea){
        String[] atributos = linea.split(";");
        return new Cientifica(atributos[0], atributos[1], atributos[2], atributos[3], atributos[4], atributos[5], atributos[6], atributos[7], atributos[8], atributos[9], atributos[10]);
    }

    public static Cientifica[] listaCientificasMarzo(Cientifica[] listaCientificas){
        Random random = new Random();
        Cientifica[] listaCientificasMarzo = new Cientifica[31];
        int dia = 1, contador = 0;
        boolean dia1;

        do{
            if(listaCientificas[contador].getFecha().equals("1-mar")){
                contador++;
                dia1 = true;
            }else
                dia1 = false;
        }while (dia1);

        int randomNum = random.nextInt((contador) + 1);
        listaCientificasMarzo[0] = listaCientificas[randomNum];

        for(int i = 0; i < listaCientificas.length; i++){
            if(cientificaValida(listaCientificas[i], listaCientificasMarzo)){
                listaCientificasMarzo[dia] = listaCientificas[i];
                dia++;
            }
        }
        return listaCientificasMarzo;
    }

    public static boolean cientificaValida(Cientifica cientifica, Cientifica[] listaCientificas){
        boolean esValida = true, mismaEspecialidad = false, encontradaUltima = false;
        Cientifica ultimaAniadida = null;

        if(!cientifica.getAnioCartel().equals("-") || cientifica.getAnioNacimiento().equals("-") || cientifica.getAnioNacimientoNumero() == 0){ //No se ha publicado antes y se conoce su año de nacimiento
            esValida = false;
        }

        for (int i = 0; i < listaCientificas.length && !mismaEspecialidad; i++){ //Especialidad distinta y fecha distinta
            if (listaCientificas[i] != null && ((cientifica.getEspecialidad().equals(listaCientificas[i].getEspecialidad())) || (cientifica.getFecha().equals(listaCientificas[i].getFecha())))){
                esValida = false;
                mismaEspecialidad = true;
            }else
                esValida = true;
        }

        for(int i = listaCientificas.length-1; i >= 0 && !encontradaUltima; i--){ //Para encontrar la última científica de la lista y que no coincidan las fechas
            if(listaCientificas[i] != null){
                ultimaAniadida = listaCientificas[i];
                encontradaUltima = true;
            }
        }

        if(ultimaAniadida != null && ultimaAniadida.getAnioNacimientoNumero()/100 == cientifica.getAnioNacimientoNumero()/100){ //Que no sea del mismo siglo que la anterior
            esValida = false;
        }

        return esValida;
    }
}