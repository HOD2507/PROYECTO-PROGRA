public class Cientifica {
    private final String nombre;
    private final String anioNacimiento;
    private final int anioNacimientoNumero;
    private final String fecha;
    private final String biografia;
    private final String especialidad;
    private final String cartel;
    private String anioCartel;
    private final String otrosCarteles;
    private final String instagram;
    private final String pais;
    private final String wikipedia;

    //Científicas;Año nacimiento;Fecha;Bio;Especialidad;Cartel;Año;otrosCarteles;Instagram;Pais;Wikipedia

    public Cientifica(String nombre, String anioNacimiento, String fecha, String biografia, String especialidad, String cartel, String anioCartel
            , String otrosCarteles, String instagram, String pais, String wikipedia) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        if(!anioNacimiento.equals("-") && !anioNacimiento.equals("Año nacimiento")){
            this.anioNacimientoNumero = Integer.parseInt(anioNacimiento);
        }else
            this.anioNacimientoNumero = 0;
        this.fecha = fecha;
        this.biografia = biografia;
        this.especialidad = especialidad;
        this.cartel = cartel;
        this.anioCartel = anioCartel;
        this.otrosCarteles = otrosCarteles;
        this.instagram = instagram;
        this.pais = pais;
        this.wikipedia = wikipedia;
    }

    // Getters and setters

    public String getNombre() {
        return nombre;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public String getCartel() {
        return cartel;
    }

    public String getAnioCartel() {
        return anioCartel;
    }

    public String getOtrosCarteles() {
        return otrosCarteles;
    }

    public String getInstagram() {
        return instagram;
    }

    public String getPais() {
        return pais;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public String getAnioNacimiento() {
        return anioNacimiento;
    }

    public void setAnioCartel(String anioCartel) {
        this.anioCartel = anioCartel;
    }

    public String getFecha() {
        return fecha;
    }

    public int getAnioNacimientoNumero() {
        return anioNacimientoNumero;
    }

    public String toString() {
        return getNombre() + ";" + getAnioNacimiento() + ";" + getFecha() + ";" + getBiografia() + ";" +
                getEspecialidad() + ";" + getCartel() + ";" + getAnioCartel() + ";" + getOtrosCarteles() + ";" +
                getInstagram() + ";" + getPais() + ";" + getWikipedia();
    }
    public String mostrar() {
        return getNombre() + "\n" + getAnioNacimiento() + "\n" + getFecha() + "\n" + getBiografia() + "\n" +
                getEspecialidad() + "\n" + getCartel() + " " + getAnioCartel() + " " + getOtrosCarteles() + "\n " +
                getInstagram() + " " + getPais() + " " + getWikipedia();
    }
}
