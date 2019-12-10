package algoritmes;
import java.io.File;


public class Compressio {
    private Algorithm algo;


    private Statistics st;
    private String ext_comp;

    public Compressio( Statistics st) {
        this.st = st;
    };

    public void compress(File infile,  String outfile, Integer type) {
        if (type==0) {
            compressFile(infile, outfile);
        }
        else { //compressio de carpeta

        }

    }

    public void compressFile(File infile,  String outfile) {
        try {
            // data = Files.readAllBytes(this.path);
            String outf = getCompressOutputFile(infile, outfile);
            Fitxer f = new Fitxer();
            String payload = f.llegirFitxer(infile);

            this.st.setInputSize(payload.length());
            this.st.setType(0); 
            algo.setData(payload);
            this.st.startTimer();

            payload = algo.compress();
            
            this.st.endTimer();
            this.st.setOutputSize(payload.length());
            this.st.updateStats(infile.getName() , algo.getId());
            // ext_comp = f.getExt(infile);
            f.writeToFile(algo.getId()+payload, outf);


        } catch (Exception e) {
            System.out.println(e);
        }

    }



    public void setAlgorithm(int algo) throws Exception {
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
                throw new InvalidAlgorithm();
        }

    }
    
    public String getCompressOutputFile(File infile, String outfile) {

        if(outfile == "") {
           outfile = infile.getName().replaceFirst("[.][^.]+$",  "."+algo.getExtension() ) ;
        } else {
            //outfile += "."+algo.getExtension();
        }
        return outfile;
    }





}
