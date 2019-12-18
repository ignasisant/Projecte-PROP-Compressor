package dades;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;


public class CtrlDades {

        public CtrlDades() {
        }

        public String read(String name) {

            try {
                
                Path path = Paths.get(name);
                byte[] data = Files.readAllBytes(path);
                String payload = new String(data);

                return payload;
            } catch (Exception e) {
                System.out.println(e);
                return "";
            }

        }

        public void write(String payload,  String fileName) throws Exception{ 

            System.out.println(fileName);
            
            File file = new File(fileName);  
            File[] parents = {}; 
            File parent = file;
            while((parent = parent.getParentFile())!=null) {
                System.out.println("PARENT: "+parent.getAbsolutePath());
                if(parent.exists()) break;
                else parent.mkdirs();

            }           
             
            //.mkdirs();
            
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write(payload);
            writer.close();
     

        }
        

        public String getExt(String file){
            File f = new File(file);
            if (f.getName().lastIndexOf(".") == -1) return "";
            return f.getName().substring(f.getName().lastIndexOf(".") + 1);
        }

        public void appendStatistic(String nomFitxer, int algoId, double compress, long duration ) {
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

        public String getChilds(String fold) {

            File folder = new File(fold);
            File[] list = folder.listFiles();
            String all = "";
            for(int i = 0; i < list.length; ++i ) {
                if(list[i].isDirectory()) all += getChilds(list[i].getAbsolutePath());
                else all += list[i].getAbsolutePath()+"//";
    
            }
            return all;
        }


        

        


}