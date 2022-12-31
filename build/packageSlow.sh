#!/usr/bin/env bash
# @stolenFrom https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"


# set machine specific variables in setenv_package.sh
# and run this command _before_ running "mvn install"


#zips up and places zip files in backend/src/main/resources,
#so that the backend maven build will include them in the spring boot uber jar.
#When spring boot starts, Snail4jInstaller.java will unzip those files into the
# .snail4j folder in the user's home folder.  Jenkins uses this approach.

#set machine-specific variables.
. $DIR/setenv_01.sh
. $DIR/setenv_02.sh

SNAIL4J_SRC=$DIR/..

#These are part of snail4j distribution
PM_HOME=$SNAIL4J_SRC/processManager
JM_HOME=$SNAIL4J_SRC/jmeterFiles
WM_HOME=$SNAIL4J_SRC/wiremock


#Snail4j starup looks for carefully named zip files in this folder, then unzips them.
TARGET=$SNAIL4J_SRC/backend/src/main/resources

echo Attempting to download $JMETER_ZIP_NAME from $JMETER_URL
curl -o $TARGET/${JMETER_ZIP_NAME} -O $JMETER_URL
echo CHECKPOINT:  Here is the downloaded jmeter zip file  -- ${JMETER_ZIP_NAME} : $( ls -lart $TARGET/$JMETER_ZIP_NAME )


#https://github.com/eostermueller/javaPerformanceTroubleshooting
# The following file is an h2 database file zipped up into data.zip.  It takes 5-10 minutes to build.
# To create it, download the above project and run the init.sh/init.cmd
# That will create perfSandboxDb.mv.db, which must be zipped into data.zip
#cp $LANDING/data.zip $TARGET/

echo Creating data.zip in folder $TARGET using H2 data file in $H2_DB
jar cvfM $TARGET/data.zip -C $H2_DB perfSandboxDb.mv.db
echo CHECKPOINT: Just created data.zip.   $( ls -lart $TARGET/data.zip )


echo Downloading Maven from $MVN_URL
curl -o $TARGET/${MVN_ZIP_NAME} -O $MVN_URL
echo CHECKPOINT Just downloaded maven.zip from $MVN_URL: $(ls -lart $TARGET/${MVN_ZIP_NAME})
ls -lart $TARGET/${MVN_ZIP_NAME}


echo Downloading Glowroot from $GLOWROOT_BIN
# without the -L, curl doesn't handle the REDIRECT that github uses
curl -L -o $TARGET/${GLOWROOT_DIST_ZIP} -O $GLOWROOT_BIN
echo CHECKPOINT: Just downloaded $TARGET/${GLOWROOT_DIST_ZIP} : $( ls -lart $TARGET/${GLOWROOT_DIST_ZIP} )
