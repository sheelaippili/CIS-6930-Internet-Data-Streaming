# Internet Data Streaming - Programming Assignment

## Project - Overview

I implemented the following - virtual bitmap and bSkt(HLL)

I implemented the project in java. Used python to generate graph for part 1.

## How to run the project

- Download the Project5.zip and unzip the file.
- Run the below commands on terminal to run the program
````
    FirstPart.java
    SecondPart.java
````
````
````

## Functions implemented

### Virtual Bitmap
- record the elements of all flows in array B of 500,000 bits; you may create elements as random numbers and you may record each element once or multiple times, which won’t make a difference. After recording, estimate the spread of each flow and plot a figure, where x-axis is the true spread and y-axis is the estimated spread. Each flow is represented by a point in the figure, whose x-coordinate is the flow’s actual spread and y-coordinate is the flow’s estimated spread. Set the range of x-axis to [0, 500].

### bSktHLL Bitmap
- record the elements of all flows in array A with 4,000 HLL estimators, each of which has 128 registers, each five bits long; each flow is hashed to and thus recorded in 3 estimators; you may create elements as random numbers and you may record each element once or multiple times, which won’t make a difference. After recording, estimate the spread of each flow and report the top-25 flows with the highest estimated speads.

