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
            String[] info = this.compressFile(infile, outfile);
            return info;
        }
        else { //compressio de carpeta
            this.compressFolder(infile, outfile);
        }
        return null;

    }

    private String[] compressFile(String infile,  String outfile) {
        try {
         
            String outf = getCompressOutputFile(infile, outfile);
            
            String payload = this.f.llegirFitxer(infile);
   
            this.algo.setData(payload);
            
     
            this.st.initStats();
            String compress = this.run();
            String[] info =  this.st.saveStats(infile,algo.getId(), payload.length(),compress.length());

            
            // ext_comp = f.getExt(infile);
     
            this.f.writeToFile(algo.getId()+compress, outf);
           return info;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private void compressFolder(String infile,  String outfile) {
        try {
            String outf = getCompressOutputFile(infile, outfile);
            String[] dirs = infile.split("/");
            String inici = dirs[dirs.length -1];
            int ini = infile.length()-inici.length();
            String files = f.getHierarchy(infile);
            String out = "";
            String[] all= files.split("//");
            int inSize = 0, outSize = 0;
            this.st.initStats();
            for(int i=0; i < all.length; ++i) {
                out += "\n"+all[i].substring(ini);
                System.out.println(all[i]);

                String payload = this.f.llegirFitxer(all[i]);
                inSize += payload.length();
                this.algo.setData(payload);
                String compress = this.run();
                out+= "\n"+compress.length()+"\n";
                out+=compress;
            
                
            }
            outSize = out.length();
            String[] info =  this.st.saveStats(infile,algo.getId(), inSize, outSize);
            //outfile=infile+this.algo.getExtension();
            this.f.writeToFile(algo.getId()+out, outf);
        } catch (Exception e) {
            System.out.println(e);
        }
        

    }

    private String run() {
        return this.algo.compress();

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
           if(f.getExt(infile) != "") outfile = infile.replaceFirst("[.][^.]+$",  "."+algo.getExtension() ) ;
           else outfile = infile+"."+this.algo.getExtension();
        } else {
            outfile += "."+algo.getExtension();
        }
        return outfile;
    }





}
