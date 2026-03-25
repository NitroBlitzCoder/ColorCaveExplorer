# DumbBot Cave Explorer

A Java program that navigates serialized cave mazes using a random-walk algorithm. Logs every move, validates the path by retracing it backwards, and benchmarks across 9 mazes.

## What it does

- Randomly picks doors room by room until it finds the exit
- Records every door and room visited along the way
- Validates the result by replaying the path in reverse back to start
- Runs across 9 maze files and prints a full breakdown of total moves vs actual path length

## Sample output
```
Found the End!
steps = 312 | path length = 47
PATH: START -> N -> E -> E -> S -> ... -> END
valid = true

Total moves between Rooms  = 2847
Total length of all paths  = 381
Total used to Explore      = 2466
```

## Built with

- Java
- ArrayList for path tracking
- Graph-based Room/Door model
- Java serialization for loading cave files

## Setup

Drop your `.ser` maze files into `CaveData/`, then:
```bash
javac src/*.java
java -cp src DumbBot
```
