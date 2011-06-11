package org.apache.commons.graph.algorithm.dataflow;

import java.util.BitSet;

import org.apache.commons.graph.*;

public interface DataFlowEquations {
    /**
     * This method shows when a definition is defined.
     */
    public BitSet generates( Vertex vertex );

    /**
     * This method shows when a definition is killed (or overwritten.)
     */
    public BitSet kills( Vertex vertex );
}
