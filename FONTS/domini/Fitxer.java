package domini;

import java.io.*;
import java.util.Vector;

public class Fitxer {

    public Fitxer(){}

    public String llegirFitxer(File f)  {
        try {
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

    public Vector<String> llegirDescomp(File f) throws IOException {

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
}
