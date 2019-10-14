LANDING=/Users/erikostermueller/Documents/src/jssource/havoc2/installer/installFiles


#These are part of snail4j distribution
PM_HOME=/Users/erikostermueller/Documents/src/jssource/havoc2/havoc2/processManager
JM_HOME=/Users/erikostermueller/Documents/src/jssource/havoc2/havoc2/jmeterFiles
WM_HOME=/Users/erikostermueller/Documents/src/jssource/havoc2/havoc2/wiremock

# local location of this repo: https://github.com/eostermueller/tjp2
TJP_HOME=/Users/erikostermueller/Documents/src/jsource/tjp2


TARGET=/Users/erikostermueller/Documents/src/jssource/havoc2/havoc2/backend/src/main/resources

echo Creating processManager.zip
jar cvfM $LANDING/processManager.zip -C $PM_HOME .
echo processManager.zip is created

echo Creating jmeterFiles.zip
jar cvfM $LANDING/jmeterFiles.zip -C $JM_HOME .
echo jmeterFiles.zip is created

echo Creating wiremockFiles.zip
jar cvfM $LANDING/wiremockFiles.zip -C $WM_HOME .
echo wiremockFiles.zip is created

echo Creating sutApp.zip
jar cvfM $LANDING/sutApp.zip -C $TJP_HOME .
echo sutApp.zip created.



cp $LANDING/* $TARGET

ls -lart $TARGET
