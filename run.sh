#!/bin/bash

RESULTS_PATH=build/results.txt
EXPECT_PATH=inputs/expect.txt

if [ -f $RESULTS_PATH ]; then
	rm $RESULTS_PATH
fi

ALL_PASSED=1
#touch build/results.txt
while IFS=, read -r day inputFile ans1 ans2; do
	output=$(set -x; ./aocrunner -d $day -i inputs/$inputFile -a1 $ans1 -a2 $ans2)
	
	echo $output >> build/results.txt
	
	if [[ $output == *"❌"* ]]; then
		echo FAILED
		echo $output
		ALL_PASSED=0
	else
		echo PASSED
	fi

	printf "\n"
done <$EXPECT_PATH

if [[ $ALL_PASSED == 1 ]]; then
	echo All tests passed.
	exit 0
else
	echo Some tests failed.
	exit 1
fi
