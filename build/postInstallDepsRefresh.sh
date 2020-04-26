#!/usr/bin/env bash
# @stolenFrom https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

# Prerequisites for running this script?  Assumes standard install/build has been run at least 1 time:  https://github.com/eostermueller/snail4j/wiki/Build
# When to execute this script?  ___post___ install, aka after you've launched the snail4j uber jar on a machine.
# What does this do?  It overwrite-copies files from "src" locations to "installed" locations. 
# Why is this helpful?  Change a src file ( processManager, jmeterFiles, wiremock, tjp2), then invoke this script so you can copy those changes into
#                       your snail4j home/install location.
# This means you don't have to take 5 minutes repackage and rebuild those changed files and then relaunch and reinstall them


#set machine-specific variables.
. $DIR/setenv_01.sh
. $DIR/setenv_02.sh

SNAIL4J_SRC=$DIR/..



$DIR/overwriteCopy.sh $SNAIL4J_SRC/processManager $SNAIL4J_USER_HOME processManager
$DIR/overwriteCopy.sh $SNAIL4J_SRC/jmeterFiles $SNAIL4J_USER_HOME jmeterFiles
$DIR/overwriteCopy.sh $SNAIL4J_SRC/wiremock $SNAIL4J_USER_HOME wiremock

echo Creating sutApp.zip

#A weird "logs " folder with a space is getting creating, not sure why.
#th snail4j java-based unzipper pukes when encountering this folder with a space in it, so delete it.
rm -rf $TJP2_DIR/log*

TARGET_DIR=$SNAIL4J_USER_HOME/sutApp
echo .
echo .
if [ -d "$TARGET_DIR" ];
then
	          echo "About to delete target folder: $TARGET_DIR"
#         read -rsp $'Ctrl+C to exit, any other key to continue.\n' -n1 key
		            rm -rf $TARGET_DIR/
		            mkdir -p $TARGET_DIR/
fi

mkdir -p $TARGET_DIR

cp -r -t $SNAIL4J_USER_HOME/sutApp $TJP2_DIR/*

SRC_COUNT=$(find $TJP2_DIR | wc -l)
TARGET_COUNT=$(find $TARGET_DIR | wc -l)
echo [ $SRC_COUNT ] of [ $TARGET_COUNT ] files copied from src dir $TJP2_DIR to target dir $TARGET_DIR

