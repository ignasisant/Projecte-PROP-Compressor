package domini;

public class FileNotSelected  extends Exception {
    public FileNotSelected() {
        super("El fitxer a comprimir/descomprimir no ha estat seleccionat");
    }
}

