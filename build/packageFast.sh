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

echo Creating processManager.zip
jar cvfM $TARGET/processManager.zip -C $PM_HOME .
echo CHECKPOINT: processManager.zip is created : $( ls -lart $TARGET/processManager.zip )

echo Creating jmeterFiles.zip
jar cvfM $TARGET/jmeterFiles.zip -C $JM_HOME .
echo CHECKPOINT jmeterFiles.zip is created : $( ls -lart $TARGET/jmeterFiles.zip )

echo Creating wiremockFiles.zip
jar cvfM $TARGET/wiremockFiles.zip -C $WM_HOME .
echo wiremockFiles.zip is created

echo Creating sutApp.zip

#A weird "logs " folder with a space is getting creating, not sure why.
#th snail4j java-based unzipper pukes when encountering this folder with a space in it, so delete it.
rm -rf $TJP2_DIR/log*
jar cvfM $TARGET/sutApp.zip -C $TJP2_DIR pom.xml -C $TJP2_DIR src
echo CHECKPOINT: sutApp.zip created. $( ls -lart $TARGET/sutApp.zip )

echo to trim uber jar size, deleting unused copies of snail4js own uber jar from M2 Repo: $MAVEN_LOCAL_REPO/repository/${SNAIL4J_MVN_DIR}/*.jar
echo Number of files to be deleted:  $( ls -lart $MAVEN_LOCAL_REPO/repository/${SNAIL4J_MVN_DIR}/*.jar 2>/dev/null | wc -l )
echo sizes of files to be deleted:  $( ls -lart $MAVEN_LOCAL_REPO/repository/${SNAIL4J_MVN_DIR}/*.jar 2>/dev/null )
rm -rf $MAVEN_LOCAL_REPO/repository/${SNAIL4J_MVN_DIR}/*.jar
echo about to delete robolectric maven repo folder -- 131412304 bytes!!!  ./android-all/10-robolectric-5803371/android-all-10-robolectric-5803371.jar
rm -rf $MAVEN_LOCAL_REPO/repository/org/robolectric
