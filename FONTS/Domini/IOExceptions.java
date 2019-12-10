package algoritmes;

class FileNotSelected  extends Exception {
    FileNotSelected() {
        super("El fitxer a comprimir/descomprimir no ha estat seleccionat");
    }
}

class AlgorithmNotSelected  extends Exception {
    AlgorithmNotSelected() {
        super("El algoritme a comprimir/descomprimir el fitxer no ha estat seleccionat");
    }
}

class InvalidAlgorithm  extends Exception {
    InvalidAlgorithm() {
        super("Algorithme no seleccionat invalid");
    }
}

class ActionNotSelected  extends Exception {
    ActionNotSelected() {
        super("Acció no seleccionada");
    }
}

