#!/bin/bash

rm -rf linda/server/*.class
rm -rf linda/application/*.class
rm -rf linda/shm/*.class
rm -rf linda/*.class
rm -rf linda/search/basic/*.class
rm -rf linda/test/*.class

javac -cp $(pwd):. linda/*.java
javac -cp $(pwd):. linda/application/*.java
javac -cp $(pwd):. linda/shm/*.java
javac -cp $(pwd):. linda/server/*.java
javac -cp $(pwd):. linda/search/basic/*.java
javac -cp $(pwd):. linda/test/TestPerformance.java
javac -cp $(pwd):. linda/test/PerformanceErastosthene.java
