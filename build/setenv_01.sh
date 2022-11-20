#!/usr/bin/env bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"




# This file downloads with https://github.com/eostermueller/load-test-in-a-box
# If this setenv_01.sh is in 
#
# 	/path/to/my/snail4j/build
#
# Running cleanAndBuild.sh will create the following, each sibling folders to the above snail4j folder:
#
# 	/path/to/my/tjp2
# 	/path/to/my/javaPerormanceTroubleshooting
# 	/path/to/my/h2-maven-plugin

#
#  $DIR        = parent/snail4j/build  -- the location of this setenv_01.sh file!!!
#  $DIR/..     = parent/snail4j/
#  $DIR/../..  = parent
#  $SRC_DIR    = parent

SRC_DIR=$DIR/../..

#Find the Maven local repository.  if $USERPROFILE is set, then OS=MS-Win else *nix
if [ "$USERPROFILE" == "" ]
then
  MAVEN_LOCAL_REPO=~/.m2
  SNAIL4j_USER_HOME=~/.load-test-in-a-box
else
  MAVEN_LOCAL_REPO=$USERPROFILE/.m2
  SNAIL4J_USER_HOME=$USERPROFILE/.load-test-in-a-box
fi

# On MS-Win, the avlue looks like this, with mixed slashes:  C:\Users\eoste/.m2/repository
# ...but MSYS/bash resolves this correctly with bash commands
