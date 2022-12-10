#!/bin/bash

RESULTS_PATH="build/results.txt"
EXPECT_PATH="inputs/expect.txt"

if [ -f $RESULTS_PATH ]; then
	rm $RESULTS_PATH
fi

all_passed=1
while IFS="," read -r day inputFile ans1 ans2; do
	output=$(set -x; ./aocrunner -d $day -i inputs/$inputFile -a1 $ans1 -a2 $ans2)
	
	echo $output >> build/results.txt
	
	if [[ $output == *"❌"* || $output == *"Exception"*  ]]; then
		printf "FAILED"
		printf "%s" $output
		all_passed=0
	else
		printf "PASSED"
	fi

	printf "\n"
done <$EXPECT_PATH

if [[ $all_passed == 1 ]]; then
	printf "All tests passed.\n"
	exit 0
else
	printf "Some tests failed.\n"
	exit 1
fi
