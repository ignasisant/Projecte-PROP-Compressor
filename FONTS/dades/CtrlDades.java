package dades;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.Arrays;

public class CtrlDades {

    public CtrlDades() {
    }

    public String read(String name) throws Exception {

        Path path = Paths.get(name);
        byte[] data = Files.readAllBytes(path);
        String payload = new String(data);

        // workaround d'un problema de trucament al llegir ppms
        while (data.length > payload.length()) {
            String aux = new String(Arrays.copyOfRange(data, payload.length(), data.length));

            payload += aux;
        }
        return payload;

    }

    public String readStats() throws Exception {

        if (winOS()) {
            return read("C:\\Temp\\stats");
        } else {
            return read("/tmp/stats");
        }
    }

    private boolean winOS() {
        return System.getProperty("os.name").toLowerCase().indexOf("windows") != -1;
    }

    public void write(String payload, String fileName) throws Exception {

        System.out.println("S'ha escrit: "+fileName);

        File file = new File(fileName);

        File parent = file.getParentFile();
       
        if (!parent.exists())
            parent.mkdirs();

        // .mkdirs();

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(payload);
        writer.close();

    }

    public void appendStatistic(String nomFitxer, int algoId, double compress, long duration) throws IOException {

        String name = "/tmp/stats";
        if (winOS())
            name = "C:\\Temp\\stats";
        FileWriter fw = new FileWriter(name, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);

        out.println(nomFitxer + "\t" + algoId + "\t" + duration + "\t" + String.format("%.2f", compress));
        out.close();

    }

    public String getChilds(String fold) {

        File folder = new File(fold);
        File[] list = folder.listFiles();
        String all = "";
        for (int i = 0; i < list.length; ++i) {
            if (list[i].isDirectory())
                all += getChilds(list[i].getAbsolutePath());
            else
                all += list[i].getAbsolutePath() + "//";

        }
        return all;
    }

}