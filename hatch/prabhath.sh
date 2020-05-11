#!/bin/bash
ARG=$1
TIMELIMIT=$2

if [ "$ARG" == "-c" ]; then
    echo "compiling..."
    javac -d ./bin ./src/com/hatchcard/prabhath/*.java
elif [ "$ARG" == "-r" ]; then
    java -cp ./bin com.hatchcard.prabhath.Boss ${TIMELIMIT}
elif [ "$ARG" == "-cr" ]; then
    javac -d ./bin ./src/com/hatchcard/prabhath/*.java
    java -cp ./bin com.hatchcard.prabhath.Boss ${TIMELIMIT}
elif [ "$ARG" == "-h" ]; then
    echo ""
    echo -e "\e[32m$ bash prabhath.sh -c                \e[39mto compile"
    echo -e "\e[32m$ bash prabhath.sh -r TIMELIMIT      \e[39mto run, where TIMELIMIT (in ms) is optional"
    echo -e "\e[32m$ bash prabhath.sh -cr TIMELIMIT     \e[39mto compile and run, where TIMELIMIT (in ms) is optional"
    echo -e "\e[32m$ bash prabhath.sh -h                \e[39m(for help)"
else
    echo -e "Command unavailable, try \e[32m$ sh prabhath.sh -h \e[39mfor help."
fi