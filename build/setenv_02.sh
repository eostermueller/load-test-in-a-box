#!/usr/bin/env bash


# If snail4j's uber jar is balooning in size and you don't know why, this comment is for you!!!
# snail4j's pom inadvertently sticks of LARGE copy of the uber jar into the local maven repo.
# The following is used to hunt down and delted it, so we don't zip a copy of the uber.jar inside the uber.jar!!!!!!
SNAIL4J_MVN_DIR=com/github/eostermueller/backend/0.0.2-SNAPSHOT




# No source code is used from this location while snail4j is running.
# Instead, this is used solely to create a 1g-ish H2 database
JAVA_PERF_TROUB_GIT=https://github.com/eostermueller/javaPerformanceTroubleshooting
JAVA_PERF_TROUB_DIR=$SRC_DIR/javaPerformanceTroubleshooting

#  GOAL:  Create database used by snail4j
#  ######################################
#  Run these instructions to create the h2 database: https://github.com/eostermueller/javaPerformanceTroubleshooting/wiki/Install-and-Run
#  Then use the following variable to point to the newly created db:
H2_DB=$SRC_DIR/javaPerformanceTroubleshooting/db/data


# GOAL: package a maven repo to be distributed with snail4j.
# ##############################################################
# Why?  snail4j goal is to run "java -jar snail4j.jar" from a USB drive without internet access.
# 1) Exercise snail4j, downloading all maven dependencies to local repo.
#    This exercise must include starting/stopping SUT, starting/stopping load generation.
# 2) Once exercise is complete, then packagingMagic.sh can be run.
#    packagingMagic.sh will zip up the repository at the following location:

# GOAL:  Download github repo for the  system under test, the SUT, aka system under test, 
# local location of this repo: https://github.com/eostermueller/tjp2
# b4 running this script, you must:
# a) cd $TJP2_DIR / git clone https://github.com/eostermueller/tjp2 
TJP2_GIT=https://github.com/eostermueller/tjp2
TJP2_DIR=$SRC_DIR/tjp2



H2_MAVEN_PLUGIN_GIT=https://github.com/eostermueller/h2-maven-plugin
H2_MAVEN_PLUGIN_DIR=$SRC_DIR/h2_maven_plugin


#The following version change must be updated and recompiled:
#  			this.setGlowrootZipFileName ("glowroot-0.13.6-dist.zip");
# ../backend/src/main/java/com/github/eostermueller/snail4j/launcher/DefaultConfiguration.java
GLOWROOT_VERSION=0.14.0-beta.3
GLOWROOT_DIST_ZIP=glowroot-${GLOWROOT_VERSION}-dist.zip
GLOWROOT_BIN=https://github.com/glowroot/glowroot/releases/download/v${GLOWROOT_VERSION}/${GLOWROOT_DIST_ZIP}

MVN_VERSION=3.6.3
MVN_ZIP_NAME=apache-maven-${MVN_VERSION}-bin.zip
MVN_URL=https://downloads.apache.org/maven/maven-3/${MVN_VERSION}/binaries/${MVN_ZIP_NAME}

JMETER_ZIP_NAME=apache-jmeter-5.2.1.zip
JMETER_URL=https://downloads.apache.org//jmeter/binaries/${JMETER_ZIP_NAME}

