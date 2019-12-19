package domini;

public abstract class Algorithm extends ActionNotSelected {

    abstract String compress();
    abstract String decompress();
    abstract void setData(String data) throws PPMBadFormatted;
    abstract int getId();
    abstract String getExtension();
    
}