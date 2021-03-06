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
    protected int hospitalICUNum;  // 城市病床数量wuhan 2018 398*1200
    protected int oneDayPatients; // 每天接诊的病人数
    protected int oneDayPatientsFever; //城市发热门诊接诊人数

    protected int taxiPeopleNum; // 出租车运送旅客总人数 wuhan 860,000
    protected int taxiOneTime = 3; // 出租车每次运送旅客数量出租车数量
    protected int taxiTimes; // 出租车运行总次数=*每辆出租车每天运行次数*出租车数量
    protected int taxiNum; //出租车数量
    protected int oneTaxiTimes = 20; //每辆出租车每天运行次数

    protected int busPeopleNum; // wuhan 3,920,000
    protected int busOneTime = 40;
    protected int busTimes;
    protected int busNum;
    protected int oneBusTimes = 8;

    protected int subwayPeopleNum;  // 4,050,000
    protected int subwayOneTime = 1000;
    protected int subwayTimes;
    protected int subwayNum;
    protected int oneSubwayTimes = 15;

    protected int shipPeopleNum; //
    protected int shipOneTime = 200;
    protected int shipTimes;
    protected int shipNum;
    protected int oneShipTimes = 8;

    protected int othertransportPeopleNum;
    protected int othertransportTime = 4;
    protected int otherTimes;

    public InitCity(int population) {
        this.population = population;
        this.hospitalNum = population / (11000000 / 398);
        this.hospitalICUNum = (int) (population / (float) (11000000 / (20 * 500)));
        this.oneDayPatients = this.hospitalNum * 400;
        this.oneDayPatientsFever = (int) (this.oneDayPatients * 0.1);  // 十分之一的发热门诊病人

        //     this.taxiPeopleNum=(int)(population/(float)(11000/860));
        this.taxiPeopleNum = (int) (population * 0.080);
        this.taxiTimes = this.taxiPeopleNum / this.taxiOneTime;
        this.taxiNum = taxiTimes / oneTaxiTimes;

        //     this.busPeopleNum=(int)(population/(float)(11000/3920));
        this.busPeopleNum = (int) (population * 0.359);
        this.busTimes = this.busPeopleNum / this.busOneTime;
        this.busNum = busTimes / oneBusTimes;

        //   this.subwayPeopleNum=(int)(population/(float)(11000/4050));
        this.subwayPeopleNum = (int) (population * 0.384);
        this.subwayTimes = this.subwayPeopleNum / this.subwayOneTime;
        this.subwayNum = this.subwayTimes / this.oneSubwayTimes;

        //this.othertransportPeopleNum=(int)(population*1);
    }

    public void setParis(int population) {
        this.population = population;

        this.hospitalNum = population / (11000000 / 600);
        this.hospitalICUNum = (int) (population / (float) (11000000 / (20 * 500)));

        this.oneDayPatients = this.hospitalNum * 400;
        this.oneDayPatientsFever = (int) (this.oneDayPatients * 0.1);  // 十分之一的发热门诊病人

        this.taxiPeopleNum = (int) (population * 0.777);
        this.taxiTimes = this.taxiPeopleNum / this.taxiOneTime;
        this.taxiNum = taxiTimes / oneTaxiTimes;

        this.busPeopleNum = (int) (population * 1.134);
        this.busTimes = this.busPeopleNum / this.busOneTime;
        this.busNum = busTimes / oneBusTimes;

        this.subwayPeopleNum = (int) (population * 1.661);
        this.subwayTimes = this.subwayPeopleNum / this.subwayOneTime;
        this.subwayNum = this.subwayTimes / this.oneSubwayTimes;

    }

    public void setStLouis(int population) {
        this.population = population;
        this.hospitalNum = population / (11000000 / 600);
        this.hospitalICUNum = (int) (population / (float) (11000000 / (20 * 500)));
        this.oneDayPatients = this.hospitalNum * 400;
        this.oneDayPatientsFever = (int) (this.oneDayPatients * 0.1);  // 十分之一的发热门诊病人

        this.taxiPeopleNum = (int) (population * 0.080);
        this.taxiTimes = this.taxiPeopleNum / this.taxiOneTime;
        this.taxiNum = taxiTimes / oneTaxiTimes;

        this.busPeopleNum = (int) (population * 0.244);
        this.busTimes = this.busPeopleNum / this.busOneTime;
        this.busNum = busTimes / oneBusTimes;

        this.subwayPeopleNum = (int) (population * 0.409);
        this.subwayTimes = this.subwayPeopleNum / this.subwayOneTime;
        this.subwayNum = this.subwayTimes / this.oneSubwayTimes;

    }

    public void setYinChuan(int population) {
        this.population = population;
        this.hospitalNum = population / (11000000 / 400);
        this.hospitalICUNum = (int) (population / (float) (11000000 / (20 * 500)));
        this.oneDayPatients = this.hospitalNum * 400;
        this.oneDayPatientsFever = (int) (this.oneDayPatients * 0.1);  // 十分之一的发热门诊病人

        this.taxiPeopleNum = (int) (population * 0.140);
        this.taxiTimes = this.taxiPeopleNum / this.taxiOneTime;
        this.taxiNum = taxiTimes / oneTaxiTimes;

        this.busPeopleNum = (int) (population * 0.29);
        this.busTimes = this.busPeopleNum / this.busOneTime;
        this.busNum = busTimes / oneBusTimes;

        this.subwayPeopleNum = 0;
        this.subwayTimes = 0;
        this.subwayNum = 0;

    }

    public void setTokyo(int population) {
        this.population = population;
        //population=12790000
        this.hospitalNum = population / (11000000 / 600);
        this.hospitalICUNum = (int) (population / (float) (11000000 / (20 * 500)));

        this.oneDayPatients = this.hospitalNum * 400;
        this.oneDayPatientsFever = (int) (this.oneDayPatients * 0.1);  // 十分之一的发热门诊病人

        this.taxiPeopleNum = (int) (population * 0.066);
        this.taxiTimes = this.taxiPeopleNum / this.taxiOneTime;
        this.taxiNum = taxiTimes / oneTaxiTimes;

        this.busPeopleNum = (int) (population * 0.234);
        this.busTimes = this.busPeopleNum / this.busOneTime;
        this.busNum = busTimes / oneBusTimes;

        this.subwayPeopleNum = (int) (population * 1.714);
        this.subwayTimes = this.subwayPeopleNum / this.subwayOneTime;
        this.subwayNum = this.subwayTimes / this.oneSubwayTimes;

    }

    public void setLosAngeles(int population) {
        this.population = population;
        //population=12150996  4000000
        this.hospitalNum = population / (11000000 / 600);
        this.hospitalICUNum = (int) (population / (float) (11000000 / (20 * 500)));

        this.oneDayPatients = this.hospitalNum * 400;
        this.oneDayPatientsFever = (int) (this.oneDayPatients * 0.1);  // 十分之一的发热门诊病人

        this.taxiPeopleNum = (int) (population * 0.08);
        this.taxiTimes = this.taxiPeopleNum / this.taxiOneTime;
        this.taxiNum = taxiTimes / oneTaxiTimes;

        this.busPeopleNum = (int) (population * 0.24);
        this.busTimes = this.busPeopleNum / this.busOneTime;
        this.busNum = busTimes / oneBusTimes;

        this.subwayPeopleNum = (int) (population * 0.4236);
        this.subwayTimes = this.subwayPeopleNum / this.subwayOneTime;
        this.subwayNum = this.subwayTimes / this.oneSubwayTimes;
    }

    public void setSingpore(int population) {
        this.population = population;
        //population=12150996
        this.hospitalNum = population / (11000000 / 600);
        this.hospitalICUNum = (int) (population / (float) (11000000 / (20 * 500)));

        this.oneDayPatients = this.hospitalNum * 400;
        this.oneDayPatientsFever = (int) (this.oneDayPatients * 0.1);  // 十分之一的发热门诊病人

        this.taxiPeopleNum = (int) (population * 0.140);
        this.taxiTimes = this.taxiPeopleNum / this.taxiOneTime;
        this.taxiNum = taxiTimes / oneTaxiTimes;

        this.busPeopleNum = (int) (population * 0.704);
        this.busTimes = this.busPeopleNum / this.busOneTime;
        this.busNum = busTimes / oneBusTimes;

        this.subwayPeopleNum = (int) (population * 0.557);
        this.subwayTimes = this.subwayPeopleNum / this.subwayOneTime;
        this.subwayNum = this.subwayTimes / this.oneSubwayTimes;

    }

    public void setNewYork(int population) {
        this.population = population;
        //population=12150996  4000000
        this.hospitalNum = population / (11000000 / 600);
        this.hospitalICUNum = (int) (population / (float) (11000000 / (20 * 500)));

        this.oneDayPatients = this.hospitalNum * 400;
        this.oneDayPatientsFever = (int) (this.oneDayPatients * 0.1);  // 十分之一的发热门诊病人

        this.taxiPeopleNum = (int) (population * 0.08);
        this.taxiTimes = this.taxiPeopleNum / this.taxiOneTime;
        this.taxiNum = taxiTimes / oneTaxiTimes;

        this.busPeopleNum = (int) (population * 0.306);
        this.busTimes = this.busPeopleNum / this.busOneTime;
        this.busNum = busTimes / oneBusTimes;

        this.subwayPeopleNum = (int) (population * 1.339);
        this.subwayTimes = this.subwayPeopleNum / this.subwayOneTime;
        this.subwayNum = this.subwayTimes / this.oneSubwayTimes;
    }
}
