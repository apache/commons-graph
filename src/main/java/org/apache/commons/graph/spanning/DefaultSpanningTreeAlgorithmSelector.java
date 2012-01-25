package org.apache.commons.graph.spanning;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.Vertex;
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.collections.DisjointSet;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.weight.OrderedMonoid;

final class DefaultSpanningTreeAlgorithmSelector<V extends Vertex, W, WE extends WeightedEdge<W>, G extends Graph<V, WE>>
    implements SpanningTreeAlgorithmSelector<V, W, WE, G>
{

    private final G graph;

    private final V source;

    public DefaultSpanningTreeAlgorithmSelector( G graph, V source )
    {
        this.graph = graph;
        this.source = source;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <OM extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingBoruvkaAlgorithm( OM orderedMonoid )
    {
        // TODO Boruvka still needs to be implemented
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <OM extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingKruskalAlgorithm( OM orderedMonoid )
    {
        final Set<V> settledNodes = new HashSet<V>();

        final PriorityQueue<WE> orderedEdges = new PriorityQueue<WE>( graph.getSize(),
                                                                      new WeightedEdgesComparator<W, WE>( orderedMonoid ) );

        for ( WE edge : graph.getEdges() )
        {
            orderedEdges.add( edge );
        }

        final DisjointSet<V> disjointSet = new DisjointSet<V>();

        MutableSpanningTree<V, WE, W> spanningTree = new MutableSpanningTree<V, WE, W>( orderedMonoid );

        while ( settledNodes.size() < graph.getOrder() )
        {
            WE edge = orderedEdges.remove();

            VertexPair<V> vertices = graph.getVertices( edge );
            V head = vertices.getHead();
            V tail = vertices.getTail();

            if ( settledNodes.add( head ) )
            {
                spanningTree.addVertex( head );
            }
            if ( settledNodes.add( tail ) )
            {
                spanningTree.addVertex( tail );
            }

            if ( !disjointSet.find( head ).equals( disjointSet.find( tail ) ) )
            {
                disjointSet.union( head, tail );
                spanningTree.addEdge( head, edge, tail );
            }
        }

        return spanningTree;
    }

    private static class WeightedEdgesComparator<W, WE extends WeightedEdge<W>> implements Comparator<WE>
    {

        private final OrderedMonoid<W> orderedMonoid;

        public WeightedEdgesComparator( OrderedMonoid<W> orderedMonoid )
        {
            this.orderedMonoid = orderedMonoid;
        }

        public int compare( WE o1, WE o2 )
        {
            return orderedMonoid.compare( o1.getWeight(), o2.getWeight() );
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <OM extends OrderedMonoid<W>> SpanningTree<V, WE, W> applyingPrimAlgorithm( OM orderedMonoid )
    {
        final ShortestEdges<V, WE, W> shortestEdges = new ShortestEdges<V, WE, W>( graph, source, orderedMonoid );

        final PriorityQueue<V> unsettledNodes = new PriorityQueue<V>( graph.getOrder(), shortestEdges );
        unsettledNodes.add( source );

        final Set<WE> settledEdges = new HashSet<WE>();

        // extract the node with the shortest distance
        while ( !unsettledNodes.isEmpty() )
        {
            V vertex = unsettledNodes.remove();

            for ( V v : graph.getConnectedVertices( vertex ) )
            {
                WE edge = graph.getEdge( vertex, v );

                // if the edge has not been already visited and its weight is less then the current Vertex weight
                boolean weightLessThanCurrent = !shortestEdges.hasWeight( v )
                        || orderedMonoid.compare( edge.getWeight(), shortestEdges.getWeight( v ) ) < 0;
                if ( settledEdges.add( edge ) && weightLessThanCurrent )
                {
                    if ( !unsettledNodes.contains( v ) )
                    {
                        unsettledNodes.add( v );
                    }

                    shortestEdges.addPredecessor( v, edge );
                }
            }
        }

        return shortestEdges.createSpanningTree();
    }

}
