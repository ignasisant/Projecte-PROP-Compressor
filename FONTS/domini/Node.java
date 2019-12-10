/**
 * Class: Node
 * Description:
 * Author: Jordi Garcia Aguilar
 */
package domini;
class Node {
    private Integer index;
    private Integer anterior;
    private char value;

    // constructor
   public Node(Integer index, Integer anterior) {
        this.index = index;
        this.anterior = anterior;
    }

    public Node(Integer anterior, char value) {
        this.anterior = anterior;
        this.value = value;
    }

    public Integer getIndex() {
        return this.index;
    }

    public Integer getAnterior() {
        return this.anterior;
    }

    public char getChar() {
        return this.value;
    }

}