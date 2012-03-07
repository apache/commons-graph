package org.apache.commons.graph.model;

import org.apache.commons.graph.Mapper;

public final class BaseWeightedEdge<W>
    implements Mapper<BaseLabeledWeightedEdge<W>, W>
{

    public W map( BaseLabeledWeightedEdge<W> edge )
    {
        return edge.getWeight();
    }

}
