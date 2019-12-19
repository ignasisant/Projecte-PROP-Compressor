package domini;

import java.io.*;

import dades.CtrlDades;

public class Fitxer {
    
    private Compressio comp;
    private Descompressio decomp;
    private CtrlDades ctrlDades;
    
    public Fitxer(){
        this.comp = new Compressio(this);
        this.decomp = new Descompressio(this);
        this.ctrlDades = new CtrlDades();
    }




    public String[] compress(String infile, String outfile, int type, int algoId) {
       this.comp.setAlgorithm(algoId);
       return this.comp.compress(infile, outfile, type);

    }

    public String[] decompress(String infile, String outfile, int type, int algoId) {
       
        this.decomp.setAlgorithm(algoId);
        return  this.decomp.decompress(infile, outfile, type);

    }

    public String llegirFitxer(String name)  {
        return this.ctrlDades.read(name);

    }

    public void writeToFile(String data, String fileName) throws Exception{
        this.ctrlDades.write(data, fileName);


    }

    public String compara() {
        String[] ret = {decomp.getAlgoData(), decomp.run()};
        return ret;
    }

    public String[] llegirDescomp(String name) throws IOException {
        String all = this.ctrlDades.read(name);
        String aux[] = all.split("\n");
        String id = aux[0];
        String nom = aux[1];
        int i = id.length()+2+nom.length();
        String data = all.substring(i);
        String ret[] =  {id, nom, data};
        return ret;


    }

    public String getExt(String file){
        return this.ctrlDades.getExt(file);

    }


    public void saveStatistic(String nomFitxer, int algoId, double compress, long duration ) {
       this.ctrlDades.appendStatistic(nomFitxer, algoId, compress, duration);

    }

    public String getStats(){
       // return this.ctrlDades.read("$HOME/.stats");
       return this.ctrlDades.readStats();

    }

    public String getHierarchy(String fold) {
      return this.ctrlDades.getChilds(fold);

    }

}


