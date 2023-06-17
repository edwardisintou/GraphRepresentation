# CITS2200 Data Structures and Algorithms

## Project 1: Graph Representation
Author 1: Edward Le\
Author 2: Ryan Alexander Hartono

Due: Wednesday, May 24, 2023, 11:59pm\
This project can be done in pairs or individual as prefer with report explaining clearly all method used and complexity in pdf format.

This project is a multi-part project, which means that it comprises several questions. The topic of this project is to analyze parts of the Wikipedia graph by creating a class that can store and analyze various features of a Wikipedia page graph. This class should implement interface CITS22000Project. Wikipedia contains numerous pages on varied topics. These pages may link to other pages inside Wikipedia. If we treat pages as vertices, and links as directed edges, then Wikipedia can be viewed as a graph. We will not be dealing with the entire Wikipedia page graph, as it is too large. Instead, the class is expected to work with arbitrarily selected subsets of pages along with the links between these pages.

## Question 1
Write a method that, given a pair of pages, returns the minimum number of links required to get from the first page to the second.

## Question 2
Write a method that finds a Hamiltonian path in a Wikipedia page graph. A Hamiltonian path is any path in some graph that visits every vertex exactly once. This method will never be called for graphs with more than 20 pages.

## Question 3
Write a method that finds every ‘strongly connected component’ of pages. A strongly connected component is a set of vertices such that there is a path between every ordered pair of vertices in the strongly connected component.

## Question 4
Write a method that finds all the centers of the Wikipedia page graph. A vertex is considered to be the center of a graph if the maximum shortest path from that vertex to any other vertex is the minimum possible.

## Testing Data
The code should be tested thoroughly with own test data. A small sample test graph provided is named "example_graph1". Another own test data made to specially test method getHamiltonianPath is named "example_graph2".

## Grade Contribution
This project worths 20% of the final grade. The marking scheme is as follows:
1. The marks for individual questions are, Q1 (3 marks), Q2 (7 marks), Q3 (6 marks), Q4 (4 marks)
2. The report should contain the pseudo codes or descriptions/source of all the algorithms that are used and also analysis their complexity, some performance studies on different size graphs.
3. Correct implementations of algorithms will get 70% of the marks, design/use/explain efficient algorithms are marked as well.

## Clarifications and Explanations
Detailed clarifications and explanations can be found in file "Explanations".