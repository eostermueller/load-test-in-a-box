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


#Snail4j starup looks for carefully named zip files in this folder, then unzips them.
TARGET=$SNAIL4J_SRC/backend/src/main/resources


echo to trim uber jar size, deleting unused copies of snail4js own uber jar from M2 Repo: 
SEARCH_PATH=$MAVEN_LOCAL_REPO/repository/com/github/eostermueller/load-test-in-a-box_agent

echo possibly with backslashes: $SEARCH_PATH
SEARCH_PATH="${SEARCH_PATH//\\//}"
echo without backslashes: $SEARCH_PATH


echo Number of files to be deleted:  $( ls -lart $SEARCH_PATH 2>/dev/null | wc -l )
echo sizes of files to be deleted:  $( ls -lart SEARCH_PATH 2>/dev/null )
rm -rf $SEARCH_PATH
echo Number of files remaining:  $( ls -lart $SEARCH_PATH 2>/dev/null | wc -l )
echo Sizes of files remaining:  $( ls -lart $SEARCH_PATH 2>/dev/null )


echo Creating repository.zip
jar cvfM $TARGET/repository.zip -C $MAVEN_LOCAL_REPO repository
echo File size of repository.zip:
ls -lart $TARGET/repository.zip

