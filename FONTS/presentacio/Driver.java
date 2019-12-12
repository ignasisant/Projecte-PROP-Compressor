//package presentacio;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//
//class Driver {
//    private String inData;
//    private String outData;
//
//    public  static void main(String[] args)  {
//        // if( args.length == 0) {
//        //     Statistics st = new Statistics();
//        //     System.out.println(st.getStats());
//        // } else {
//        //     Path de = Paths.get(args[0]);
//
//        //     IOUtils test1 = new IOUtils(de, Integer.parseInt(args[1]), new LZ78(), args[2]);
//
//        //     test1.run();
//        // }
//        String input = "", output = "";
//        Boolean out = false;
//        int algo = -1, action = -1;
//
//        int i = 0;
//        while (i < args.length) {
//            // System.out.println(i+" "+args[i].length());
//            int length = args[i].length();
//            String first = args[i].substring(2, length);
//            if(args[i].substring(0, 2).equals("--")) {
//                ++i;
//                switch (first) {
//                    case "input":
//                        input = args[i];
//                        break;
//                    case "algorithm":
//                        algo = Integer.parseInt(args[i]);
//                         break;
//                    case "action":
//                        action = Integer.parseInt(args[i]);
//                         break;
//                    case "output":
//                        output = args[i];
//                        break;
//                }
//                ++i;
//            } else {
//
//                usage();
//                return;
//            }
//        }
//
//
//        IOUtils test = new IOUtils();
//        if(args.length == 0) test.getStats();
//        else {
//            try {
//                test.setInputFile(input);
//                test.setAlgorithm(algo);
//                test.setAction(action);
//                test.setOutputFile(output);
//                test.run();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//
//    }
//
//    public static void usage() {
//        System.out.println("java Driver --input <in> --algorithm <algoId> --action <act> [--output <out> ]");
//    }
//
//    public static void testInputFile(String name) {
//        IOUtils test = new IOUtils();
//        try {
//            test.setInputFile(name);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void testRunNoInput() {
//        IOUtils test = new IOUtils();
//        try {
//            test.setAlgorithm(0);
//            test.setAction(0);
//            test.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void testRunNoAlgorithm(String name) {
//        IOUtils test = new IOUtils();
//        try {
//            test.setInputFile(name);
//            test.setAction(0);
//            test.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void testRunNoAction(String name) {
//        IOUtils test = new IOUtils();
//        try {
//            test.setInputFile(name);
//            test.setAlgorithm(0);
//            test.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void testRun(String name) {
//        IOUtils test = new IOUtils();
//        try {
//            test.setInputFile(name);
//            test.setAlgorithm(0);
//            test.setAction(0);
//            test.run();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}