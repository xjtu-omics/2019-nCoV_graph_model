# 2019-nCoV_graph_model

```
usage: java -jar 2019-nCoV_v4.jar subcommand -t type -r replicate_time -os outsign -o outdir -config configFile
-------- subcommand (required, string): noaction or action
-------- type(required, string): 
               noaction: NO 
                         reduceDeathRate 
                           Paris 
                         stlouis 
                         LosAngeles 
                         NewYork 
                         Singpore 
                         Tokyo 
                         YinChuan 
               action:   springFestival 
                         peoplePanic 
                         goodHealth 
                         communityHospital 
                         closeTransportation 
                         closeCity 
                         publicDisinfectants 
                         homeIsolation 
                         multiple 
-------- configFile (require, string)
-------- replicate_time (optional, int)
-------- outsign (optional, string, default = "")
-------- outdir (optional, string, default = ./)

```

# the default config file

```
##########################################################################
##theconfigfileofdynamicgraphmodelof2019-nCoVsimulation
##########################################################################
population=100000								## population size of a city
actionDay=20									## if there are some actions, the action begin at which day
initialInfectedCount=1							## initial infection count
minIncubation=0									## min incubation period
maxIncubation=24								## max incubation period
meanIncubation=3								## means incubation period
familyMemberNumber=3							## means family member number
busPassangerNum=40								## means passanger number of a bus
taxiPassangerNum=3								## means passanger number of a taxi
subwayPassangerNum=1000							## means passanger number of a subway
taxiTouchNumber=2								## means touch number in a taxi
busTouchNumber=5								## means touch number in a bus
subwayTouchNumber=5								## means touch number in a subway
hospitalTouchNumber=20							## means touch number in a hospital
familyTouchNumber=3								## means touch number in a family / family car
publicTouchNumber=10							## means touch number in a public region
treatLastTime=21								## means the treatment days in hospital
panicFold=2										## means the fold change of transportation passangers when people panic
populationProporationForFamily=0.8				## the proporation of people in the city have family
populationProporationForPersonInteraction=0.1	## the proporation of people will interact daily
familyInteractionProporation=0.2				## the proporation of families will interact in spring festival
publicPopulationPropation=0.01					## the proporation of population will goto public region in the city
peopleInitInfectionPro=0.1						## the initial infection probability of a person
peopleInitDeadPro=0.05							## the initial dead probability of a person when infected the virus
peopleHospitalDeadPro=0.01						## the dead probability of a person when he/she is treated in a hospital
goodHealthReducedSpreProFold=0.4				## the fold of virus spreading probability when individual performs good health habits
cureInfectionProFold=0.4						## the fold of infection probability when individual was cured
cureStillInfected=false							## is the person still will be infected after cure
hospitalCapacityForVirus=						## hospital capacity for virus
closeTransportationPerscent=0.0                 ## the percentage of public transportation close when action is close public transportation
patientWithFeverProbability=0.438				## the proporation of patients with fever
patientSevereProbability=0.15					## the proporation of patients is severe
patientRNADetectionProbability=0.9				## the proporation of patients can be detected by RNA/DNA
increaseHospitalResource=100					## increase the hospital resource, if the action is decrease death rate
decreaseDeathRateInfectionNum=100				## the infection number threshold for begining decrease death rate, if the action is decrease death rate
```
