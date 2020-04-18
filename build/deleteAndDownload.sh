#!/usr/bin/env bash

# @stolenFrom https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

. $DIR/setenv_01.sh
. $DIR/setenv_02.sh


$DIR/deleteAndClone.sh $H2_MAVEN_PLUGIN_GIT $H2_MAVEN_PLUGIN_DIR
$DIR/deleteAndClone.sh $JAVA_PERF_TROUB_GIT $JAVA_PERF_TROUB_DIR
$DIR/deleteAndClone.sh $TJP2_GIT            $TJP2_DIR




