#!/bin/bash

#checking for arguments that should be one
if [[ $# -ne 1 ]]
then
	echo "Syntax: bash duplicate.sh directory"
	exit 1
fi

#check the argument should be a folder
if [[ -f $1  ]]
then
	echo "The argument should be a folder"
	exit 1	
fi

#Get all files with directories from the directory and subdirectories
FILES=$(find $1 -type f)

#Get all files without directories from the directory and subdirectories
FILESonly=$(find $1 -type f -printf "%f\n")

#Convert a space delimited string into an array
FILESarray=( $FILES )
FILESonlyarray=( $FILESonly )

#Get length
length=${#FILESonly[@]}

#Get checksum for all files
declare -a FILESmd5sum
declare -a BYTESarray

for file in ${FILESarray[@]}; do
	FILESmd5sum+=($(md5sum $file | awk '{ printf $1 }'))
	BYTESarray+=($(ls -lh $file | awk '{ print $5 }'))
done

declare -a DUPLICATEarray
#array to store the index of similar files
declare -a DUPLICATEindexarray

#Get Length of the Files
Fmd=${#FILESmd5sum[@]}

#echo ${FILESmd5sum[@]}

for ((i=0;i<$Fmd;i++)) do
	if [[ FILESmd5sum[i]!=null ]]; then
		for ((j=$i+1;j<$Fmd;j++)) do
			if [[ ${FILESmd5sum[j]} = ${FILESmd5sum[i]} && i!=j ]]; then
				DUPLICATEindexarray[i]="${DUPLICATEindexarray[i]} $j"
				FILESmd5sum[j]=null
				DUPLICATEarray[i]=$((${DUPLICATEarray[i]}+1))
			fi
		done
	fi
done

#echo ${FILESmd5sum[@]}
echo ${DUPLICATEarray[@]}
echo ${DUPLICATEindexarray[@]}

for ((i=0;i<$Fmd;i++)) do
	if [[ ${DUPLICATEindexarray[i]} != "" ]]; then
		printf "${FILESonlyarray[i]} ${BYTESarray[i]}"
		temparray=( ${DUPLICATEindexarray[i]} )
		length=${#temparray[@]}
		for((j=0;j<$length;j++)) do
			printf "\n\t\t ${FILESarray[${temparray[j]}]}\n"
		done
	fi
done
