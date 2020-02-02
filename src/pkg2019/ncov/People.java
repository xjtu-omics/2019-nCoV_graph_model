/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

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
    private boolean isAntibody; 
    private boolean everHospital;
    private boolean isCommunityHospital;
    
    private int incubation; // how many days?
    private int infectionTime; // how many days of infection
    private int lastTime; // how many days from infection to die or cure
    
    private int familyID;
    private long myR;
    
    private float spreadPro;
    private float familySpreadPro;
    private float infectionPro;
    private float deadPro;
    
    public People(){
        Random oneRandom = new Random();
        this.isDead = false;
        myR=0;
        this.isIsolation = false;
        this.isHomeIsolation = false;
        this.isGoodHealthHabit = false;
        this.isAntibody = false;
        this.isCommunityHospital = false;
        
        this.incubation = (int)(AllParameters.meanIncubation + oneRandom.nextGaussian());
        if(this.incubation < AllParameters.minIncubation){
            this.incubation = AllParameters.minIncubation;
        }
        if(this.incubation > AllParameters.maxIncubation){
            this.incubation = AllParameters.maxIncubation;
        }
        this.infectionTime = 0;
        this.spreadPro = AllParameters.peopleInitSpreadPro; // may be need to adjust
        this.lastTime = this.incubation + AllParameters.treatLastTime;  // may be need to adjust
        this.infectionPro = AllParameters.peopleInitInfectionPro; // fix parameter, may be need to change to a random value
        this.deadPro  = AllParameters.peopleInitDeadPro;  // may be need to adjust
        this.familySpreadPro = this.spreadPro;
        
        this.familyID = -1;
        this.everHospital=false;
    }
    
    public void setDead(){
        this.isDead = true;
        this.isAntibody =false;
        this.isIsolation = false;
        this.isHomeIsolation = false;
        this.isCommunityHospital = false;
        this.spreadPro = 0f;
        this.infectionPro = 0f;
        this.infectionTime = -1; // was infected
        this.incubation = 0;
        this.deadPro = 0f;
        this.familySpreadPro = 0f;
    }
    public boolean getDead(){
        return this.isDead;
    }
    
    public void setAntibody(){
        this.isAntibody = true;
        this.isIsolation = false;
        this.isHomeIsolation = false;
        this.isCommunityHospital = false;
        this.spreadPro = 0f;
        this.infectionPro = 0f;
        this.infectionTime = -1; // was infected
        this.deadPro = 0f;
        this.incubation = 0;
        this.familySpreadPro = 0;
    }
    public boolean getAntibody(){
        return this.isAntibody;
    }
    
    private void setDeadOrAntiBody(){
        if(this.infectionTime >= this.lastTime){
            float deadFloat = new Random().nextFloat();
            if(deadFloat < this.deadPro){
                this.setDead();
            }else{
                this.setAntibody();
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
        this.deadPro = AllParameters.peopleIsolationDeadPro;
    }
    
    public void setHomeIsolation(){
        this.isHomeIsolation = true;
        this.spreadPro = 0f;
    }
    
    public void setCommunityHospital(){
//        this.setHomeIsolation();
        if(this.isPhenotype()){
            this.setIsolation();
            this.isCommunityHospital = true;
            this.deadPro = AllParameters.peopleIsolationDeadPro;
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
        float fsp = this.familySpreadPro;
        if(!AllParameters.incubationInfection){
            if(this.infectionTime<=this.incubation){
                fsp=0;
            }
        }
        return fsp;
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
        if(!AllParameters.incubationInfection){
            if(this.infectionTime<=this.incubation){
                this.spreadPro=0;
            }
        }
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
    
    public void updateInfectionTime(){
        if(this.infectionTime == 0 || this.infectionTime == -1){ // health people or was infected but cure people
            return;
        }
        this.infectionTime++;
        if(this.spreadPro == 1){
            this.spreadPro -= AllParameters.peopleSpreadProChangePerDay;
            if(this.spreadPro < 0f){
                this.spreadPro = 0f;
            }
        }else{
            this.spreadPro += AllParameters.peopleSpreadProChangePerDay;
            if(this.spreadPro > 1f){
                this.spreadPro = 1f;
            }
        }
//        if(!AllParameters.incubationInfection){
//            if(this.infectionTime<=this.incubation){
//                this.spreadPro=0;
//            }
//        }
        this.familySpreadPro = this.spreadPro;
        setDeadOrAntiBody();
    }
    public void infection(People sourcePeople){
        if(this.infectionTime == 0){
            this.infectionTime = 1;
            this.spreadPro = AllParameters.peopleSpreadProChangePerDay;
            if(!AllParameters.incubationInfection){
                if(this.infectionTime<=this.incubation){
                    this.spreadPro=0;
                }
            }
            this.familySpreadPro = this.spreadPro;
            if(sourcePeople!=null){
                sourcePeople.addMyR();
            }
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
        if(!AllParameters.incubationInfection){
            if(this.infectionTime<=this.incubation){
                sp=0;
            }
        }
        return sp;
    }
    
    public void setInfectionPro(float iPro){
        this.infectionPro = iPro;
    }
    public float getInfectionPro(){
        return this.infectionPro;
    }
    
    public boolean isInIncubation(){
        return (this.infectionTime > 0 && this.infectionTime <= this.incubation);
    }
    
    public boolean isPhenotype(){
        return (this.infectionTime > this.incubation);
    }
    
    public boolean isNeedHospital(){
        if(this.isIsolation){
            return false;
        }else if(this.infectionTime > this.incubation){
            if(this.everHospital){
                return true;
            }else{
                Random infectRandom = new Random(); // 0-1 random float
                if(infectRandom.nextFloat() > 0.5){
                    this.everHospital=true;
                    return true;
                }
                else{
                    return false;
                }
            }
        }else{
            return false;
        }
    }
    
    public void touch(People sourcePeople){
        if(this.getAntibody()){
            return;
        }
        Random infectRandom = new Random(); // 0-1 random float
        if(infectRandom.nextFloat() < infectionPro){
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
