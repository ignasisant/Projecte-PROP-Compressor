package presentacio;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import domini.*;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.*;

public class IOUtils {
    private int action = -1;
    private Integer algoId;
    private String outfile;
    private File infile;
    private int type;
    private Compressio comp;
    private Descompressio decomp;
    private VistaPrincipal vistaPrincipal;

    public IOUtils() {
        this.comp = new Compressio();
        this.decomp = new Descompressio();
        this.vistaPrincipal = new VistaPrincipal(this);      
                                                                                           
    }


    public IOUtils(String infile, String outfile, int action, int algo) throws Exception {
        setInputFile(infile);
        this.action = action;
        this.algoId = algo;
        setOutputFile(outfile);
        this.comp = new Compressio();  //borrar
        this.decomp = new Descompressio();//borrar
        //Fitxer f = Fitxer.getfitxer();


    }



    public void run() throws Exception {
        vistaPrincipal.hacerVisible();
        if(this.infile.getName() == "") throw new FileNotSelected();
        if(this.algoId != 0 && this.algoId != 1 && this.algoId != 2) throw new AlgorithmNotSelected();
        if(this.action == -1) throw new ActionNotSelected();

        switch (this.action+(this.type*2)) {

            case 0:
                comp.setAlgorithm(this.algoId); // passar amb parametre compress (algoid, )
                comp.compress(infile.getName(), outfile, 0); // 0  o 1 => fitxer o carpeta
                break; // lo de dalt amb algoID
            case 1:
                decomp.setAlgorithm(this.algoId);
                decomp.decompress(infile.getName(), outfile, 0); // 0  o 1 => fitxer o carpeta
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
        File in = new File(file);
        if (in.isFile()) {
            setType(0);
            this.infile = in;
        }
        else if (in.isDirectory()) {
            setType(1);
            this.infile = in;
        }
        else {

            throw new FileNotFoundException(); //tractem les excepcions el més aviat possible
        }
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
