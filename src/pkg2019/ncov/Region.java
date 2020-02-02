/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author fei
 */
public class Region {
    private String regionName;
    private int capacity; // specific to hospital, and specific to infected person
    private Vector<People> bedPeople; // specific to hospital, the persons live or isolation in hospital
    private Vector<People> thePeople;
    private Vector<People> deadPeople; // specific to City
    private float remainSpreadPro; // the infected person may remain some virus in the region, and these regions has probability to spread
    private boolean isDisinfectant;
    
    public Region(){
        this.regionName = "";
        this.capacity = 0;
        this.thePeople = new Vector<>();
        this.bedPeople = new Vector<>();
        this.deadPeople = new Vector<>();
        this.remainSpreadPro = 0;
        this.isDisinfectant = false;
    }
    
    public void setRegionName(String name){
        this.regionName = name;
    }
    public String getRegionName(){
        return this.regionName;
    }
    
    public void setDisinfectant(){
        this.isDisinfectant = true;
    }
    public boolean getDisinfectant(){
        return this.isDisinfectant;
    }
    
    public void setRemainSpreadPro(float rip){
        this.remainSpreadPro = rip;
    }
    public float getRemainSpreadPro(){
        return this.remainSpreadPro;
    }
    
    public void setCapacity(int cap){
        this.capacity = cap;
    }
    public int getCapacity(){
        return this.capacity;
    }
    
    public void setPeople(Vector<People> thePeople){
        this.thePeople = thePeople;
    }
    public Vector<People> getPeople(){
        return this.thePeople;
    }
    
    public int getPeopleSize(){
        return this.thePeople.size();
    }
    
    public int getInfectionPeopleNumber(){ // specific to City
        int num = 0;
        for(People onePerson : this.thePeople){
            if(onePerson.isInfection()){
                num++;
            }
        }
        return num;
    }
    
    public int getIncubationPeopleNumber(){ // specific to City
        int num = 0;
        for(People onePerson : this.thePeople){
            if(onePerson.isInIncubation()){
                num++;
            }
        }
        return num;
    }
    
    public int getPhenotypePeopleNumber(){ // specific to city
        int num = 0;
        for(People onePerson : this.thePeople){
            if(onePerson.isPhenotype()){
                num++;
            }
        }
        return num;
    }
    
    public int getAntibodyPeopleNumber(){ // specific to City
        int num = 0;
        for(People onePerson : this.thePeople){
            if(onePerson.getAntibody()){
                num++;
            }
        }
        return num;
    }
    
    public int getDeadPeopleNumber(){  // specific to City
        return this.deadPeople.size();
    }
    
    public int getAccumulateInfectedPeopleNumber(){
        int num = 0;
        for(People onePerson : this.thePeople){
            if(onePerson.getInfectionTime() != 0){
                num++;
            }
        }
        num += this.getDeadPeopleNumber();
        return num;
    }
    
    public void setGoodHealthHabit(){ // specific to City
        for(People onePerson : this.thePeople){
            onePerson.setGoodHealth();
        }
    }
    
    public void updateAllPeople(){ // specific to city
        for(People onePerson : this.thePeople){
            onePerson.updateInfectionTime();
        }
    }
    
    public void homeIsolation(){ // specific to city
        for(People onePerson : this.thePeople){
            onePerson.setHomeIsolation();
        }
    }
    
    public void communityHospital(){ // specific to city
        for(People onePerson : this.thePeople){
            onePerson.setCommunityHospital();
        }
    }
    
    public void removeDeadPeople(){  // specific to City
        for (Iterator<People> iterator = this.thePeople.iterator(); iterator.hasNext();) {
            People onePerson = iterator.next();
            if(onePerson.getDead()){
                this.deadPeople.add(onePerson);
                iterator.remove();
            }
        }
    }
    
    public int getAviliableSizeOfHospital(){
        return this.capacity - this.bedPeople.size();
    }
    
    public void removePerson(People onePerson){
        this.thePeople.remove(onePerson);
    }
    
    public void addPerson(People onePerson){
        this.thePeople.add(onePerson);
    }
    public void addPersonGroup(Vector<People> pg){
        this.thePeople.addAll(pg);
    }
    
    public boolean addBedPeople(People oneperson){ // specific to hospital
        if(this.getAviliableSizeOfHospital() >= 1){
            this.bedPeople.add(oneperson);
            return true;
        }else{
            return false;
        }
    }
    
    public void removeBedPeople(){   // specific to hospital     
        for (Iterator<People> iterator = this.bedPeople.iterator(); iterator.hasNext();) {
            People oneBedPerson = iterator.next();
            if(oneBedPerson.getDead() || oneBedPerson.getAntibody()){
                iterator.remove();
            }
        }
    }
    
    public Vector<People> getInfectPeople(){
        Vector<People> infectedPeoples = new Vector<>();
        for(People onePerson : this.thePeople){
            if(onePerson.getInfectionTime() > 0){
                infectedPeoples.add(onePerson);
            }
        }
        return infectedPeoples;
    }
    
    public void peopleSpread(float spredPro, int touchNumber, int peopleNumber, People sourcePeople){
        Random oneRand = new Random(System.currentTimeMillis()); // who will be touched
        for(int j = 0; j < touchNumber; ++j){   // mi qie jie chu zhe
            int index = oneRand.nextInt(peopleNumber);
            Random spreRandom = new Random();
            float willSpread = spreRandom.nextFloat(); // generate (0-1) random float
            if(willSpread < spredPro){ // in the spread probability, e.g. spredPro = 0.7, willSpread = 0.6
                this.thePeople.elementAt(index).touch(sourcePeople);
            }
        }
    }
    public void spreading(){
        Vector<People> infectedPeople = this.getInfectPeople();
        int peopleNumber = this.thePeople.size();
        int maxTouchNumber = Functions.regionMaxTouchNumber.get(this.regionName);
        int touchNumber = (peopleNumber < maxTouchNumber ? peopleNumber : maxTouchNumber);
        float rsp = this.remainSpreadPro;
        if(!AllParameters.incubationInfection){
            rsp = 0f;
        }
        if(infectedPeople.size() == 0){
            peopleSpread(this.remainSpreadPro, touchNumber, peopleNumber,null);
            return;
        }
        for(People onePerson : infectedPeople){
            // the i-th infected people
            float spredPro = onePerson.getSpreadPro(); // spread probability
            peopleSpread(spredPro, touchNumber, peopleNumber,onePerson);
        }
    }
    
    public void spreading(People specialCase){
        int peopleNumber = this.thePeople.size();
        int maxTouchNumber = Functions.regionMaxTouchNumber.get(this.regionName);
        int touchNumber = (peopleNumber < maxTouchNumber ? peopleNumber : maxTouchNumber);
        People onePerson = specialCase;
        // the i-th infected people
        float spredPro = onePerson.getSpreadPro(); // spread probability
        peopleSpread(spredPro, touchNumber, peopleNumber,specialCase);
    }
    
    public void familySpreading(){ // specific to family
        Vector<People> infectedPeople = this.getInfectPeople();
        int peopleNumber = this.thePeople.size();
        int maxTouchNumber = Functions.regionMaxTouchNumber.get(this.regionName);
        int touchNumber = (peopleNumber < maxTouchNumber ? peopleNumber : maxTouchNumber);
        for(People onePerson : infectedPeople){
            // the i-th infected people
            float spredPro = onePerson.getFamilySpreadPro(); // spread probability
            peopleSpread(spredPro, touchNumber, peopleNumber,onePerson);
        }
    }
    public void springSpreading(){
        Vector<People> infectedPeople = this.getInfectPeople();
        boolean toInfect=false;
        for(People onePerson:this.thePeople){
            if(onePerson.getSpreadPro()>0){
                toInfect=true;
                break;
            }
        }
        if(toInfect){
            for(People onePerson:this.thePeople){
                if(!onePerson.getAntibody()){
                    onePerson.infection(null);
                }
            }
        }
    }
    public void clearMemory(){
        this.thePeople.clear();
    }
    
    public void staR0(){
        int infectPeopleNum=0;
        double totalInfectNum=0;
        for (Iterator<People> iterator = this.thePeople.iterator(); iterator.hasNext();) {
            People onePerson = iterator.next();
            if(onePerson.getAntibody()){
                infectPeopleNum++;
                totalInfectNum+=onePerson.getMyR();
            }
        }
        for (Iterator<People> iterator = this.deadPeople.iterator(); iterator.hasNext();) {
            People onePerson = iterator.next();
            infectPeopleNum++;
            totalInfectNum+=onePerson.getMyR();
        }
        if(infectPeopleNum==0){
            System.out.printf("r0:-1\n");
        }
        else{
            System.out.printf("r0:%.6f\n",totalInfectNum/infectPeopleNum);
        }
    }
}
