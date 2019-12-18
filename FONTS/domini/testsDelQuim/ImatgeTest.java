package domini.testsDelQuim;

import domini.Fitxer;
import domini.Imatge;

import java.io.File;
import java.io.IOException;

public class ImatgeTest {

    public static void main(String[] args) throws IOException {
        Fitxer f = new Fitxer();
        String img = (f.llegirFitxer("./ppms/blanc.ppm"));
        Imatge imatge = new Imatge();
        imatge.creaImatgeDePPM(img);

        System.out.println("Provat amb el que tenia abans:");
        //imatge.creaImatge("./boxes_1.ppm");
    }
}
