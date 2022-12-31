#!/usr/bin/env bash

# @stolenFrom https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"


GIT_URL=$1
TARGET_DIR=$2

#if [ -d "$TARGET_DIR" ]; 
#then
#	  echo "About to delete folder: $TARGET_DIR"
#	  read -rsp $'Ctrl+C to exit, any other key to continue.\n' -n1 key
	  rm -rf $TARGET_DIR
#fi

mkdir -p $TARGET_DIR


git clone $GIT_URL $TARGET_DIR



