#!/usr/bin/env bash

# @stolenFrom https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

. $DIR/setenv_01.sh
. $DIR/setenv_02.sh


echo "Attempting Maven build for pom: $H2_MAVEN_PLUGIN_DIR/pom.xml"
mvn -f $H2_MAVEN_PLUGIN_DIR/pom.xml clean install

#Build an H2 DB with > 1m records
pushd $JAVA_PERF_TROUB_DIR
./init.sh
popd

echo "Here is the freshly built H2 DB (perfSandboxDb.mv.db).  It should be about 705294336 bytes"
ls -lart $H2_DB/perfSandboxDb.mv.db



