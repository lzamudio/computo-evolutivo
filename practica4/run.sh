#!/bin/sh
clear
find . -type f  -name "*.class"  -exec rm -f {} \;
find . -type f -name "*.java" | xargs javac  

cd programa && java  Pruebas
