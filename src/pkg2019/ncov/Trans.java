/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;
/**
 *
 * @author ttbond
 */
import java.util.*;
public class Trans {
    private Region myRegion;
    private int passengerNum;
    public static float maxRemainSpreadPro=0.1f;
    public Trans(){
        myRegion = new Region();
    }
    
    public void init(int psn, String name){
        passengerNum=psn;
        myRegion.setRegionName(name);
    }
    
    public void setpassengerNum(int psn){
        this.passengerNum = psn;
    }
    
    public void oneTrans(Region oneCity, People specialCase){
        myRegion.setCapacity(AllParameters.cityMaxPeople);
        Vector<People> passengers = new Vector<>();
        Random oneRand = new Random();
        for(int i = 0; i < passengerNum; ++i){
            int index = oneRand.nextInt(oneCity.getPeopleSize());
            People onePerson = oneCity.getPeople().elementAt(index);
            if(!onePerson.getIsolation() && !onePerson.getHomeIsolation()){
                passengers.add(onePerson);
            }
        }
        //ttbond:set thePeople of region
        myRegion.setPeople(passengers);
        if(specialCase != null){
            myRegion.addPerson(specialCase);
            myRegion.spreading(specialCase);
        }else{
            myRegion.spreading();
        }
        if(!AllParameters.incubationInfection){
            myRegion.setRemainSpreadPro(0f);
        }else{
            if(!myRegion.getDisinfectant()){
                int haveInfect=0;
                for(int i=0;i<myRegion.getPeople().size();i++){
                    if(myRegion.getPeople().elementAt(i).getInfectionTime()>0){
                        myRegion.setRemainSpreadPro(maxRemainSpreadPro);
                        haveInfect=1;
                        break;
                    }
                }
                if(haveInfect==0){
                    myRegion.setRemainSpreadPro(myRegion.getRemainSpreadPro() * 0.5f);
                }
            }
        }
    }
    
    public Region getMyRegion(){
        return myRegion;
    }
    
    public void clearMemory(){
        this.myRegion.clearMemory();
    }
    /*    
//ttbond:add param busNum
    public static void oneBusTransport(Region oneCity, int busPassangerNum, People specialCase){
        Region bus = new Region();
        bus.setRegionName("bus");
        bus.setCapacity(cityMaxPeople);
        Vector<People> theBusPassengers = new Vector<>();
        Random oneRand = new Random(System.currentTimeMillis());
        for(int i = 0; i < busPassangerNum; ++i){
            int index = oneRand.nextInt(oneCity.getPeopleSize());
            People onePerson = oneCity.getPeople().elementAt(index);
            if(!onePerson.getDead()){
                theBusPassengers.add(onePerson);
            }
        }
        //ttbond:set thePeople of region
        bus.setPeople(theBusPassengers);
        if(specialCase != null){
            bus.addPerson(specialCase);
            bus.spreading(specialCase);
        }else{
            bus.spreading();
        }
    }
    public static void busTransport(Region oneCity, int busPassangerNum,int busNum){
        //ttbond:add loop for multibus
        for(int id=0;id<busNum;id++){
            oneBusTransport(oneCity, busPassangerNum, null);
        }
    }
    
    public static void oneTaxiTransport(Region oneCity, int taxiPassangerNum, People specialCase){
        Region taxi = new Region();
        taxi.setRegionName("taxi");
        taxi.setCapacity(cityMaxPeople);
        Vector<People> theTaxiPassengers = new Vector<>();
        Random oneRand = new Random(System.currentTimeMillis());
        for(int i = 0; i < taxiPassangerNum; ++i){
            int index = oneRand.nextInt(oneCity.getPeopleSize());
            People onePerson = oneCity.getPeople().elementAt(index);
            if(!onePerson.getDead()){
                theTaxiPassengers.add(onePerson);
            }
        }
        taxi.setPeople(theTaxiPassengers);
        if(specialCase != null){
            taxi.addPerson(specialCase);
            taxi.spreading(specialCase);
        }else{
            taxi.spreading();
        }
    }
    //ttbond:init this function
    public static void taxiTransport(Region oneCity,int taxiPassangerNum,int taxiNum){
        for(int id=0;id<taxiNum;id++){
            oneTaxiTransport(oneCity, taxiPassangerNum, null);
        }
    }
    
    public static void oneSubWayTransport(Region oneCity, int subPassangerNum, People specialCase){
        Region sub = new Region();
        sub.setRegionName("subway");
        sub.setCapacity(cityMaxPeople);
        Vector<People> theSubwayPassengers = new Vector<>();
        Random oneRand = new Random(System.currentTimeMillis());
        for(int i = 0; i < subPassangerNum; ++i){
            int index = oneRand.nextInt(oneCity.getPeopleSize());
            People onePerson = oneCity.getPeople().elementAt(index);
            if(!onePerson.getDead()){
                theSubwayPassengers.add(onePerson);
            }
        }
        sub.setPeople(theSubwayPassengers);
        if(specialCase != null){
            sub.addPerson(specialCase);
            sub.spreading(specialCase);
        }else{
            sub.spreading();
        }
    }
    
    public static void subWayTransport(Region oneCity,int subPassangerNum,int subNum){
        for(int id=0;id<subNum;id++){
            oneSubWayTransport(oneCity, subPassangerNum, null);
        }
    }
    
    public static void oneBoatTransport(Region oneCity, int boatPassangerNum, People specialCase){
        Region boat = new Region();
        boat.setRegionName("boat");
        boat.setCapacity(cityMaxPeople);
        Vector<People> theBoatPassengers = new Vector<>();
        Random oneRand = new Random(System.currentTimeMillis());
        for(int i = 0; i < boatPassangerNum; ++i){
            int index = oneRand.nextInt(oneCity.getPeopleSize());
            People onePerson = oneCity.getPeople().elementAt(index);
            if(!onePerson.getDead()){
                theBoatPassengers.add(onePerson);
            }
        }
        boat.setPeople(theBoatPassengers);
        if(specialCase != null){
            boat.addPerson(specialCase);
            boat.spreading(specialCase);
        }else{
            boat.spreading();
        }
    }
    
    public static void boatTransport(Region oneCity,int boatPassangerNum,int boatNum){
        for(int id=0;id<boatNum;id++){
            oneBoatTransport(oneCity, boatPassangerNum, null);
        }
    }
    
    public static void onePubRegion(Region oneCity, int pubPeopleNum, People specialCase){
        Region pub = new Region();
        pub.setRegionName("publicRegion");
        pub.setCapacity(cityMaxPeople);
        Vector<People> pubPeople = new Vector<>();
        Random oneRand = new Random(System.currentTimeMillis());
        for(int i = 0; i < pubPeopleNum; ++i){
            int index = oneRand.nextInt(oneCity.getPeopleSize());
            People onePerson = oneCity.getPeople().elementAt(index);
            if(!onePerson.getDead()){
                pubPeople.add(onePerson);
            }
        }
        pub.setPeople(pubPeople);
        if(specialCase != null){
            pub.addPerson(specialCase);
            pub.spreading(specialCase);
        }else{
            pub.spreading();
        }
    }
    public static void publicRegion(Region oneCity,int pubPeopleNum,int pubNum){
        // simulate some public region, like mall, restaurant, hotel, etc.
        for(int id=0;id<pubNum;id++){
            onePubRegion(oneCity, pubPeopleNum, null);
        }
    }*/
}
