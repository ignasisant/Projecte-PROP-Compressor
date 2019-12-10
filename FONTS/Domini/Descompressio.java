package algoritmes;
import java.io.*;
import java.util.Vector;

public class Descompressio {
    private Algorithm algo;

    private Statistics st;
    //private String ext_comp;

    public Descompressio( Statistics st) {
        this.st = st;
    };

    public void decompress(File infile,  String outfile, Integer type) {
        if (type==0) {
            decompressFile(infile, outfile);
        }
        else { //decompressio de carpeta

        }

    }

    public void decompressFile(File infile,  String outfile) {
        try {
            // data = Files.readAllBytes(this.path);
           
            Fitxer f = new Fitxer();
            Vector<String> info;

            info = f.llegirDescomp(infile);
            //this.ext_comp = getExtFromId(info.get(0)); aixo ena anira be per quan haguem d'endevinar el algo
            String outf = getDecompressOutputFile(infile, outfile);
            String payload = info.get(1);

            

            this.st.setInputSize(payload.length());
            this.st.setType(1); // Descompressio
            algo.setData(payload);
            this.st.startTimer();

            payload = algo.decompress();

            this.st.endTimer();
            this.st.setOutputSize(payload.length());
            this.st.updateStats(infile.getName() , algo.getId());
            f.writeToFile(payload, outf);

        } catch (Exception e) {
            System.out.println(e);
        }

    }
    public String getDecompressOutputFile(File infile, String outfile) {
        if(outfile == "") {
            outfile = infile.getName().replaceFirst("[.][^.]+$", "")  ;
        } else {
           // outfile +=  "."+ ext_comp; // no cal pasar el ext_comp ja que guardem el fitxer aqui
        }
        return outfile;
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
