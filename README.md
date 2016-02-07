# CS 7641 : Machine Learning
## Assignment 1: Supervised Learning

Simon Hunt (gtid = _shunt31_)

# Environment
The following assumptions are made about your environment:

* Java JDK 1.7 or 1.8
* Apache Maven 3.3.9 
* Access to the Internet

The software (and data files) written for this assignment can be 
downloaded with the following command:
 
 `git clone https://github.com/sdhunt/ML.git`


I use JetBrains IntelliJ IDEA for my development environment. It manages
the library dependencies using the Maven configuration file (`pom.xml`)
and sets up the appropriate class path for running the main java classes.

I have not provided a shell script to correctly invoke the Java Code; I am
assuming that you will use an IDE to make modifications to the code
and from which you will run the experiments.


# Project Structure

## Source Code

The source is divided up into three sub-projects:

+ *fetcher* - Code to access the BGG XML API to pull raw game data and write it
  to local disk in JSON format. 
   
+ *bggxform* - Code to transform the raw JSON game data into ARFF file format, 
   suitable for using with the WEKA libraries.
   
+ *wekarun* - Code to configure and run various WEKA experiments.


### fetcher

This code was written to access the XML API provided at the _Board Game Geek_
website, in order to pull interesting information about Board Games, and 
store that data locally, formatted in JSON files.

See [BGG's XML API](http://boardgamegeek.com/xmlapi)


### bggxform

This code generates ARFF files from the raw game data stored in JSON files.

A few attributes of each game were chosen. Since games could belong to 
multiple subdomains, the list of subdomains was converted to a set of 
8 boolean attributes. Finally, the class (game weight) was discretized from
the float value assigned, by matching the nearest value:

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

CSV generators were also implemented to allow the results of experiments
to be written out to CSV files, suitable for importing directly into a 
spreadsheet, so that results could be plotted in charts.

## Data

The data directory contains the input ARFF files, as well as output from
runs (both CSV files useful for importing into a spreadsheet, and the
text output from the run).

* *bgg* - a place-holder directory (this is where the raw JSON files would be)
* *bgg-extra* - some scripts for processing subdomain and mechanic data
* *bgg-out* - the BGG `.arff` data files
* *results* - both generated and manual notes about runs stored here
* *samples* - some sample raw data files
* *uci* - the UCI datasets as distributed with the WEKA code


# Designing, Configuring and Running Experiments

## WekaExperiment

An _experiment_ is **designed** by implementing a subclass of `WekaExperiment`.
For example, see [BggReducedDataExperiment](src/main/java/com/meowster/omscs/ml/wekarun/BggReducedDataExperiment.java).

The concrete class defines which directory any CSV results should be written
into, as well as providing any required pre-processing of instance data
read from ARFF files.

The superclass [WekaExperiment](src/main/java/com/meowster/omscs/ml/wekarun/WekaExperiment.java)
implements the `run()` method to execute the configured experiment.

## DateFile, Classifier, and Filter Groups

An experiment operates on defined groups of datafiles, classifiers and filters.
Concrete classes define the different groupings required for the experiments
by implementing subclasses of 
[DataFileGroup](src/main/java/com/meowster/omscs/ml/wekarun/config/DataFileGroup.java),
[ClassifierGroup](src/main/java/com/meowster/omscs/ml/wekarun/config/ClassifierGroup.java), and
[FilterGroup](src/main/java/com/meowster/omscs/ml/wekarun/config/FilterGroup.java)
respectively.

See the following examples:

* [LearningBgWeight10k](src/main/java/com/meowster/omscs/ml/wekarun/config/bgg/LearningBgWeight10k.java) - BGG data files containing 1,000 to 10,000 instances
* [VaryingIBk](src/main/java/com/meowster/omscs/ml/wekarun/config/VaryingIBk.java) - IBk (k-NN) classifier with varying values of _k_
* [VaryingRemovePercentFilterGroup](src/main/java/com/meowster/omscs/ml/wekarun/config/VaryingRemovePercentFilterGroup.java) - RemovePercent filter with varying values to remove

Each of these groups are iterated over in the experiment _run()_ method.

## MainWekaRun

To actually run an experiment, the [MainWekaRun](src/main/java/com/meowster/omscs/ml/wekarun/MainWekaRun.java) 
_main()_ method should be modified to instantiate the appropriate groups,
 instantiate a `WekaExperiment` subclass parameterized with those groups, 
 and finally invoke the _run()_ method on the experiment.
  

# Final Notes

The complete set of raw JSON data files have not been included in this repo.

* They may be added, at some future date
* A few sample files have been included in the `data/samples` directory


Finally... _Have fun training classifiers..._