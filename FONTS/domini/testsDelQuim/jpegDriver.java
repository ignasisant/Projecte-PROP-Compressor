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
//
//        byte[] b = {'a',(byte)0x00,'c'};
//        String i = new String(b);
//        System.out.println(i.length());
//        System.out.println(i);


        Fitxer f = new Fitxer();
        String fitxer = f.llegirFitxer("./ppms/2pixel.ppm");
        System.out.println("Començo a comprimir");
        System.out.println("Començo a comprimir");
        jpegCompressor j = new jpegCompressor();
        j.setData(fitxer);

        String comprimit = j.compress();
        //Compessio acabada

        Vector<Integer> debugging = j.getDebugging();

        jpegDecompressor dj = new jpegDecompressor();
        //dj.setDebugging(debugging, 56, 56);
        dj.setData(comprimit);  //el huffmann es queda encallat aquí.
        String Descomprimit = dj.decompress();
        f = new Fitxer();
        f.writeToFile(Descomprimit,"out_amb_huffman.ppm");
        //f.writeToFile(Descomprimit,"out_sense_huffman.ppm");

    }

}
