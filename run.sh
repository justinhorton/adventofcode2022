#!/bin/bash

while IFS=, read -r day inputFile ans1 ans2; do
	echo "$day inputs/$inputFile $ans1 $ans2"
	(set -x; ./aocrunner -d $day -i inputs/$inputFile -a1 $ans1 -a2 $ans2)
done <expect.txt
#grep -oE '[^[:space:]]+$' out1.txt | tr '\n' ',' 
