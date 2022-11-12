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



#https://github.com/eostermueller/javaPerformanceTroubleshooting
# The following file is an h2 database file zipped up into data.zip.  It takes 5-10 minutes to build.
# To create it, download the above project and run the init.sh/init.cmd
# That will create perfSandboxDb.mv.db, which must be zipped into data.zip
#cp $LANDING/data.zip $TARGET/





echo Downloading Glowroot from $GLOWROOT_BIN
# without the -L, curl doesn't handle the REDIRECT that github uses
curl -L -o $TARGET/${GLOWROOT_DIST_ZIP} -O $GLOWROOT_BIN
echo CHECKPOINT: Just downloaded $TARGET/${GLOWROOT_DIST_ZIP} : $( ls -lart $TARGET/${GLOWROOT_DIST_ZIP} )
