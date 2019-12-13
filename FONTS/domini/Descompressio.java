package domini;
import java.io.*;
import java.util.Vector;

class Descompressio {
    private Algorithm algo;
    private Fitxer f;
    private Statistics st;
    //private String ext_comp;

    public Descompressio( ) {
        this.st = Statistics.getStatistics();
        this.f = Fitxer.getFitxer();
    };

    public void decompress(String infile,  String outfile, Integer type) {
        if (type==0) {
            decompressFile(infile, outfile);
        }
        else { //decompressio de carpeta

        }

    }

    public void decompressFile(String infile,  String outfile) {
        try {
            // data = Files.readAllBytes(this.path);
           
           
            Vector<String> info;

            info = this.f.llegirDescomp(infile);
            //this.ext_comp = getExtFromId(info.get(0)); aixo ena anira be per quan haguem d'endevinar el algo
            String outf = getDecompressOutputFile(infile, outfile);
            String payload = info.get(1);

            

            algo.setData(payload);
           

            this.st.initStats();
            String compress = algo.decompress();
            this.st.saveStats(infile,algo.getId(), payload.length(),compress.length());

            
            this.f.writeToFile(compress, outf);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public String getDecompressOutputFile(String infile, String outfile) {
        if(outfile == "") {
            outfile = infile.replaceFirst("[.][^.]+$", "")  ;
        } else {
           // outfile +=  "."+ ext_comp; // no cal pasar el ext_comp ja que guardem el fitxer aqui
        }
        return outfile;
    }






    public void setAlgorithm(int algo) /*throws Exception */{
        switch (algo) {
            case 0:
                this.algo = new LZ78();
                break;
            case 1:
                this.algo = new LZW();
                break;
            case 2:
                // this.algo = new jpeg();
                break;
            default:
              //  throw new InvalidAlgorithm();
        }


    }
    public String getExtFromId(String i) throws Exception {
        int id = Integer.parseInt(i);
        Algorithm al = null;
        switch (id) {
            case 0:
                al = new LZ78();
                break;
            case 1:
                al = new LZW();
                break;
            case 2:
                // this.algo = new jpeg();
                break;
            default:
                 throw new InvalidAlgorithm();
        
        }
        return al.getExtension();

    }




}
