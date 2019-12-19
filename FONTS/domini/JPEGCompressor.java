package domini;

import java.io.*;
import java.util.*;
//import java.awt.Desktop;


//Cal que consideri com ho tractaré quan el nombre del color sigui més gran
//de 255 o més petit. El valor del RGB cal que estigui sempre entre 0 i 255

/*
1. Transform RGB to YCbCr
2. Preprossessing for DCT transformation
2.1. Crear les matrius de 8x8 de illumination i de crominance
2.2. Restar 127 a cada valor
3. DCT (necessitarem taules) /ho he de fer per cada valor del YCrCb?
4. Huffman
5. de Huffman a JPEG //això no se fer-ho encara




*/

//TODO: Iniciar estructures de dades a la creadora.

public class JPEGCompressor extends JPEG {

    private String data;

   public JPEGCompressor() {
       // Iniciar estructures de dades.
       imatge = new Imatge();

   }




    @Override
    public void setData(String data) {
        this.data = data;
    }

    public String getData () {
       return this.data;
    }


//
//   public void print() { // mostra tota la image per cada unitat rgb que tenim per les proves
//       for (int i = 0; i < imatge.getAlt(); i++) {
//           for (int j = 0; j < imatge.getAmple(); j++) {
//               System.out.printf("%s", this.imatge.getColorPerIndex(i * j + j).getR());
//               System.out.printf("%s", this.imatge.getColorPerIndex(i * j + j).getG());
//               System.out.printf("%s", this.imatge.getColorPerIndex(i * j + j).getB());
//               System.out.printf(" ");
//           }
//           System.out.printf("\n");
//       }
//   }

   // Funcions de la presentació.



   @Override
   protected void transformBlocks() {
       createDCTs(); // Caldira mirar si ho fa del tot bé
       DCTiluTrans = new int[DCTilu.length][8][8];
       DCTcrTrans = new int[DCTcr.length][8][8];
       DCTcbTrans = new int[DCTcb.length][8][8];


       for (int i = 0; i < DCTilu.length; i++){ //Fa la segobna transformada
           DCTiluTrans[i] = transformDCTs(DCTilu[i]);
           DCTcbTrans[i] = transformDCTs(DCTcb[i]);
           DCTcrTrans[i] = transformDCTs(DCTcr[i]);
       }
   }

   private static void creazigzag(int matrix[][], Vector<Integer> a) {
       int block = 64;
       boolean dir = false;// true = cap amunt, false cap abaix;
       boolean direcciopos = true; // Pos ha d'augmentar fins a la meitat
       int pos = 0;
       int i = 0;
       int j = 0;
       while (block != 0) {
           do {
               block--; // s'ha computat un bloc més
               a.add(matrix[i][j]);
               if (dir) { // estic de pujada
                   i -= 1;
                   j += 1;
               } else { // estic de baixada
                   i += 1;
                   j -= 1;
               }
           } while (i >= 0 && i < 8 && j >= 0 && j < 8);
           // sha acabat una diagonal
           dir = !dir;
           pos++;
           if (pos == 8) {
               direcciopos = !direcciopos;
               pos = 1;
           }
           // Pos sempre ha d'incrementar.
           // if(direcciopos){
           // pos++;
           // } else{
           // pos--;
           // }
           if (direcciopos) { // Si estic a la primera meitat de la matriu
               if (dir) { // sha anat cap baix, cal anar cap amunt
                   i = pos;
                   j = 0;
               } else {
                   i = 0;
                   j = pos;
               } // sha anat cap amunt i cal anar cap a baix
           } else { // Si estic a la segona meitat de la matriu
               if (dir) { // sha anat cap baix, cal anar cap amunt
                   i = 7;
                   j = pos;
               } else {
                   i = pos;
                   j = 7;
               } // sha anat cap amunt i cal anar cap a baix
           }

       }
   }

   private void preparaMatrius() {
       Y = new Vector<Integer>();
       Cr = new Vector<Integer>();
       Cb = new Vector<Integer>();

       // Aquí genero els zigzags per despres applicar huffman
       for (int[][] matrix : DCTiluTrans) {
           creazigzag(matrix, Y);
       }
       for (int[][] matrix : DCTcrTrans) {
           creazigzag(matrix, Cr);
       }
       for (int[][] matrix : DCTcbTrans) {
           creazigzag(matrix, Cb);
       }
//        for (int i = 0; i < Y.size(); i++){
//            System.out.println(Y.get(i));
//        }
       // #####################################################
       // Aqui seguim aquesta tarda

   }

   private static int[][] transformDCTs(int DCT[][]) // https://www.geeksforgeeks.org/discrete-cosine-transform-algorithm-program/
   {
       //Vector de coeficients
       final double[] c = new double[8];
       //Inicialitzo coeficients
       for (int i=1;i<8;i++) {
           c[i]=1;
       }
       c[0]=1/Math.sqrt(2.0);
       int[][] F = new int[8][8];
       for (int u=0;u<8;u++) {
           for (int v=0;v<8;v++) {
               double sum = 0.0;
               for (int i=0;i<8;i++) {
                   for (int j=0;j<8;j++) {
                       sum+=Math.cos(((2*i+1)/(2.0*8))*u*Math.PI)*Math.cos(((2*j+1)/(2.0*8))*v*Math.PI)*DCT[i][j];
                   }
               }
               sum*=((c[u]*c[v])/4.0);
               F[u][v]= (int)Math.round(sum);
           }
       }
       return F;
   }

   private void createDCTs() { // Cal repassar la lògica d'això.

       int i = 0;
       int j = 0;
       int matriu = 0;// matriu a la que accedeixo
       int salt = 0; // per poder omplir les matrius de baix
       int ms; // nombre de matrius per linea
       int nouAmple = imatge.getAmple(); // Aquestes variables son posades per poder controlar quan cal repetir els
                             // colors
       int nouAlt = imatge.getAlt();
       while (nouAmple % 8 != 0)
           nouAmple++; // Ha d'augmentar si no encaixa
       while (nouAlt % 8 != 0)
           nouAlt++; // Ha d'augmentar si no encaixa
       ms = nouAmple / 8;
       DCTilu = new int[nouAlt * nouAmple / 64][8][8];
       DCTcr = new int[nouAlt * nouAmple / 64][8][8];
       DCTcb = new int[nouAlt * nouAmple / 64][8][8];
       // int llarg = this.imatge.size();
       for (int fila = 0; fila < nouAlt; fila++) {
           Color lastColoret = new Color(0, 0, 0);
           for (int col = 0; col < nouAmple; col++) {
               Color coloret;
               if (col >= imatge.getAmple() || fila >= imatge.getAlt()) {
                   coloret = lastColoret; // Per tenir l'ultim color si no coincideix
               } else {
                   coloret = this.imatge.getColorPerIndex(fila * imatge.getAmple() + col);
                   lastColoret = coloret;
               }
               j = j % 8;
               matriu = matriu % ms;
               i %= 8;
               // System.out.println("Z: " + z + " matriu: " + matriu + " i: " + i + " j: " + j
               // + " valor: " + this.imatge.get(z).r);
               // print();
               // System.out.println("bchdsjla: " + this.imatge.get(z).r + " Amb z = " + z);
               DCTilu[matriu + salt][i][j] = coloret.getR() - 127;
               DCTcr[matriu + salt][i][j] = coloret.getG()- 127;
               DCTcb[matriu + salt][i][j] = coloret.getB()- 127;
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
       imatge.setAmple(nouAmple);
       imatge.setAlt(nouAlt);
   }

   private void divideix() {
       for (int matrix = 0; matrix < DCTiluTrans.length; matrix++) {
           for (int i = 0; i < 8; i++) {
               for (int j = 0; j < 8; j++) {
                   DCTiluTrans[matrix][i][j] /= JPEG.QUANTY[i][j];
                   DCTcrTrans[matrix][i][j] /= JPEG.QUANTC[i][j];
                   DCTcbTrans[matrix][i][j] /= JPEG.QUANTC[i][j];
               }
           }
       }

       // Xivato zone
       // Xivato zone
//        for (int matrix = 0; matrix < DCTiluTrans.length; matrix++) {
//            System.out.println("Matriu numero " + matrix + ":");
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    System.out.printf("%d\t", DCTiluTrans[matrix][i][j]);
//                }
//                System.out.println();
//            }
//        }
   }

   private void guardaenfitxer() throws IOException {
       String header = Integer.toString(imatge.getAlt()) + "\n" + Integer.toString(imatge.getAmple()) + "\n";
       FileOutputStream Hd = new FileOutputStream("./header.txt");
       DataOutputStream Hf = new DataOutputStream(Hd);
       Hf.writeChars(header);
       Hf.close();
       FileOutputStream Yd = new FileOutputStream("./Y.txt");
       DataOutputStream Yf = new DataOutputStream(Yd);
       for (int i = 0; i < Y.size(); i++){
           Yf.writeInt(Y.get(i));
       }
       FileOutputStream Crd = new FileOutputStream("./Cr.txt");
       DataOutputStream Crf = new DataOutputStream(Crd);
       for (int i = 0; i < Cr.size(); i++){
           Crf.writeInt(Cr.get(i));
       }
       FileOutputStream Cbd = new FileOutputStream("./Cb.txt");
       DataOutputStream Cbf = new DataOutputStream(Cbd);
       for (int i = 0; i < Cb.size(); i++){
           Cbf.writeInt(Cr.get(i));
       }
       Yf.close();
       Crf.close();
       Cbf.close();
   }

   @Override
   public String decompress(){
       return null;
   }


    @Override
   public String compress() throws Exception {
        imatge.creaImatgeDePPM(this.data);
       // this.print();
       imatge.operaToYCbCr(); //Ho he de fer a imatge
       this.transformBlocks(); // Aquí tinc els blocs ja transformats.
       System.out.println("Printo la matriu DCTilu[0] abans de dividir:");
       for (int i = 0; i < 8; i++){
           for (int j = 0; j < 8; j++){
               System.out.print(DCTiluTrans[0][i][j]);
               System.out.print(" ");
           }
           System.out.println();
       }
       this.divideix(); // divideix les matrius entre les valors de la taula.
       this.preparaMatrius();
    //    try {
    //        guardaenfitxer();
    //    } catch (IOException e) {
    //        e.printStackTrace();
    //    }
       System.out.println(Y.size());
       System.out.println("Printo la matriu DCTilu[0]:");
       for (int i = 0; i < 8; i++){
           for (int j = 0; j < 8; j++){
               System.out.print(DCTilu[0][i][j]);
               System.out.print(" ");
           }
           System.out.println();
       }
       System.out.println("Printo la matriu DCTiluTrans[0]:");
       for (int i = 0; i < 8; i++){
           for (int j = 0; j < 8; j++){
               System.out.print(DCTiluTrans[0][i][j]);
               System.out.print(" ");
           }
           System.out.println();
       }

       HuffmanTree huffcoder = new HuffmanTree();
       Vector<Integer> bigVector = new Vector<Integer>();
       bigVector.addAll(Y);
       bigVector.addAll(Cb);
       bigVector.addAll(Cr);
       // this.DCTilu = imatge.getDTCblocks("Y");
       // this.DCTcr = imatge.getDCTblocks("Cr");
       // this.DCTcb = imatge.getDCTblocks("Cb");

        //faig això pel debugging:
//        String img = huffcoder.encode(bigVector);
       return imatge.getAmple() + " " + imatge.getAlt() + '\n' +  huffcoder.encode(bigVector); //cacho string


   }

   public Vector<Integer> getDebugging(){
       Vector<Integer> bigVector = new Vector<Integer>();
       bigVector.addAll(Y);
       bigVector.addAll(Cb);
       bigVector.addAll(Cr);

       return bigVector;
   }

}
