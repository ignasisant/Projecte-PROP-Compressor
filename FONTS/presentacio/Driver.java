/**
 * Class: Driver
 * Description:
 * Author: Ignasi Sant Albors
 */
package presentacio;

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
