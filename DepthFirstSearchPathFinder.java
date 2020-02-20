package net.coderodde.fun.cannibals;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.*;

public class DepthFirstSearchPathFinder<N extends AbstractUndirectedGraphNode<N>>
        implements UnweightedShortestPathFinder<N> {

    @Override
    public List<N> search(N source, Predicate<N> targetPredicate) {
        Objects.requireNonNull(source, "The source node is null.");
        Objects.requireNonNull(targetPredicate,
                "The target predicate is null.");

        Map<N, N> parentMap = new HashMap<>();
        Stack<N> stack = new Stack<>();

        parentMap.put(source, null);
        stack.push(source);

        while (!stack.isEmpty()) {
            N current = stack.pop();

            if (targetPredicate.test(current)) {
                return tracebackPath(current, parentMap);
            }

            for (N child : current.children()) {
                if (!parentMap.containsKey(child)) {
                    parentMap.put(child, current);
                    stack.push(child);
                }
            }
        }

        return Collections.<N>emptyList();
    }

}