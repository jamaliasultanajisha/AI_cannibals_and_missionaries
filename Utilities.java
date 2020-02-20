package net.coderodde.fun.cannibals;

class Utilities {

    static void checkIntNotLess(int integer, int minimum, String errorMessage) {
        if (integer < minimum) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    static void checkNotNegative(int integer, String errorMessage) {
        checkIntNotLess(integer, 0, errorMessage);
    }
}