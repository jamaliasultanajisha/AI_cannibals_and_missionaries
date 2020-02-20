package net.coderodde.fun.cannibals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import static net.coderodde.fun.cannibals.Utilities.checkIntNotLess;
import static net.coderodde.fun.cannibals.Utilities.checkNotNegative;


public class StateNode implements AbstractUndirectedGraphNode<StateNode> {

    private static int counter;

    public enum BoatLocation {

        SOURCE_BANK,
        TARGET_BANK
    }

    private final int missionaries;

    private final int cannibals;

    private final GameConfiguration configuration;

    private final BoatLocation boatLocation;

    public StateNode(int missionaries, int cannibals, int totalMissionaries, int totalCannibals, int boatCapacity, BoatLocation boatLocation)
    {
        Objects.requireNonNull(boatLocation, "Boat location is null.");
        checkCannibalCount(cannibals, totalCannibals);
        checkMissionaryCount(missionaries, totalMissionaries);

        this.cannibals = cannibals;
        this.missionaries = missionaries;
        this.boatLocation = boatLocation;
        this.configuration = new GameConfiguration(totalMissionaries, totalCannibals, boatCapacity);

        StateNode.counter++;
    }

    StateNode(int missionaries, int cannibals, BoatLocation boatLocation, GameConfiguration configuration)
    {
        this.missionaries = missionaries;
        this.cannibals = cannibals;
        this.boatLocation = boatLocation;
        this.configuration = configuration;

        StateNode.counter++;
    }

    public static StateNode getInitialStateNode(int totalMissionaries, int totalCannibals, int boatCapacity)
    {
        return new StateNode(totalMissionaries, totalCannibals, totalMissionaries, totalCannibals, boatCapacity, BoatLocation.SOURCE_BANK);
    }

    public static int getCounter() {
        return counter;
    }

    public boolean isSolutionState() {
        return boatLocation == BoatLocation.TARGET_BANK && missionaries == 0 && cannibals == 0;
    }

    public boolean isTerminalState() {
        if (missionaries > 0 && missionaries < cannibals) {
            return true;
        }

        int missionariesAtTargetBank = configuration.getTotalMissionaries() -
                missionaries;
        int cannibalsAtTargetBank = configuration.getTotalCannibals() -
                cannibals;

        if (missionariesAtTargetBank > 0 && missionariesAtTargetBank < cannibalsAtTargetBank) {
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        int missionaryFieldLength = ("" + configuration.getTotalMissionaries())
                .length();

        int cannibalFieldLength = ("" + configuration.getTotalCannibals())
                .length();

        sb.append(String.format("[m: %" + missionaryFieldLength + "d",
                missionaries));

        sb.append(String.format(", c: %" + cannibalFieldLength + "d]",
                cannibals));

        switch (boatLocation) {
            case SOURCE_BANK: {
                sb.append("v ~~~  ");
                break;
            }

            case TARGET_BANK: {
                sb.append("  ~~~ v");
                break;
            }
        }

        sb.append(String.format("[m: %" + missionaryFieldLength + "d",
                configuration.getTotalMissionaries() - missionaries));

        sb.append(String.format(", c: %" + cannibalFieldLength + "d]",
                configuration.getTotalCannibals() - cannibals));

        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 13;
        hash = 179 * hash + this.missionaries;
        hash = 179 * hash + this.cannibals;
        hash = 179 * hash + Objects.hashCode(this.configuration);
        hash = 179 * hash + Objects.hashCode(this.boatLocation);
        return hash;
    }

    @Override
    public boolean equals(Object ob) {
        if (!(ob instanceof StateNode)) {
            return false;
        }

        StateNode other = (StateNode) ob;
        return missionaries == other.missionaries
                && cannibals == other.cannibals
                && boatLocation == other.boatLocation
                && configuration.equals(other.configuration);
    }

    @Override
    public List<StateNode> children() {
        if (isTerminalState()) {
            return Collections.<StateNode>emptyList();
        }

        List<StateNode> ret = new ArrayList<>();

        switch (boatLocation) {
            case SOURCE_BANK: {
                trySendFromSourceBank(ret);
                break;
            }

            case TARGET_BANK: {
                trySendFromTargetBank(ret);
                break;
            }
        }

        return ret;
    }

    private void trySendFromSourceBank(List<StateNode> list) {
        int boatCapacity = configuration.getBoatCapacity();
        int availableMissionaries = Math.min(missionaries, boatCapacity);

        for (int m = 0; m <= availableMissionaries; ++m) {
            for (int c = ((m == 0) ? 1 : 0), availableCannibals = Math.min(cannibals, boatCapacity - m);
                 c <= availableCannibals; ++c)
            {
                list.add(new StateNode(missionaries - m, cannibals - c,
                        BoatLocation.TARGET_BANK, configuration));
            }
        }
    }

    private void trySendFromTargetBank(List<StateNode> list) {

        int boatCapacity = configuration.getBoatCapacity();
        int availableCannibals = Math.min(configuration.getTotalCannibals() - cannibals, boatCapacity);
        int availableMissionaries = Math.min(configuration.getTotalMissionaries() - missionaries, boatCapacity);

        for (int m = 0; m <= availableMissionaries; ++m) {
            for (int c = ((m == 0) ? 1 : 0), cend = Math.min(availableCannibals, boatCapacity - m);
                 c <= availableCannibals; ++c)
            {
                list.add(new StateNode(missionaries + m, cannibals + c,
                        BoatLocation.SOURCE_BANK, configuration));
            }
        }
    }

    private static void checkMissionaryCount(int missionaries,
                                             int totalMissionaries) {
        checkNotNegative(missionaries, "Negative amount of missionaries: " + missionaries);

        checkIntNotLess(totalMissionaries, missionaries, "Missionaries at a bank (" + missionaries + "), " +
                        "missionaries in total (" + totalMissionaries + ").");
    }

    private static void checkCannibalCount(int cannibals, int totalCannibals) {
        checkNotNegative(cannibals, "Negative amount of cannibals: " + cannibals);

        checkIntNotLess(totalCannibals, cannibals, "Cannibals at a bank (" + cannibals + "), " +
                        "cannibals in total (" + totalCannibals + ").");
    }

}
