=================================================
| Assignment 4: MDPs and Reinforcement Learning |
=================================================
| Name  : Simon Hunt                            |
| GTid  : shunt31                               |
| Email : shunt31@gatech.edu                    |
=================================================



===================
This README file
===================

Can be found on GitHub:

  https://github.com/sdhunt/ML/tree/master/src/main/java/ml/project4/readme.txt



===================
Source Code
===================

Can be found on GitHub:

  https://github.com/sdhunt/ML/tree/master/src/main/java/ml/project4/



===================
Directory Structure
===================


augment/
  - contains augmented BURLAP classes
  |
  +-- AugmentedPolicyIteration          <-- See (1)
  +-- AugmentedQLearning                <-- See (1)
  +-- AugmentedValueIteration           <-- See (1)

  (1) provides access to iteration count for convergence to policy


grid/
  - contains Grid-World MDP code
  |
  +-- util/ ...                         <-- see (2)
  |
  +-- BasicGridWorld                    <-- see (2)
  +-- GridWorldMDP                      <-- Grid World MDP encapsulation
  +-- GridWorldProblem                  <-- Main launch point

  (2) Classes copied and/or modified from...
      https://github.com/juanjose49/omscs-cs7641-machine-learning-assignment-4


river/
  - contains River Problem MDP code
  |
  +-- RiverAnalysisRunner               <-- Runs VI, PI, and Q-Learning
  +-- RiverMDP                          <-- River Problem MDP encapsulation
  +-- RiverPrinter                      <-- Prints the results of a run
  +-- RiverProblem                      <-- Main launch point
  +-- RiverProblemDomainGenerator       <-- Encoded river problem domain


results/
  - contains run results output for posterity
  |
  +-- GW-run-50.txt                     <-- GridWorld run : prob = 50%
  +-- GW-run-75.txt                     <-- GridWorld run : prob = 75%
  +-- GW-run-99.txt                     <-- GridWorld run : prob = 99%
  +-- River-run-100.txt                 <-- River run : 100 QL iterations
  +-- River-run-1000.txt                <-- River run : 1000 QL iterations


===================
Installing the code
===================

Clone this repository.



================
Running the code
================

  It is assumed that you will be using an IDE (Eclipse, IntelliJ, ...) to
  examine the code. Furthermore, it is assumed that you will use the IDE
  to select and launch the main class for each of the experiments.

  Load and run the following classes:

    RiverProblem
    GridWorldProblem


======
