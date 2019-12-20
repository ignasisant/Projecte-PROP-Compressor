package domini;

public class PPMTooBig extends Exception {
    public PPMTooBig() {
        super("El PPM és massa gran i pot petar la pila. No es podrà comprimir amb JPEG");
    }

}
