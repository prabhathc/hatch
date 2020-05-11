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
    echo -e "\e[32m$ sh prabhath.sh -c                \e[39mto compile"
    echo -e "\e[32m$ sh prabhath.sh -r TIMELIMIT      \e[39mto run, where TIMELIMIT (in ms) is optional"
    echo -e "\e[32m$ sh prabhath.sh -cr TIMELIMIT     \e[39mto compile and run, where TIMELIMIT (in ms) is optional"
    echo -e "\e[32m$ sh prabhath.sh -h                \e[39m(for help)"
    echo ""
    echo "This program outputs a single line of information for each thread, and final line that analyzes performance."
    echo "The output is in descending order by ELAPSED_TIME."
    echo "Each thread's output is printed in one of the following formats: [ELAPSED_TIME, TOTAL_BYTES_READ, STATUS] or [STATUS]"
    echo "The status SUCCESS indicates the thread succesfully discovered \"FiCo\" within the time limit."
    echo "The status TIMEOUT indicates the thread could not discover \"FiCo\" within the time limit."
    echo "The status FAILURE indicates the thread could not execute properly, error messages are sent to stderr."
    echo "The final line that is printed indicates amount of bytes read per millisecond."
else
    echo -e "Command unavailable, try \e[32m$ sh prabhath.sh -h \e[39mfor help."
fi