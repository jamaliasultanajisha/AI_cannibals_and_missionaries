package net.coderodde.fun.cannibals;

import static net.coderodde.fun.cannibals.Utilities.checkIntNotLess;


class GameConfiguration {

    private static final int min_capacity = 1;
    private static final int min_total = 1;

    private final int boatCapacity;
    private final int totalMissionaries;
    private final int totalCannibals;

    GameConfiguration(int totalMissionaries, int totalCannibals, int boatCapacity) {

        this.boatCapacity = boatCapacity;
        this.totalCannibals = totalCannibals;
        this.totalMissionaries = totalMissionaries;

        checkBoatCapacity(boatCapacity);
        checkTotalCannibals(totalCannibals);
        checkTotalMissionaries(totalMissionaries);
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 173 * hash + this.totalMissionaries;
        hash = 173 * hash + this.totalCannibals;
        hash = 173 * hash + this.boatCapacity;
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GameConfiguration)) {
            return false;
        }

        GameConfiguration other = (GameConfiguration) o;

        return totalMissionaries == other.totalMissionaries && totalCannibals == other.totalCannibals && boatCapacity == other.boatCapacity;
    }

    int getTotalCannibals() {
        return totalCannibals;
    }

    int getBoatCapacity() {
        return boatCapacity;
    }

    int getTotalMissionaries() {
        return totalMissionaries;
    }

    private static void checkTotalCannibals(int totalCannibals) {
        checkIntNotLess(totalCannibals, min_total, "The total amount of cannibals is too small: " +
                totalCannibals +
                ". Should be at least " +
                min_total);
    }

    private static void checkBoatCapacity(int boatCapacity) {
        checkIntNotLess(boatCapacity, min_capacity, "Boat capacity too small: " +
                boatCapacity +
                ", " +
                "must be at least " +
                min_capacity + ".");
    }

    private static void checkTotalMissionaries(int totalMissionaries) {
        checkIntNotLess(totalMissionaries, min_total, "The total amount of missionaries is too small: " +
                        totalMissionaries +
                        ". Should be at least " +
                        min_total);
    }
}