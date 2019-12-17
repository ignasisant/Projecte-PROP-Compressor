package domini;



class Statistics {
    private long start;
    private long end;
    private long duration;
    private Fitxer f;

    private static Statistics singleton = new Statistics();


    private Statistics() {
        this.f = new Fitxer();//Fitxer.getFitxer();
    }
    
    public static Statistics getStatistics() {
        return singleton;
    }

    public void initStats() {
        this.start =  System.currentTimeMillis();
    }

    public String[] saveStats(String nomFitxer, int algoId, int inSize, int outSize ) {
       //TODO: if (this.start == null) tira excepcio no sha inicialitzat el contador
        this.end =  System.currentTimeMillis();
        duration = this.end-this.start;
        double compress = 100 - ((float) outSize/ (float) inSize*100.0);

        String[] info = { nomFitxer, String.valueOf(algoId), String.valueOf(inSize), String.valueOf(outSize), String.valueOf(compress), String.valueOf(duration) } ;

        this.f.saveStatistic(nomFitxer, algoId, compress, duration);
        return info;
       
    }
   

   



}
