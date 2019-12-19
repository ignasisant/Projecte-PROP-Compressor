package domini;

public abstract class Algorithm extends ActionNotSelected {

    abstract String compress();
    abstract String decompress();
    abstract void setData(String data);
    abstract int getId();
    abstract String getExtension();
    
}