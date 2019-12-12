package domini.testsDelQuim;

import domini.HuffmanTree;

import java.math.BigInteger;
import java.util.Vector;

public class huffmannTest {

    private static String intToChars(Integer in){
        String res = "";
        BigInteger big = BigInteger.valueOf(in);
        byte[] array = big.toByteArray();
        System.out.println("El byte array te una llargada de " + array.length);
        for (byte i : array){
            res += (char)i;
        }
        return res;
    }

    private static Integer charsToInteger(String in){
        byte[] array = new byte[in.length()];
        for (int i = 0; i < in.length(); i++ ){
            array[i] = (byte) in.toCharArray()[i];
        }
        BigInteger big = new BigInteger(array);
        return big.intValue();
    }


    private static String donamString(String in){
        String result = "";
        int mascara = 128;
        for (char i : in.toCharArray()){
            int aux = i;
            for (int j = 0; j<8; j++){
                if ((aux & mascara) == 0){
                    result += '0';
                } else {
                    result += '1';
                }
                aux = aux << 1;
            }

        }
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

        String result = "1010101011010101010101010101010101010101010111010101010101010101011111111111111111";
        while (result.length()%8 != 0){  //en aquets cas
            result+=('0');
        }
        String comprimit = "";
        char[] res = result.toCharArray();
        for (int x = 0; x < res.length;){
            char a = '\0';
            int ini = x;
            for (; x < ini + 8; x++){
                if (res[x] == '0'){
                    a = (char) (a << 1);

                } else {
                    a = (char) ((a << 1) | 1);
                }
            }
            comprimit+=a; //vaig fincant els chars
        }


        System.out.println(donamString(comprimit));

    }
}
