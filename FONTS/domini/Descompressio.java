package domini;
import java.io.*;
import java.util.Vector;

class Descompressio {
    private Algorithm algo;
    private Fitxer f;
    private Statistics st;
    //private String ext_comp;

    public Descompressio(Fitxer f ) {
        this.st = Statistics.getStatistics();
        this.f = f;
    };

    public String[] decompress(String infile,  String outfile, Integer type) {
        String[] info;
        try {
        info = this.f.llegirDescomp(infile);


        if (info[0].length()==1) {
            String[] stats =  this.decompressFile(infile, outfile, info[1], info[2]);
            return stats;
        }
        else { //decompressio de carpeta

            String[] stats = this.decompressFolder(infile, outfile, info[0]+"\n"+info[1]+"\n"+info[2]);
            return stats;

        }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

    private String[] decompressFile(String infile,  String outfile, String origName, String data) {
        try {

            //this.ext_comp = getExtFromId(info.get(0)); aixo ena anira be per quan haguem d'endevinar el algo
            //String outf = getDecompressOutputFile(infile, outfile);
            if(outfile == "") outfile = origName;
            String payload =data;

            algo.setData(payload);

            this.st.initStats();

            String decompress = this.run();

            String[] stat = this.st.saveStats(infile,algo.getId(), payload.length(),decompress.length());

            this.f.writeToFile(decompress, outfile);
            return stat;

        } catch (Exception e) {
            System.out.println(e);
        }
        return null;

    }

    private String[] decompressFolder(String infile, String outfile, String all) {

        int ini =  all.indexOf("\n");
        all = all.substring(ini+1);
        int totini=0, totend=0;
        int fin = all.length();
        this.st.initStats();

        while(true) {

            ini =  all.indexOf("\n");

            String nom = all.substring(0, ini);

            all = all.substring(ini+1);
            ini =  all.indexOf("\n");
            int max = Integer.parseInt(all.substring(0,ini));
            all = all.substring(ini+1);
            String payload = all.substring(0,max);
            totini += payload.length();
            algo.setData(payload);
            String decompress = this.run();
            totend += decompress.length();
            try {
            this.f.writeToFile(decompress, "/tmp/"+nom);
            } catch (Exception e) {
                e.printStackTrace();

            }
            if (max == all.length()) break;
            all = all.substring(max+1);

            fin = all.length();

        }
        String[] stat = this.st.saveStats(infile,algo.getId(),totini,totend);
        return stat;
        //String nom = all.substring(0, ini-1);

    }

    private String getDecompressOutputFile(String infile, String outfile) {
        if(outfile == "") {
            outfile = infile.replaceFirst("[.][^.]+$", "")  ;
        } else {
           // outfile +=  "."+ ext_comp; // no cal pasar el ext_comp ja que guardem el fitxer aqui
        }
        return outfile;
    }

    private String run() {
        return this.algo.decompress();
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
