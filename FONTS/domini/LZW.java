/**
 * Class: LZW
 * Description:
 * Author: Ignasi Sant Albors
 */
package domini;


import java.util.*;



public class LZW extends Algorithm{

    private String inData;
    private int ID = 1;
    private String ext = "lzw";

    public static void main(String[] args) {


    }
    public LZW() {



    }
    public int getId() { return this.ID;    }
    public String getExtension() { return this.ext;   }

    public String getData() { return this.inData;  }
    public void setData(String data) { this.inData = data;   }

    public String compress(){
        String s= this.inData;
        String r="";
        java.util.HashMap<String, Integer> taula = new java.util.HashMap<String, Integer>();
        
        //coloquem al hashmap la taula ascii
        for (int i = 0; i < 256 ; i++ ){
            String aux = "";
            aux += (char)i; // converteix la i a ascii??
            taula.put(aux , i);
        }

        String p = "", c="";

        p += s.charAt(0);

        Integer codi = 256;

        Vector<Integer> codis_sortida = new Vector<Integer>();

        for (int i = 0; i < s.length() ; i++ ){
            if ( i < s.length() - 1) c += s.charAt(i+1);
            if (taula.containsKey(p+c) == true ) p += c;
            else {
                //codis_sortida.add(taula.get(p));
                r+= String.valueOf(taula.get(p));
                r+=":";
                taula.put(p+c, codi);
                codi++;
                p = c;
            }
            c = "";
        }

        //codis_sortida.add(taula.get(p));
        r+= String.valueOf(taula.get(p));
        r+=":";
        return r;
       
    }

    public String decompress(){
        String r="";
        String input= this.inData;
        java.util.HashMap<Integer, String> taula = new java.util.HashMap<Integer , String>();
        String[] parts = input.split(":");
        Vector<Integer> op = new Vector<Integer>();

        for (int i = 0 ; i < parts.length ; i++ ){
            op.add(Integer.parseInt(parts[i]));
        }

        
        //coloquem al hashmap la taula ascii
        for (int i = 0; i < 256 ; i++ ){
            String aux = "";
            aux += (char)i;
            taula.put(i , aux);
        }

        int antic = op.get(0), n;

        String s = taula.get(antic), c = "";
        c += s.charAt(0);
        r+=s;

        int contador = 256;

        for (int i = 0; i < op.size() - 1 ; i++){
            n = op.get(i+1);
            if(taula.containsKey(n) != true){
                s = taula.get(antic);
                s = s + c;
            }
            else{
                s = taula.get(n);
            }
            r +=s;
            c = "";
            c += s.charAt(0);
            taula.put(contador, taula.get(antic) + c);
            contador++;
            antic = n;
        }
        return r;
    }

   
}