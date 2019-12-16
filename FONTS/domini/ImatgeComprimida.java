package domini;

import java.util.Vector;

public class ImatgeComprimida extends Imatge {
    public ImatgeComprimida(String entrada, int ample, int alt) {
        HuffmanTree huff = new HuffmanTree();
        this.ample = ample;
        this.alt = alt;
        Vector<Integer> descomp = huff.decode(entrada);        
    }

    @Override
    public Color getColorPerIndex(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setColorPerIndex(int index, Color valor) {
        // TODO Auto-generated method stub

    }

}