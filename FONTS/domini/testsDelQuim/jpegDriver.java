package domini.testsDelQuim;

import domini.Algorithm;
import domini.Fitxer;
import domini.jpegCompressor;
import domini.jpegDecompressor;

import java.io.File;
import java.io.IOException;
import java.util.Vector;


public class jpegDriver {
    public static void main(String[] args) throws Exception {
        Fitxer f = new Fitxer();
        String fitxer = f.llegirFitxer("./ppms/stalin.ppm");
        System.out.println("Començo a comprimir");
        jpegCompressor j = new jpegCompressor();
        j.setData(fitxer);

        String comprimit = j.compress();
        //Compessio acabada

        Vector<Integer> debugging = j.getDebugging();

        jpegDecompressor dj = new jpegDecompressor();
        dj.setDebugging(debugging, 56, 56);
        //dj.setData(comprimit);
        String Descomprimit = dj.decompress();
        f = new Fitxer();
        f.writeToFile(Descomprimit,"out_sense_huffman.ppm");

    }

}
