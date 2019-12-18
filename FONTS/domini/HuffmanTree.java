//author joaquim.ferrer

package domini;

import java.math.BigInteger;
import java.util.*;

//Passos per codificar
//1. Insertar els nombres i els seves frequencies en una cua de prioritat inversa
//2. Mentre la llargada de la cua sigui major a 1, vaig omplint els nodes de l'arbre com pugui
//3. 


public class HuffmanTree {  //aquesta classe construeix un huffmanTree des de qualsevol element. Es mes facil per testejar.

    private class comparador implements Comparator<Node> {  //visibilitat de package
        @Override
        public int compare(Node arg0, Node arg1) {
            return arg0.freq - arg1.freq;
        }

    }

//    private class parell<int> {
//        public int x;
//        public int y;
//        public parell(int x, int y){
//            this.x = x;
//            this.y = y;
//        }
//    }

    private static class Node {   //Visibilitatd e package
        private int freq;
        private int nombre;
        private Node left;
        private Node right;

        public Node() {
            freq = 0;
            nombre = 0;
            left = null;
            right = null;
        }

        public int getFreq() {
            return this.freq;
        }

        public void setFreq(int freq) {
            this.freq = freq;
        }

        public int getNombre() {
            return this.nombre;
        }

        public void setNombre(int nombre) {
            this.nombre = nombre;
        }

        public Node getLeft() {
            return this.left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return this.right;
        }

        public void setRight(Node right) {
            this.right = right;
        }

    }


    private HashMap<Integer, Integer> frequencies; //llargada màxima que pot arribar a tenir
    private Node root;
    private HashMap<Integer, String> traductor; //té el nombre, la traducció a int i en nombre de bits que té
    private String comprimit;
    private String Arbre;

    public HuffmanTree() {


    }

    private String creaPedazoString(Vector<Integer> input) {  //Crea un pedazostring a través de
        StringBuilder a = new StringBuilder();
        for (int i : input) {
            a.append(traductor.get(i)); //aqui li cardo tot l'input
        }
        return a.toString();
    }

    private String intToChars(int n, int tamany){  //donc el nombre i el tamany en bits que vull que tingui
//        StringBuilder res = new StringBuilder();
//        BigInteger big = BigInteger.valueOf(in);
//        byte[] array = big.toByteArray();
//        System.out.println("El byte array te una llargada de " + array.length);
//        for (byte i : array){
//            res.append((char) i);
//        }
//        return res.toString();
        String numero = Integer.toBinaryString(0xFFFF & (short)n); //Guardo una string que es el numerop en binari.
        StringBuilder relleno = new StringBuilder();
        for (int y = 0; y < tamany-numero.length(); y++ ){
            relleno.append('0');
        }
        return relleno + numero;
    }



    private Integer charsToInteger(String in){ //EPA I ELS NEGATIUS KLK?
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
        if (in.length() <= 16){
            return (int)(short)result; //es fa aquest triquinyelismo perque puguin contar bé els negatius.
        } else {
            return result;
        }
    }



    private void creatraductor(Node n, String colocar) {  //em crearà també l'arbre per a guardar
        if (n.getLeft() == null && n.getRight() == null) {
            //Estic en una fulla
            traductor.put(n.getNombre(), colocar); //Per cada nombre que hi ha tinc la seva traducció
            //System.out.println("Fulla!!");
            //Coses a fer per guardar l'arbre
            Arbre = Arbre + '1'; //Guardo que es una fulla
            String numero = intToChars(n.getNombre(), 16); //el creo amb un tamany de 16 bits
//            String numero = Integer.toBinaryString(0xFFFF & (short)n.getNombre()); //Guardo una string que es el numerop en binari.
//            StringBuilder relleno = new StringBuilder();
//            for (int y = 0; y < 16-numero.length(); y++ ){
//                relleno.append('0');
//            }

            Arbre += (numero); //entro un nombre de 16 bits

            //store del valor del int
        }
        Arbre = Arbre+'0';  //Guardo que es una arrel
        if (n.getLeft() != null) {
            //System.out.println("Esquerra");
            creatraductor(n.getLeft(), colocar + '0'); //Shifto. He ficat un zero
        }
        if (n.getRight() != null) {
            //System.out.println("Dreta");
            creatraductor(n.getRight(), colocar + '1'); //Shifto i he fet la or amb un 1 per tant he ficat un u al final
        }
    }

    public String encode(Vector<Integer> input) {   //Complexitat de O(nlogn)

        frequencies = new HashMap<Integer, Integer>();  //nombre de valors que té una DCT amb un maxval de 255
        traductor = new HashMap<Integer, String>();
        root = null;
        comprimit = "";
        Arbre = "";
        int llarg; //serveix per deixar constància de com de llarg es l'string



        for (int i : input) {
            if (frequencies.get(i) == null) { //no hi ha valor, creal
                frequencies.put(i, 1);
            } else {  //cal sumar el valor.
                int canvi = frequencies.get(i);
                frequencies.replace(i, canvi + 1);//actualitzo el valor per al canvi.
            }

        }
        PriorityQueue<Node> cua = new PriorityQueue<Node>(input.size(), new comparador());
        for (int i : frequencies.keySet()) {  //Creo tots els nodes i els encuo segons la prioritat de la seva frequencia.
            Node n = new Node();
            n.setNombre(i); //Cal restar perque he modificat abans el numero.
            n.setFreq(frequencies.get(i)); //li poso el seu nombre de frequencia.
            cua.add(n); //afegeixo el node a la cua
            //Aqui començo a crear l'arbre
        }
        //Aqui comença la magia del huffmann encoding
        while (cua.size() > 1) {  //mentre tingui algo am priotitat major de un nombre. Mai serà 1. Quan sigui 1 hauré acabat perque haué encuat la santa arrel de totes.
            Node n1 = cua.poll(); //tipic pop de tota la vida.
            Node n2 = cua.poll();
            //He obtingut els dos primers de menys prioritat   
            Node pare = new Node();
            pare.setNombre(0); //és un node intermig
            assert n2 != null;
            pare.setFreq(n1.getFreq() + n2.getFreq()); //la frequencia és la suma de les dues. Node intern.
            pare.setRight(n1); //a la dreta el petit
            pare.setLeft(n2); //a l'esquerra el gran.
            cua.add(pare); //afegeixo al pare.
            //CAL MARCAR SEMPRE L'ARREL!!!!!
            root = pare; //Aquesa arrel anirà canviant fins a que quedarà l'ultim node
        }
        //tinc l'arbre generat. Ara fa falta anar creant el codi que estarà codificat
        assert root != null;
        creatraductor(root, ""); //tinc el traductor fet ja jejejejeje
        System.out.print("He obtingut el seguent diccionari:  ");
        System.out.println(traductor);
        int control = 0;
        String pedazoString = creaPedazoString(input);
        llarg = pedazoString.length();
        //System.out.println("Aquest es només el contingut traduit: " + pedazoString);
        String l = intToChars(llarg,32);
        StringBuilder result = new StringBuilder(l + creaPedazoString(input) + Arbre); //He obtingut un string ja amb el codi de huffmann, li sumo l'arbre i al davant el sey tamany en 32 bits
        while (result.length()%8 != 0){  //en aquets cas
            result.append('0');
            //System.out.println("Ara l'string te " + result.length() + " caracters");
        }
        //System.out.println("Aquest es el contingut traduit més l'arbre més relleno " + result);
        //Això em passa un string a una colla de bytes
        //D'aquí a baix està comprovat el seu funcionament
        //String debugging = ""; //Aixo es pel debugging i caldrà borrar-ho q flipes
        char[] res = result.toString().toCharArray();
        for (int x = 0; x < res.length;){
            char a = '\0';
            int ini = x;
            for (; x < ini + 8; x++){
                if (res[x] == '0'){
                    a = (char) (a << 1);

                } else {
                    a = (char) ((a << 1) | 1);
                }
//                debugging += res[x];
//                //System.out.println("Acabo d'afegir l'element " + x +"/159");
//                System.out.println(debugging);
//                System.out.println("Ara l'string te " + debugging.length() + " caracters");
            }
            //String dbg =  res.toString();
            //System.out.println("Això és debugging " + debugging);
            comprimit+=a; //vaig fincant els chars
        }
        //System.out.println("Acabo el codificador ficant  " + comprimit.length() + " bytes.");
        return comprimit;  // Y+Cb+Cr+Arbre+relleno

    }

    private Node muntArbre(char[] arbre, int[] n){  //he de fer triquinyeles per passar el valor com a referencia
        Node node = new Node();
        //System.out.println(arbre[n[0]]);
        //System.out.println(n[0]);
        if (arbre[n[0]] == '1'){ //és una fulla
            //System.out.println("Fulla!!");
            n[0] ++;
            //Aqui comença el nombre a col.locar
            StringBuilder nombre = new StringBuilder();
            int ini = n[0];
            for (;n[0]<ini+16;n[0]++) {
                nombre.append(arbre[n[0]]);
            }
            node.setNombre(charsToInteger(nombre.toString()));
            //System.out.println("he aconseguit el nombre " + node.getNombre());


        } else {  //vigilar aqui, java torna a comparar? com arreglar això
            //System.out.println("Esquerra");
            n[0]++;
            node.setLeft(muntArbre(arbre,n));
            //System.out.println("Dreta");
            n[0]++;
            node.setRight(muntArbre(arbre,n));

        }
        return node;
    }


    private String donamString(String in){
        String result = "";
        int mascara = 128;
        //int cont = 0;
        for (char i : in.toCharArray()){
            int aux = i;
            for (int j = 0; j<8; j++){
                if ((aux & mascara) == 0){
                    result += '0';
                } else {
                    result += '1';
                }
                aux = aux << 1;
                //System.out.println("Acabo d'obtenir el nombre " + cont);
                //System.out.println("Tinc " + result.length() + " caracters");
                //cont ++;

            }

        }
        //System.out.println("El resultat de la descompressió de binari a ascii és de " + result.length() + " bytes");
        return result;
    }


    public Vector<Integer> muntaStrucura( ){
        Vector<Integer> result = new Vector<Integer>();
        Node node = root;
        char[] joc = comprimit.toCharArray();
        for (char i : joc ){
            if (i == '0'){ //cal mirar a l'esquerra
                if (node.getLeft().getLeft() == null && node.getLeft().getRight() == null){ //és una fulla
                    result.add(node.getLeft().getNombre());
                    //System.out.println("Introdueixo " + result);
                    node = root;
                } else {
                    node = node.getLeft();
                }
            } else { //cal mirar a la dreta 
                if (node.getRight().getLeft() == null && node.getRight().getRight() == null){ //comprovo si el de la dreta es una fulla
                    result.add(node.getRight().getNombre());
                    node = root;
                    //System.out.println("Introdueixo " + result);
                } else {
                    node = node.getRight();
                }
            }
        }
        return result;
    
        
        // int elqtoca = joc[it[0]];
        // if (elqtoca == 0){ //toca mirar a l'esquerra
        //     if (node.getLeft().getLeft() == null && node.getLeft().getRight() == null){ //comprovo si el de l'esquerra es una fulla
        //         result.add(node.getLeft().getNombre()); //obtinc el nombre
        //         it[0]++;
        //     } else {
        //         it[0]++;
        //         muntaStrucura(node.getLeft(), joc, it, result);
        //     }
        // } else { //toca mirar a la dreta
        //     if (node.getRight().getLeft() == null && node.getLeft().getRight() == null){ //comprovo si el de la dreta es una fulla
        //         result.add(node.getRight().getNombre()); //obtinc el nombre
        //         it[0]++;
        //     } else {
        //         it[0]++;
        //         muntaStrucura(node.getRight(), joc, it, result);
        //     }
        // }
        
    }

    public Vector<Integer> decode (String in){ //Cal saber la llargda de cada vector
        //System.out.println("Inicio el decoder: m'arriba una string de " + in.length() + " bytes.");
        frequencies = new HashMap<Integer, Integer>();  //nombre de valors que té una DCT amb un maxval de 255
        traductor = new HashMap<Integer, String>();
        root = null;
        comprimit = "";
        Arbre = "";

        String res = donamString(in);//Està comprovat el seu funcionament.
        //System.out.println("Printo el comprimit + l'arbre " + res);
        int llarg = charsToInteger(res.substring(0,32));
        comprimit = res.substring(32,32 + llarg);
        //System.out.println("Printo el que he compimit. Això és el codificat " + comprimit);
        Arbre = res.substring(32 + llarg);
        //System.out.println("Printo l'arbre " + Arbre);
        int[] a = {0};
        root = muntArbre(Arbre.toCharArray(), a); //ja tinc l'arbre muntat FINS AQUI FUNCIONA LA MAR DE BÉ.
        // Vector<Integer> result = new Vector<Integer>();
        return muntaStrucura();
    }
}

