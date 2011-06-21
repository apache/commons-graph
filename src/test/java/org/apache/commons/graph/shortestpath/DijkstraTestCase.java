package org.apache.commons.graph.shortestpath;

import static junit.framework.Assert.assertEquals;
import static org.apache.commons.graph.shortestpath.Dijkstra.findShortestPath;

import org.apache.commons.graph.Path;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.DirectedMutableWeightedGraph;
import org.apache.commons.graph.model.InMemoryWeightedPath;
import org.junit.Test;

public final class DijkstraTestCase
{

    /**
     * Test Graph and Dijkstra's solution can be seen on
     * Wikipedia {@link http://en.wikipedia.org/wiki/Dijkstra's_algorithm}
     */
    @Test
    public void findShortestPathAndVerify()
    {
        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge> mutable =
            new DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge>();

        // building Graph

        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        BaseLabeledVertex three = new BaseLabeledVertex( "3" );
        BaseLabeledVertex four = new BaseLabeledVertex( "4" );
        BaseLabeledVertex five = new BaseLabeledVertex( "5" );
        BaseLabeledVertex six = new BaseLabeledVertex( "6" );

        mutable.addVertex( one );
        mutable.addVertex( two );
        mutable.addVertex( three );
        mutable.addVertex( four );
        mutable.addVertex( five );
        mutable.addVertex( six );

        mutable.addEdge( new BaseLabeledWeightedEdge( "", one, six, 14D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", one, three, 9D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", one, two, 7D ) );

        mutable.addEdge( new BaseLabeledWeightedEdge( "", two, three, 10D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", two, four, 15D ) );

        mutable.addEdge( new BaseLabeledWeightedEdge( "", three, six, 2D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", three, four, 11D ) );

        mutable.addEdge( new BaseLabeledWeightedEdge( "", four, five, 6D ) );
        mutable.addEdge( new BaseLabeledWeightedEdge( "", six, five, 9D ) );

        // expected path

        InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge> expected =
            new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge>( one, five );

        expected.addVertexInTail( three );
        expected.addVertexInTail( six );
        expected.addVertexInTail( five );

        expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", one, three, 9D ) );
        expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", three, six, 2D ) );
        expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", six, five, 9D ) );

        // actual path

        Path<BaseLabeledVertex, BaseLabeledWeightedEdge> actual = findShortestPath( mutable, one, five );

        // assert!

        assertEquals( expected, actual );
    }

}
