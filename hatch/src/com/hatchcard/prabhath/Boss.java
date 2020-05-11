package com.hatchcard.prabhath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

class Boss {

    
    /** 
     * @param args commandline args, this method accepts an optional timelimit argument, must be an integer
     * @throws InterruptedException if thread is interrupted
     * @throws ExecutionException if results cannot be retrived from thread
     * @throws NumberFormatException if optional timelimit argument is not a valid integer
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException, NumberFormatException {  
        // set default time limit.
        int timeLimit = 60000;
        // check if argument exists, verify it is an integer and if possible, set timeLimit to this argument
        if (args.length > 0) {
            try {
                timeLimit = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                System.err.println("-------------------------------------------");
                System.err.println("TIMELIMIT argument was not a valid integer.");
                System.err.println("-------------------------------------------");
                System.exit(1);
            }
        }
        // start up an ExecutorService with a pool of 10 threads and add 10 workers to a taskList
        ExecutorService service = Executors.newFixedThreadPool(10);
        List<Callable<List<String>>> taskList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            taskList.add(new Worker(timeLimit));
        }
        // try to run 10 threads concurrently
        try {
            List<Future<List<String>>> results = service.invokeAll(taskList);
            List<List<String>> finalOutput = new ArrayList<>();
            // retrieve results from Future objects
            for (Future<List<String>> result : results) {
                finalOutput.add(result.get());
            }
            // sort results in descending order
            Collections.sort(finalOutput, new Comparator<List<String>>() {
                public int compare(List<String> a, List<String> b) {
                    if (a.size() < 3) {
                        return -1;
                    } else if (b.size() < 3) {
                        return 1;
                    } else {
                        return Integer.parseInt(b.get(0)) - Integer.parseInt(a.get(0));
                    }
                }
            });
            // print results to stdout, and while doing so calculate average bytes read per ms
            double bps = 0;
            int numSuccess = 0;
            for (List<String> result : finalOutput) {
                if (result.size() > 1) {
                    numSuccess++;
                    bps += (Integer.parseInt(result.get(1)) / Integer.parseInt(result.get(0)));
                } 
                System.out.println(result);
            }
            // print final line, average bytes read per ms
            System.out.println("Average bytes read per ms: " + bps/numSuccess);

        } catch (InterruptedException e) {
            System.err.println("-----------------------");
            System.err.println("Thread was interrupted.");
            System.err.println("-----------------------");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.err.println("------------------------------------");
            System.err.println("Could not retrieve result from task.");
            System.err.println("------------------------------------");
        }
        // shutdown service
        service.shutdownNow();
    }
}