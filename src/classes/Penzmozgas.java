package classes;

public class Penzmozgas {

    private String datum;
    private char tipus;
    private String megnevezes;
    private int osszeg;
    private String fizetesiMod = null;

    //Konstruktor(ok)
    public Penzmozgas(String datum, char tipus, String megnevezes, int osszeg) {
        this.datum = datum;
        this.tipus = tipus;
        this.megnevezes = megnevezes;
        this.osszeg = osszeg;
    }

    //Getter(ek)
    public String getDatum() {
        return datum;
    }

    public char getTipus() {
        return tipus;
    }

    public String getMegnevezes() {
        return megnevezes;
    }

    public int getOsszeg() {
        return osszeg;
    }

    public String getFizetesiMod() {
        return fizetesiMod;
    }

    // Setter(ek)
    public void setFizetesiMod(String fizetesiMod) {
        this.fizetesiMod = fizetesiMod;
    }

    @Override
    public String toString(){
        String penzmozgasTipus = tipus == 'K' ? "Kiadás" : "Bevétel";
        return String.format("%s:%s -> %s (%d Ft)", penzmozgasTipus, datum, megnevezes, osszeg);
    }
}
