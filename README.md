
# Hatch Coding Exercise 

## What does this program do?
- This program runs 10 threads that concurrently process an infinite stream of random characters in search of the string ```"FiCo"```.
- The program accepts a ```TIMELIMIT``` argument which is used to limit the amount of time the aforementioned threads conduct their search.
- If no ```TIMELIMIT``` argument is specified, it defaults to 60000 milliseconds (1 minute).

## What does the output mean?
- This program outputs a single line of information per thread created. It also outputs one final line that analyzes performance.
- Each thread's output is printed in one of the following formats: ```[ELAPSED_TIME, TOTAL_BYTES_READ, STATUS]``` or ```[STATUS]```
- The output is in descending order by ```ELAPSED_TIME```.
- The status ```SUCCESS``` indicates the thread succesfully discovered ```"FiCo"``` within the time limit.
- The status ```TIMEOUT``` indicates the thread could not discover ```"FiCo"``` within the time limit.
- The status ```FAILURE``` indicates the thread could not execute properly. Error messages are sent to ```stderr```.
- The final line of output indicates amount of bytes read per millisecond.

## Instructions
- After extracting contents of hatch.zip, open a shell and navigate into the ```./hatch``` directory.
- Use  ```$ sh prabhath.sh -h``` to recieve a list of commands and general info regarding the program and what its output means.

## Code
- The ```.java``` files can be found in the ```./hatch/src/com/hatchcard/prabhath``` directory.
- The ```.class``` files can be found in the ```./hatch/bin/com/hatchcard/prabhath``` directory.
- The ```Boss``` class is the main class and entry point of the program, it spawns each ```Worker```.
- The ```Worker``` class creates an infinite stream of random characters (using ```DataStreamUtils```) and iterates through each character in the stream using a ```CustomForEach``` iterator.
