package com.hatchcard.prabhath;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

class Worker implements Callable<List<String>> {

    private long startTime;
    private int byteCount;
    private String status;
    private String goal;
    private char[] goalArr;
    private int check;
    private List<String> result;
    private int timeLimit = 60000;

    public Worker() {
        this.startTime = System.currentTimeMillis();
        this.byteCount = 0;
        this.status = "ALIVE";
        this.goal = "FiCo";
        this.goalArr = goal.toCharArray();
        this.check = 0;
        this.result = new ArrayList<>();
    }

    public Worker(int timeLimit) {
        this();
        this.timeLimit = timeLimit;
    }
    
    
    /** 
     * @return List<String>, in the format <ELAPSEDTIME, BYTESREAD, STATUS> if goal String is found,
     * or <STATUS> if thread times out or throws and error
     * @throws InterruptedException if thread is interrupted
     */
    public List<String> call() throws InterruptedException {
        // try to execute thread
        try {
            // create a stream and retrieve supplier from DataStreamUtils
            Stream<Character> stream = Stream.generate(DataStreamUtils.getSupplier());
            // use customForEach to process each character from the stream
            CustomForEach.forEach(stream, (x, breaker) -> {
                // increment counter that keeps track of number of bytes read
                byteCount++;
                // check if thread has surpassed time limit
                if (System.currentTimeMillis() - startTime <= timeLimit) {
                    // if character from stream is equal to the current char being evaluated in the goal string,
                    // increment index to evaluate next character in goal string, if not, reset index to 0
                    if (x.equals(goalArr[check])) {
                        check++;
                    } else {
                        check = 0;
                    }
                    // if index has reached the length of the goal string, meaning the goal string has been found,
                    // set status to SUCCESS, calculate elapsed time, and store status, elapsedTime, byteCount into
                    // an Arraylist and stop the forEach
                    if (check == goalArr.length) {
                        status = "SUCCESS";
                        long elapsedTime = System.currentTimeMillis() - startTime;
                        result.add(String.valueOf(elapsedTime));
                        result.add(String.valueOf(byteCount));
                        result.add(status);
                        breaker.stop();
                    }
                } else {
                    // if thread has surpassed time limit, set status to TIMEOUT and stop forEach
                    status = "TIMEOUT";
                    result.add(status);
                    breaker.stop();
                }
            });
            // return collected information
            return result;
        } catch (Exception e) {
            // if thread could not complete, print thread information + stack trace, set status to ERROR
            // and return result
            e.printStackTrace();
            result.clear();
            status = "FAILURE";
            result.add(status);
            return result;
        }
    }
}