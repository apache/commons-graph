package org.apache.commons.graph.spanning;

import static junit.framework.Assert.assertEquals;
import static org.apache.commons.graph.CommonsGraph.minimumSpanningTree;

import org.apache.commons.graph.SpanningTree;
import org.apache.commons.graph.model.BaseLabeledVertex;
import org.apache.commons.graph.model.BaseLabeledWeightedEdge;
import org.apache.commons.graph.model.MutableSpanningTree;
import org.apache.commons.graph.model.UndirectedMutableWeightedGraph;
import org.apache.commons.graph.weight.primitive.DoubleWeight;
import org.junit.Test;

/**
 *
 */
public class ReverseDeleteTestCase
{

    /**
     * Test Graph and Reverse-Delete Algorithm
     */
    @Test
    public void verifyMinimumSpanningTree()
    {
        UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> input =
            new UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>();

        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 21D ), c );
        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeight() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input ).applyingReverseDeleteAlgorithm( new DoubleWeight() );
        assertEquals( expected, actual );
    }

    /**
     * Test Graph and Reverse-Delete Algorithm
     */
    @Test
    public void verifyNotConnectGraph()
    {
        UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> input =
            new UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>();

        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );

        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeight() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input ).applyingReverseDeleteAlgorithm( new DoubleWeight() );
        assertEquals( expected, actual );
    }

    /**
     * Test Graph and Reverse-Delete Algorithm
     */
    @Test
    public void verifyNotConnectGraph2()
    {
        UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> input =
            new UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>();

        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        BaseLabeledVertex e = new BaseLabeledVertex( "e" );


        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );
        input.addVertex( d );
        input.addVertex( e );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 21D ), c );
        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        input.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 4D ), e );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeight() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        expected.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 4D ), e );

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input ).applyingReverseDeleteAlgorithm( new DoubleWeight() );
        assertEquals( expected, actual );
    }


    /**
     * Test Graph and Reverse-Delete Algorithm
     */
    @Test
    public void verifyNotConnectGraph3()
    {
        UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> input =
            new UndirectedMutableWeightedGraph<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>();

        BaseLabeledVertex a = new BaseLabeledVertex( "a" );
        BaseLabeledVertex b = new BaseLabeledVertex( "b" );
        BaseLabeledVertex c = new BaseLabeledVertex( "c" );
        BaseLabeledVertex d = new BaseLabeledVertex( "d" );
        BaseLabeledVertex e = new BaseLabeledVertex( "e" );
        BaseLabeledVertex f = new BaseLabeledVertex( "f" );


        input.addVertex( a );
        input.addVertex( b );
        input.addVertex( c );
        input.addVertex( d );
        input.addVertex( e );
        input.addVertex( f );

        input.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        input.addEdge( b, new BaseLabeledWeightedEdge<Double>( "b <-> c", 21D ), c );
        input.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        input.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 7D ), e );
        input.addEdge( e, new BaseLabeledWeightedEdge<Double>( "e <-> f", 21D ), f );
        input.addEdge( f, new BaseLabeledWeightedEdge<Double>( "f <-> d", 4D ), d );

        // expected

        MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> expected =
            new MutableSpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double>( new DoubleWeight() );

        for ( BaseLabeledVertex vertex : input.getVertices() )
        {
            expected.addVertex( vertex );
        }

        expected.addEdge( a, new BaseLabeledWeightedEdge<Double>( "a <-> b", 7D ), b );
        expected.addEdge( c, new BaseLabeledWeightedEdge<Double>( "c <-> a", 4D ), a );

        expected.addEdge( d, new BaseLabeledWeightedEdge<Double>( "d <-> e", 4D ), e );
        expected.addEdge( f, new BaseLabeledWeightedEdge<Double>( "f <-> d", 4D ), d );

        SpanningTree<BaseLabeledVertex, BaseLabeledWeightedEdge<Double>, Double> actual =
                        minimumSpanningTree( input ).applyingReverseDeleteAlgorithm( new DoubleWeight() );
        assertEquals( expected, actual );
    }

}
