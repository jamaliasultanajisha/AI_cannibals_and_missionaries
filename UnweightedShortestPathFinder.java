package net.coderodde.fun.cannibals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


public interface UnweightedShortestPathFinder <N extends AbstractUndirectedGraphNode<N>> {

    List<N> search(N source, Predicate<N> targetPredicate);

    default List<N> tracebackPath(N target, Map<N, N> parentMap) {
        List<N> ret = new ArrayList<>();
        N current = target;

        while (current != null) {
            ret.add(current);
            current = parentMap.get(current);
        }

        Collections.<N>reverse(ret);
        return ret;
    }
}
