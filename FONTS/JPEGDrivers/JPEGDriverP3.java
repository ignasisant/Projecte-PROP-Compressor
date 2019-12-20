package JPEGDrivers;

import domini.Fitxer;
import domini.JPEGCompressor;
import domini.JPEGDecompressor;

import java.util.Vector;


public class JPEGDriverP3 {
    public static void main(String[] args) throws Exception {
//
//        byte[] b = {'a',(byte)0x00,'c'};
//        String i = new String(b);
//        System.out.println(i.length());
//        System.out.println(i);


        Fitxer f = new Fitxer();


        String fitxer = f.llegirFitxer("./FONTS/JPEGDrivers/ppms/image.ppm");
        System.out.println("Començo a comprimir");
        System.out.println("Començo a comprimir");
        JPEGCompressor j = new JPEGCompressor();
        j.setData(fitxer);

        String comprimit = j.compress();
        //Compessio acabada

        Vector<Integer> debugging = j.getDebugging();

        JPEGDecompressor dj = new JPEGDecompressor();
        //dj.setDebugging(debugging, 56, 56);
        dj.setData(comprimit);  //el huffmann es queda encallat aquí.
        String Descomprimit = dj.decompress();
        f = new Fitxer();
        //f.writeToFile(Descomprimit,"out_amb_huffman.ppm");
        f.writeToFile(Descomprimit,"./outout.ppm");

    }

}
