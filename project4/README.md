# Internet Data Streaming - Programming Assignment

## Project - Overview

I implemented the following single-flow spread sketches

- Bitmap
- Probabilistic Bitmap
- Hyper Log Log 

I implemented the project in java

## How to run the project

- Download the Project4.zip and unzip the file.
- Run the below commands on terminal to run the program
````
    javac Project4.java
````
````
    java Project4
````


## Functions implemented

### Bitmap
- Input: number m of bits in the bitmap --- for demo, m = 10,000.
- 5 flows with spreads of 100, 1,000, 10,000, 100,000, and 1,000,000 are recorded one flow at a time by the bitmap. Reset all bits to zero before recording each flow, and then used the formula taught in class to calculate the flow spread after recording. Displayed the true size and estimated size for each spread size.
### Probabilistic Bitmap
- number m of bits in the bitmap and sampling probability p --- for demo, m = 10,000, p = 0.1
- 5 flows with spreads of 100, 1,000, 10,000, 100,000, and 1,000,000 are recorded one flow at a time by the bitmap. Reset all bits to zero before recording each flow, and then used the formula taught in class to calculate the flow spread after recording. Displayed the true size and estimated size for each spread size.
### HyperLogLog
- number m registers used by HLL, each of 5 bits --- for demo, m = 256
- Four flows with spreads of 1,000, 10,000, 100,000, and 1,000,000 were recorded by HLL one flow at a time. Reset all registers to zero before recording each flow, and then used the formula taught in class to calculate the flow spread after recording. Displayed the true size and estimated size for each spread size.
