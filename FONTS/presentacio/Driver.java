package presentacio;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import domini.*;


class Driver {
    public  static void main(String[] args)  {
        IOUtils test = new IOUtils();
        try {
            test.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }    
}
