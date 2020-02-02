/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

/**
 *
 * @author fei
 */
public class AllParameters {
    public static int cityMaxPeople     = Integer.MAX_VALUE;
    public int populationSize           = 100000;
    public int familyMemberNumber       = 3;
    public int familyNumber             = (int)(populationSize * 0.8 / familyMemberNumber);
    public int familyInteractionNumber  = (int)(familyNumber*0.2); 
    public int personalInteractionNum   = 5000;
    public int hospitalPeopleNumPerDay  = 5000;
    public int hospitalCapacityForVirus = 3000;
    
    public int busPassangerNum    = 40;
    public int taxiPassangerNum   = 3;
    public int subwayPassangerNum = 1000;
//    public int boatPassangerNum   = 100;
    public int publicPassangerNum = (int)(populationSize * 0.01);
    public int otherPassangerNum  = 100;
    
    public int taxiNumber    = 500;
    public int busNumber     = 100;
//    public int boatNumber    = 50;
    public int subwayNumber  = 4;
    public int publicNumber  = 1;
    public int otherNumber   = 1;
    
    public int taxiWorkNumPerDay    = 20; 
    public int busWorkNumPerDay     = 20;
//    public int boatWorkNumPerDay    = 10;
    public int subwayWorkNumPerDay  = 10;
    public int publicWorkNumPerDay  = 1;
    public int otherWorkNumPerDay   = 1;
    
    public int taxiTouchNumber     = 2; // how many people may touched with another in this region
    public int busTouchNumber      = 5;
//    public int boatTouchNumber     = 3; // 
    public int subwayTouchNumber   = 5;
    public int hospitalTouchNumber = 20;
    public int familyTouchNumber   = 3;
    public int interFamilyTouchNumber= 100;
    public int publicTouchNumber   = 10;
    public int otherTouchNumber    = 1;
    
    public static float interCityTransportProbability = 0.001f;
    
    public static float peopleInitSpreadPro         = 0f;
    public static float peopleSpreadProChangePerDay = 0.04f; // may be need to adjust
    public static float peopleInitInfectionPro      = 0.2f;  // may be need to adjust
    public static float peopleInitDeadPro           = 0.05f;
    public static float peopleIsolationDeadPro      = 0.025f;
    public static float goodHealthReduceSpreProFold     = 0.1f;  // may be need to adjust
    public static float homeIsolationSpreadPro      = 0.001f;
    
    public static int treatLastTime  = 14;
    public static int minIncubation  = 1;
    public static int maxIncubation  = 14;
    public static int meanIncubation = 7;
    
    public static int panicFold      = 2;
    
    public static boolean incubationInfection = false;
    
    public void resetParameters(InitCity oneInitCiyt){
        this.populationSize           = oneInitCiyt.population;
        this.hospitalCapacityForVirus = oneInitCiyt.hospitalBedNum;
        this.hospitalPeopleNumPerDay  = oneInitCiyt.oneDayPatients;
        this.familyNumber             = (int)(populationSize * 0.8 / familyMemberNumber);
        this.personalInteractionNum   = (int)(populationSize * 0.1);
        this.familyInteractionNumber  = (int)(familyNumber*0.2);

        this.taxiNumber = oneInitCiyt.taxiNum;
        this.busNumber  = oneInitCiyt.busNum;
        this.subwayNumber = oneInitCiyt.subwayNum;
//        this.boatNumber   = oneInitCiyt.shipNum;
        
        this.taxiWorkNumPerDay = (int)(oneInitCiyt.taxiPeopleNum / (taxiNumber * taxiPassangerNum));
        this.busWorkNumPerDay  = (int)(oneInitCiyt.busPeopleNum / (busNumber * busPassangerNum));
        this.subwayWorkNumPerDay = (int)(oneInitCiyt.subwayPeopleNum / (subwayNumber * subwayPassangerNum));
//        this.boatWorkNumPerDay = (int)(oneInitCiyt.shipPeopleNum / (boatNumber * boatPassangerNum));
    }
}
