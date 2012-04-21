package org.apache.commons.graph.model;

import java.io.Serializable;

import org.apache.commons.graph.Mapper;

public final class BaseWeightedEdge<W>
    implements Mapper<BaseLabeledWeightedEdge<W>, W>, Serializable
{

    private static final long serialVersionUID = -2024378704087762740L;

    public W map( BaseLabeledWeightedEdge<W> edge )
    {
        return edge.getWeight();
    }

}
