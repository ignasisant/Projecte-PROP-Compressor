package domini;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Statistics {
    private long start;
    private long end;
    private long duration;

    private static Statistics singleton = new Statistics();


    }
    public static Statistics getStatistics() {
        return singleton;
    }

    public void initStats() {
        this.start =  System.currentTimeMillis();
    }

    public void saveStats(String nomFitxer, int algoId, int inSize, int outSize ) {
       //TODO: if (this.start == null) tira excepcio no sha inicialitzat el contador
        this.end =  System.currentTimeMillis();
    }
   
    public void setInputSize(int size) {
        this.inSize = size;
    }
   
    public void setOutputSize(int size) {
        this.outSize =  size;
    }
   
    public void setType(int type) {
        this.type =  type;
    }
   
    public void updateStats(String path, int algoId) {
        duration = this.end-this.start;
        double compress = 100 - ((float) this.outSize/ (float) this.inSize*100.0);
        printHeader();
        System.out.println(path+"\t "+type+"\t "+algoId+"\t "+duration+"\t  "+String.format("%.2f", compress));
        try(
            
            FileWriter fw = new FileWriter("/tmp/stats" ,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)
            ) {
                out.println(path+"\t"+type+"\t"+algoId+"\t"+duration+"\t"+String.format("%.2f", compress));
            } catch (IOException e){
                e.printStackTrace();
            }


        Fitxer.saveStatistic(nomFitxer, algoId, compress, duration);

       


    }




    public void printHeader(){
        System.out.println("Fitxer\t\t|"+"Type |"+" AlgoId |"+" Duration |"+" %");
        System.out.println("-----------------------------------------------------");

    }


}
