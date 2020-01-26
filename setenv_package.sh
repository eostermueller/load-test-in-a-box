#Variables required for running packagingMagic.sh.
# packagingMagic.sh must be run before "mvn install"
#For most purpposes, these are all the variables that will 
#change from machine to machine

#Tested on macos and on mingw64 on MS-Win

#GOAL: Get latest snail4j source:
# b4 runninig packagingMagic.sh, you must:
# cd $SNAIL4j_SRC / git clone https://github.com/eostermueller/snail4j.git ( in January 2020 and prior, repo was named https://github.com/eostermueller/havoc2.git )
SNAIL4J_SRC=/C/Users/eoste/Documents/src/jssource/snail4j



#  GOAL:  Create database used by snail4j
#  ######################################
#  Run these instructions to create the h2 database: https://github.com/eostermueller/javaPerformanceTroubleshooting/wiki/Install-and-Run
#  Then use the following variable to point to the newly created db:
H2_DB=/c/Users/eoste/Documents/src/jsource/javaPerformanceTroubleshooting/db/data


# GOAL: package a maven repo to be distributed with snail4j.
# ##############################################################
# Why?  snail4j goal is to run "java -jar snail4j.jar" from a USB drive without internet access.
# 1) Exercise snail4j, downloading all maven dependencies to local repo.
#    This exercise must include starting/stopping SUT, starting/stopping load generation.
# 2) Once exercise is complete, then packagingMagic.sh can be run.
#    packagingMagic.sh will zip up the repository at the following location:
MVN_REPO=/C/Users/eoste/.m2

# GOAL:  Download github repo for the  system under test, the SUT, aka system under test, 
# local location of this repo: https://github.com/eostermueller/tjp2
# b4 running this script, you must:
# a) cd $TJP_HOME / git clone https://github.com/eostermueller/tjp2 
TJP_HOME=/C/Users/eoste/Documents/src/jsource/tjp2/tjp2
