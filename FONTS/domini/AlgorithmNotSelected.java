package domini;


public class AlgorithmNotSelected  extends Exception {
    public AlgorithmNotSelected() {
        super("El algoritme a comprimir/descomprimir el fitxer no ha estat seleccionat");
    }
}

