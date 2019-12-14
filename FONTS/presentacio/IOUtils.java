package presentacio;
import domini.Fitxer;


import java.io.File;
import java.io.FileNotFoundException;


public class IOUtils {
    private int action = -1;
    private Integer algoId;
    private String outfile;
    private String infile;
    private int type;
    private Fitxer ctrlDom;
    private VistaPrincipal vistaPrincipal;

    public IOUtils() {

        this.vistaPrincipal = new VistaPrincipal(this);      
        this.ctrlDom = Fitxer.getFitxer();                                                                 
    }


    public IOUtils(String infile, String outfile, int action, int algo) throws Exception {
        setInputFile(infile);
        this.action = action;
        this.algoId = algo;
        setOutputFile(outfile);


    }



    public void run() throws Exception {
        vistaPrincipal.hacerVisible();
       // if(this.infile.getName() == "") throw new FileNotSelected();
        //if(this.algoId != 0 && this.algoId != 1 && this.algoId != 2) throw new AlgorithmNotSelected();
        //if(this.action == -1) throw new ActionNotSelected();

        switch (this.action+(this.type*2)) {

            case 0:
                ctrlDom.compress(infile, outfile, 0, this.algoId); // 0  o 1 => fitxer o carpeta
                break;
            case 1:
                ctrlDom.decompress(infile, outfile, 0, this.algoId); // 0  o 1 => fitxer o carpeta
                break;

            // case 2:
            //     comp.setAlgorithm(this.algoId);
            //     comp.compress(infile, outfile, 1); // 0  o 1 => fitxer o carpeta
            //     break;
            // case 3:
            //     decomp.setAlgorithm(this.algoId);
            //     decomp.decompress(infile, outfile, 1); // 0  o 1 => fitxer o carpeta
            //     break;
 
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
    
}
