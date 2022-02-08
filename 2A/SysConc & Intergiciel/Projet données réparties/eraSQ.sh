#!/bin/bash
if [ $# -eq 1 ]
then
  java linda.application.SequentielleEratosthene $1
else
  echo "Mettre en argument le nombre avec lequel on veut tester!"
fi

