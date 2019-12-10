package capaDades;

public class Color {  //cada imatge és una matriu de Colors
    private int r;
    private int g;
    private int b;

    public int getR() {
        return this.r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return this.g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return this.b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public Color(int r,int g,int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
}