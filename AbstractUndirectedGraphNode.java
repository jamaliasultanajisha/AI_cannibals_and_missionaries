package net.coderodde.fun.cannibals;

import java.util.List;

public interface AbstractUndirectedGraphNode <N extends AbstractUndirectedGraphNode<N>> {

    public List<N> children();
}