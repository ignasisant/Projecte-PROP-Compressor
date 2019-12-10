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
    private int inSize;
    private int outSize;
    private int type;
    private long start;
    private long end;


    public  static void main(String[] args) {

        
    }
    public Statistics() {

    }


    public void startTimer() {
        this.start =  System.currentTimeMillis();
    }

    public void endTimer() {
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
        long duration = this.end-this.start;
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

    }

    public String getStats(){
        Path path = Paths.get("/tmp/stats");
        byte[] data = new byte[0];
        String stats = "";
        try {
        data = Files.readAllBytes(path);
        stats = new String(data);
       
        } catch (IOException e) {
        System.out.println("No hi ha cap estadistica per el moment");
        }
        return stats;
    }

    public void printHeader(){
        System.out.println("Fitxer\t\t|"+"Type |"+" AlgoId |"+" Duration |"+" %");
        System.out.println("-----------------------------------------------------");

    }


}
