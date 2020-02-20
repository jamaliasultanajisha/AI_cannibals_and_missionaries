package net.coderodde.fun.cannibals;
import java.util.List;
import java.util.Scanner;

import static java.lang.System.exit;

public class MissionAndConDep {
    public static void main(String[] args) {
        UnweightedShortestPathFinder<StateNode> finder = new DepthFirstSearchPathFinder<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of missionaries: ");
        int missionaries = sc.nextInt();
        System.out.println("Enter number of cannibals: ");
        int cannibals = sc.nextInt();
        System.out.println("Enter capacity of boat: ");
        int boatcap = sc.nextInt();

        List<StateNode> path = null;

        long startTime = System.currentTimeMillis();
        long boundTime = startTime + 30000;

        while(System.currentTimeMillis() <= boundTime){
            int flag = 1;
            /*List<StateNode>*/ path =
                    finder.search(StateNode.getInitialStateNode(missionaries, cannibals, boatcap),
                            (StateNode node) -> {
                                return node.isSolutionState();
                            });
            if(StateNode.getCounter() > 1000000){
                System.out.println("States generated: " + StateNode.getCounter());
                System.out.print("Exceed contraint of nodes\nNo solution\n");
                exit(1);
            }
            flag++;
            if (flag > 1) {
                break;
            }
        }

        if (System.currentTimeMillis() > boundTime) {
            System.out.print("Exceed constraint time\nNo solution\n");
            exit(1);
        }

        long endTime = System.currentTimeMillis();

        System.out.println("Duration: " + (endTime - startTime) +
                " milliseconds.");

        int fieldLength = ("" + path.size()).length();

        if (path.isEmpty()) {
            System.out.println("No solution.");
        } else {
            int i = 0;

            for (StateNode node : path) {
                System.out.printf("State %" + fieldLength + "d: %s\n",
                        i++,
                        node);
            }
        }

        System.out.println("States generated: " + StateNode.getCounter());
    }
}
