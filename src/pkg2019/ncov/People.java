/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author fei
 */
public class People {
    private boolean isDead;
    private boolean isIsolation;
    private boolean isHomeIsolation;
    private boolean isGoodHealthHabit;
//    private boolean wasInfected;
    private boolean isCure; 
//    private boolean everHospital;
    private boolean isCommunityHospital;
    private boolean willCure;  // will cure = true, this person will cure. otherwise this person will dead
    
    private int infectionType; // 1: infection from fever, 2, infection from non-fever, 3, infection from environment, 0: healthy one
    
//    private boolean isIncubationInfection;
    private boolean isFever;   // patient fever or not
    private boolean isSevere;  // patient severe or not
    private boolean isDNADetectable; // patient can be detected by DNA/RNA or not
    private float maxVirus;  // the maximize virus content
    
    private int incubation; // how many days?
    private int infectionTime; // how many days of infection
    private int lastTime; // how many days from infection to die or cure
//    private int treatmentTime;
    
    private int familyID;
    private long myR;
    
    private float spreadPro;
    private float familySpreadPro;
    private float infectionPro;
//    private float deadPro;
    private HashMap<Integer, Float> incubationSpreadPro;
    
//    public static boolean isFirst = true;
    
    public People(){
        Random oneRandom = new Random();
        this.isDead = false;
        myR=0;
        this.isIsolation = false;
        this.isHomeIsolation = false;
        this.isGoodHealthHabit = false;
        this.isCure = false;
        this.isCommunityHospital = false;
        this.willCure = false;
//        this.wasInfected = false;
        this.isFever = false;
        this.isSevere = false;
        this.isDNADetectable = false;
        
        this.maxVirus = 0;
        
        this.infectionType = 0;
        
        this.incubation = (int)(AllParameters.meanIncubation + oneRandom.nextGaussian());
        if(this.incubation < AllParameters.minIncubation){
            this.incubation = AllParameters.minIncubation;
        }
        if(this.incubation > AllParameters.maxIncubation){
            this.incubation = AllParameters.maxIncubation;
        }
        
        this.infectionTime = 0;
        this.incubationSpreadPro = null;
        this.spreadPro = 0f; // may be need to adjust
        this.lastTime = this.incubation + AllParameters.treatLastTime;  // may be need to adjust
        this.infectionPro = AllParameters.peopleInitInfectionPro; // fix parameter, may be need to change to a random value
//        this.deadPro  = AllParameters.peopleInitDeadPro;  // may be need to adjust
        this.familySpreadPro = this.spreadPro;
        
//        this.treatmentTime = 0;
        
        this.familyID = -1;
//        this.everHospital=false;
    }
    
    private HashMap<Integer, Float> incubationSpreadProCalculation(){
        HashMap<Integer, Float> incubationSpreadPro = new HashMap<>();
        if(this.incubation == 0){
            // incubation = 0
            incubationSpreadPro.put(1, this.maxVirus);
            return incubationSpreadPro;
        }
        float maxSpreadPro = maxVirus;
        incubationSpreadPro.put(this.incubation + 1, this.maxVirus);
        float currSpreadPro = maxSpreadPro;
        for(int i = this.incubation; i > 0; --i){
            currSpreadPro = currSpreadPro / 2;
            incubationSpreadPro.put(i, currSpreadPro);
        }
        return incubationSpreadPro;
    }
    
    public void setDead(){
        this.isDead = true;
        this.isCure =false;
        this.isIsolation = false;
        this.isHomeIsolation = false;
        this.isCommunityHospital = false;
        this.isFever = false;
        this.isSevere = false;
        this.isDNADetectable = false;
        
        this.maxVirus = 0f;
        this.spreadPro = 0f;
        this.infectionPro = 0f;
        this.infectionTime = 0; // was infected
        this.incubation = 0;
//        this.deadPro = 0f;
        this.familySpreadPro = 0f;
//        this.treatmentTime = 0;
    }
    public boolean getDead(){
        return this.isDead;
    }
    
    public void setCure(){
        this.isCure = true;
        this.isIsolation = false;
        this.isHomeIsolation = false;
        this.isCommunityHospital = false;
//        this.wasInfected = true;
        this.willCure    = false;
        this.isFever = false;
        this.isSevere = false;
        this.isDNADetectable = false;
        this.maxVirus = 0f;
        this.spreadPro = 0f;
        this.infectionPro = AllParameters.peopleInitInfectionPro * AllParameters.cureInfectionProFold;
        if(AllParameters.cureStillBeInfected){
            this.infectionTime = 0; // was infected
        }else{
            this.infectionTime = -1; // cannot be infected any more
        }
        this.familySpreadPro = 0;
//        this.treatmentTime = 0;
    }
    public boolean getCure(){
        return this.isCure;
    }
//    public boolean getWasInfected(){
//        return this.wasInfected;
//    }
    
    public boolean getFever(){
        return this.isFever;
    }
    
    public boolean getSevere(){
        return this.isSevere;
    }
    
    public boolean getDNADetectable(){
        return this.isDNADetectable;
    }
    
    private void setDeadOrCure(){
        if(this.infectionTime >= this.lastTime){
            if(!this.willCure){
                this.setDead();
            }else{
                this.setCure();
            }
        }
    }
    
    public void addMyR(){
        myR++;
    }
    
    public void setIsolation(){
        this.isIsolation = true;
        this.spreadPro = 0f;
        this.infectionPro = 0f;
        this.familySpreadPro = 0f;
//        this.deadPro = AllParameters.peopleHospitalDeadPro;
//        this.treatmentTime = 1;
        
        if(!this.willCure){
            // will dead, need to reset the cure or dead since the dead probability changed
            float thePro = AllParameters.peopleHospitalDeadPro / AllParameters.peopleInitDeadPro;
            Random theRand = new Random();
            float oneRandNumber = theRand.nextFloat();
            if(oneRandNumber <= thePro){
                this.willCure = false;
            }else{
                this.willCure = true;
            }
        }
    }
    
    public void setHomeIsolation(){
        this.isHomeIsolation = true;
        this.spreadPro = 0f;
    }
    
    public void setCommunityHospital(){
//        this.setHomeIsolation();
        if(this.getFever()){
            this.isCommunityHospital = true;
            this.setIsolation();
        }
    }
    
    public boolean getCommnityHospital(){
        return this.isCommunityHospital;
    }
    
    public boolean getIsolation(){
        return this.isIsolation;
    }
    
    public boolean getHomeIsolation(){
        return this.isHomeIsolation;
    }
    
    public void setFamilySpreadPro(float fsp){
        this.familySpreadPro = fsp;
    }
    
    public float getFamilySpreadPro(){
//        float fsp = this.familySpreadPro;
//        if(!this.isIncubationInfection){
//            if(this.infectionTime<=this.incubation){
//                fsp=0;
//            }
//        }
        return this.familySpreadPro;
    }
    
    public void setFamilyID(int id){
        this.familyID = id;
    }
    
    public int getFamilyID(){
        return this.familyID;
    }
    public long getMyR(){
        return this.myR;
    }
    public void setGoodHealth(){
        // good healph can reduce the infection pro and spread pro significantly
        // AllParameters.goodHealthReduceSpreProFold is the reduce fold
        this.isGoodHealthHabit = true;
        this.spreadPro = this.spreadPro * AllParameters.goodHealthReduceSpreProFold;
        if(this.spreadPro <= 0.0f){
            this.spreadPro = 0.01f;
        }
//        if(!this.isIncubationInfection){
//            if(this.infectionTime<=this.incubation){
//                this.spreadPro=0;
//            }
//        }
        this.familySpreadPro = this.spreadPro;
        this.infectionPro = this.infectionPro * AllParameters.goodHealthReduceSpreProFold;
        if(this.infectionPro <= 0.0f){
            this.infectionPro = 0.01f;
        }
    }
    public boolean getGoodHealth(){
        return this.isGoodHealthHabit;
    }
    
    public void setIncubation(int time){
        this.incubation = time;
    }
    public int getIncubation(){
        return this.incubation;
    }
    
//    public boolean getIncubationInfected(){
//        return this.isIncubationInfection;
//    }
    
    private float getSpreadDecreasePerDay(){
        return this.maxVirus / AllParameters.treatLastTime;
    }
    
    public void updateInfectionTime(){
        if(this.infectionTime == 0 || this.infectionTime == -1){ // health people or was infected but cure people
            return;
        }
        this.infectionTime++;
//        if(this.isIsolation){
//            this.treatmentTime++;
//        }
        
         // spredPro has been calculated in the hashmap
        if(!this.isIsolation){
            if(this.infectionTime <= this.incubation + 1){
                this.spreadPro = this.incubationSpreadPro.get(this.infectionTime);
            }else{
                if(this.willCure){ 
                    // if patient will cure, the spread probability will decrease
                    this.spreadPro -= this.getSpreadDecreasePerDay();
                }
                if(this.spreadPro < 0){
                    this.spreadPro = 0;
                }
            }
        }

        if(this.spreadPro > 1){
            System.err.println("there are some erros");
        }
        if(this.spreadPro < 0){
            System.err.println("there are some erros");
        }
        if(!this.isHomeIsolation){
            this.familySpreadPro = this.spreadPro;
        }
        this.setDeadOrCure();
        
        // once over incubation, should set fever and severe
//        this.setFeverAndSevere();
    }
    
    public int getInfectionType(){
        return this.infectionType;
    }
    
    public void infection(People sourcePeople){
        if(this.infectionTime == 0){
            this.infectionTime = 1;
            
            this.setPatientType();
            this.incubationSpreadPro = this.incubationSpreadProCalculation();
            
            this.spreadPro = this.incubationSpreadPro.get(this.infectionTime);
            this.familySpreadPro = this.spreadPro;
//            this.setWillCure();
            this.isCure = false; // reset the cure state
            if(sourcePeople!=null){
                sourcePeople.addMyR();
            }
            
            if(sourcePeople == null){
                this.infectionType = 3;
            }else{
                if(sourcePeople.getFever()){
                    this.infectionType = 1;
                }else{
                    this.infectionType = 2;
                }
            }
            // for person with 0 incubation
//            this.setFeverAndSevere();
        }
    }
    public int getInfectionTime(){
        return this.infectionTime;
    }
    public boolean isInfection(){
        return this.infectionTime > 0;
    }
    
    public void setSpreadPro(float sPro){
        this.spreadPro = sPro;
    }
    public float getSpreadPro(){
        float sp = this.spreadPro;
//        if(!this.isIncubationInfection){
//            if(this.infectionTime<=this.incubation){
//                sp=0;
//            }
//        }
        return sp;
    }
    
    public void setInfectionPro(float iPro){
        this.infectionPro = iPro;
    }
    public float getInfectionPro(){
//        return this.infectionPro;
        
        float ip = this.infectionPro;
//        if(!AllParameters.cureStillBeInfected){
//            if(this.wasInfected){
//                ip = 0;
//            }
//        }
        return ip;
    }
    
    public boolean isInIncubation(){
        return (this.infectionTime > 0 && this.infectionTime <= this.incubation);
    }
    
    private void setPatientType(){
        Random virusRandom = new Random();
        float oneMaxVirus = virusRandom.nextFloat();
        this.maxVirus = oneMaxVirus;

        // patient type grouped into: server, fever, dna/rna detectable, others
        if(this.maxVirus >= (1 - AllParameters.severePropation)){
            this.isSevere = true;
            this.isFever = true;
            this.isDNADetectable = true;
            // if one patient is server, he/she will have probability to dead
            float severDeadPro = AllParameters.peopleInitDeadPro / AllParameters.severePropation;
            Random cureRandom = new Random();
            float oneCure = cureRandom.nextFloat();
            if(oneCure >= severDeadPro){ // will cure
                this.willCure = true;
            }
        }else if(this.maxVirus >= (1 - AllParameters.feverPropation)){
            this.isFever = true;
            this.isDNADetectable = true;
            this.willCure = true;
        }else if(this.maxVirus >= (1 - AllParameters.rnaDetectionPropation)){
            this.isDNADetectable = true;
            this.willCure = true;
        }else{
            this.willCure = true;
        }
    }
    
    public boolean isNeedHospital(){
        if(this.isIsolation){
            return false;
        }else{
            if(this.infectionTime > this.incubation && this.getFever()){
                if(Functions.hosiptal.getAviliableSizeOfHospital() > 0){
                    return true;
                }else{
                    this.setHomeIsolation();
                    return false;
                }
            }else{
                return false;
            }
        }
    }
    
    public void touch(People sourcePeople){
        Random infectRandom = new Random(); // 0-1 random float
        if(infectRandom.nextFloat() < this.getInfectionPro()){
            this.infection(sourcePeople);
        }
    }
    
    public void spreading(People otherPeople){
        if(this.infectionTime == 0){
            return;
        }
        float spredPro = this.getSpreadPro(); // spread probability
        Random spreRandom = new Random();
        float willSpread = spreRandom.nextFloat(); // generate (0-1) random float
        if(willSpread < spredPro){ // in the spread probability, e.g. spredPro = 0.7, willSpread = 0.6
            otherPeople.touch(this);
        }
    }
}
