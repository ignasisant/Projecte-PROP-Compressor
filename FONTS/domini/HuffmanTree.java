//author joaquim.ferrer

package domini;
import java.util.*;

//Passos per codificar
//1. Insertar els nombres i els seves frequencies en una cua de prioritat inversa
//2. Mentre la llargada de la cua sigui major a 1, vaig omplint els nodes de l'arbre com pugui
//3. 


public class HuffmanTree {  //aquesta classe construeix un huffmanTree des de qualsevol element.
    
    private class comparador implements Comparator<Node> {  //visibilitat de package
        @Override
        public int compare(Node arg0, Node arg1) {
            return arg0.freq - arg1.freq;
        }

    }

    private class parell<T> {
        public T x;
        public T y;
        public parell(T x, T y){
            this.x = x;
            this.y = y;
        }
    }
    
    private class Node{   //Visibilitatd e package
        private int freq;
        private int nombre;
        private Node left;
        private Node right;

        public Node(){
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
    
    
    private int[] frequencies; //llargada màxima que pot arribar a tenir 
    private Node root;
    private PriorityQueue<Node> Cua;
    private HashMap<Integer,parell<Integer>> traductor; //té el nombre, la traducció a int i en nombre de bits que té
    private String comprimit;

    public HuffmanTree(){
        frequencies = new int[4096];  //nombre de valors que té una DCT amb un maxval de 255
        Arrays.fill(frequencies, 0);
        root = null;
        comprimit = "";
        
    } 

    private void creatraductor(Node n, int colocar, int num){
        if (n.getLeft() == null && n.getRight() == null){
            //Estic en una fulla
            traductor.put(n.getNombre(),new parell(colocar,num)); //Per cada nombre que hi ha tinc la seva traducció
        }
        if (n.getLeft() != null){
            creatraductor(n.getLeft(), (colocar<<<1), num+1); //Shifto. He ficat un zero
        } 
        if (n.getRight() != null){
            creatraductor(n.getRight(), (colocar<<<1)|1, num+1); //Shifto i he fet la or amb un 1 per tant he ficat un u al final
        }
    }

    public void encode(Vector<Integer> input){   //Complexitat de O(nlogn)
        for ( int i : input){
            frequencies [i+2047]++;  //Carrego les frequencies
        }
        Cua = new PriorityQueue<Node>(input.size(), new comparador());
        for (int i = 0; i < frequencies.length; i++) {
            if(frequencies[i] != 0){
                Node n = new Node();
                n.setNombre(i-2047); //Cal restar perque he modificat abans el numero.
                n.setFreq(frequencies[i]); //li poso el seu nombre de frequencia.
                Cua.add(n); //afegeixo el node a la cua
            }

            //Aqui començo a crear l'arbre
        }
        //Aqui comença la magia del huffmann encoding
        while (Cua.size() > 1){  //mentre tingui algo am priotitat major de un nombre. Mai serà 1. Quan sigui 1 hauré acabat perque haué encuat la santa arrel de totes.
            Node n1 = Cua.poll(); //tipic pop de tota la vida.
            Node n2 = Cua.poll();
            //He obtingut els dos primers de menys prioritat   
            Node pare = new Node();
            pare.setNombre(-1); //és un node intermig
            pare.setFreq(n1.getFreq() + n2.getFreq()); //la frequencia és la suma de les dues. Node intern.
            pare.setRight(n2); //aqui va el segon pq es un minheap
            pare.setLeft(n1); //aqui el primer.
            Cua.add(pare); //afegeixo al pare.
            //CAL MARCAR SEMPRE L'ARREL!!!!!
            root = pare; //Aquesa arrel anirà canviant fins a que quedarà l'ultim node
        }
         //tinc l'arbre generat. Ara fa falta anar creant el codi que estarà codificat
        creatraductor(root,0,0); //tinc el traductor fet ja jejejejeje
        int control = 0;
        String result = "";
        char aux;
        for (int i : input){
            parell trad = traductor.get(i); //obtinc un parell de la traduccio
            
        }
    }

}