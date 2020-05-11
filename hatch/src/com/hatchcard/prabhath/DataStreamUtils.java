package com.hatchcard.prabhath;

import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

class DataStreamUtils {

    
    /** 
     * @return Supplier<Character>, an object which is accepted by Stream and used to generate random
     * characters
     */
    public static Supplier<Character> getSupplier() {
        Supplier<Character> randChar = () -> getRandChar();
        return randChar;
    }

    
    /** 
     * @return Character, a randomly generated character, either lowercase or uppercase english alphabet
     */
    private static Character getRandChar() {
        int upperOrLower = ThreadLocalRandom.current().nextInt(2);
        if (upperOrLower == 0) {
            // lowercase letters
            return (char) ThreadLocalRandom.current().nextInt(97, 123);
        } else {
            // uppercase letters
            return (char) ThreadLocalRandom.current().nextInt(65, 91);
        }
    }
}