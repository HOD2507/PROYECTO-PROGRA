public class Cientifica { // Define la clase Cientifica
    private final String nombre; // Nombre de la científica
    private final String anioNacimiento; // Año de nacimiento de la científica en formato de texto
    private final int anioNacimientoNumerico; // Año de nacimiento de la científica en formato numérico
    private final String fechaPublicacion; // Fecha propuesta para la publicación
    private final String biografia; // Biografía de la científica
    private final String especialidad; // Especialidad de la científica
    private final String tieneCartel; // Indica si tiene cartel (sí/no)
    private String anioCartel; // Año en el que se realizó el cartel
    private final String otrosCarteles; // Indica si hay otros carteles
    private final String instagram; // Nombre de usuario de Instagram
    private final String pais; // País de origen de la científica
    private final String wikipedia; // Indica si tiene página en Wikipedia y en qué idioma

    // Constructor: Inicializa todos los campos de la clase
    public Cientifica(String nombre, String anioNacimiento, String fechaPublicacion, String biografia, String especialidad, String tieneCartel, String anioCartel, String otrosCarteles, String instagram, String pais, String wikipedia) {
        this.nombre = nombre; // Asigna el nombre de la científica
        this.anioNacimiento = anioNacimiento; // Asigna el año de nacimiento en formato de texto
        if (!anioNacimiento.equals("-") && !anioNacimiento.equals("Año nacimiento")) { // Comprueba si el año de nacimiento es conocido y no es el encabezado de la columna
            this.anioNacimientoNumerico = Integer.parseInt(anioNacimiento); // Convierte el año de nacimiento a número
        } else {
            this.anioNacimientoNumerico = 0; // Si no se conoce el año de nacimiento, asigna 0
        }
        //Asignamos los datos
        this.fechaPublicacion = fechaPublicacion; // Asigna la fecha propuesta para la publicación
        this.biografia = biografia; // Asigna la biografía de la científica
        this.especialidad = especialidad; // Asigna la especialidad de la científica
        this.tieneCartel = tieneCartel; // Asigna si tiene cartel (sí/no)
        this.anioCartel = anioCartel; // Asigna el año en que se realizó el cartel
        this.otrosCarteles = otrosCarteles; // Asigna si hay otros carteles
        this.instagram = instagram; // Asigna el nombre de usuario de Instagram
        this.pais = pais; // Asigna el país de origen de la científica
        this.wikipedia = wikipedia; // Asigna si tiene página en Wikipedia y en qué idioma
    }

    // Métodos Getters para obtener los valores de los atributos
    public String getNombre() {
        return nombre; // Retorna el nombre de la científica
    }

    public String getBiografia() {
        return biografia; // Retorna la biografía de la científica
    }

    public String getEspecialidad() {
        return especialidad; // Retorna la especialidad de la científica
    }

    public String getTieneCartel() {
        return tieneCartel; // Retorna si tiene cartel (sí/no)
    }

    public String getAnioCartel() {
        return anioCartel; // Retorna el año en que se realizó el cartel
    }

    public String getOtrosCarteles() {
        return otrosCarteles; // Retorna si hay otros carteles
    }

    public String getInstagram() {
        return instagram; // Retorna el nombre de usuario de Instagram
    }

    public String getPais() {
        return pais; // Retorna el país de origen de la científica
    }

    public String getWikipedia() {
        return wikipedia; // Retorna si tiene página en Wikipedia y en qué idioma
    }

    public String getAnioNacimiento() {
        return anioNacimiento; // Retorna el año de nacimiento en formato de texto
    }

    public void setAnioCartel(String anioCartel) {
        this.anioCartel = anioCartel; // Asigna el año en que se realizó el cartel
    }

    public String getFechaPublicacion() {
        return fechaPublicacion; // Retorna la fecha propuesta para la publicación
    }

    public int getAnioNacimientoNumerico() {
        return anioNacimientoNumerico; // Retorna el año de nacimiento en formato numérico
    }

    // Convierte los datos del objeto a una cadena formateada para CSV
    public String aCadenaCsv() {
        return getNombre() + ";" + getAnioNacimiento() + ";" + getFechaPublicacion() + ";" + getBiografia() + ";" +
                getEspecialidad() + ";" + getTieneCartel() + ";" + getAnioCartel() + ";" + getOtrosCarteles() + ";" +
                getInstagram() + ";" + getPais() + ";" + getWikipedia(); // Retorna los datos en formato CSV
    }

    // Muestra los datos del objeto en un formato legible
    public String mostrar() {
        return getNombre() + "\n" + getAnioNacimiento() + "\n" + getFechaPublicacion() + "\n" + getBiografia() + "\n" +
                getEspecialidad() + "\n" + getTieneCartel() + " " + getAnioCartel() + " " + getOtrosCarteles() + "\n" +
                getInstagram() + " " + getPais() + " " + getWikipedia(); // Retorna los datos en un formato legible
    }
}

