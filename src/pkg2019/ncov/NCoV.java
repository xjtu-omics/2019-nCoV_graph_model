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
    
    public static HashSet<String> subcommand = new HashSet<>();
    public static HashSet<String> noaction = new HashSet<>();
    public static HashSet<String> actions = new HashSet<>();

    /**
     * @param args the command line arguments
     */
    public static void InitilizeParameter(){
        NCoV.subcommand.add("noaction"); 
        NCoV.subcommand.add("action");
        NCoV.noaction.add("no"); 
        NCoV.noaction.add("paris"); 
        NCoV.noaction.add("stlouis");
        NCoV.noaction.add("yinchuan");
        NCoV.noaction.add("tokyo");
        NCoV.noaction.add("losangeles");
        NCoV.noaction.add("singpore");
        NCoV.noaction.add("newyork");
        NCoV.noaction.add("reducedeathrate");
        NCoV.actions.add("springfestival"); 
        NCoV.actions.add("peoplepanic");
        NCoV.actions.add("goodhealth"); 
        NCoV.actions.add("closetransportation");
        NCoV.actions.add("communityhospital"); 
        NCoV.actions.add("closecity");
        NCoV.actions.add("homeisolation"); 
        NCoV.actions.add("multiple");
    }
    
    public static void printHelpMessage(){
        System.out.println("###################### help message ################");
            System.out.println("usage: java -jar 2019-nCoV_v4.jar subcommand -t type -r replicate_time -os outsign -o outdir -config configFile");
            System.out.println("-------- subcommand (required, string): noaction or action");
            System.out.println("-------- type(required, string): \n"
                             + "               noaction: NO \n"
                             + "                         reduceDeathRate \n  "
                             + "                         Paris \n"
                             + "                         stlouis \n"
                             + "                         LosAngeles \n"
                             + "                         NewYork \n"
                             + "                         Singpore \n"
                             + "                         Tokyo \n" 
                             + "                         YinChuan \n");
            System.out.println("               action:   springFestival \n"
                             + "                         peoplePanic \n"
                             + "                         goodHealth \n"
                             + "                         communityHospital \n"
                             + "                         closeTransportation \n"
                             + "                         closeCity \n"
                             + "                         publicDisinfectants \n"
                             + "                         homeIsolation \n"
                             + "                         multiple ");
            System.out.println("-------- configFile (require, string)");
            System.out.println("-------- replicate_time (optional, int)");
            System.out.println("-------- outsign (optional, string, default = \"\")");
            System.out.println("-------- outdir (optional, string, default = ./)");
    }
    
    public static void parameterParser(String[] args){
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
        String outDir = ".";
        String outSign = "";
        String configFile = "";
        int repeat_time = 1;
        
        for(int i = 1; i < args.length; ++i){
            if(args[i].equals("-t")){
                type = args[++i];
            }else if(args[i].equals("-o")){
                outDir = args[++i];
            }else if(args[i].equals("-os")){
                outSign = args[++i];
            }else if(args[i].equals("-config")){
                configFile = args[++i];
            }else if(args[i].equals("-r")){
                repeat_time = new Integer(args[++i]);
            }else{
                System.err.println(args[i] + ", not corrected parameters");
                printHelpMessage();
                return;
            }
        }
        if(command.equals("noaction")){
            if(type.equals("") || !noaction.contains(type.toLowerCase())){
                System.err.println(type + " must correct!");
                printHelpMessage();
                return;
            }
        }
        if(command.equals("action")){
            if(type.equals("") || !actions.contains(type.toLowerCase())){
                System.err.println(type + " must correct!");
                printHelpMessage();
                return;
            }
        }
        if(configFile.equals("")){
            System.err.println("config file is a required parameter");
            printHelpMessage();
            return;
        }
        SpreadingTest.oneCitySpreadingProcess(command, type, repeat_time, outDir, outSign, configFile);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here 
        InitilizeParameter();
        parameterParser(args);
    }
    
}
