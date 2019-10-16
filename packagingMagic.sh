SNAIL4J_SRC=/Users/erikostermueller/Documents/src/jssource/havoc2/havoc2
LANDING=$SNAIL4J_SRC/../installer/installFiles

#zips up and places zip files in backend/src/main/resources,
#so that the backend maven build will include them in the spring boot uber jar.
#When spring boot starts, Snail4jInstaller.java will unzip those files into the
# .snail4j folder in the user's home folder.  Jenkins uses this approach.


#These are part of snail4j distribution
PM_HOME=$SNAIL4J_SRC/processManager
JM_HOME=$SNAIL4J_SRC/jmeterFiles
WM_HOME=$SNAIL4J_SRC/wiremock
MVN_BIN=http://www-us.apache.org/dist/maven/maven-3/3.6.2/binaries/apache-maven-3.6.2-bin.zip
MVN_REPO=/Users/erikostermueller/.m2

# local location of this repo: https://github.com/eostermueller/tjp2
# b4 running this script, you must:
# a) cd $TJP_HOME / git clone https://github.com/eostermueller/tjp2 
# b) Must 'mvn clean install' to get glowroot downloaded
TJP_HOME=/Users/erikostermueller/Documents/src/jsource/tjp2

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
jar cvfM $TARGET/sutApp.zip -C $TJP_HOME .
echo sutApp.zip created.

echo Creating repository.zip
jar cvfM $TARGET/repository.zip -C $MVN_REPO repository
echo repository.zip is created

#https://github.com/eostermueller/javaPerformanceTroubleshooting
# The following file is an h2 database file zipped up into data.zip.  It takes 5-10 minutes to build.
# To create it, download the above project and run the init.sh/init.cmd
# That will create perfSandboxDb.mv.db, which must be zipped into data.zip
cp $LANDING/data.zip $TARGET/


wget --output-document=$TARGET/apache-maven-3.6.2-bin.zip $MVN_BIN

