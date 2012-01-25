package org.apache.commons.graph.spanning;

import static org.apache.commons.graph.utils.Assertions.checkNotNull;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.WeightedEdge;

public final class DefaultSpanningTreeSourceSelector<V extends Vertex, W, WE extends WeightedEdge<W>, G extends Graph<V, WE>>
    implements SpanningTreeSourceSelector<V, W, WE, G>
{

    private final G graph;

    public DefaultSpanningTreeSourceSelector( G graph )
    {
        this.graph = graph;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpanningTreeAlgorithmSelector<V, W, WE, G> fromArbitrarySource()
    {
        return fromSource( graph.getVertices().iterator().next() );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public SpanningTreeAlgorithmSelector<V, W, WE, G> fromSource( V source )
    {
        source = checkNotNull( source, "Spanning tree cannot be calculated without expressing the source vertex" );
        return new DefaultSpanningTreeAlgorithmSelector<V, W, WE, G>( graph, source );
    }

}
