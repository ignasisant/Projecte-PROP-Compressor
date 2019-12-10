package algoritmes;
abstract class Algorithm {

    abstract String compress();
    abstract String decompress();
    abstract void setData(String data);
    abstract int getId();
    abstract String getExtension();
    
}