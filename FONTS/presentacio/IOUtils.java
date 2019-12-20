/**
 * Class: IOUtils
 * Description:
 * Author: Ignasi Sant Albors
 */
package presentacio;
import domini.Fitxer;

public class IOUtils {
    private int action = -1;
    private Integer algoId;
    private String outfile;
    private String infile;
    private int type;
    private Fitxer ctrlDom;
    private VistaPrincipal vistaPrincipal;
    private String[] stats;

    public IOUtils() {
        this.vistaPrincipal = new VistaPrincipal(this);      
        this.ctrlDom = new Fitxer();//Fitxer.getFitxer();                                                                 
    }

    public void run() throws Exception {
        vistaPrincipal.hacerVisible();
        switch (this.action+(this.type*2)) {
            case 0:
                stats = ctrlDom.compress(infile, outfile, 0, this.algoId); // 0  o 1 => fitxer o carpeta
                break;
            case 1:
                stats = ctrlDom.decompress(infile, outfile, 0, this.algoId); // 0  o 1 => fitxer o carpeta
                break;
            case 2:
                stats = ctrlDom.compress(infile, outfile, 1, this.algoId); // 0  o 1 => fitxer o carpeta
                break;
            case 3:
                stats = ctrlDom.decompress(infile, outfile, 1, this.algoId); // 0  o 1 => fitxer o carpeta
                break;

        }
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setInputFile(String file) throws Exception {
            this.infile = file;
    }

    public void setOutputFile(String file) {
        this.outfile = file;
    }

    public void setAction(int action) {
        this.action = action;
    }


    public void setAlgorithm(int algo) {
        this.algoId = algo;
    }

    public String[] getStats(){
        return stats;
    }

    public String getAllStats(){
        return ctrlDom.getStats();
    }

    public String[] getCompare(){
        return ctrlDom.compara();
    }

    public int getType() {
        return type;
    }


    
}
