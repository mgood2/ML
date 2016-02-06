# Data Directory

## bgg 

This directory would normally contain a number of subdirectories, 
k00, k01, k02, ... in which are stored JSON files containing data
for the first, second, third, "thousand" games pulled from BGG.

At some point, I will add a zip file of this data; you will have to 
expand it locally if you want to see the raw data.

## bgg-extra

This directory contains extra scripts to generate the unique
subdomains and game mechanics from the raw JSON data files.

## bgg-out

This is where the transformed BGG data is written as .arff files.

## results

This is where output files from the WEKA runs will be written

## samples

Some sample data files in here.
