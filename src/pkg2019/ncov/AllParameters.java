/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fei
 */
public class AllParameters {
    public int familyNumber   ;  // set in resetParameters function
    public int familyInteractionNumber;  // set in resetParameters function 
    public int personalInteractionNum ;  // set in resetParameters function 
    public int hospitalPeopleNumPerDay;  // set in resetParameters function 
    public int hospitalCapacityForVirus; // set in resetParameters function, also can set from config file
    
    public int busPassangerNum;  // set from config file,
    public int taxiPassangerNum;   // set from config file,
    public int subwayPassangerNum;  // set from config file,
//    public int boatPassangerNum   = 100;
    public int publicPassangerNum ; // set in resetParameters function 
    public int otherPassangerNum  = 100;  
    
    public int taxiNumber    = 500;  // set in resetParameters function,
    public int busNumber     = 100; // set in resetParameters function,
//    public int boatNumber    = 50;
    public int subwayNumber  = 4;  // set in resetParameters function,
    public int publicNumber  = 1;
    public int otherNumber   = 1;
    
    public int taxiWorkNumPerDay    = 20;  // set in resetParameters function,
    public int busWorkNumPerDay     = 20;  // set in resetParameters function,
//    public int boatWorkNumPerDay    = 10;
    public int subwayWorkNumPerDay  = 10;  // set in resetParameters function,
    public int publicWorkNumPerDay  = 1;
    public int otherWorkNumPerDay   = 1;
    
    public int taxiTouchNumber; //set from config file, how many people may touched with another in this region
    public int busTouchNumber; // set from config file,
//    public int boatTouchNumber     = 3; // 
    public int subwayTouchNumber; // set from config file,
    public int hospitalTouchNumber; // set from config file,
    public int familyTouchNumber; // set from config file,
    public int interFamilyTouchNumber= 100; 
    public int publicTouchNumber; // set from config file,
    public int otherTouchNumber    = 1;

    public static int familyMemberNumber  = 3;
    public static int populationSize      = 100000;
    
//    public static float peopleInitSpreadPro         = 0f;
//    public static float peopleMaxmizeSpreadPro      = 0.3f; // may be need to adjust
    public static int   cityMaxPeople               = Integer.MAX_VALUE;
    public static float peopleInitInfectionPro      = 0.1f;  // may be need to adjust
    public static float peopleInitDeadPro           = 0.05f;
    public static float peopleHospitalDeadPro       = 0.01f;
    public static float goodHealthReduceSpreProFold = 0.4f;  // may be need to adjust
    public static float cureInfectionProFold        = 0.4f;  // may be need to adjust
//    public static float homeIsolationSpreadPro      = 0.001f;
    
    public static int treatLastTime  = 14;
    public static int minIncubation  = 1;
    public static int maxIncubation  = 24;
    public static int meanIncubation = 3;
    
    public static int panicFold      = 2;
    
    public static int theBedNumberIncrease = 100;
    public static int theInfectionThresholdForBedNumberIncrease = 100;
    
    public static float populationProporationForFamily = 0.8f;
    public static float populationProporationForPersonInteraction = 0.1f;
    public static float familyInteractionProporation = 0.2f;
    public static float publicPopulationPropation = 0.01f;
    public static float percentageCloseTransportation = 0.0f;
    public static float severePropation       = 0.15f;  // the proporation of patients with non-severe
    public static float feverPropation        = 0.5f; // the percentage of patients with fever 
    public static float rnaDetectionPropation = 0.9f; // the proporation of patients with rna detection
    
//    public static float incubationInfectionPro = 0.0f;
    public static boolean cureStillBeInfected = false;
    public static boolean isCloseTransportation = false;
    
    public static void initilizeStaticParameters(String configFile){
        try {
            BufferedReader br  = new BufferedReader(new FileReader(configFile));
            String line = "";
            while((line = br.readLine()) != null){
                if(line.startsWith("#") || line.trim().equals("")){
                    continue;
                }
                if(line.contains("#")){
                    line = line.substring(0, line.indexOf("#"));
                }
                
                String[] oneParameter = line.split("=");
                if(oneParameter.length > 2){
                    System.err.println("There are some errors in the config file of line: " + line);
                    System.exit(1);
                }
                String parameter = oneParameter[0].trim();               
                String value     = oneParameter[1].trim();
                
                if(parameter.equals("population")){
                    AllParameters.populationSize = new Integer(value);
                    if(AllParameters.populationSize == 0){
                        System.err.println("The population size is zero");
                        System.exit(1);
                    }
                }else if(parameter.equals("familyMemberNumber")){
                    AllParameters.familyMemberNumber = new Integer(value);
                }else if(parameter.equals("peopleInitInfectionPro")){
                    AllParameters.peopleInitInfectionPro = new Float(value);
                }else if(parameter.equals("peopleInitDeadPro")){
                    AllParameters.peopleInitDeadPro = new Float(value);
                }else if(parameter.equals("peopleHospitalDeadPro")){
                    AllParameters.peopleHospitalDeadPro = new Float(value);
                }else if(parameter.equals("goodHealthReducedSpreProFold")){
                    AllParameters.goodHealthReduceSpreProFold = new Float(value);
                }else if(parameter.equals("cureInfectionProFold")){
                    AllParameters.cureInfectionProFold  = new Float(value);
                }else if(parameter.equals("treatLastTime")){
                    AllParameters.treatLastTime = new Integer(value);
                }else if(parameter.equals("minIncubation")){
                    AllParameters.minIncubation = new Integer(value);
                }else if(parameter.equals("maxIncubation")){
                    AllParameters.maxIncubation = new Integer(value);
                }else if(parameter.equals("meanIncubation")){
                    AllParameters.meanIncubation = new Integer(value);
                }else if(parameter.equals("panicFold")){
                    AllParameters.panicFold = new Integer(value);
                }else if(parameter.equals("patientSevereProbability")){
                    AllParameters.severePropation = new Float(value);
                }else if(parameter.equals("patientWithFeverProbability")){
                    AllParameters.feverPropation = new Float(value);
                }else if(parameter.equals("patientRNADetectionProbability")){
                    AllParameters.rnaDetectionPropation = new Float(value);
                }else if(parameter.equals("populationProporationForFamily")){
                    AllParameters.populationProporationForFamily = new Float(value);
                }else if(parameter.equals("populationProporationForPersonInteraction")){
                    AllParameters.populationProporationForPersonInteraction  = new Float(value);
                }else if(parameter.equals("familyInteractionProporation")){
                    AllParameters.familyInteractionProporation  = new Float(value);
                }else if(parameter.equals("publicPopulationPropation")){
                    AllParameters.publicPopulationPropation = new Float(value);
                }
                else if(parameter.equals("increaseHospitalResource")){
                    AllParameters.theBedNumberIncrease = new Integer(value);
                }else if(parameter.equals("decreaseDeathRateInfectionNum")){
                    AllParameters.theInfectionThresholdForBedNumberIncrease = new Integer(value);
                }
                else if(parameter.equals("cureStillInfected")){
                    AllParameters.cureStillBeInfected = new Boolean(value);
                }else if(parameter.equals("closeTransportationPerscent")){
                    AllParameters.percentageCloseTransportation = new Float(value);
                }
                else{
                    continue;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AllParameters.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AllParameters.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void resetParameters(InitCity oneInitCiyt, String configFile){
//        AllParameters.populationSize           = oneInitCiyt.population;
        try {
            BufferedReader br  = new BufferedReader(new FileReader(configFile));
            String line = "";
            while((line = br.readLine()) != null){
                if(line.startsWith("#") || line.trim().equals("")){
                    continue;
                }
                if(line.contains("#")){
                    line = line.substring(0, line.indexOf("#"));
                }
                
                String[] oneParameter = line.split("=");
                if(oneParameter.length > 2){
                    System.err.println("There are some errors in the config file of line: " + line);
                    System.exit(1);
                }
                String parameter = oneParameter[0].trim();
                String onevalue  = oneParameter[1].trim();
                
                if(parameter.equals("hospitalCapacityForVirus")){
                    if(onevalue != null && !onevalue.equals("")){
                        this.hospitalCapacityForVirus = new Integer(onevalue);
                    }else{
                        this.hospitalCapacityForVirus = -1;
                    }
                }else if(parameter.equals("busPassangerNum")){
                    this.busPassangerNum = new Integer(onevalue);
                }else if(parameter.equals("taxiPassangerNum")){
                    this.taxiPassangerNum = new Integer(onevalue);
                }else if(parameter.equals("subwayPassangerNum")){
                    this.subwayPassangerNum = new Integer(onevalue);
                }else if(parameter.equals("taxiTouchNumber")){
                    this.taxiTouchNumber = new Integer(onevalue);
                }else if(parameter.equals("busTouchNumber")){
                    this.busTouchNumber  = new Integer(onevalue);
                }else if(parameter.equals("subwayTouchNumber")){
                    this.subwayTouchNumber = new Integer(onevalue);
                }else if(parameter.equals("hospitalTouchNumber")){
                    this.hospitalTouchNumber = new Integer(onevalue);
                }else if(parameter.equals("familyTouchNumber")){
                    this.familyTouchNumber = new Integer(onevalue);
                    if(this.familyTouchNumber > AllParameters.familyMemberNumber){
                        this.familyTouchNumber = AllParameters.familyMemberNumber;
                    }
                }else if(parameter.equals("publicTouchNumber")){
                    this.publicTouchNumber = new Integer(onevalue);
                }else{
                    continue;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AllParameters.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AllParameters.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(this.hospitalCapacityForVirus == -1){
            this.hospitalCapacityForVirus = oneInitCiyt.hospitalICUNum;
        }
        this.hospitalPeopleNumPerDay  = oneInitCiyt.oneDayPatients;
        this.familyNumber             = (int)(AllParameters.populationSize * AllParameters.populationProporationForFamily / AllParameters.familyMemberNumber);
        this.personalInteractionNum   = (int)(AllParameters.populationSize * AllParameters.populationProporationForPersonInteraction);
        this.familyInteractionNumber  = (int)(this.familyNumber * AllParameters.familyInteractionProporation);
        this.publicPassangerNum       = (int)(AllParameters.populationSize * AllParameters.publicPopulationPropation);
        
        this.taxiNumber = oneInitCiyt.taxiNum;
        this.busNumber  = oneInitCiyt.busNum;
        this.subwayNumber = oneInitCiyt.subwayNum;
//        this.boatNumber   = oneInitCiyt.shipNum;
        
        if(this.taxiNumber != 0 && this.taxiPassangerNum != 0){
            this.taxiWorkNumPerDay = (int)(oneInitCiyt.taxiPeopleNum / (this.taxiNumber * this.taxiPassangerNum));
        }else{
            this.taxiWorkNumPerDay = 0;
        }
        if(this.busNumber != 0 && this.busPassangerNum != 0){
            this.busWorkNumPerDay  = (int)(oneInitCiyt.busPeopleNum / (this.busNumber * this.busPassangerNum));
        }else{
            this.busWorkNumPerDay = 0;
        }
        if(this.subwayNumber != 0 && this.subwayPassangerNum != 0){
            this.subwayWorkNumPerDay = (int)(oneInitCiyt.subwayPeopleNum / (this.subwayNumber * this.subwayPassangerNum));
        }else{
            this.subwayWorkNumPerDay = 0;
        }
//        this.boatWorkNumPerDay = (int)(oneInitCiyt.shipPeopleNum / (boatNumber * boatPassangerNum));
    }
}
