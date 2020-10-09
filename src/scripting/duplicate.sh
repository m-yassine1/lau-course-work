#!/bin/bash

#checking for arguments that should be one
if [[ $# -ne 1]]
then
	echo "Syntax: bash duplicate.sh directory"
	exit 1
fi


#check the argument should be a folder
if [[ -f $1  ]]
then
	echo "The argument should be a folder"
	exit 1
elif [[ -d $1 ]]
then
	echo "hello"
else
	echo "The argument should be a folder"
	exit 1
fi

#Get all files from the directory and subdirectories
FILES=$(find $1 -type f)

#Convert a space delimited string into an array
FILESarray=( $FILES )

for i in ${array[@]}; do
    echo $i
done