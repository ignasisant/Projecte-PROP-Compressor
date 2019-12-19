

import domini.HuffmanTree;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Vector;

import static java.lang.System.exit;

public class huffmannTest {

//    private static String intToChars(Integer in){
//        return getString(in);
//    }
//
private static String intToChars(Integer in){
    StringBuilder res = new StringBuilder();
    BigInteger big = BigInteger.valueOf(in);
    byte[] array = big.toByteArray();
    System.out.println("El byte array te una llargada de " + array.length);
    for (byte i : array){
        res.append((char) i);
    }
    return res.toString();
}
//
//
//    private static String donamString(String in){
//        return getString(in);
//    }

    private static Integer charsToInteger(String in){
//        byte[] array = new byte[in.length()];
////        for (int i = 0; i < in.length(); i++ ){
////            array[i] = (byte) in.toCharArray()[i];
////        }
////        BigInteger big = new BigInteger(array);
////        return big.intValue();
        char[] numbers = in.toCharArray();
        int result = 0;
        for(int i=numbers.length - 1; i>=0; i--)
            if(numbers[i]=='1')
                result += Math.pow(2, (numbers.length-i - 1));
        return result;
    }

    public static void main(String[] args) {
//        String a = "hhhhoollllllla";
//        Vector<Character> ap = new Vector<Character>();
//        for (int i = 0; i < a.length(); i++){
//            ap.add(a.toCharArray()[i]);
//        }
//        HuffmanTree<Character> h = new HuffmanTree<Character>();
//        h.encode(ap);
//
        /*char a = '\0';
        char b = (char) ((a << 1) | 1 );
        System.out.println(b);*/

//        int a = 543782956;

//        String result = "1010101011010101010101010101010101010101010111010101010101010101011111111111111111";
//        while (result.length()%8 != 0){  //en aquets cas
//            result+=('0');
//        }
//        String comprimit = "";
//        char[] res = result.toCharArray();
//        for (int x = 0; x < res.length;){
//            char a = '\0';
//            int ini = x;
//            for (; x < ini + 8; x++){
//                if (res[x] == '0'){
//                    a = (char) (a << 1);
//
//                } else {
//                    a = (char) ((a << 1) | 1);
//                }
//            }
//            comprimit+=a; //vaig fincant els chars
//        }
//
//
//        System.out.println(donamString(comprimit));

        //A sobre tests parcials de funcions concretes
        //A sota, test de l'aplicatiu principal
       Vector <Integer> prova1 = new Vector<Integer>();
       Vector <Integer> prova3 = new Vector<Integer>();
       Vector <Integer> prova2 = new Vector<Integer>();
       for (int i = 0; i < 2000; i++){
           if(i%2 == 1){
            prova1.add(0);
            prova2.add(0);
            prova3.add(0);
           } else {
               prova1.add(i);
               prova2.add(i);
               prova3.add(i);
           }
       }

        Vector<Integer> tot = new Vector<Integer>();
        // tot.add(0);
        // tot.add(0);
        // tot.add(0);
        // tot.add(0);
        // tot.add(0);
        // tot.add(0);
        // tot.add(0);
        // tot.add(0);
        // tot.add(0);
        // tot.add(0);
        // tot.add(-244);
        // tot.add(1);
        // tot.add(1);
        // tot.add(1);
        // tot.add(2);
        // tot.add(2);
        // tot.add(3);
        // tot.add(4);
        // tot.add(5);
        // tot.add(5);
        // tot.add(5);
       tot.addAll(prova1);
       tot.addAll(prova2);
       tot.addAll(prova3);
        HuffmanTree huff = new HuffmanTree();
        System.out.println("El vector té una llargada de " + tot.size());
        String result = huff.encode(tot);
        System.out.println("L'String comprimit te una llargada de " + result.length());
        Vector<Integer> stalin = huff.decode(result);
        //System.out.println(intToChars(32));
        System.out.println("Ha de printar el nombre 759: " + charsToInteger("1011110111"));
        for (int i = 0; i < tot.size(); i++){
            if ((int)stalin.get(i) != (int)tot.get(i)){
                System.out.println("A l'element " + i + "no coincideixen els vectors. Todo Mal");
                System.out.println("l'original diu " + tot.get(i) + " el decodificat diu " + stalin.get(i));
                exit(0);
            }
        }
        System.out.println("tot bé piratilla jejejeje");
    }

}
