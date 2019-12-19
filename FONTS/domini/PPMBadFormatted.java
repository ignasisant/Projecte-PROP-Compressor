package domini;

public class PPMBadFormatted extends Exception {
        public PPMBadFormatted() {
            super("El PPM introduit no compleix els estàndards. Ha de ser P6 i amb un color màxim de 255");
        }

}
