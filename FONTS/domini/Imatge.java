//autor: joaquim.ferrer


//autor: joaquim.ferrer

package domini;

import java.io.*;
import java.util.ArrayList;
import java.util.Vector;



public class Imatge{
    private int ample;
private int alt;
private int maxVal; // Color maxim de valor
private String magicNum;
   private Vector<Color> imatge;

   public Imatge(){
       imatge = new Vector<Color>(); // Colorets
    }
    public Imatge(int size){
        imatge = new Vector<Color>(size); // Colorets
    }

//   public Imatge (String path) throws IOException {
//       imatge = new Vector<Color>(); // Colorets
//       creaImatge(path);
//   }

//    public void creaImatgeDePPM(String img){
//       System.out.println(img.length());
//       int it = 0;
//       char[] imag = img.toCharArray();
//       String aux = "";
//       it = img.indexOf('P');
//       magicNum = img.substring(it, it+2);
//       img = img.substring(it+3);
//        while (img.charAt(0) == '#'){ //retallo la string
//           it = img.indexOf('\n') + 1;
//           img = img.substring(it);
//       }
//        aux = img.substring(0,img.indexOf(' '));
//        ample = Integer.parseInt(aux);
//        aux = img.substring(img.indexOf(' ')+1, img.indexOf('\n'));
//        alt = Integer.parseInt(aux);
//        img = img.substring(img.indexOf('\n')+1);
//        while (img.charAt(0) == '#'){ //retallo la string
//            it = img.indexOf('\n') + 1;
//            img = img.substring(it);
//        }
//        maxVal = Integer.parseInt(img.substring(0, img.indexOf('\n')));
//        img = img.substring(img.indexOf('\n')+1);
//        if (maxVal != 255){
////            llençar excepcio
//        }
//        System.out.println("La imatge en strinng te ara" + img.length() + " chars o bytes ni idea");
//        String contingut = img;
//        System.out.println(magicNum);
//        System.out.println(alt);
//        System.out.println(ample);
//        System.out.println(aux);
//        System.out.println(it);
//        System.out.println("Printo Imatge \n" + contingut);
//        char[] bytes = contingut.toCharArray();
//        System.out.println("Nombre de chars de la imatge: " + bytes.length); //Aixo no dona i no se perquè
//        imatge = new Vector<Color>();
//        imatge.setSize(ample*alt);
//        int cont = 0;
//        for (int i = 0; i < bytes.length; i+=3){  //tenir molt en compte que un char son dos bytes!!!! jo només en vull un.
//            int r = (byte)bytes[i];
//            int g = (byte)bytes[i+1];
//            int b = (byte)bytes[i+2];
//            if (r < 0) r = 256 + r;
//            if (g < 0) g = 256 + g;
//            if (b < 0) b = 256 + b;
//
//
//            Color c = new Color(r, g, b);
//            imatge.setElementAt(c, cont);  //Això m'ha d'omplir la imatge
//            cont++;
//        }
//        System.out.println("");
//    }

    public void operaFromYCbCr(){

        for (Color c : imatge) {
            double Y = (double) c.getR();
            double Cb = (double) c.getG();
            double Cr = (double) c.getB();
            double R = (Y + (1.402 * (Cr - 128)));
            double G = (Y - 0.34414 * (Cb - 128) - 0.71414 * (Cr - 128));
            double B = (Y + 1.772 * (Cb - 128));
            c.setR((int)R+20);
            c.setG((int)G+20);
            c.setB((int)B+20);
        }

    }

    public void operaToYCbCr() { // done
        for (Color i : imatge){
            int R = i.getR();
            int G = i.getG();
            int B = i.getB();
            int y =  (int) ( 16 + ((65.738/256) * R) + ((129.057/256) * G) + ((25.064/256) * B));//16 + (((R << 6) + (R << 1) + (G << 7) + G + (B << 4) + (B << 3) + B) >> 8);
            int  Cb =  (int) ( 128 - ((37.945/256) * R) - ((74.494/256) * G) + ((112.439/256) * B) ); //128 + ((-((R << 5) + (R << 2) + (R << 1)) - ((G << 6) + (G << 3) + (G << 1)) + (B << 7)
            // - (B << 4)) >> 8);
            int Cr = (int) ( 128 + ((112.439/256) * R) - ((94.154/256) * G) - ((18.285/256) * B ) );//128 + (((R << 7) - (R << 4) - ((G << 6) + (G << 5) - (G << 1)) - ((B << 4) + (B << 1))
            i.setR(y);
            i.setG(Cb);
            i.setB(Cr);
        }
    }

    public void creaImatgeDePPM(String img) throws PPMBadFormatted {
        System.out.println(img.length());
        int it = 0;
        char[] imag = img.toCharArray();
        String aux = "";
        it = img.indexOf('P');
        if (img.charAt(it+1) != '6') throw new PPMBadFormatted(); //No és p6 la imatge
        if (img.charAt(it+1) == '\r') it++;
        magicNum = img.substring(it, it+2);
        img = img.substring(it+3);
        while (img.charAt(0) == '#'){ //retallo la string
            it = img.indexOf('\n') + 1;
            img = img.substring(it);
        }
        int a;
        if (img.charAt(0) == '\n') a = 1;
        else a = 0;
        aux = img.substring(a,img.indexOf(' '));
        ample = Integer.parseInt(aux);
        if (img.charAt(0)=='\n') img = img.substring(1);
        aux = img.substring(img.indexOf(' ')+1, img.indexOf('\n'));
        if (aux.charAt(aux.length()-1) == '\r') aux=aux.substring(0,aux.length()-1);
        alt = Integer.parseInt(aux);
        img = img.substring(img.indexOf('\n')+1);
        while (img.charAt(0) == '#'){ //retallo la string
            it = img.indexOf('\n') + 1;
            img = img.substring(it);
        }
        if (img.charAt(img.indexOf('\n')-1) == '\r') it = img.indexOf('\r');
        else it = img.indexOf('\n');
        maxVal = Integer.parseInt(img.substring(0, it));
        img = img.substring(img.indexOf('\n')+1);
        if (maxVal != 255){
            throw new PPMBadFormatted();

        }
        System.out.println("La imatge en strinng te ara" + img.length() + " chars o bytes ni idea");
        String contingut = img;
        System.out.println(magicNum);
        System.out.println(alt);
        System.out.println(ample);
        System.out.println(aux);
        System.out.println(it);
        System.out.println("Printo Imatge \n" + contingut);
        char[] bytes = contingut.toCharArray();
        System.out.println("Nombre de chars de la imatge: " + bytes.length); //Aixo no dona i no se perquè
        imatge = new Vector<Color>();
        imatge.setSize(ample*alt);
        int cont = 0;
        if (bytes.length != alt*ample*3) throw new PPMBadFormatted();
        for (int i = 0; i < bytes.length; i+=3){  //tenir molt en compte que un char son dos bytes!!!! jo només en vull un.
            int r = (byte)bytes[i];
            int g = (byte)bytes[i+1];
            int b = (byte)bytes[i+2];
            if (r < 0) r = 256 + r;
            if (g < 0) g = 256 + g;
            if (b < 0) b = 256 + b;


            Color c = new Color(r, g, b);
            imatge.setElementAt(c, cont);  //Això m'ha d'omplir la imatge
            cont++;
        }
        System.out.println("");
    }


//

    public String retallaHeaders(String data){
        String am = "";
        String al = "";
        int it = 0;
        char[] aux = data.toCharArray();
        while (aux[it] != ' '){
            am += aux[it];
            it++;
        }
        it++;
        while (aux[it] != '\n'){
            al += aux[it];
            it++;
        }
        ample = Integer.parseInt(am);
        alt = Integer.parseInt(al);
        it++;
        return data.substring(it);
    }

    public String creaImatgeFinal(){  //aixo ho canviarem aquesta tarda per un mètode de la classe imatge
        //Tot aixo cal canviar-ho encara que va bé pel debugging
        String header = "P6\n" + Integer.toString(ample) + " " + Integer.toString(alt) + "\n255\n";
        StringBuilder result = new StringBuilder();
        for (Color i : imatge){
            result.append((byte) i.getR()).append((byte) i.getG()).append((byte) i.getB());
        }
        return header + result; //la imatge ve per aquí.
//        FileOutputStream Hd = null;  //cal canviar-ho perque no estigui hardcoded
//        Hd = new FileOutputStream("out.ppm");
//        DataOutputStream Hf = new DataOutputStream(Hd);
//        Hf.writeBytes(header);
//        for (Color i : imatge.getImatge()){
//            Hf.writeByte((byte)i.getR());
//            Hf.writeByte((byte)i.getG());
//            Hf.writeByte((byte)i.getB());
//        }
//        Hf.close();

//        String result = "";
//        result += header;
//        return result;

    }


   public Color getColorPerIndex(int index){
       return this.imatge.get(index);
   }

   public void setColorPerIndex(int index, Color valor){
       imatge.set(index, valor);
   }

    public int getAmple() {
        return this.ample;
    }

    public void setAmple(int ample) {
        this.ample = ample;
    }

    public int getAlt() {
        return this.alt;
    }

    public void setAlt(int alt) {
        this.alt = alt;
    }

    public int getMaxVal() {
        return this.maxVal;
    }

    public void setMaxVal(int maxVal) {
        this.maxVal = maxVal;
    }

    public String getMagicNum() {
        return this.magicNum;
    }

    public void setMagicNum(String magicNum) {
        this.magicNum = magicNum;
    }
    public Vector<Color> getImatge(){
        return this.imatge;
    }

    public void setImatge(Vector<Color> im) {
        this.imatge = im;
    }
}