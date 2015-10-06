#!/bin/sh
clear
find . -type f  -name "*.class"  -exec rm -f {} \;
find . -type f -name "*.java" | xargs javac -cp .:lib/robocode.jar  

java  -Xmx512M -DNOSECURITY=true  -cp .:lib/robocode.jar GeneraBatalla
