# set machine specific variables in setenv_package.sh
# and run this command _before_ running "mvn install"


#zips up and places zip files in backend/src/main/resources,
#so that the backend maven build will include them in the spring boot uber jar.
#When spring boot starts, Snail4jInstaller.java will unzip those files into the
# .snail4j folder in the user's home folder.  Jenkins uses this approach.

#set machine-specific variables.
. setenv_package.sh

#These are part of snail4j distribution
PM_HOME=$SNAIL4J_SRC/processManager
JM_HOME=$SNAIL4J_SRC/jmeterFiles
WM_HOME=$SNAIL4J_SRC/wiremock

GLOWROOT_BIN=https://github.com/glowroot/glowroot/releases/download/v0.13.5/glowroot-0.13.5-dist.zip
MVN_ZIP_NAME=apache-maven-3.6.3-bin.zip
MVN_URL=https://downloads.apache.org/maven/maven-3/3.6.3/binaries/${MVN_ZIP_NAME}


#Snail4j starup looks for carefully named zip files in this folder, then unzips them.
TARGET=$SNAIL4J_SRC/backend/src/main/resources


echo Creating sutApp.zip

#A weird "logs " folder with a space is getting creating, not sure why.
#th snail4j java-based unzipper pukes when encountering this folder with a space in it, so delete it.
rm -rf $TJP_HOME/log*
jar cvfM $TARGET/sutApp.zip -C $TJP_HOME pom.xml -C $TJP_HOME src
echo sutApp.zip created.

echo deleting cruft from M2 Repo
rm -rf $MVN_REPO/repository/com/github/eostermueller/backend/0.0.2-SNAPSHOT/*.jar


