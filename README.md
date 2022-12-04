# Advent of Code 2022

![CI](https://github.com/justinhorton/adventofcode2022/actions/workflows/build.yml/badge.svg)

[Advent of Code 2022](https://adventofcode.com/2022/) using [Kotlin](https://kotlinlang.org/).

To keep things interesting, this year I'm using [Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html) and producing a native runner binary. This at least works on Github Actions Linux agents and Apple silicon Macs.

## Command Line Usage

```
./aocrunner -d [1-25] (-i [input file]) (-a1 [expected part 1 answer]) (-a2 [expected part 2 answer])
```

```
Usage: aocrunner options_list
Options: 
    --day, -d -> Day number (always required) { Int }
    --in-file, -i -> Input file path { String }
    --answer1, -a1 -> Part 1 expected answer { String }
    --answer2, -a2 -> Part 2 expected answer { String }
    --help, -h -> Usage info 
```

Typical usage would simply be:
```
./aocrunner -d [1-25]
```
where the default input file is expected at `./inputs/xx.txt`, where `xx` is the 2-character (0-padded) day number.`

## Test Structure

`inputs/` contains all 'real' inputs, as well as any tested samples. E.g. for day 1, the real input is `01.txt` and the sample is `01-s1.txt`.

`./gradlew runDays` invokes the native runner executable for each line in `expect.txt` and succeeds if all actual outputs match those from the file. Each line in `expect.txt` is a comma-separated list of the 4-args to pass to the runner.
