# Internet Data Streaming - Project 3

## Project - Overview

The idea is to implement CountMin, CounterSketch and ActiveCounters.

## How to run the project

- Download the Project3.zip and unzip the file.
- Run the below command to compile the 3 counters implemented.
````
    javac CountMin.java
    javac CounterSketch.java
    javac ActiveCounters.java
````
- Run the below command to run the executable java files of the 3 counters implemented.
````
    java CountMin
    java CounterSketch
    java ActiveCounters
````

## Implementation of the counter techniques

### CountMin
- Input: Reads in the project3input.txt file, the number of packets in the flow (n), number of counter arrays (k) and number of counters in each array (w) are provided in the code as n = 10,000, k = 3, w = 3000
- Output: Average error among all flows, the flows for each line with the biggest projected sizes, together with the flow id, estimated size, and real size

### CounterSketch
- Input: Reads in the project3input.txt file, the number n of flows, which is followed by n lines, each for a flow, containing its flow id (source address) and the  number  of  packets  in  the  flow (n), number  of  counter  arrays (k), number  of counters in each array(w) are provided in code as n = 10,000, k = 3, w = 3000  
- Output: Average error among all flows, the flows for each line with the biggest projected sizes, together with the flow id, estimated size, and real size

### ActiveCounter
- Input: the number part of the counter is 16 bits, and the exponent part of the counter is also 16 bits. 
- Output: Final value of the active counter in decimal   


