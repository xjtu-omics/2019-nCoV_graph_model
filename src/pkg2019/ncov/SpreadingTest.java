/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fei
 */
public class SpreadingTest {
    
    public static void oneCitySpreadingProcess(String subCommand, String type, int rep_time, String outDir, 
            String outSign, String configFile){
        
        AllParameters.initilizeStaticParameters(configFile);
        
        int actionDays = -1;
        int initiaInfectedCount = 1;
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
//                line = line.trim();
                String[] oneParameter = line.split("=");
                if(oneParameter.length > 2){
                    System.err.println("There are some errors in the config file of line: " + line);
                    System.exit(1);
                }
                String parameter = oneParameter[0].trim();
                String value     = oneParameter[1].trim();
                
                if(parameter.equals("actionDay")){
                    actionDays = new Integer(value);
                }else if(parameter.equals("initialInfectedCount")){
                    initiaInfectedCount = new Integer(value);
                }else{
                    continue;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AllParameters.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AllParameters.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(subCommand.toLowerCase().equals("noaction")){
            oneCitySpreadingProcess_NO(type, rep_time, outDir, outSign, initiaInfectedCount, configFile);
        }
        if(subCommand.toLowerCase().equals("action")){
            if(actionDays == -1){
                System.err.println("action should begin at which day, please set the actionDay parameter in config file");
                System.exit(1);
            }
            oneCitySpreadingProcess_someAction(type, actionDays, rep_time, outDir, outSign, initiaInfectedCount, configFile);
        }  
    }
    
    public static void oneCitySpreadingProcess_NO(String type, int rep_time, String outdir, String outSign, 
            int initialCount, String configFile){
        try {
            int times = rep_time;
            System.out.println("initialize the city, family, hosiptal, and pubulic transport / regions");
//            int populationSize = cityPopulationSize;
            InitCity oneCity = new InitCity(AllParameters.populationSize);
            if(type.toLowerCase().equals("paris")){
                oneCity.setParis(AllParameters.populationSize);
            }
            if(type.toLowerCase().equals("stlouis")){
                oneCity.setStLouis(AllParameters.populationSize);
            }
            if(type.toLowerCase().equals("yinchuan")){
                oneCity.setYinChuan(AllParameters.populationSize);
            }
            if(type.toLowerCase().equals("tokyo")){
                oneCity.setTokyo(AllParameters.populationSize);
            }
            if(type.toLowerCase().equals("losangeles")){
                oneCity.setLosAngeles(AllParameters.populationSize);
            }
            if(type.toLowerCase().equals("singpore")){
                oneCity.setSingpore(AllParameters.populationSize);
            }
            if(type.toLowerCase().equals("newyork")){
                oneCity.setNewYork(AllParameters.populationSize);
            }
            Functions.parameters = new AllParameters();
            Functions.parameters.resetParameters(oneCity, configFile);
            
            
            Region city = Functions.initilizeACity(Functions.parameters.populationSize);

            Functions.allFamily = Functions.initilizeFamily(city, Functions.parameters.familyNumber, Functions.parameters.familyMemberNumber);
            Functions.initilizeTrans();
            Functions.hosiptal = Functions.initilizeHospital(Functions.parameters.hospitalCapacityForVirus);
            System.out.println("Hospital capacity: " + Functions.parameters.hospitalCapacityForVirus);
            Functions.initilizeTouchNumber();
            
            System.out.println("One day, someone eat wild animals and infected virus: 2019-nCoV");
            Vector<Integer> personIndex = Functions.infectFromAnimalOrInput(city, initialCount); // the first one infection virus
            
            File theOutDir = new File(outdir);
            theOutDir.mkdir();
            File theResDir = new File(outdir + "/simulation_res");
            theResDir.mkdir();
            BufferedWriter ResBw = new BufferedWriter(new FileWriter(outdir + "/simulation_res/theResults_" + type + "_" + outSign + "_" + AllParameters.populationSize + "_" + times + ".txt"));
            ResBw.write("time\tpopulation\tacc_infected\tacc_fever_infection\tacc_non_fever_infection\tinfected\tincubation\tfever\tsevere\tcure\tdead\n");
            System.out.println("day: 0 \t city population: " + (city.getPeopleSize() + city.getDeadPeopleNumber()) + 
                               "\t accumulate infected people: " + city.getAccumulateInfectedPeopleNumber() + 
                               "\t accumulate fever infection: " + city.getInfectionFromFeverPeopleNumber() + 
                               "\t accumulate non-fever infection: " + city.getInfectionFromNonFeverPeopleNumber() + 
                               "\t infected people: " + city.getInfectionPeopleNumber() + 
                               "\t incubation people: " + city.getIncubationPeopleNumber() +
                               "\t fever people: " + city.getFeverPeopleNumber() +
                               "\t severe people: " + city.getSeverePeopleNumber() +
                               "\t cure people: " + city.getCuredPeopleNumber() +
                               "\t dead people: " + city.getDeadPeopleNumber());
            ResBw.write(0 + "\t" + (city.getPeopleSize() + city.getDeadPeopleNumber()) + "\t" + 
                        city.getAccumulateInfectedPeopleNumber() + "\t" + 
                        city.getInfectionFromFeverPeopleNumber() + "\t" + 
                        city.getInfectionFromNonFeverPeopleNumber() + "\t" +
                        city.getInfectionPeopleNumber() + "\t" + city.getIncubationPeopleNumber() + "\t" +
                        city.getFeverPeopleNumber() + "\t" + city.getSeverePeopleNumber() + "\t" + 
                        city.getCuredPeopleNumber() + "\t" +
                        city.getDeadPeopleNumber() + "\n");

            int time_day = 0;
            
            boolean hasIncreaseBed = false;
            
            while(true){               
                // should first goto hospital
                Functions.goToHospital(city, Functions.hosiptal);
//                    System.out.println("type:" + type + "\tafter phenotype goto hospital infection: " + city.getInfectionPeopleNumber());

                Functions.personalSpread(city, Functions.parameters.personalInteractionNum);
//                    System.out.println("type:" + type + "\t After personal infection: " + city.getInfectionPeopleNumber());
                Functions.familySpread(Functions.allFamily);
//                    System.out.println("type:" + type + "\tafter family infection: " + city.getInfectionPeopleNumber());
                Functions.transSpread(city);
//                    System.out.println("type:" + type + "\tafter transport infection: " + city.getInfectionPeopleNumber());
                Functions.hospitalSpread(city, Functions.hosiptal);
//                    System.out.println("type:" + type + "\tafter hospital infection: " + city.getInfectionPeopleNumber());
//                Functions.goToHospital(city, Functions.hosiptal);
//                    System.out.println("type:" + type + "\tafter phenotype goto hospital infection: " + city.getInfectionPeopleNumber());
                city.updateAllPeople();
                city.removeDeadPeople();
//                    city.staR0();
                time_day++;
                System.out.println("sign:" + type + "\tday:" + time_day + "\t city population: " + (city.getPeopleSize() + city.getDeadPeopleNumber()) + 
                                   "\t accumulate infected people: " + city.getAccumulateInfectedPeopleNumber() + 
                                   "\t accumulate fever infection: " + city.getInfectionFromFeverPeopleNumber() + 
                                   "\t accumulate non-fever infection: " + city.getInfectionFromNonFeverPeopleNumber() + 
                                   "\t infected people: " + city.getInfectionPeopleNumber() + 
                                   "\t incubation people: " + city.getIncubationPeopleNumber() +
                                   "\t fever people: " + city.getFeverPeopleNumber() +
                                   "\t severe people: " + city.getSeverePeopleNumber() +
                                   "\t cure people: " + city.getCuredPeopleNumber() +
                                   "\t dead people: " + city.getDeadPeopleNumber());
                ResBw.write(time_day + "\t" + (city.getPeopleSize() + city.getDeadPeopleNumber()) + "\t" + 
                        city.getAccumulateInfectedPeopleNumber() + "\t" + 
                        city.getInfectionFromFeverPeopleNumber() + "\t" + 
                        city.getInfectionFromNonFeverPeopleNumber() + "\t" +
                        city.getInfectionPeopleNumber() + "\t" + 
                        city.getIncubationPeopleNumber() + "\t" +
                        city.getFeverPeopleNumber() + "\t" + 
                        city.getSeverePeopleNumber() + "\t" + 
                        city.getCuredPeopleNumber() + "\t" +
                        city.getDeadPeopleNumber() + "\n");

                if(city.getInfectionPeopleNumber() == 0){
                    break;
                }
                
                if(type.toLowerCase().equals("reducedeathrate")){
                    if(!hasIncreaseBed && city.getAccumulateInfectedPeopleNumber() >= AllParameters.theInfectionThresholdForBedNumberIncrease){
                        Functions.closeCity(city);
                        Functions.parameters.hospitalCapacityForVirus += AllParameters.theBedNumberIncrease;
                        Functions.hosiptal.setCapacity(Functions.parameters.hospitalCapacityForVirus);
                        hasIncreaseBed = true;
                    }
                }
                
                if(time_day >= 500){
                    break;
                }
            }
            ResBw.flush();
            ResBw.close();
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void oneCitySpreadingProcess_someAction(String type, int action_day, int reptime, 
            String outdir, String outSign, int initialCount, String configFile){
        try {
            int oneActionDays = action_day;
            int times = reptime;
            System.out.println("initialize the city, family, hosiptal, and pubulic transport / regions");
//            int populationSize = cityPopulationSize;
            InitCity oneCity = new InitCity(AllParameters.populationSize);
            Functions.parameters.resetParameters(oneCity, configFile);
            Region city = Functions.initilizeACity(Functions.parameters.populationSize);

            Functions.allFamily = Functions.initilizeFamily(city, Functions.parameters.familyNumber, Functions.parameters.familyMemberNumber);
            Functions.initilizeTrans();
            Functions.hosiptal = Functions.initilizeHospital(Functions.parameters.hospitalCapacityForVirus);
            Functions.initilizeTouchNumber();
            System.out.println("One day, someone eat wild animals and infected virus: 2019-nCoV");
            Vector<Integer> personIndex = Functions.infectFromAnimalOrInput(city, initialCount); // the first one infection virus

            File theOutDir = new File(outdir);
            theOutDir.mkdir();
            File theResDir = new File(outdir + "/simulation_res");
            theResDir.mkdir();
            BufferedWriter ResBw = new BufferedWriter(new FileWriter(outdir + "/simulation_res/theResults_" + type + "_" + outSign + "_day_" + oneActionDays + "_" + AllParameters.populationSize + "_" + times + ".txt"));
            ResBw.write("time\tpopulation\tacc_infected\tacc_fever_infection\tacc_non_fever_infection\tinfected\tincubation\tfever\tsevere\tcure\tdead\n");
            System.out.println("sign:" + type + "\tday: 0 \t city population: " + (city.getPeopleSize() + city.getDeadPeopleNumber()) +
                               "\t accumulate infected people: " + city.getAccumulateInfectedPeopleNumber() + 
                               "\t accumulate fever infection: " + city.getInfectionFromFeverPeopleNumber() + 
                               "\t accumulate non-fever infection: " + city.getInfectionFromNonFeverPeopleNumber() + 
                               "\t infected people: " + city.getInfectionPeopleNumber() + 
                               "\t incubation people: " + city.getIncubationPeopleNumber() +
                               "\t Fever people: " + city.getFeverPeopleNumber() +
                               "\t Severe people: " + city.getSeverePeopleNumber() +
                               "\t cure people: " + city.getCuredPeopleNumber() +
                               "\t dead people: " + city.getDeadPeopleNumber());
            ResBw.write(0 + "\t" + (city.getPeopleSize() + city.getDeadPeopleNumber()) + "\t" + 
                        city.getAccumulateInfectedPeopleNumber() + "\t" + 
                        city.getInfectionFromFeverPeopleNumber() + "\t" + 
                        city.getInfectionFromNonFeverPeopleNumber() + "\t" +
                        city.getInfectionPeopleNumber() + "\t" + 
                        city.getIncubationPeopleNumber() + "\t" +
                        city.getFeverPeopleNumber() + "\t" + 
                        city.getSeverePeopleNumber() + "\t" + 
                        city.getCuredPeopleNumber() + "\t" +
                        city.getDeadPeopleNumber() + "\n");

            int time_day = 0;
            boolean isSpring = false;
            boolean isPanic  =false;
            int     springLastTime = 0;
            int     panicLastTime  = 0;
            while(true){
                
                // should first go to hospital
                Functions.goToHospital(city, Functions.hosiptal);
//                System.out.println("type:" + type + "\tafter phenotype goto hospital infection: " + city.getInfectionPeopleNumber());
                
                Functions.personalSpread(city, Functions.parameters.personalInteractionNum);
//                System.out.println("type:" + type + "\t After personal infection: " + city.getInfectionPeopleNumber());
                Functions.familySpread(Functions.allFamily);
//                System.out.println("type:" + type + "\tafter family infection: " + city.getInfectionPeopleNumber());
                Functions.transSpread(city);
//                System.out.println("type:" + type + "\tafter transport infection: " + city.getInfectionPeopleNumber());
                Functions.hospitalSpread(city, Functions.hosiptal);
//                System.out.println("type:" + type + "\tafter hospital infection: " + city.getInfectionPeopleNumber());
                Functions.specialAction(city);
//                Functions.goToHospital(city, Functions.hosiptal);
//                System.out.println("type:" + type + "\tafter phenotype goto hospital infection: " + city.getInfectionPeopleNumber());
                city.updateAllPeople();
                city.removeDeadPeople();
                time_day++;
                if(isSpring){
                    springLastTime++;
                }
                if(isPanic){
                    panicLastTime++;
                }
                System.out.println("sign:" + type + "\tday:" + time_day + "\t city population: " + (city.getPeopleSize() + city.getDeadPeopleNumber()) + 
                                   "\t accumulate infected people: " + city.getAccumulateInfectedPeopleNumber() + 
                                   "\t accumulate fever infection: " + city.getInfectionFromFeverPeopleNumber() + 
                                   "\t accumulate non-fever infection: " + city.getInfectionFromNonFeverPeopleNumber() + 
                                   "\t infected people: " + city.getInfectionPeopleNumber() + 
                                   "\t incubation people: " + city.getIncubationPeopleNumber() +
                                   "\t phenotype people: " + city.getFeverPeopleNumber() +
                                   "\t Severe people: " + city.getSeverePeopleNumber() +
                                   "\t cure people: " + city.getCuredPeopleNumber() +
                                   "\t dead people: " + city.getDeadPeopleNumber());
                ResBw.write(time_day + "\t" + (city.getPeopleSize() + city.getDeadPeopleNumber()) + "\t" + 
                        city.getAccumulateInfectedPeopleNumber() + "\t" + 
                        city.getInfectionFromFeverPeopleNumber() + "\t" + 
                        city.getInfectionFromNonFeverPeopleNumber() + "\t" +
                        city.getInfectionPeopleNumber() + "\t" + 
                        city.getIncubationPeopleNumber() + "\t" +
                        city.getFeverPeopleNumber() + "\t" + 
                        city.getSeverePeopleNumber() + "\t" + 
                        city.getCuredPeopleNumber() + "\t" +
                        city.getDeadPeopleNumber() + "\n");

                if(time_day == oneActionDays){
                    if(type.toLowerCase().equals("springfestival")){
                        Functions.springFestival();
                        isSpring = true;
                    }
                    if(type.toLowerCase().equals("closebus")){
                        Functions.closeBus();
                    }
                    if(type.toLowerCase().equals("closetaxi")){
                        Functions.closeTaxi();
                    }
                    if(type.toLowerCase().equals("closesubway")){
                        Functions.closeSubWay();
                    }
                    if(type.toLowerCase().equals("communityhospital")){
                        Functions.communityHospital(city);
                    }
                    if(type.toLowerCase().equals("closecity")){
                        Functions.closeCity(city);
                    }
                    if(type.toLowerCase().equals("goodhealth")){
                        Functions.goodHealth(city);
                    }
                    if(type.toLowerCase().equals("publicdisinfectants")){
                        Functions.publicDisinfectants();
                    }
                    if(type.toLowerCase().equals("homeisolation")){
                        Functions.homeIsolation(city);
                    }
                    if(type.toLowerCase().equals("closetransportation")){
                        Functions.closePublicTransportation(city);
                    }
                    if(type.toLowerCase().equals("multiple")){
                        Functions.closeCity(city);
                        Functions.goodHealth(city);
                        Functions.publicDisinfectants();
//                        Functions.homeIsolation(city);
                    }
                    if(type.toLowerCase().equals("peoplepanic")){
                        Functions.peoplePanic();
                        isPanic = true;
                    }
                }

                if(isSpring && springLastTime == 7){
                    Functions.endSpringFestival();
                    isSpring = false;
                    springLastTime = 0;
                }
                if(isPanic && panicLastTime == 7){
                    Functions.peopleCalmdown();
                    isPanic = false;
                    panicLastTime = 0;
                }
                if(city.getInfectionPeopleNumber() == 0){
                    break;
                }
                
                if(time_day >= 500){
                    break;
                }
            }
            ResBw.flush();
            ResBw.close();
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
