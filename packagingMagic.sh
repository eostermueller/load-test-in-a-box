
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
MVN_URL=http://www-us.apache.org/dist/maven/maven-3/3.6.3/binaries/${MVN_ZIP_NAME}

# local location of this repo: https://github.com/eostermueller/tjp2
# b4 running this script, you must:
# a) cd $TJP_HOME / git clone https://github.com/eostermueller/tjp2 

TJP_HOME=/C/Users/eoste/Documents/src/jsource/tjp2/tjp2

#Snail4j starup looks for carefully named zip files in this folder, then unzips them.
TARGET=$SNAIL4J_SRC/backend/src/main/resources

echo Creating processManager.zip
jar cvfM $TARGET/processManager.zip -C $PM_HOME .
echo processManager.zip is created

echo Creating jmeterFiles.zip
jar cvfM $TARGET/jmeterFiles.zip -C $JM_HOME .
echo jmeterFiles.zip is created

echo Creating wiremockFiles.zip
jar cvfM $TARGET/wiremockFiles.zip -C $WM_HOME .
echo wiremockFiles.zip is created

echo Creating sutApp.zip

#A weird "logs " folder with a space is getting creating, not sure why.
#th snail4j java-based unzipper pukes when encountering this folder with a space in it, so delete it.
rm -rf $TJP_HOME/log*
jar cvfM $TARGET/sutApp.zip -C $TJP_HOME .
echo sutApp.zip created.

echo Creating repository.zip
jar cvfM $TARGET/repository.zip -C $MVN_REPO repository
echo repository.zip is created

#https://github.com/eostermueller/javaPerformanceTroubleshooting
# The following file is an h2 database file zipped up into data.zip.  It takes 5-10 minutes to build.
# To create it, download the above project and run the init.sh/init.cmd
# That will create perfSandboxDb.mv.db, which must be zipped into data.zip
#cp $LANDING/data.zip $TARGET/

echo Creating data.zip in folder $TARGET using H2 data file in $H2_DB
jar cvfM $TARGET/data.zip -C $H2_DB perfSandboxDb.mv.db
echo Just created data.zip.  
ls -lart $TARGET/data.zip


curl -o $TARGET/${MVN_ZIP_NAME} -O $MVN_URL


# without the -L, curl doesn't handle the REDIRECT that github uses
curl -L -o $TARGET/glowroot-0.13.5-dist.zip -O $GLOWROOT_BIN

