#!/usr/bin/env bash
# @stolenFrom https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"

MVN_SUB_MOD_NAME=$1

if [ -n "$MVN_SUB_MOD_NAME" ]; then
       mvn -e -X -f $DIR/../pom.xml --projects $MVN_SUB_MOD_NAME --also-make clean install 
else
       mvn -e -X -f $DIR/../pom.xml clean install 
fi


