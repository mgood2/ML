# CS 7641 : Machine Learning
## Assignment 1: Supervised Learning

Simon Hunt (gtid = _shunt31_)

# Environment
The following assumptions are made about your environment:

* Java JDK 1.7 or 1.8
* Apache Maven 3.3.9 
* Access to the Internet

Do a `git clone https://github.com/sdhunt/ML.git` to download the code 
and data files to your local machine.

I use JetBrains IntelliJ IDEA for my development environment. It manages
the library dependencies using the Maven configuration file (`pom.xml`)
and sets up the appropriate class path for running the main java classes.

I have not provided a shell script to correctly invoke the Java Code; it
is assumed that you will be using an IDE to make modifications to the code
and from which to run the experiments.


# Project Structure

## Source Code

The source is divided up into three sub-projects:

+ *fetcher* - Code to access the BGG XML API to pull raw game data and write it
  to local disk in JSON format. 
   
+ *bggxform* - Code to transform the raw JSON game data into .arff file format, 
   suitable for using with the WEKA libraries.
   
+ *wekarun* - Code to run WEKA experiments.


### fetcher

This code was written to access the XML API provided at the _Board Game Geek_
website, in order to pull interesting information about Board Games, and 
store that data locally, formatted in JSON files.


### bggxform

This code generates .arff files from the raw game data stored in JSON files.
A few attributes of each game were chosen. Since games could belong to 
multiple subdomains, the list of subdomains was converted to a set of 
8 boolean attributes. Finally, the class (game weight) was discretized from
the float value assigned:

* light = 1.0
* medium_light = 2.0
* medium = 3.0
* medium_heavy = 4.0
* heavy = 5.0

Instance attributes were assigned as follows:

1.  game identifier
2.  minimum players
3.  maximum players
4.  typical duration (in minutes)
5.  subdomain: abstract game
6.  subdomain: childrens game
7.  subdomain: customizable game
8.  subdomain: family game
9.  subdomain: party game
10. subdomain: strategy game
11. subdomain: thematic game
12. subdomain: wargame
13. (no subdomain)
14. class (Game Weight):
    - LIGHT
    - MEDIUM_LIGHT
    - MEDIUM
    - MEDIUM_HEAVY
    - HEAVY"

### wekarun

This code was written to facilitate running experiments using the WEKA 
library. Java classes were designed to make configuring experiments as
easy as possible, and yet still allowing full scope of tweaking options
to the WEKA classifier implementations.

## Data

The data directory contains the input `.arff` files, as well as output from
runs (both `.csv` files useful for importing into a spreadsheet, and the
text output from the run).

* *bgg* - a place-holder directory (this is where the JSON files would be)
* *bgg-extra* - some scripts for processing subdomain and mechanic data
* *bgg-out* - the BGG `.arff` data files
* *results* - both generated and manual notes about runs
* *samples* - some sample raw data files
