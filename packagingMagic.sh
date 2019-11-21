#SNAIL4J_SRC=/Users/erikostermueller/Documents/src/jssource/snail4j/snail4j
SNAIL4J_SRC=/Users/German/development/workspace-oepe-bch/havoc2
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
#MVN_REPO=/Users/erikostermueller/.m2
MVN_REPO=/Users/erikostermueller/.m2

GLOWROOT_BIN=https://github.com/glowroot/glowroot/releases/download/v0.13.5/glowroot-0.13.5-dist.zip

# local location of this repo: https://github.com/eostermueller/tjp2
# b4 running this script, you must:
# a) cd $TJP_HOME / git clone https://github.com/eostermueller/tjp2 

#TJP_HOME=/Users/erikostermueller/Documents/src/jsource/tjp2
TJP_HOME=/Users/German/development/workspace-oepe-bch/tjp2

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
cp $LANDING/data.zip $TARGET/


curl -o $TARGET/apache-maven-3.6.2-bin.zip -O $MVN_BIN


# without the -L, curl doesn't handle the REDIRECT that github uses
curl -L -o $TARGET/glowroot-0.13.5-dist.zip -O $GLOWROOT_BIN

