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

    private class Node {   //Visibilitatd e package
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
    private PriorityQueue<Node> Cua;
    private HashMap<Integer, String> traductor; //té el nombre, la traducció a int i en nombre de bits que té
    private String comprimit;
    private String Arbre;

    public HuffmanTree() {


    }

    private String creaPedazoString(Vector<Integer> input) {  //Crea un pedazostring a través de
        String a = "";
        for (int i : input) {
            a+=traductor.get(i); //aqui li cardo tot l'input
        }
        return a;
    }

    private String intToChars(Integer in){
        String res = "";
        BigInteger big = BigInteger.valueOf(in);
        byte[] array = big.toByteArray();
        System.out.println("El byte array te una llargada de " + array.length);
        for (byte i : array){
            res += (char)i;
        }
        return res;
    }

    private Integer charsToInteger(String in){
        byte[] array = new byte[in.length()];
        for (int i = 0; i < in.length(); i++ ){
            array[i] = (byte) in.toCharArray()[i];
        }
        BigInteger big = new BigInteger(array);
        return big.intValue();
    }

    private void creatraductor(Node n, String colocar) {  //em crearà també l'arbre per a guardar
        if (n.getLeft() == null && n.getRight() == null) {
            //Estic en una fulla
            traductor.put(n.getNombre(), colocar); //Per cada nombre que hi ha tinc la seva traducció

            //Coses a fer per guardar l'arbre
            Arbre = Arbre + '1'; //Guardo que es una fulla
            String numero = Integer.toBinaryString(0xFFFF & (short)n.getNombre()); //Guardo una string que es el numerop en binari.
            String relleno = "";
            for (int y = 0; y < 16-numero.length(); y++ ){
                relleno += '0';
            }

            Arbre += (relleno + numero); //entro un nombre de 16 bits

            //store del valor del int
        }
        Arbre = Arbre+'0';  //Guardo que es una arrel
        if (n.getLeft() != null) {
            creatraductor(n.getLeft(), colocar + '0'); //Shifto. He ficat un zero
        }
        if (n.getRight() != null) {
            creatraductor(n.getRight(), colocar + '1'); //Shifto i he fet la or amb un 1 per tant he ficat un u al final
        }
    }

    public String encode(Vector<Integer> input) {   //Complexitat de O(nlogn)

        frequencies = new HashMap<Integer, Integer>();  //nombre de valors que té una DCT amb un maxval de 255
        traductor = new HashMap<Integer, String>();
        root = null;
        comprimit = "";
        Arbre = "";



        for (int i : input) {
            if (frequencies.get(i) == null) { //no hi ha valor, creal
                frequencies.put(i, 1);
            } else {  //cal sumar el valor.
                int canvi = frequencies.get(i);
                frequencies.replace(i, canvi + 1);//actualitzo el valor per al canvi.
            }

        }
        Cua = new PriorityQueue<Node>(input.size(), new comparador());
        for (int i : frequencies.keySet()) {  //Creo tots els nodes i els encuo segons la prioritat de la seva frequencia.
            Node n = new Node();
            n.setNombre(i); //Cal restar perque he modificat abans el numero.
            n.setFreq(frequencies.get(i)); //li poso el seu nombre de frequencia.
            Cua.add(n); //afegeixo el node a la cua
            //Aqui començo a crear l'arbre
        }
        //Aqui comença la magia del huffmann encoding
        while (Cua.size() > 1) {  //mentre tingui algo am priotitat major de un nombre. Mai serà 1. Quan sigui 1 hauré acabat perque haué encuat la santa arrel de totes.
            Node n1 = Cua.poll(); //tipic pop de tota la vida.
            Node n2 = Cua.poll();
            //He obtingut els dos primers de menys prioritat   
            Node pare = new Node();
            pare.setNombre(0); //és un node intermig
            pare.setFreq(n1.getFreq() + n2.getFreq()); //la frequencia és la suma de les dues. Node intern.
            pare.setRight(n1); //a la dreta el petit
            pare.setLeft(n2); //a l'esquerra el gran.
            Cua.add(pare); //afegeixo al pare.
            //CAL MARCAR SEMPRE L'ARREL!!!!!
            root = pare; //Aquesa arrel anirà canviant fins a que quedarà l'ultim node
        }
        //tinc l'arbre generat. Ara fa falta anar creant el codi que estarà codificat
        creatraductor(root, ""); //tinc el traductor fet ja jejejejeje
        System.out.print("He obtingut el seguent diccionari:  ");
        System.out.println(traductor);
        int control = 0;
        String result = creaPedazoString(input) + Arbre; //He obtingut un string ja amb el codi de huffmann, li sumo l'arbre
        while (result.length()%8 != 0){  //en aquets cas
            result+=('\0');
        }

        //Això em passa un string a una colla de bytes
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
        return comprimit;  // Y+Cb+Cr+Arbre+relleno

    }

    private Node muntArbre(char[] arbre, Integer n){
        Node node = new Node();
        System.out.println(n);
        if (arbre[n] == '1'){ //és una fulla
            n ++;
            //Aqui comença el nombre a col.locar
            String nombre = "";
            for (;n<n+17;n++) { //aixi ja ha sumat un quan acabi aixo
                nombre += arbre[n];
            }


        } else {
            node.setLeft(muntArbre(arbre,n+1));
            node.setRight(muntArbre(arbre,n+1));

        }
        return node;
    }


    private String donamString(String in){
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

    public Vector<Integer> muntaStrucura(){
        Vector<Integer> result = new Vector<Integer>();
        Node lab = root;
        char[] joc = comprimit.toCharArray();
        for (int i = 0; i < joc.length; i++){
            if (lab.getLeft() == null && lab.getRight() == null){
                //Estic a una fulla
                result.add(lab.getNombre());
                lab = root;
            } else { //estic en una branca
                if (joc[i] == 0){ //cal anar a l'esquerra
                    lab = lab.getLeft();
                } else { //cal anar a la dreta
                    lab = lab.getRight();
                }
            }
        }
        return result;
    }

    public Vector<Integer> decode (String in, int llarg){ //Cal saber la llargda de cada vector

        frequencies = new HashMap<Integer, Integer>();  //nombre de valors que té una DCT amb un maxval de 255
        traductor = new HashMap<Integer, String>();
        root = null;
        comprimit = "";
        Arbre = "";

        String res = donamString(in);//Està comprovat el seu funcionament.
        comprimit = res.substring(0,llarg*3);
        Arbre = res.substring(llarg*3);
        root = muntArbre(Arbre.toCharArray(), 1); //ja tinc l'arbre muntat
        return muntaStrucura();
    }
}

