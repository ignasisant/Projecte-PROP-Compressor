//autor: joaquim.ferrer


//autor: joaquim.ferrer

package domini;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;



public class Imatge{
    private int ample;
private int alt;
private int maxVal; // Color maxim de valor
private String magicNum;
   private Vector<Color> imatge;

   public Imatge(){

   }

   public Imatge (String path) throws IOException {
       creaImatge(path);
   }


   private void creaImatge(String path) throws IOException {
       // String path = "./boxes_1.ppm";
       File image = new File(path);
       if (!image.exists()) {
           System.out.println("No existeix la imatge");
       }

       BufferedReader br = new BufferedReader(new FileReader(image)); // permet llegir linies cosa que mola
       FileInputStream fin = new FileInputStream(path); // el necessitarem per llegir shorts
       this.magicNum = br.readLine();
       if (magicNum != "P6"){
           //llançó excepció.
       }
       String line = br.readLine();
       ArrayList<String> a = new ArrayList<String>();
       for (String s : line.split(" ")) {
           a.add(s);
       }
       this.ample = Integer.parseInt(a.get(0));
       this.alt = Integer.parseInt(a.get(1));
       a = null; // elimino l'objecte per no gastar memòria dinàmica.
       this.maxVal = Integer.parseInt(br.readLine()); // cardem el valor més alt de Color. En funció d'aquest hi ha
       // altres params
       if (maxVal < 0 || maxVal > 65536) { // Format incorrecte. Cal excepció?
           System.out.println("Quillo t'has colao");
           // no se que ficar-hi aqui
       }

       // Xivatos zone
       // System.out.println(magicNum);
       // System.out.println(ample);
       // System.out.println(alt);
       // System.out.println(maxVal);

       DataInputStream din = new DataInputStream(fin); // Inicialitzo per als shortsprivate  String magicNum;
       imatge = new Vector<Color>(); // Colorets
       imatge.setSize(alt * ample);
       if (maxVal >= 256) { // Cada valor de Color son dos bytes
           int R, G, B; // Els bytes entren per triades
           for (int i = 0; i < alt; i++) {
               for (int j = 0; j < ample; j++) {
                   R = (int) din.readUnsignedShort();
                   G = (int) din.readUnsignedShort();
                   B = (int) din.readUnsignedShort();
                   imatge.set(i * j + j, new Color(R, G, B));
               }
           }
       } else { // un byte
           short R, G, B; // Els short entren per triades
           for (int i = 0; i < ample; i++) {
               for (int j = 0; j < ample; j++) {
                   R = (short) din.readUnsignedByte();
                   G = (short) din.readUnsignedByte();
                   B = (short) din.readUnsignedByte();
                   int pos = i * ample + j;
                   imatge.set(pos, new Color(R, G, B));
               }
           }
       }
       din.close();
       br.close();

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
}