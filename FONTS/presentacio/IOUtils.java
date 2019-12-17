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
    private String[] stats;

    public IOUtils() {

        this.vistaPrincipal = new VistaPrincipal(this);      
        this.ctrlDom = new Fitxer();//Fitxer.getFitxer();                                                                 
    }


    public IOUtils(String infile, String outfile, int action, int algo) throws Exception {
        setInputFile(infile);
        this.action = action;
        this.algoId = algo;
        setOutputFile(outfile);


    }



    public void run() throws Exception {
        vistaPrincipal.hacerVisible();
        //if(this.infile.getName() == "") throw new FileNotSelected();
        //if(this.algoId != 0 && this.algoId != 1 && this.algoId != 2) throw new AlgorithmNotSelected();
        //if(this.action == -1) throw new ActionNotSelected();



        System.out.println(infile);
            System.out.println(outfile);
            System.out.println(type);
            System.out.println(algoId);
        switch (this.action+(this.type*2)) {
    

            case 0:
                stats = ctrlDom.compress(infile, outfile, 0, this.algoId); // 0  o 1 => fitxer o carpeta
                break;
            case 1:
                stats = ctrlDom.decompress(infile, outfile, 0, this.algoId); // 0  o 1 => fitxer o carpeta
                break;

            case 2:
                ctrlDom.compress(infile, outfile, 1, this.algoId); // 0  o 1 => fitxer o carpeta
                break;
            case 3:
                ctrlDom.decompress(infile, outfile, 1, this.algoId); // 0  o 1 => fitxer o carpeta
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

    
}
