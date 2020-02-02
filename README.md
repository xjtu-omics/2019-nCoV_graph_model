# 2019-nCoV_graph_model

usage: java -jar 2019-nCoV_v2.jar noaction -ii incubation_infected -t type -p populaiton -icount initilizeInfectedCount -r replicate_time -minI minIncubation -maxI maxIncubation -meanI meanIncubation -os outsign -o outdir
       java -jar 2019-nCoV_v2.jar action   -ii incubation_infected -t type -p populaiton -icount initilizeInfectedCount -d action_day -r replicate_time -minI minIncubation -maxI maxIncubation -meanI meanIncubation -os outsign -o outdir
-------- subcommand (required, string): noaction or action
-------- incubation_infected (required, boolean): incubation infected, true or false
-------- type(required, string): 
               noaction: NO 
                         America 
                         Poor 

               action:   springFestival 
                         peoplePanic 
                         goodHealth 
                         closeBus 
                         communityHospital 
                         closeTaxi 
                         closeSubway 
                         closeCity 
                         publicDisinfectants 
                         homeIsolation 
                         multiple
-------- population (required int): e.g. 100000
-------- action_day (required int): e.g. 5
-------- initilizeInfectedCount (optional int, default = 1): e.g. 1
-------- replicate_time (optional, int, default = 1): e.g. 1, 2, 3...
-------- minIncubation (optional, int, default = 1)
-------- maxIncubation (optional, int, default = 14): e.g. 1, 2, 3...
-------- meanIncubation (optional, int, default = 7): e.g. 1, 2, 3...
-------- outsign (optional, string, default = "")
-------- outdir (optional, string, default = ./)
