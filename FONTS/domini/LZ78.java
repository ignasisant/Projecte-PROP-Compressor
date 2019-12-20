
/**
 * Class: LZ78
 * Author: Jordi Garcia Aguilar
 */
package domini;

import java.util.HashMap;

public class LZ78 extends Algorithm {

    private String inData;
    private int ID = 0;
    private String ext = "lz78";

    public int getId() {
        return this.ID;
    }

    public String getExtension() {
        return this.ext;
    }

    public String getData() {
        return this.inData;
    }

    public void setData(String data) {
        this.inData = data;
    }

    public String compress() {
        String payload = this.inData;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        String key = "", carry = "", ant = "0";
        Integer index = 1;
        Integer n = payload.length();
        for (int i = 0; i < n; i++) {
            key += payload.charAt(i);

            if (!map.containsKey(key)) { // no existeix: l'afegim al map i actualitzem el carry
                map.put(key, index++);
                carry += ant + payload.charAt(i) + ":";
                key = "";
                ant = "0";
            } else { // existeix
                ant = map.get(key).toString();

                if (i + 1 == n)
                    carry += "0" + key; // actualitzem el carry amb ultim si ha quedat penjat

            }
        }

        return carry;
    }

    public String decompress() {
        String payload = this.inData;
        HashMap<Integer, Node> tree = new HashMap<Integer, Node>();
        String[] parts = payload.split(":");
        Integer n = parts.length;
        Integer index = 1;
        boolean last = false;
        for (int i = 0; i < n; i++) {
            int size;

            if (last) {
                last = false;
            } else if (i < n - 1) {
                size = parts[i + 1].length();
                if (size == 0) {
                    tree.put(index++, new Node(Integer.parseInt(parts[i]), ':'));
                    last = true;
                } else {
                    size = parts[i].length();
                    tree.put(index++, new Node(Integer.parseInt((String) parts[i].subSequence(0, size - 1)),
                            parts[i].charAt(size - 1)));
                }
            } else {
                size = parts[i].length();
                tree.put(index++, new Node(Integer.parseInt((String) parts[i].subSequence(0, size - 1)),
                        parts[i].charAt(size - 1)));
            }
            // System.out.println(index);
        }

        return build(tree);

    }

    public static String build(HashMap<Integer, Node> tree) {
        Integer ini, curr;
        ini = curr = tree.size();
        // System.out.println("SSS: "+curr);
        String buff = "";
        while (ini > 0) {
            if (curr == 0) {
                --ini;
                curr = ini;
            } else {
                buff = tree.get(curr).getChar() + buff;
                curr = tree.get(curr).getAnterior();
            }
        }

        return buff;
    }

}
