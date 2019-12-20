package domini;

public abstract class Algorithm  {

    abstract String compress() throws Exception;
    abstract String decompress();
    abstract String getData();
    abstract void setData(String data);
    abstract int getId();
    abstract String getExtension();
    
}