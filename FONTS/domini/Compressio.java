package domini;
import java.io.File;


public class Compressio {
    private Algorithm algo;

    private Fitxer f;
    private Statistics st;
    private String ext_comp;

    public Compressio() {
        this.st = Statistics.getStatistics();
        this.f = Fitxer.getFitxer();
    };

    public void compress(String infile,  String outfile, Integer type) {
        if (type==0) {
            compressFile(infile, outfile);
        }
        else { //compressio de carpeta

        }

    }

    public void compressFile(String infile,  String outfile) {
        try {
        
            String outf = getCompressOutputFile(infile, outfile);
         
            String payload = this.f.llegirFitxer(infile);
                
   
            this.algo.setData(payload);
     
            this.st.initStats();
            String compress = algo.compress();
            this.st.saveStats(infile,algo.getId(), payload.length(),compress.length());

    
            // ext_comp = f.getExt(infile);
            this.f.writeToFile(algo.getId()+compress, outf);


        } catch (Exception e) {
            System.out.println(e);
        }

    }



    public void setAlgorithm(int algo) /*throws Exception*/ {
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
               // throw new InvalidAlgorithm();
        }

    }
    
    public String getCompressOutputFile(String infile, String outfile) {

        if(outfile == "") {
           outfile = infile.replaceFirst("[.][^.]+$",  "."+algo.getExtension() ) ;
        } else {
            //outfile += "."+algo.getExtension();
        }
        return outfile;
    }





}
