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
    public void shortestPath1()
    {
        // test Graph

        BaseLabeledVertex one = new BaseLabeledVertex( "1" );
        BaseLabeledVertex two = new BaseLabeledVertex( "2" );
        BaseLabeledVertex three = new BaseLabeledVertex( "3" );
        BaseLabeledVertex four = new BaseLabeledVertex( "4" );
        BaseLabeledVertex five = new BaseLabeledVertex( "5" );
        BaseLabeledVertex six = new BaseLabeledVertex( "6" );

        DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge> testGraph =
            new DirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge>();

        testGraph.addVertex( one );
        testGraph.addVertex( two );
        testGraph.addVertex( three );
        testGraph.addVertex( four );
        testGraph.addVertex( five );
        testGraph.addVertex( six );

        testGraph.addEdge( new BaseLabeledWeightedEdge( "", one, six, 14D ) );
        testGraph.addEdge( new BaseLabeledWeightedEdge( "", one, three, 9D ) );
        testGraph.addEdge( new BaseLabeledWeightedEdge( "", one, two, 7D ) );

        testGraph.addEdge( new BaseLabeledWeightedEdge( "", two, three, 10D ) );
        testGraph.addEdge( new BaseLabeledWeightedEdge( "", two, four, 15D ) );

        testGraph.addEdge( new BaseLabeledWeightedEdge( "", three, six, 2D ) );
        testGraph.addEdge( new BaseLabeledWeightedEdge( "", three, four, 11D ) );

        testGraph.addEdge( new BaseLabeledWeightedEdge( "", four, five, 6D ) );
        testGraph.addEdge( new BaseLabeledWeightedEdge( "", six, five, 9D ) );

        // expected path

        Path<BaseLabeledVertex, BaseLabeledWeightedEdge> actual = findShortestPath( testGraph, one, five );

        InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge> expected =
            new InMemoryWeightedPath<BaseLabeledVertex, BaseLabeledWeightedEdge>( one, five, 20D );

        expected.addVertexInTail( three );
        expected.addVertexInTail( six );
        expected.addVertexInTail( five );

        expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", one, three, 9D ) );
        expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", three, six, 2D ) );
        expected.addEdgeInTail( new BaseLabeledWeightedEdge( "", six, five, 9D ) );

        assertEquals( expected, actual );
    }

}
