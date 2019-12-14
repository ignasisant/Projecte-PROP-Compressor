package domini;
import java.io.File;


class Compressio {
    private Algorithm algo;

    private Fitxer f;
    private Statistics st;
    private String ext_comp;

    public Compressio(Fitxer f) {
        this.st = Statistics.getStatistics();
        this.f = f;//Fitxer.getFitxer();
    };

    public  String[]  compress(String infile,  String outfile, Integer type) {
        if (type==0) {
            String[] info = compressFile(infile, outfile);
            return info;
        }
        else { //compressio de carpeta

        }
        return null;

    }

    public String[] compressFile(String infile,  String outfile) {
        try {
         
            String outf = getCompressOutputFile(infile, outfile);
            
            String payload = this.f.llegirFitxer(infile);
   
            this.algo.setData(payload);
            
     
            this.st.initStats();
            String compress = algo.compress();
            String[] info =  this.st.saveStats(infile,algo.getId(), payload.length(),compress.length());

            
            // ext_comp = f.getExt(infile);
     
            this.f.writeToFile(algo.getId()+compress, outf);
           return info;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
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
            outfile += "."+algo.getExtension();
        }
        return outfile;
    }





}
