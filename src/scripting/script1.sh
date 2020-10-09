#!/bin/bash

cat s*.fasta > allSequences.fasta

grep ">" allSequences.fasta | grep -v ">.*HUMAN.*" > Result.txt
