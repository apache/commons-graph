package org.apache.commons.graph.model;

import org.apache.commons.graph.WeightedEdges;

public final class BaseWeightedEdge<W>
    implements WeightedEdges<BaseLabeledWeightedEdge<W>, W>
{

    public W getWeightForEdge( BaseLabeledWeightedEdge<W> edge )
    {
        return edge.getWeight();
    }

}
