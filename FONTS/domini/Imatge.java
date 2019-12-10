//autor: joaquim.ferrer

package domini;

import java.util.Vector;

public abstract class Imatge {
    
    protected int ample;
    protected int alt;
    protected int maxVal; // Color maxim de valor
    protected String magicNum;

    public  Imatge(){

    }

    public abstract Vector<Color> getImatge();

    public abstract void setImatge(Vector<Color> imatge);

    public abstract void setAmple(int ample);

    public abstract int getAlt();

    public abstract void setAlt(int alt);

    public abstract int getMaxVal();

    public abstract void setMaxVal(int maxVal);

    public abstract String getMagicNum();

    public abstract void setMagicNum(String magicNum);

    public abstract int getAmple();

    public abstract Color getColorPerIndex(int index);

    public abstract void setColorPerIndex(int index, Color valor);
}