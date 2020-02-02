/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg2019.ncov;

/**
 *
 * @author pengjia
 */
public class InitCity {
    protected int population; // 城市的人口 wuhan 11,000,000
    protected int hospitalNum; // 城市医院数量wuhan 2018 398
    protected int hospitalBedNum;  // 城市病床数量wuhan 2018 398*1200
    protected int oneDayPatients; // 每天接诊的病人数
    protected int oneDayPatientsFever; //城市发热门诊接诊人数

    protected int taxiPeopleNum; // 出租车运送旅客总人数 wuhan 860,000
    protected int taxiOneTime=4; // 出租车每次运送旅客数量出租车数量
    protected int taxiTimes; // 出租车运行总次数=*每辆出租车每天运行次数*出租车数量
    protected int taxiNum; //出租车数量
    protected int oneTaxiTimes=20; //每辆出租车每天运行次数

    protected int busPeopleNum; // wuhan 3,920,000
    protected int busOneTime=40;
    protected int busTimes;
    protected int busNum;
    protected int oneBusTimes=8;

    protected int subwayPeopleNum;  // 4,050,000
    protected int subwayOneTime=1000;
    protected int subwayTimes;
    protected int subwayNum;
    protected int oneSubwayTimes=15;

    protected int shipPeopleNum; //
    protected int shipOneTime=200;
    protected int shipTimes;
    protected int shipNum;
    protected int oneShipTimes=8;

    protected int othertransportPeopleNum;
    protected int othertransportTime=4;
    protected int otherTimes;

    public InitCity(int population){
        this.population=population;
        this.hospitalNum=population/(11000000/398);
        this.hospitalBedNum=(int)(population/(float)(11000000/(20*500)));
        this.oneDayPatients=this.hospitalNum*400;
        this.oneDayPatientsFever=(int)(this.oneDayPatients*0.1);  // 十分之一的发热门诊病人


        this.taxiPeopleNum=(int)(population/(float)(11000/860));
        this.busPeopleNum=(int)(population/(float)(11000/3920));
        this.subwayPeopleNum=(int)(population/(float)(11000/4050));
        this.shipPeopleNum=(int)(population/(float)(11000/390));
        this.otherTimes=(int)(population/(float)(11000/5000));

        this.taxiTimes=this.taxiPeopleNum/this.taxiOneTime;
        this.busTimes=this.busPeopleNum/this.busOneTime;
        this.subwayTimes=this.subwayPeopleNum/this.subwayOneTime;
        this.shipTimes=this.shipPeopleNum/this.shipOneTime;
        this.otherTimes=this.othertransportPeopleNum/this.othertransportTime;

        this.taxiNum=taxiTimes/oneTaxiTimes;
        this.busNum=busTimes/oneBusTimes;
        this.subwayNum=subwayTimes/oneSubwayTimes;
        this.shipNum=shipTimes/oneShipTimes;
    }
}
