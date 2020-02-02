/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fei
 */
public class Functions {
    public static HashMap<String, Integer> regionMaxTouchNumber = new HashMap<>();
    public static HashMap<String, Integer> transPassengerNumber = new HashMap<>();
    public static HashMap<String, Integer> transNumber = new HashMap<>();
    public static HashMap<String, Integer> transWorkNumPerDay = new HashMap<>();
    public static HashMap<String, Integer> transIdSt = new HashMap<>();
    public static Vector<Trans> transVec = new Vector<>();
    public static Vector<Region> allFamily;
    public static Region hosiptal;
    public static int totalTransNum;
    public static AllParameters parameters = new AllParameters();
    public static boolean isSpringFestival=false;
   
    public static void initilizeTouchNumber(){
        regionMaxTouchNumber.put("taxi", parameters.taxiTouchNumber);
        regionMaxTouchNumber.put("bus", parameters.busTouchNumber);
//        regionMaxTouchNumber.put("boat", parameters.boatTouchNumber);
        regionMaxTouchNumber.put("subway", parameters.subwayTouchNumber);
        regionMaxTouchNumber.put("public", parameters.publicTouchNumber);
        
        regionMaxTouchNumber.put("hospital", parameters.hospitalTouchNumber);
        regionMaxTouchNumber.put("family", parameters.familyTouchNumber);
        regionMaxTouchNumber.put("interFamily", parameters.interFamilyTouchNumber);
        regionMaxTouchNumber.put("other", parameters.otherTouchNumber);
    }
    
    public static void resetTouchNumber(String regionName, int number){
        regionMaxTouchNumber.replace(regionName, number);
    }
    
    public static void setToZero(){ // close all public region
        regionMaxTouchNumber.put("taxi", 0);
        regionMaxTouchNumber.put("bus", 0);
        regionMaxTouchNumber.put("subway", 0);
        regionMaxTouchNumber.put("public", 0);
//        regionMaxTouchNumber.put("boat", 0);
        regionMaxTouchNumber.put("other", 0);
    }
    
    public static Region initilizeACity(int populationSize){
        Region oneCity = new Region();
        oneCity.setCapacity(AllParameters.cityMaxPeople);
        Vector<People> thePopulation = new Vector<>();
        for(int i = 0; i < populationSize; ++i){
            thePopulation.add(new People());
        }
        oneCity.setPeople(thePopulation);
        return oneCity;
    }
    
    public static Vector<Region> initilizeFamily(Region oneCity, int familyNumber, int familyMemberNumber){
        Vector<Region> theFamily = new Vector<>();
        int currMemberNumber = 0;
        int currFamilyNumber = 0;
        Region family = new Region();
        family.setRegionName("family");
        family.setCapacity(familyMemberNumber);
        for(int k = 0; k < oneCity.getPeopleSize(); ++k){
            family.addPerson(oneCity.getPeople().elementAt(k));
            oneCity.getPeople().elementAt(k).setFamilyID(currFamilyNumber);
            currMemberNumber++;
            if(currMemberNumber == familyMemberNumber){
                theFamily.add(family);
                currFamilyNumber++;
                if(currFamilyNumber == familyNumber){
                    break;
                }
                family = new Region();
                family.setRegionName("family");
                family.setCapacity(familyMemberNumber);
                currMemberNumber = 0;
            }
        }
        if(family.getPeopleSize() != 0){
            theFamily.add(family);
        }
        return theFamily;
    }
    
    public static Region initilizeHospital(int hospitalCapacity){
        Region hospital = new Region();
        hospital.setRegionName("hospital");
        hospital.setCapacity(hospitalCapacity);
        return hospital;
    }
    
    public static Vector<Integer> infectFromAnimalOrInput(Region oneCity, int count){ // the first infection (1 person)
        Random peopleRandom = new Random();
        Vector<Integer> theInitalInfectedPerson = new Vector<>();
        for(int i = 1; i <= count; ++i){
            int personIndex = peopleRandom.nextInt(oneCity.getPeopleSize());
            oneCity.getPeople().elementAt(personIndex).infection(null);
            theInitalInfectedPerson.add(personIndex);
        }
        return theInitalInfectedPerson;
    }
    
    public static void initilizeTrans(){
        transPassengerNumber.put("taxi",   parameters.taxiPassangerNum);
        transPassengerNumber.put("bus",    parameters.busPassangerNum);
//        transPassengerNumber.put("boat",   parameters.boatPassangerNum);
        transPassengerNumber.put("subway", parameters.subwayPassangerNum);
        transPassengerNumber.put("public", parameters.publicPassangerNum);
        transPassengerNumber.put("other",  parameters.otherPassangerNum);
        
        transNumber.put("taxi",   parameters.taxiNumber);
        transNumber.put("bus",    parameters.busNumber);
//        transNumber.put("boat",   parameters.boatNumber);
        transNumber.put("subway", parameters.subwayNumber);
        transNumber.put("public", parameters.publicNumber);
        transNumber.put("other",  parameters.otherNumber);
        
        transWorkNumPerDay.put("taxi",   parameters.taxiWorkNumPerDay);
        transWorkNumPerDay.put("bus",    parameters.busWorkNumPerDay);
//        transWorkNumPerDay.put("boat",   parameters.boatWorkNumPerDay);
        transWorkNumPerDay.put("subway", parameters.subwayWorkNumPerDay);
        transWorkNumPerDay.put("public", parameters.publicWorkNumPerDay);
        transWorkNumPerDay.put("other",  parameters.otherWorkNumPerDay);
        
        int totalNum=0;
        for (Map.Entry<String, Integer> entry : transNumber.entrySet()) {
            String transName = entry.getKey();
            int transNum = entry.getValue();
            transIdSt.put(entry.getKey(),totalNum);
            totalNum += transNum;
        }
        
        for (Map.Entry<String, Integer> entry : transNumber.entrySet()) {
            String transName= entry.getKey();
            int transNum = entry.getValue();
            for(int i=0;i<transNum;i++){
                Trans oneTrans = new Trans();
                oneTrans.init(transPassengerNumber.get(transName), transName);
                transVec.add(oneTrans);
            }
        }
        
        totalTransNum = totalNum;
        
        System.out.println("total transNum = " + totalTransNum);
    }
    
    public static void familySpread(Vector<Region> allFamilies){
        for(Region oneFamily : allFamilies){
            oneFamily.familySpreading();
        }
    }
    
    public static void personalSpread(Region oneCity, int interactionNum){
        Random oneRand = new Random();
        for(int i = 0; i < interactionNum; ++i){
            int index1 = oneRand.nextInt(oneCity.getPeopleSize());
            int index2 = oneRand.nextInt(oneCity.getPeopleSize());
            if(index1 != index2){
                People people1 = oneCity.getPeople().elementAt(index1);
                People people2 = oneCity.getPeople().elementAt(index2);
                if(people1.getIsolation() || people2.getIsolation() || 
                   people1.getHomeIsolation() || people2.getHomeIsolation()){
                    continue;
                }
                if(people1.isInfection() || people2.isInfection()){
                    people1.spreading(people2);
                    people2.spreading(people1);
                }
            }else{
                continue;
            }
        }
    }
    
    public static void hospitalSpread(Region oneCity, Region hospital){
        Vector<People> pubPeople = new Vector<>();
        Random oneRand = new Random();
//        System.out.println("hospital people number: " + parameters.hospitalPeopleNumPerDay);
        for(int i = 0; i < parameters.hospitalPeopleNumPerDay; ++i){
            int index = oneRand.nextInt(oneCity.getPeopleSize());
            People onePerson = oneCity.getPeople().elementAt(index);
            if(!onePerson.getIsolation() && !onePerson.getHomeIsolation()){
                pubPeople.add(onePerson);
            }
        }
        hospital.setPeople(pubPeople);
        hospital.spreading();
//        hospital.clearMemory();
    }
    
    
    
    public static void transSpread(Region oneCity){
        // simulate some public region, like mall, restaurant, hotel, etc.
        // every time, the passangerNum may increase or decrease
        transPassengerNumber.replace("taxi",   parameters.taxiPassangerNum);
        transPassengerNumber.replace("bus",    parameters.busPassangerNum);
//        transPassengerNumber.replace("boat",   parameters.boatPassangerNum);
        transPassengerNumber.replace("subway", parameters.subwayPassangerNum);
        transPassengerNumber.replace("public", parameters.publicPassangerNum);
        transPassengerNumber.replace("other",  parameters.otherPassangerNum);
        
        transWorkNumPerDay.replace("taxi", parameters.taxiWorkNumPerDay);
        transWorkNumPerDay.replace("bus", parameters.busWorkNumPerDay);
        transWorkNumPerDay.replace("subway", parameters.subwayWorkNumPerDay);
        
        
        for(int i = 0; i < totalTransNum; i++){
            String name=transVec.elementAt(i).getMyRegion().getRegionName();
            for(int j = 0; j < transWorkNumPerDay.get(name); j++){
                transVec.elementAt(i).setpassengerNum(transPassengerNumber.get(name));
                transVec.elementAt(i).oneTrans(oneCity, null);
            }
        }
        
        for(int i = 0; i < totalTransNum; ++i){
            transVec.elementAt(i).clearMemory();
        }
    }
    
    public static int goToHospital(Region oneCity, Region hospital){
        int numGoToHospital = 0;
        hospital.removeBedPeople(); // remove the dead or antibody person
        for(int i = 0; i < oneCity.getPeopleSize(); ++i){
            // if one person infection_time > Incubation_time
            People onePerson = oneCity.getPeople().elementAt(i);
            if(onePerson.getCommnityHospital()){
                continue;
            }
            if(onePerson.isNeedHospital()){
                // chose transport, 0: bus, 1, subway, 2, boat, 3, taxi
                String transName;
                numGoToHospital++;
                while(true){
                    int wayId = new Random().nextInt(4);
                    if(wayId == 0){
                        transName = "bus";
                    }else if(wayId == 1){
                        transName = "subway";
//                    }else if(wayId == 2){
//                        transName = "boat";
                    }else if(wayId == 2){
                        transName = "taxi";
                    }else{
                        transName = "other";
                    }
                    if(transNumber.get(transName) > 0){
                        break;
                    }
                }
                int offSetId = new Random().nextInt(transNumber.get(transName));
                int id = transIdSt.get(transName) + offSetId;
                transVec.elementAt(id).oneTrans(oneCity, onePerson);
                hospital.spreading(onePerson);
                if(hospital.getAviliableSizeOfHospital() > 1){
                    hospital.addBedPeople(onePerson);
                    onePerson.setIsolation();
//                    hospital.clearMemory();
                }else{
                    onePerson.setHomeIsolation();
                }
            }
        }
        return numGoToHospital;
    }
    
    public static void closeTaxi(){
        resetTouchNumber("taxi", 0);
    }
    
    public static void closeBus(){
        resetTouchNumber("bus", 0);
    }
    
    public static void closeSubWay(){
        resetTouchNumber("subway", 0);
    }
    
    public static void closePublic(){
        resetTouchNumber("public", 0);
    }
    
    public static void communityHospital(Region city){
        city.communityHospital();
    }
    
    public static void publicDisinfectants(){
        // clear the remain spread probability
        Trans.maxRemainSpreadPro = 0;
        for(Trans onePub : transVec){
            onePub.getMyRegion().setRemainSpreadPro(0);
        }
    }
    
    public static void closeCity(Region city){
//        closeBoat();
        closeBus();
        closeTaxi();
        closeSubWay();
        closePublic();
        city.homeIsolation();
        parameters.personalInteractionNum = 0;
    }
    
    public static void goodHealth(Region city){
        city.setGoodHealthHabit();
    }
    
    public static void homeIsolation(Region city){
        parameters.personalInteractionNum = 0;
        city.homeIsolation();
    }
    
    public static void peoplePanic(){
        // if people are panic, most of them will go to hospital
        parameters.hospitalPeopleNumPerDay = parameters.hospitalPeopleNumPerDay * AllParameters.panicFold;
        parameters.busPassangerNum   = parameters.busPassangerNum * AllParameters.panicFold;
        parameters.subwayPassangerNum = parameters.subwayPassangerNum * AllParameters.panicFold;
        parameters.taxiWorkNumPerDay = parameters.taxiWorkNumPerDay * AllParameters.panicFold;
    }
    
    public static void peopleCalmdown(){ 
        // this function must excute after people panic.
        parameters.hospitalPeopleNumPerDay /= AllParameters.panicFold;
        parameters.busPassangerNum /= AllParameters.panicFold;
        parameters.subwayPassangerNum /= AllParameters.panicFold;
        parameters.taxiWorkNumPerDay /= AllParameters.panicFold;
    }
    
    public static void springFestival(){
        isSpringFestival=true;
//        parameters.personalInteractionNum = parameters.personalInteractionNum * 5;
//        parameters.boatPassangerNum   = (int)(parameters.boatPassangerNum * 1.5);
//        parameters.busPassangerNum    = (int)(parameters.busPassangerNum * 1.5);
//        parameters.taxiPassangerNum   = (int)(parameters.taxiPassangerNum * 1.5);
//        parameters.subwayPassangerNum = (int)(parameters.subwayPassangerNum * 1.5);
//        parameters.publicPassangerNum = (int)(parameters.publicPassangerNum * 1.5);
//        parameters.otherPassangerNum  = (int)(parameters.otherPassangerNum * 1.5);
    }
    public static void endSpringFestival(){
        if(isSpringFestival)
            isSpringFestival = false;
    }
    
    public static void AmericaLikeRegion(){
//        parameters.personalInteractionNum = parameters.personalInteractionNum / 2;
//        
//        parameters.busNumber = parameters.busNumber / 3;
//        parameters.busPassangerNum = parameters.busPassangerNum / 3;
//        parameters.busWorkNumPerDay = parameters.busWorkNumPerDay / 3;
//        
//        parameters.taxiNumber = parameters.taxiNumber / 3;
//        parameters.taxiWorkNumPerDay = parameters.taxiWorkNumPerDay / 3;
//        
//        parameters.subwayNumber = parameters.subwayNumber / 3;
//        parameters.subwayPassangerNum = parameters.subwayPassangerNum / 3;
//        parameters.subwayWorkNumPerDay = parameters.subwayWorkNumPerDay / 3;
//        
//        parameters.publicPassangerNum  = parameters.publicPassangerNum / 3;
        closeBus();
        closeTaxi();
        closeSubWay();
    }
    
    public static void PoorLikeRegion(){        
//        parameters.busNumber = parameters.busNumber / 5;
//        parameters.busPassangerNum = parameters.busPassangerNum / 5;
//        parameters.busWorkNumPerDay = parameters.busWorkNumPerDay / 5;
//        
//        parameters.taxiNumber = parameters.taxiNumber / 5;
//        parameters.taxiWorkNumPerDay = parameters.taxiWorkNumPerDay / 5;
//        
//        parameters.subwayNumber = 0;
//        parameters.subwayPassangerNum = 0;
//        parameters.subwayWorkNumPerDay = 0;
//        
//        parameters.publicPassangerNum  = parameters.publicPassangerNum / 5;
        closeBus();
        closeTaxi();
        closeSubWay();
        closePublic();
        
        parameters.hospitalCapacityForVirus /= 2;
    }
    
    public static int getTotalInfectionNumber(Region oneCity){
        return oneCity.getInfectionPeopleNumber();
    }
    
    public static int getTotalDeadNumber(Region oneCity){
        return oneCity.getDeadPeopleNumber();
    }
    
    public static int getTotalAntibodyNumber(Region oneCity){
        return oneCity.getAntibodyPeopleNumber();
    }
   
    public static void specialAction(Region oneCity){
        if(isSpringFestival){
            for(int i=0;i<parameters.familyInteractionNumber;i++){
                Region tmpRegion=new Region();
                tmpRegion.setRegionName("interFamily");
                for(int j=0;j<3;j++){
                    Random peopleRandom = new Random();
                    int familyIndex = peopleRandom.nextInt(allFamily.size());
                    tmpRegion.addPersonGroup(allFamily.elementAt(familyIndex).getPeople());
                }
                //System.out.printf("springFestival!\n");
                tmpRegion.springSpreading();
                //System.out.printf("springFestival2!\n");
                tmpRegion.clearMemory();
            }
        }
    }
}
