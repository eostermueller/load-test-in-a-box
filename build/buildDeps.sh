#!/usr/bin/env bash

# @stolenFrom https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

. $DIR/setenv_01.sh
. $DIR/setenv_02.sh


#Build an H2 DB with > 1m records
pushd $JAVA_PERF_TROUB_DIR
./init.sh
popd

echo "Here is the freshly built H2 DB (perfSandboxDb.mv.db).  It should be about 705294336 bytes"
ls -lart $H2_DB/perfSandboxDb.mv.db

#Keeping the size of the uber jar sufficiently small is a big concer....it has been as large as 800mb!!!
#Here, we delete all Maven downloads used to build the database...because those jars/processes will not be used again
rm -rf $MAVEN_LOCAL_REPO/repository


echo "Attempting Maven build for pom: $H2_MAVEN_PLUGIN_DIR/pom.xml"
mvn -f $H2_MAVEN_PLUGIN_DIR/pom.xml clean install
