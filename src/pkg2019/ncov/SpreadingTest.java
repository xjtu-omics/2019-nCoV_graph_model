/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

import java.io.BufferedWriter;
import java.io.File;
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
    public static void oneCitySpreadingProcess_NO(String sign, boolean isIncubationInfection, 
            int cityPopulationSize, int rep_time, int maxIncubation, 
            int minIncubation, int meanIncubation, String outdir, 
            String outSign, int initialCount){
        try {
            AllParameters.incubationInfection = isIncubationInfection;
            AllParameters.maxIncubation = maxIncubation;
            AllParameters.minIncubation = minIncubation;
            AllParameters.meanIncubation = meanIncubation;
            
            /// use to debug the infection pro
//            AllParameters.peopleInitInfectionPro = infectionPro; 
            
            int times = rep_time;
            System.out.println("initialize the city, family, hosiptal, and pubulic transport / regions");
            int populationSize = cityPopulationSize;
            InitCity theParameters = new InitCity(populationSize);
            Functions.parameters = new AllParameters();
            Functions.parameters.resetParameters(theParameters);
            
            Region city = Functions.initilizeACity(Functions.parameters.populationSize);

            Functions.allFamily = Functions.initilizeFamily(city, Functions.parameters.familyNumber, Functions.parameters.familyMemberNumber);
            Functions.initilizeTrans();
            Functions.hosiptal = Functions.initilizeHospital(Functions.parameters.hospitalCapacityForVirus);
            Functions.initilizeTouchNumber();
            
            if(sign.equals("America")){
                Functions.AmericaLikeRegion();
            }
            if(sign.equals("Poor")){
                Functions.PoorLikeRegion();
            }
            
            System.out.println("One day, someone eat wild animals and infected virus: 2019-nCoV");
            Vector<Integer> personIndex = Functions.infectFromAnimalOrInput(city, initialCount); // the first one infection virus
            
            File theOutDir = new File(outdir);
            theOutDir.mkdir();
            File theResDir = new File(outdir + "/simulation_res");
            theResDir.mkdir();
            BufferedWriter ResBw = new BufferedWriter(new FileWriter(outdir + "/simulation_res/theResults_" + sign + "_" + outSign + "_" + populationSize + "_" + times + ".txt"));
            ResBw.write("time\tpopulation\tacc_infected\tinfected\tincubation\tphenotype\tcure\tdead\n");
            System.out.println("day: 0 \t city population: " + (city.getPeopleSize() + city.getDeadPeopleNumber()) + 
                               "\t accumulate infected people: " + city.getAccumulateInfectedPeopleNumber() + 
                               "\t infected people: " + city.getInfectionPeopleNumber() + 
                               "\t incubation people: " + city.getIncubationPeopleNumber() +
                               "\t phenotype people: " + city.getPhenotypePeopleNumber() +
                               "\t cure people: " + city.getAntibodyPeopleNumber() +
                               "\t dead people: " + city.getDeadPeopleNumber());
            ResBw.write(0 + "\t" + (city.getPeopleSize() + city.getDeadPeopleNumber()) + "\t" + 
                        city.getAccumulateInfectedPeopleNumber() + "\t" + 
                        city.getInfectionPeopleNumber() + "\t" + city.getIncubationPeopleNumber() + "\t" +
                        city.getPhenotypePeopleNumber() + "\t" + city.getAntibodyPeopleNumber() + "\t" +
                        city.getDeadPeopleNumber() + "\n");

            int time_day = 0;
            while(true){
//                if(city.getPhenotypePeopleNumber() == 1){
//                    System.out.println("xxxxx");
//                }
                Functions.personalSpread(city, Functions.parameters.personalInteractionNum);
//                    System.out.println("sign:" + sign + "\t After personal infection: " + city.getInfectionPeopleNumber());
                Functions.familySpread(Functions.allFamily);
//                    System.out.println("sign:" + sign + "\tafter family infection: " + city.getInfectionPeopleNumber());
                Functions.transSpread(city);
//                    System.out.println("sign:" + sign + "\tafter transport infection: " + city.getInfectionPeopleNumber());
                Functions.hospitalSpread(city, Functions.hosiptal);
//                    System.out.println("sign:" + sign + "\tafter hospital infection: " + city.getInfectionPeopleNumber());
                Functions.goToHospital(city, Functions.hosiptal);
//                    System.out.println("sign:" + sign + "\tafter phenotype goto hospital infection: " + city.getInfectionPeopleNumber());
                city.updateAllPeople();
                city.removeDeadPeople();
//                    city.staR0();
                time_day++;
                System.out.println("sign:" + sign + "\tday:" + time_day + "\t city population: " + (city.getPeopleSize() + city.getDeadPeopleNumber()) + 
                                   "\t accumulate infected people: " + city.getAccumulateInfectedPeopleNumber() + 
                                   "\t infected people: " + city.getInfectionPeopleNumber() + 
                                   "\t incubation people: " + city.getIncubationPeopleNumber() +
                                   "\t phenotype people: " + city.getPhenotypePeopleNumber() +
                                   "\t cure people: " + city.getAntibodyPeopleNumber() +
                                   "\t dead people: " + city.getDeadPeopleNumber());
                ResBw.write(time_day + "\t" + (city.getPeopleSize() + city.getDeadPeopleNumber()) + "\t" + 
                        city.getAccumulateInfectedPeopleNumber() + "\t" + 
                        city.getInfectionPeopleNumber() + "\t" + city.getIncubationPeopleNumber() + "\t" +
                        city.getPhenotypePeopleNumber() + "\t" + city.getAntibodyPeopleNumber() + "\t" +
                        city.getDeadPeopleNumber() + "\n");

                if(city.getInfectionPeopleNumber() == 0){
                    break;
                }
            }
            ResBw.flush();
            ResBw.close();
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void oneCitySpreadingProcess_someAction(String sign, boolean isIncubationInfection, 
            int cityPopulationSize, int action_day, int reptime, 
            int maxIncubation, int minIncubation, int meanIncubation, 
            String outdir, String outSign, int initialCount){
        try {
            AllParameters.incubationInfection = isIncubationInfection;
            AllParameters.maxIncubation = maxIncubation;
            AllParameters.minIncubation = minIncubation;
            AllParameters.meanIncubation = meanIncubation;
            
            int oneActionDays = action_day;
            int times = reptime;
            System.out.println("initialize the city, family, hosiptal, and pubulic transport / regions");
            int populationSize = cityPopulationSize;
            InitCity theParameters = new InitCity(populationSize);
            Functions.parameters.resetParameters(theParameters);
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
            BufferedWriter ResBw = new BufferedWriter(new FileWriter(outdir + "/simulation_res/theResults_" + sign + "_" + outSign + "_day_" + oneActionDays + "_" + populationSize + "_" + times + ".txt"));
            ResBw.write("time\tpopulation\tacc_infected\tinfected\tincubation\tphenotype\tcure\tdead\n");
            System.out.println("sign:" + sign + "\tday: 0 \t city population: " + (city.getPeopleSize() + city.getDeadPeopleNumber()) +
                               "\t accumulate infected people: " + city.getAccumulateInfectedPeopleNumber() + 
                               "\t infected people: " + city.getInfectionPeopleNumber() + 
                               "\t incubation people: " + city.getIncubationPeopleNumber() +
                               "\t phenotype people: " + city.getPhenotypePeopleNumber() +
                               "\t cure people: " + city.getAntibodyPeopleNumber() +
                               "\t dead people: " + city.getDeadPeopleNumber());
            ResBw.write(0 + "\t" + (city.getPeopleSize() + city.getDeadPeopleNumber()) + "\t" + 
                        city.getAccumulateInfectedPeopleNumber() + "\t" + 
                        city.getInfectionPeopleNumber() + "\t" + city.getIncubationPeopleNumber() + "\t" +
                        city.getPhenotypePeopleNumber() + "\t" + city.getAntibodyPeopleNumber() + "\t" +
                        city.getDeadPeopleNumber() + "\n");

            int time_day = 0;
            boolean isSpring = false;
            boolean isPanic  =false;
            int     springLastTime = 0;
            int     panicLastTime  = 0;
            while(true){
                Functions.personalSpread(city, Functions.parameters.personalInteractionNum);
//                System.out.println("sign:" + sign + "\t After personal infection: " + city.getInfectionPeopleNumber());
                Functions.familySpread(Functions.allFamily);
//                System.out.println("sign:" + sign + "\tafter family infection: " + city.getInfectionPeopleNumber());
                Functions.transSpread(city);
//                System.out.println("sign:" + sign + "\tafter transport infection: " + city.getInfectionPeopleNumber());
                Functions.hospitalSpread(city, Functions.hosiptal);
//                System.out.println("sign:" + sign + "\tafter hospital infection: " + city.getInfectionPeopleNumber());
                Functions.specialAction(city);
                Functions.goToHospital(city, Functions.hosiptal);
//                System.out.println("sign:" + sign + "\tafter phenotype goto hospital infection: " + city.getInfectionPeopleNumber());
                city.updateAllPeople();
                city.removeDeadPeople();
                time_day++;
                if(isSpring){
                    springLastTime++;
                }
                if(isPanic){
                    panicLastTime++;
                }
                System.out.println("sign:" + sign + "\tday:" + time_day + "\t city population: " + (city.getPeopleSize() + city.getDeadPeopleNumber()) + 
                                   "\t accumulate infected people: " + city.getAccumulateInfectedPeopleNumber() + 
                                   "\t infected people: " + city.getInfectionPeopleNumber() + 
                                   "\t incubation people: " + city.getIncubationPeopleNumber() +
                                   "\t phenotype people: " + city.getPhenotypePeopleNumber() +
                                   "\t cure people: " + city.getAntibodyPeopleNumber() +
                                   "\t dead people: " + city.getDeadPeopleNumber());
                ResBw.write(time_day + "\t" + (city.getPeopleSize() + city.getDeadPeopleNumber()) + "\t" + 
                        city.getAccumulateInfectedPeopleNumber() + "\t" + 
                        city.getInfectionPeopleNumber() + "\t" + city.getIncubationPeopleNumber() + "\t" +
                        city.getPhenotypePeopleNumber() + "\t" + city.getAntibodyPeopleNumber() + "\t" +
                        city.getDeadPeopleNumber() + "\n");

                if(time_day == oneActionDays){
                    if(sign.equals("springFestival")){
                        Functions.springFestival();
                        isSpring = true;
                    }
                    if(sign.equals("closeBus")){
                        Functions.closeBus();
                    }
                    if(sign.equals("closeTaxi")){
                        Functions.closeTaxi();
                    }
                    if(sign.equals("closeSubway")){
                        Functions.closeSubWay();
                    }
                    if(sign.equals("communityHospital")){
                        Functions.communityHospital(city);
                    }
                    if(sign.equals("closeCity")){
                        Functions.closeCity(city);
                    }
                    if(sign.equals("goodHealth")){
                        Functions.goodHealth(city);
                    }
                    if(sign.equals("publicDisinfectants")){
                        Functions.publicDisinfectants();
                    }
                    if(sign.equals("homeIsolation")){
                        Functions.homeIsolation(city);
                    }
                    if(sign.equals("multiple")){
                        Functions.closeCity(city);
                        Functions.goodHealth(city);
                        Functions.publicDisinfectants();
//                        Functions.homeIsolation(city);
                    }
                    if(sign.equals("peoplePanic")){
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
            }
            ResBw.flush();
            ResBw.close();
        } catch (IOException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
