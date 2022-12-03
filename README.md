# Advent of Code 2022

![CI](https://github.com/justinhorton/adventofcode2022/actions/workflows/build.yml/badge.svg)

[Advent of Code 2022](https://adventofcode.com/2022/) using [Kotlin](https://kotlinlang.org/).

## Command Line Usage

```
./gradlew run --args="-d [1-25] (-i [input file])"

Options: 
    --day, -d -> Day number (always required) { Int }
    --in-file, -i -> Input file path { String }
    --help, -h -> Usage info
```

The default input file for a given day is expected at `./inputs/xx.txt`, where `xx` is the 2-character (0-padded) day number.
