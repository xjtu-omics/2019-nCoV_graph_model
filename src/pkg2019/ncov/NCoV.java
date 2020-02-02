/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

import java.util.HashSet;

/**
 *
 * @author fei
 */
public class NCoV {

    /**
     * @param args the command line arguments
     */
    public static void printHelpMessage(){
        System.out.println("###################### help message ################");
            System.out.println("usage: java -jar 2019-nCoV_v2.jar noaction -ii incubation_infected -t type -p populaiton -icount initilizeInfectedCount -r replicate_time -minI minIncubation -maxI maxIncubation -meanI meanIncubation -os outsign -o outdir");
            System.out.println("       java -jar 2019-nCoV_v2.jar action   -ii incubation_infected -t type -p populaiton -icount initilizeInfectedCount -d action_day -r replicate_time -minI minIncubation -maxI maxIncubation -meanI meanIncubation -os outsign -o outdir");
            System.out.println("-------- subcommand (required, string): noaction or action");
            System.out.println("-------- incubation_infected (required, boolean): incubation infected, true or false");
            System.out.println("-------- type(required, string): \n"
                             + "               noaction: NO \n"
                             + "                         America \n"
                             + "                         Poor \n");
            System.out.println("               action:   springFestival \n"
                             + "                         peoplePanic \n"
                             + "                         goodHealth \n"
                             + "                         closeBus \n"
                             + "                         communityHospital \n"
                             + "                         closeTaxi \n"
                             + "                         closeSubway \n"
                             + "                         closeCity \n"
                             + "                         publicDisinfectants \n"
                             + "                         homeIsolation \n"
                             + "                         multiple");
            System.out.println("-------- population (required int): e.g. 100000");
            System.out.println("-------- action_day (required int): e.g. 5");
            System.out.println("-------- initilizeInfectedCount (optional int, default = 1): e.g. 1");
            System.out.println("-------- replicate_time (optional, int, default = 1): e.g. 1, 2, 3...");
            System.out.println("-------- minIncubation (optional, int, default = 1)");
            System.out.println("-------- maxIncubation (optional, int, default = 14): e.g. 1, 2, 3...");
            System.out.println("-------- meanIncubation (optional, int, default = 7): e.g. 1, 2, 3...");
            System.out.println("-------- outsign (optional, string, default = \"\")");
            System.out.println("-------- outdir (optional, string, default = ./)");
    }
    
    public static void parameterParser(String[] args){
        HashSet<String> subcommand = new HashSet<>();
        subcommand.add("noaction"); subcommand.add("action");
        HashSet<String> noaction = new HashSet<>();
        noaction.add("NO"); noaction.add("America"); noaction.add("Poor");
        HashSet<String> actions = new HashSet<>();
        actions.add("springFestival"); actions.add("peoplePanic");
        actions.add("goodHealth"); actions.add("closeBus");
        actions.add("communityHospital"); 
        actions.add("closeTaxi");
        actions.add("closeSubway"); actions.add("closeCity");
        actions.add("homeIsolation"); actions.add("multiple");
        if(args.length == 0){
            System.err.println("no parameters");
            printHelpMessage();
            return;
        }
        if(args.length > 0 && !subcommand.contains(args[0])){
            System.err.println("error subcommand");
            printHelpMessage();
            return;
        }
        String command = args[0];
        String type = "";
        int population = 0;
        int initilizeCount = 1;
        int action_day = 0;
        int repeat_time = 1;
        boolean incubationInfection = false;
        int minIncubation = 1;
        int maxIncubation = 14;
        int meanIncubation = 7;
        String outDir = ".";
        String outSign = "";
        
        for(int i = 1; i < args.length; ++i){
            if(args[i].equals("-t")){
                type = args[++i];
            }else if(args[i].equals("-p")){
                population = new Integer(args[++i]);
            }else if(args[i].equals("-d")){
                action_day = new Integer(args[++i]);
            }else if(args[i].equals("-r")){
                repeat_time = new Integer(args[++i]);
            }else if(args[i].equals("-ii")){
                i++;
                incubationInfection = ((args[i].toLowerCase().equals("true")) || 
                                       (args[i].toLowerCase().equals("t"))) ? true : false;
            }else if(args[i].equals("-minI")){
                minIncubation = new Integer(args[++i]);
            }else if(args[i].equals("-maxI")){
                maxIncubation = new Integer(args[++i]);
            }else if(args[i].equals("-meanI")){
                meanIncubation = new Integer(args[++i]);
            }else if(args[i].equals("-o")){
                outDir = args[++i];
            }else if(args[i].equals("-os")){
                outSign = args[++i];
            }else if(args[i].equals("-icount")){
                initilizeCount = new Integer(args[++i]);
            }else{
                System.err.println(args[i] + ", not corrected parameters");
                printHelpMessage();
                return;
            }
        }
        if(command.equals("noaction")){
            if(type.equals("") || population == 0){
                printHelpMessage();
                return;
            }
        }
        if(command.equals("action")){
            if(type.equals("") || population == 0 || action_day == 0){
                printHelpMessage();
                return;
            }
        }
        if(command.equals("noaction")){
            SpreadingTest.oneCitySpreadingProcess_NO(type, incubationInfection, 
                    population, repeat_time, maxIncubation, 
                    minIncubation, meanIncubation, outDir, outSign, initilizeCount);
        }
        if(command.equals("action")){
            SpreadingTest.oneCitySpreadingProcess_someAction(type, incubationInfection, 
                    population, action_day, repeat_time, maxIncubation, 
                    minIncubation, meanIncubation, outDir, outSign, initilizeCount);
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", false, 100000, 5, 14, 1, 7);
//        SpreadingTest.oneCitySpreadingProcess_NO("America", 100000);
//        SpreadingTest.oneCitySpreadingProcess_NO("Africa", 100000);

//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 1, 14, 1, 7, ".", "50_initilizeInfected", 50);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 2, 14, 1, 7, ".", "50_initilizeInfected", 50);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 3, 14, 1, 7, ".", "50_initilizeInfected", 50);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 4, 14, 1, 7, ".", "50_initilizeInfected", 50);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 5, 14, 1, 7, ".", "50_initilizeInfected", 50);
//        
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", false, 100000, 1, 14, 1, 7, ".", "incubation_unifected", 1);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", false, 100000, 2, 14, 1, 7, ".", "incubation_unifected", 1);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", false, 100000, 3, 14, 1, 7, ".", "incubation_unifected", 1);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", false, 100000, 4, 14, 1, 7, ".", "incubation_unifected", 1);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", false, 100000, 5, 14, 1, 7, ".", "incubation_unifected", 1);
//        
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 1, 14, 1, 7, ".", "1000_initilizeInfected", 1000);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 2, 14, 1, 7, ".", "1000_initilizeInfected", 1000);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 3, 14, 1, 7, ".", "1000_initilizeInfected", 1000);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 4, 14, 1, 7, ".", "1000_initilizeInfected", 1000);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO", true, 100000, 5, 14, 1, 7, ".", "1000_initilizeInfected", 1000);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_zeroIncubation", true, 100000, 1, 0, 0, 0);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_zeroIncubation", true, 100000, 2, 0, 0, 0);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_zeroIncubation", true, 100000, 3, 0, 0, 0);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_zeroIncubation", true, 100000, 4, 0, 0, 0);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_zeroIncubation", true, 100000, 5, 0, 0, 0);
//        
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_largerIncubation", true, 100000, 1, 21, 7, 14);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_largerIncubation", true, 100000, 2, 21, 7, 14);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_largerIncubation", true, 100000, 3, 21, 7, 14);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_largerIncubation", true, 100000, 4, 21, 7, 14);
//        SpreadingTest.oneCitySpreadingProcess_NO("NO_largerIncubation", true, 100000, 5, 21, 7, 14);
//        
//        SpreadingTest.oneCitySpreadingProcess_someAction("closeCity", 100000, 4, 1);
//        SpreadingTest.oneCitySpreadingProcess_someAction("peoplePanic", 100000, 4, 1);
//        SpreadingTest.oneCitySpreadingProcess_someAction("springFestival", 10000000, 4, 1);
           
//          String[] aargs = {"noaction", "-ii", "true", "-t", "America", "-p", "100000", "-r", "1", 
//              "-minI", "1", "-maxI", "14", "-meanI", "7", "-o", "diff_place2", "-os", "America_like"};
          parameterParser(args);
//       
    }
    
}
