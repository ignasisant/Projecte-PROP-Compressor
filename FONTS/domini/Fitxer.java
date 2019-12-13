package domini;

import java.io.*;
import java.util.Vector;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Fitxer {

    public Fitxer(){}

    public String llegirFitxer(String name)  {
        try {
            File f = new File(name);
            FileReader fr = new FileReader(f);


            String payload = "";
            int i;
            while ((i = fr.read()) != -1) payload += (char) i;
            fr.close();

            return payload;
        } catch (Exception e) {
            System.out.println(e);
            return "";
        }
    }

    public void writeToFile(String data, String fileName) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(data);
        writer.close();

    }

    public Vector<String> llegirDescomp(String name) throws IOException {
        File f = new File(name);
        FileReader fr = new FileReader(f);
        Vector<String> r = new Vector<>();
        int i;
        String ext_comp ="";
        ext_comp += fr.read();
        // while((i=fr.read())!=-1 ){
        //     if((char)i == ':')break;  // 58 en ascii son ":"
        //     ext_comp +=(char)i;
        // }
        r.add(ext_comp);

        String payload =  "";
        while((i=fr.read())!=-1) payload+=(char)i;
        fr.close();
        r.add(payload);
        return r;

    }

    public String getExt(File f){
        return f.getName().substring(f.getName().lastIndexOf(".") + 1);
    }


    static public void saveStatistic(String nomFitxer, int algoId, double compress, long duration ) {
        try(
            
            FileWriter fw = new FileWriter("/tmp/stats" ,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)
            ) {
                out.println(nomFitxer+"\t"+algoId+"\t"+duration+"\t"+String.format("%.2f", compress));
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
        stats = "No hi ha cap estadistica per el moment";
        }
        return stats;
    }
}
