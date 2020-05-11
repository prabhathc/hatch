package com.hatchcard.prabhath;

import java.util.Spliterator;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

class CustomForEach {
 
    public static class Breaker {
        private boolean shouldBreak = false;
 
        public void stop() {
            shouldBreak = true;
        }
 
        boolean get() {
            return shouldBreak;
        }
    }
 
    
    /** 
     * @param stream accepts Stream which is iterated upon
     * @param consumer accepts BiConsumer, which includes a Breaker that is used to terminate
     * the iterator.
     */
    public static <T> void forEach(Stream<T> stream, BiConsumer<T, Breaker> consumer) {
        Spliterator<T> spliterator = stream.spliterator();
        boolean hadNext = true;
        Breaker breaker = new Breaker();
 
        while (hadNext && !breaker.get()) {
            hadNext = spliterator.tryAdvance(elem -> {
                consumer.accept(elem, breaker);
            });
        }
    }
}