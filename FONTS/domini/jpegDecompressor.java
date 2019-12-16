package domini;

import java.io.*;
import java.util.*;

import domini.testsDelQuim.huffmannTest;

public class jpegDecompressor extends jpeg {
   public jpegDecompressor(final String treta) {
       // Aqui anira la crida a la classe imatge comprimida que donarà d'una imatge
       // jpeg una cosa llegible
       // De moment això no està implememtat i esta "emulat" per la classe
       // llegeixdefitxer
       // llegeixdefitxe
       //imatge = new Vector<Color>();
       String am = "";
       String al = "";
       int it = 0;
       char[] aux = treta.toCharArray();
        while (aux[it] != ' '){
            am += aux[it];
            it++;
        }
        it++;
        while (aux[it] != '\n'){
            al += aux[it];
            it++;
        }
        imatge = new Imatge(Integer.parseInt(am)*Integer.parseInt(al));
        imatge.setAmple(Integer.parseInt(am));
        imatge.setAlt(Integer.parseInt(al));
        HuffmanTree huff = new HuffmanTree();
        Vector <Integer> tot = huff.decode(treta.substring(it));
        Y.addAll(tot.subList(0, imatge.getAlt()*imatge.getAmple()));
        Cb.addAll(tot.subList(imatge.getAlt()*imatge.getAmple(), imatge.getAlt()*imatge.getAmple()*2));
        Cr.addAll(tot.subList(imatge.getAlt()*imatge.getAmple()*2, imatge.getAlt()*imatge.getAmple()*3));

   }


//    @Override
//    protected void creaImatge (final String path) throws IOException {
//        String header = "P6\n" + Integer.toString(ample) + " " + Integer.toString(alt) + "\n255\n";
//        FileOutputStream Hd = null;  //cal canviar-ho perque no estigui hardcoded
//        Hd = new FileOutputStream("out.ppm");
//        DataOutputStream Hf = new DataOutputStream(Hd);
//        Hf.writeBytes(header);
//        for (Color i : imatge){
//            Hf.writeByte((byte)i.r);
//            Hf.writeByte((byte)i.g);
//            Hf.writeByte((byte)i.b);
//        }
//        Hf.close();
//    }


   @Override
   protected void operaYCbCr(){
      for (Color c : imatge.getImatge()) {
           double Y = (double) c.getR();
           double Cb = (double) c.getG();
           double Cr = (double) c.getB();

           c.setR((int) (Y + 1.40200 * (Cr - 0x80)));
           c.setG((int) (Y - 0.34414 * (Cb - 0x80) - 0.71414 * (Cr - 0x80)));
           c.setB((int) (Y + 1.77200 * (Cb - 0x80)));
       }

   }

   private static int[][] transformDCTs (final int DCT[][]){
       //Vector de coeficients
       final double[] c = new double[8];
       //Inicialitzo coeficients
       for (int i=1;i<8;i++) {
           c[i]=1;
       }
       c[0]=1/Math.sqrt(2.0);
       int[][] f = new int[8][8];
       for (int i=0;i<8;i++) {
           for (int j=0;j<8;j++) {
               double sum = 0.0;
               for (int u=0;u<8;u++) {
                   for (int v=0;v<8;v++) {
                       sum+=((2*c[u]*c[v])/Math.sqrt(8*8))*Math.cos(((2*i+1)/(2.0*8))*u*Math.PI)*Math.cos(((2*j+1)/(2.0*8))*v*Math.PI)*DCT[u][v];
                   }
               }
               f[i][j]=(int)Math.round(sum);
           }
       }
       return f;
   }


   private Vector<Color> getImageArray(){ //Falta repassar aqui.
       Vector <Color> res = new Vector<Color>();
       res.setSize(imatge.getAlt()*imatge.getAmple());
       int i = 0;
       int j = 0;
       int ms = imatge.getAmple()/8; //nombre de matrius que hi ha per fila
       int matriu = 0;// matriu a la que accedeixo
       int salt = 0; // per poder omplir les matrius de baix
       for (int fila = 0; fila < imatge.getAlt(); fila++) {
           for (int col = 0; col < imatge.getAmple(); col++) {
               Color coloret = new Color(0,0,0);
               // if (col >= ample || fila >= alt) {
               //     coloret = lastColoret; // Per tenir l'ultim color si no coincideix
               // } else {
               // coloret = this.imatge.get(fila * ample + col);
               //     lastColoret = coloret;
               // }
               j = j % 8;
               matriu = matriu % ms;
               i %= 8;
               // System.out.println("Z: " + z + " matriu: " + matriu + " i: " + i + " j: " + j
               // + " valor: " + this.imatge.get(z).r);
               // print();
               // System.out.println("bchdsjla: " + this.imatge.get(z).r + " Amb z = " + z);
               // DCTilu[matriu + salt][i][j] = coloret.r - 127;
               // DCTcr[matriu + salt][i][j] = coloret.g - 127;
               // DCTcb[matriu + salt][i][j] = coloret.b - 127;
               coloret.setR(DCTilu[matriu + salt][i][j] + 127);  //Y
               coloret.setG(DCTilu[matriu + salt][i][j] + 127); //Cb
               coloret.setB(DCTilu[matriu + salt][i][j] + 127); //Cr
               res.set(fila*imatge.getAmple() + col, coloret);
               DCTilu[matriu+salt][i][j] = 0; //Això és per comprovar que funciona bé l'algoritme.
               j++;
               if (j == 8) {
                   matriu++;
               }
           }
           i++;
           if (i % 8 == 0) {
               salt += ms; // cada cop que han passat 8 files cal saltar la fila de la columna.
           }
       }
       return res;
   }

   @Override
   protected void transformBlocks(){
//       imatge = new Imatge();
//       imatge.setSize(ample*alt); //formato el tamany del vector de colors
       DCTilu = new int[DCTiluTrans.length][8][8];
       DCTcb = new int[DCTiluTrans.length][8][8];
       DCTcr = new int[DCTiluTrans.length][8][8];
       //Té dos passos.
       //1. DCT3
       //2. sumar 127 als valors per tenir bé les matrius.
       //////
       System.out.println("Printo la matriu DCTiluTrans[0]:");
       for (int i = 0; i < 8; i++){
           for (int j = 0; j < 8; j++){
               System.out.print(DCTiluTrans[0][i][j]);
               System.out.print(" ");
           }
           System.out.println();
       }

       /////////
       for (int i = 0; i < DCTiluTrans.length; i++){
           DCTilu[i] = transformDCTs(DCTiluTrans[i]);
           DCTcb[i] = transformDCTs(DCTcbTrans[i]);
           DCTcr[i] = transformDCTs(DCTcrTrans[i]);
       }
       System.out.println("Printo la matriu DCTilu[0]:");
       for (int i = 0; i < 8; i++){
           for (int j = 0; j < 8; j++){
               System.out.print(DCTilu[0][i][j]);
               System.out.print(" ");
           }
           System.out.println();
       }
       //Aquí ja tenim les matrius transformades. Procedim a la suma i a colocar
       imatge.setImatge(getImageArray()); //Obtinc la imatge, només em falta parsejarla a PPM


   }

   private static void desfeszigzag(final int[][] matrix, final Vector<Integer> a){
       int block = 64;
       int index = 0;
       boolean dir = false;// true = cap amunt, false cap abaix;
       boolean direcciopos = true; //Pos ha d'augmentar fins a la meitat
       int pos = 0;
       int i = 0;
       int j = 0;
       while(block != 0){
           do {
               //System.out.println(index);
               block--; //s'ha computat un bloc més
               matrix[i][j] = a.get(0); //Pillo el primer que hi ha al vector i el borro perque estan tots en fileta. El poso on toca.
               a.remove(0);
             if (dir){ //estic de pujada
                 i -= 1;
                 j += 1;
             } else { //estic de baixada
                 i += 1;
                 j -= 1;
               }
               index++;
           } while(i >= 0 && i < 8 && j >=0 && j < 8);
           //sha acabat una diagonal
           dir = !dir;
           pos++;
           if (pos == 8){
               direcciopos = !direcciopos;
               pos = 1;
           }
            //Pos sempre ha d'incrementar.
           // if(direcciopos){
           //     pos++;
           // } else{
           //     pos--;
           // }
           if (direcciopos){ //Si estic a la primera meitat de la matriu
               if(dir){ //sha anat cap baix, cal anar cap amunt
                   i = pos;
                   j = 0;
               } else {
                   i = 0;
                   j = pos;
               }//sha anat cap amunt i cal anar cap a baix
           } else {  //Si estic a la segona meitat de la matriu
               if(dir){ //sha anat cap baix, cal anar cap amunt
                   i = 7;
                   j = pos;
               } else {
                   i = pos;
                   j = 7;
               }//sha anat cap amunt i cal anar cap a baix
           }

       }
   }

   private void preparaMatrius(){ 
       final int nmats = Y.size()/64; //Obtinc quantes matrius hi ha en total
       DCTiluTrans = new int[nmats][8][8];
       DCTcrTrans = new int[nmats][8][8];
       DCTcbTrans = new int[nmats][8][8];
       System.out.println("Computo el zigzag Y");
       int count = 0;
       //System.out.println(Y.size());
       for (final int[][] matrix : DCTiluTrans) {
//            System.out.println(count);
           count++;
           desfeszigzag(matrix, Y);
       }
       System.out.println("Computo el zigzag Cr");
       for (final int[][] matrix : DCTcrTrans) {
           desfeszigzag(matrix, Cr);
       }
       System.out.println("Computo el zigzag Cb");
       for (final int[][] matrix : DCTcbTrans) {
           desfeszigzag(matrix, Cb);
       }
       System.out.println("Printo la matriu DCTiluTrans[0]:");
       for (int i = 0; i < 8; i++){
           for (int j = 0; j < 8; j++){
             System.out.print(DCTiluTrans[0][i][j]);
             System.out.print(" ");
           }
           System.out.println();
       }
   }

   private void multiplica(){
       for (int matrix = 0; matrix < DCTiluTrans.length; matrix++) {
           for (int i = 0; i < 8; i++) {
               for (int j = 0; j < 8; j++) {
                   DCTiluTrans[matrix][i][j] *= jpeg.QUANTY[i][j];
                   DCTcrTrans[matrix][i][j] *= jpeg.QUANTC[i][j];
                   DCTcbTrans[matrix][i][j] *= jpeg.QUANTC[i][j];
               }
           }
       }
   }

   @Override
   public String compress(){
       //No fa res perquè aquesta classe no comprimeix. Miraré de fer això millor de cara a la segona entrega.
       return null;
   }

   @Override
   public String decompress() {
       //Obtinc els vectors (aixó canviarà a la segona entrega)
    //    try {
    //        llegeixdefitxer(path);
    //    } catch (final IOException e) {
    //        // TODO Auto-generated catch block
    //        e.printStackTrace();
    // //    }

       preparaMatrius();  //Desfem les matrius i queden les matrius transformades.
       multiplica(); //Multipliquem les matrius per retornar el valor "Original"
       transformBlocks(); //Transformen les dctstrans a dcts matrius de YCbCr DCT3
       operaYCbCr();
       String fin = "";
       try {
           creaImatge("out.ppm");
       } catch (IOException e) {
           e.printStackTrace();
       }
       return fin; //Això canviarà de cara a la segona entrega.
   }

    @Override
    protected void creaImatge(String path) throws IOException {
        //Tot aixo cal canviar-ho encara que va bé pel debugging
        String header = "P6\n" + Integer.toString(imatge.getAmple()) + " " + Integer.toString(imatge.getAlt()) + "\n255\n";
        FileOutputStream Hd = null;  //cal canviar-ho perque no estigui hardcoded
        Hd = new FileOutputStream("out.ppm");
        DataOutputStream Hf = new DataOutputStream(Hd);
        Hf.writeBytes(header);
        for (Color i : imatge.getImatge()){
            Hf.writeByte((byte)i.getR());
            Hf.writeByte((byte)i.getG());
            Hf.writeByte((byte)i.getB());
        }
        Hf.close();

//        String result = "";
//        result += header;
//        return result;

    }

}