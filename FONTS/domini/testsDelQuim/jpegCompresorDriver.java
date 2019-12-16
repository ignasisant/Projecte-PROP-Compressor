package domini.testsDelQuim;

import domini.Fitxer;

import java.io.File;


public class jpegCompresorDriver {
    public static void main(String[] args) {
        Fitxer f = new Fitxer();
        System.out.println(f.llegirFitxer(new File("./boxes_1.ppm")));

    }

}
