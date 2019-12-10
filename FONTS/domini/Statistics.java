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
        long duration = this.end-this.start;
        double compress = 100 - ((float) outSize/ (float) inSize*100.0);


        Fitxer.saveStatistic(nomFitxer, algoId, compress, duration);

       


    }




    public void printHeader(){
        System.out.println("Fitxer\t\t|"+"Type |"+" AlgoId |"+" Duration |"+" %");
        System.out.println("-----------------------------------------------------");

    }


}
