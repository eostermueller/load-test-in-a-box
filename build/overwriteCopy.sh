#!/usr/bin/env bash

# @stolenFrom https://stackoverflow.com/questions/59895/how-to-get-the-source-directory-of-a-bash-script-from-within-the-script-itself
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"


SRC_DIR=$1
TARGET_ROOT_DIR=$2
TARGET_LEAF_DIR=$3
TARGET_DIR=$TARGET_ROOT_DIR/$TARGET_LEAF_DIR


echo .
echo .
if [ -d "$TARGET_ROOT_DIR/$TARGET_LEAF_DIR" ]; 
then
	  echo "About to delete target folder: $TARGET_DIR"
#	  read -rsp $'Ctrl+C to exit, any other key to continue.\n' -n1 key
	  rm -rf $TARGET_DIR/
fi

mkdir -p $TARGET_DIR


cp -r $SRC_DIR $TARGET_ROOT_DIR

SRC_COUNT=$(find $SRC_DIR | wc -l)
TARGET_COUNT=$(find $TARGET_DIR | wc -l)
echo [ $SRC_COUNT ] of [ $TARGET_COUNT ] files copied from src dir $SRC_DIR to target dir $TARGET_DIR
