package org.apache.commons.graph;

/*
 * Copyright 2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * GraphTest This is a superclass of other tests to provide some utilities in
 * verifying graphs. This test will provide Undirected and Directed Graphs as
 * well. We will implement the following graphs. Undirected Graphs
 * ------------------- () No Vertex, No Edges @ One Vertex, No Edges @ @ Two
 * Vertices, No Edges @-@ Two Vertices, One edge /-\ @ @ Two Vertices, Two Edges
 * (Parallel Edges) \-/ @ / \ Three Vertices, Three Edges (Cycle) @---@ @--@--@
 * Three Vertices, Two Edges (No Cycle) @ / \ @ @ 5 Vertices, 4 Edges (Tree) / \
 * @ @ @-@ @-@ 4 Vertices, 2 Edges (Disconnected)
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;

import junit.framework.*;

import org.apache.commons.graph.domain.basic.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class GraphTest extends TestCase
{
    /**
     * Description of the Class
     */
    public class VertexImpl
         implements Vertex
    {
        private String name = null;

        /**
         * Constructor for the VertexImpl object
         *
         * @param name
         */
        public VertexImpl(String name)
        {
            this.name = name;
        }

        /**
         * Description of the Method
         */
        public String toString()
        {
            return name;
        }
    }

    /**
     * Description of the Class
     */
    public class EdgeImpl
         implements Edge
    {
        private Vertex start;
        private Vertex end;

        /**
         * Constructor for the EdgeImpl object
         *
         * @param start
         * @param end
         */
        public EdgeImpl(Vertex start,
                        Vertex end)
        {
            this.start = start;
            this.end = end;
        }

        /**
         * Gets the otherVertex attribute of the EdgeImpl object
         */
        public Vertex getOtherVertex(Vertex v)
        {
            if (v == start)
            {
                return end;
            }
            if (v == end)
            {
                return start;
            }
            return null;
        }

        /**
         * Gets the vertices attribute of the EdgeImpl object
         */
        public Set getVertices()
        {
            Set RC = new HashSet();
            RC.add(start);
            RC.add(end);
            return RC;
        }

        /**
         * Gets the source attribute of the EdgeImpl object
         */
        public Vertex getSource()
        {
            return start;
        }

        /**
         * Gets the target attribute of the EdgeImpl object
         */
        public Vertex getTarget()
        {
            return end;
        }

        /**
         * Description of the Method
         */
        public String toString()
        {
            return start.toString() + " <-> " + end.toString();
        }
    }

    /**
     * Description of the Field
     */
    public Vertex V1 = new VertexImpl("V1");
    /**
     * Description of the Field
     */
    public Vertex V2 = new VertexImpl("V2");
    /**
     * Description of the Field
     */
    public Vertex V3 = new VertexImpl("V3");
    /**
     * Description of the Field
     */
    public Vertex V4 = new VertexImpl("V4");
    /**
     * Description of the Field
     */
    public Vertex V5 = new VertexImpl("V5");

    /**
     * Description of the Field
     */
    public EdgeImpl V1_V1 = new EdgeImpl(V1, V1);// For Self-Loops.
    /**
     * Description of the Field
     */
    public EdgeImpl V1_V2 = new EdgeImpl(V1, V2);
    /**
     * Description of the Field
     */
    public EdgeImpl V1_V2_ = new EdgeImpl(V1, V2);// For Parallel
    /**
     * Description of the Field
     */
    public EdgeImpl V1_V2__ = new EdgeImpl(V1, V2);// For Parallel #2

  public EdgeImpl V1_V2_V3 = new EdgeImpl(V1, V2); // HyperEdge. . .
  
    /**
     * Description of the Field
     */
    public EdgeImpl V1_V3 = new EdgeImpl(V1, V3);
    /**
     * Description of the Field
     */
    public EdgeImpl V1_V4 = new EdgeImpl(V1, V4);
    /**
     * Description of the Field
     */
    public EdgeImpl V1_V5 = new EdgeImpl(V1, V5);

    /**
     * Description of the Field
     */
    public EdgeImpl V2_V1 = new EdgeImpl(V2, V1);
    /**
     * Description of the Field
     */
    public EdgeImpl V2_V3 = new EdgeImpl(V2, V3);
    /**
     * Description of the Field
     */
    public EdgeImpl V2_V4 = new EdgeImpl(V2, V4);
    /**
     * Description of the Field
     */
    public EdgeImpl V2_V5 = new EdgeImpl(V2, V5);

    /**
     * Description of the Field
     */
    public EdgeImpl V3_V1 = new EdgeImpl(V3, V1);
    /**
     * Description of the Field
     */
    public EdgeImpl V3_V2 = new EdgeImpl(V3, V2);
    /**
     * Description of the Field
     */
    public EdgeImpl V3_V4 = new EdgeImpl(V3, V4);
    /**
     * Description of the Field
     */
    public EdgeImpl V3_V5 = new EdgeImpl(V3, V5);

    /**
     * Description of the Field
     */
    public EdgeImpl V4_V1 = new EdgeImpl(V4, V1);
    /**
     * Description of the Field
     */
    public EdgeImpl V4_V2 = new EdgeImpl(V4, V2);
    /**
     * Description of the Field
     */
    public EdgeImpl V4_V3 = new EdgeImpl(V4, V3);
    /**
     * Description of the Field
     */
    public EdgeImpl V4_V5 = new EdgeImpl(V4, V5);

    /**
     * Description of the Field
     */
    public EdgeImpl V5_V1 = new EdgeImpl(V5, V1);
    /**
     * Description of the Field
     */
    public EdgeImpl V5_V2 = new EdgeImpl(V5, V2);
    /**
     * Description of the Field
     */
    public EdgeImpl V5_V3 = new EdgeImpl(V5, V3);
    /**
     * Description of the Field
     */
    public EdgeImpl V5_V4 = new EdgeImpl(V5, V4);

    /**
     * Constructor for the GraphTest object
     *
     * @param name
     */
    public GraphTest(String name)
    {
        super(name);
        this.testName = name;
    }

    private String testName = null;

    /**
     * Return this graph: ()
     */
    public UndirectedGraph makeNullGraph()
        throws GraphException
    {
        return new UndirectedGraphImpl();
    }

    /**
     * Description of the Method
     */
    public DirectedGraph makeDirNullGraph()
        throws GraphException
    {
        return new DirectedGraphImpl();
    }

    /**
     * Return this graph: v1
     */
    public UndirectedGraph makeSingleVertex()
        throws GraphException
    {
        UndirectedGraphImpl RC = new UndirectedGraphImpl();
        RC.addVertex(V1);
        return RC;
    }

    /**
     * Description of the Method
     */
    public DirectedGraph makeDirSingleVertex()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();
        RC.addVertex(V1);
        return RC;
    }

    /**
     * /--\ v1 | ^--/
     */
    public DirectedGraph makeSelfLoop()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();
        RC.addVertex(V1);
        RC.addEdge(V1_V1, V1, V1);
        return RC;
    }

    /**
     * v1 v2 Two Vertices, No Edges
     */
    public UndirectedGraph makeDoubleVertex()
        throws GraphException
    {
        UndirectedGraphImpl RC = new UndirectedGraphImpl();
        RC.addVertex(V1);
        RC.addVertex(V2);
        return RC;
    }

    /**
     * Description of the Method
     */
    public DirectedGraph makeDirDoubleVertex()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();
        RC.addVertex(V1);
        RC.addVertex(V2);
        return RC;
    }

    /**
     * v1-v2 Two Vertices, One edge
     */
    public UndirectedGraph makeSingleEdge()
        throws GraphException
    {
        UndirectedGraphImpl RC = new UndirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);

        RC.addEdge(V1_V2, V1_V2.getVertices());

        return RC;
    }

    /**
     * v1 -> v2 Directed Edge
     */
    public DirectedGraph makeDirectedEdge()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);

        RC.addEdge(V1_V2,
            V1, V2);

        return RC;
    }

    /**
     * /-\ v1 v2 Two Vertices, Two Edges (Parallel Edges) \-/
     */
    public UndirectedGraph makeParallelEdges()
        throws GraphException
    {
        UndirectedGraphImpl RC = new UndirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);

        RC.addEdge(V1_V2, V1_V2.getVertices());
        RC.addEdge(V1_V2_, V1_V2_.getVertices());

        return RC;
    }

    /**
     * /--->\ @ @ \--->/
     */
    public DirectedGraph makeDirParallelEdges()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);

        RC.addEdge(V1_V2, V1, V2);
        // Second edge must be distinct. . .
        RC.addEdge(V1_V2_, V1, V2);

        return RC;
    }

    /**
     * v1 / \ Three Vertices, Three Edges (Cycle) v2---v3
     */
    public UndirectedGraph makeCycle()
        throws GraphException
    {
        UndirectedGraphImpl RC = new UndirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);

        RC.addEdge(V1_V2, V1_V2.getVertices());
        RC.addEdge(V2_V3, V2_V3.getVertices());
        RC.addEdge(V3_V1, V3_V1.getVertices());

        return RC;
    }

    /**
     * /--->\ v1 v2 \<---/
     */
    public DirectedGraph makeTwoCycle()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);

        RC.addEdge(V1_V2, V1, V2);
        RC.addEdge(V2_V1, V2, V1);

        return RC;
    }

    /**
     * v1 / ^ v \ v2 ---> v3
     */
    public DirectedGraph makeDirectedCycle()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);

        RC.addEdge(V1_V2, V1, V2);
        RC.addEdge(V2_V3, V2, V3);
        RC.addEdge(V3_V1, V3, V1);

        return RC;
    }

    /**
     * v1 / ^ v \ v2 ---> v3
     */
    public DirectedGraph makeDirected4Cycle()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);
	RC.addVertex(V4);

        RC.addEdge(V1_V2, V1, V2);
        RC.addEdge(V2_V3, V2, V3);
        RC.addEdge(V3_V4, V3, V4);
	RC.addEdge(V4_V1, V4, V1);

        return RC;
    }

    /**
     * v1--v2--v3 Three Vertices, Two Edges (No Cycle)
     */
    public UndirectedGraph makeNoCycle()
        throws GraphException
    {
        UndirectedGraphImpl RC = new UndirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);

        RC.addEdge(V1_V2, V1_V2.getVertices());
        RC.addEdge(V2_V3, V2_V3.getVertices());

        return RC;
    }

    /**
     * v1 --> v2 --> v3
     */
    public DirectedGraph makePipe()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);

        RC.addEdge(V1_V2, V1, V2);
        RC.addEdge(V2_V3, V2, V3);

        return RC;
    }

    /**
     * v1 / \ v v v2 v3 \ / v v v4
     */
    public DirectedGraph makeDiamond()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);
        RC.addVertex(V4);

        RC.addEdge(V1_V2, V1, V2);
        RC.addEdge(V1_V3, V1, V3);
        RC.addEdge(V2_V4, V2, V4);
        RC.addEdge(V3_V4, V3, V4);

        return RC;
    }

    /**
     * v1 / \ v v v2 v3 ^ ^ \ / v4
     */
    public DirectedGraph makePipelessCycle()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);
        RC.addVertex(V4);

        RC.addEdge(V1_V2, V1, V2);
        RC.addEdge(V1_V3, V1, V3);
        RC.addEdge(V4_V2, V4, V2);
        RC.addEdge(V4_V3, V4, V3);

        return RC;
    }

    /**
     * v1 / \ v2 v3 5 Vertices, 4 Edges (Tree) / \ v4 v5
     */
    public UndirectedGraph makeTree()
        throws GraphException
    {
        UndirectedGraphImpl RC = new UndirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);
        RC.addVertex(V4);
        RC.addVertex(V5);

        RC.addEdge(V1_V2, V1_V2.getVertices());
        RC.addEdge(V1_V3, V1_V3.getVertices());
        RC.addEdge(V3_V4, V3_V4.getVertices());
        RC.addEdge(V3_V5, V3_V5.getVertices());

        return RC;
    }

    /**
     * Description of the Method
     */
    public DirectedGraph makeParentTree()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);
        RC.addVertex(V4);
        RC.addVertex(V5);

        RC.addEdge(V2_V1, V2, V1);
        RC.addEdge(V3_V1, V3, V1);
        RC.addEdge(V4_V3, V4, V3);
        RC.addEdge(V5_V3, V5, V3);

        return RC;
    }

    /**
     * Description of the Method
     */
    public DirectedGraph makeChildTree()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);
        RC.addVertex(V4);
        RC.addVertex(V5);

        RC.addEdge(V1_V2, V1, V2);
        RC.addEdge(V1_V3, V1, V3);
        RC.addEdge(V3_V4, V3, V4);
        RC.addEdge(V3_V5, V3, V5);

        return RC;
    }

    /**
     * v1-v2 v3-v4 4 Vertices, 2 Edges (Disconnected)
     */
    public UndirectedGraph makeDisconnected()
        throws GraphException
    {
        UndirectedGraphImpl RC = new UndirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);
        RC.addVertex(V4);

        RC.addEdge(V1_V2, V1_V2.getVertices());
        RC.addEdge(V3_V4, V3_V4.getVertices());

        return RC;
    }

  /**
   * makeHyperEdgeGraph
   *
   * This makes a graph which has more than one Vertex on an Edge.
   *
   *                   V1
   *                   |
   *                   *
   *                  / \
   *                V2   V3
   *
   */
  public Graph makeHyperGraph() {
    UndirectedGraphImpl RC = new UndirectedGraphImpl();
    
    RC.addVertex( V1 );
    RC.addVertex( V2 );
    RC.addVertex( V3 );

    RC.addEdge( V1_V2_V3 );
    RC.connect( V1_V2_V3, V1 );
    RC.connect( V1_V2_V3, V2 );
    RC.connect( V1_V2_V3, V3 );

    return RC;
  }


    /**
     *   
     *   V1 --> V2 --> V3 <--> V4
     */
    public DirectedGraph makeCycleNoReturn()
        throws GraphException
    {
        DirectedGraphImpl RC = new DirectedGraphImpl();

        RC.addVertex(V1);
        RC.addVertex(V2);
        RC.addVertex(V3);
        RC.addVertex(V4);

        RC.addEdge(V1_V2, V1, V2);
        RC.addEdge(V2_V3, V2, V3);
        RC.addEdge(V3_V4, V3, V4);
        RC.addEdge(V4_V3, V4, V3);

        return RC;
    }

    /**
     * A unit test for JUnit
     */
    public void testNothing()
        throws Throwable { }

    /**
     * Description of the Method
     */
    public void verifyVertexEdgeCount(Graph IUT,
                                      Vertex v,
                                      int numEdge)
        throws Throwable
    {
        assertEquals(v.toString() + " has wrong number of adjacent edges.",
            IUT.getEdges(v).size(), numEdge);
    }

    /**
     * Gets the outboundVertices attribute of the GraphTest object
     */
    private Set getOutboundVertices(DirectedGraph IUT,
                                    Vertex v)
    {
        Set RC = new HashSet();
        Iterator edges = IUT.getOutbound(v).iterator();

        while (edges.hasNext())
        {
            Edge e = (Edge) edges.next();
            RC.add(IUT.getTarget(e));
        }

        return RC;
    }

    /**
     * Gets the inboundVertices attribute of the GraphTest object
     */
    private Set getInboundVertices(DirectedGraph IUT,
                                   Vertex v)
    {
        Set RC = new HashSet();
        Iterator edges = IUT.getInbound(v).iterator();

        while (edges.hasNext())
        {
            Edge e = (Edge) edges.next();
            RC.add(IUT.getSource(e));
        }

        return RC;
    }


    /**
     * Gets the adjVertices attribute of the GraphTest object
     */
    private Set getAdjVertices(Graph IUT,
                               Vertex v)
    {
        Set RC = new HashSet();
        Iterator edges = IUT.getEdges(v).iterator();

        while (edges.hasNext())
        {
            Edge e = (Edge) edges.next();

            Set verts = IUT.getVertices(e);
            RC.addAll(verts);
        }

        return RC;
    }

    /**
     * Description of the Method
     */
    public Set makeSet()
    {
        return new HashSet();
    }


  /**
     * Description of the Method
     */
    public Set makeSet(Object v)
    {
        Set RC = new HashSet();
        RC.add(v);
        return RC;
    }

    /**
     * Description of the Method
     */
    public Set makeSet(Object v1,
                       Object v2)
    {
        Set RC = makeSet(v1);
        RC.add(v2);
        return RC;
    }

    /**
     * Description of the Method
     */
    public Set makeSet(Set s1,
                       Set s2)
    {
        Set RC = new HashSet();
        RC.addAll(s1);
        RC.addAll(s2);
        return RC;
    }

    /**
     * Description of the Method
     */
    public Set makeSet(Object v1,
                       Object v2,
                       Object v3)
    {
        return makeSet(makeSet(v1, v2),
            makeSet(v3));
    }

    /**
     * Description of the Method
     */
    public Set makeSet(Object v1,
                       Object v2,
                       Object v3,
                       Object v4)
    {
        return makeSet(makeSet(v1, v2),
            makeSet(v3, v4));
    }

    /**
     * Description of the Method
     */
    public void verifyAdjVertices(DirectedGraph IUT,
                                  Vertex v,
                                  Set inExpect,
                                  Set outExpect)
    {
        Set inbound = getInboundVertices(IUT, v);
        Set outbound = getOutboundVertices(IUT, v);

        Iterator verts;

        // inbound is a subset of inExpect
        verts = inbound.iterator();

        while (verts.hasNext())
        {
            Vertex curr = (Vertex) verts.next();

            assertTrue(curr.toString() + " is not supposed to be " +
                "next to " + v.toString(),
                inExpect.contains(curr));
        }

        // inExpect is a subset of inbound
        verts = inExpect.iterator();

        while (verts.hasNext())
        {
            Vertex curr = (Vertex) verts.next();
            assertTrue(curr.toString() + " is supposed to be next to " +
                v.toString(),
                inbound.contains(curr));
        }

        // outbound is a subset of outExpect
        verts = outbound.iterator();

        while (verts.hasNext())
        {
            Vertex curr = (Vertex) verts.next();

            assertTrue(curr.toString() + " is not supposed to be " +
                "next to " + v.toString(),
                outExpect.contains(curr));
        }

        // outExpect is a subset of outbound
        verts = outExpect.iterator();

        while (verts.hasNext())
        {
            Vertex curr = (Vertex) verts.next();
            assertTrue(curr.toString() + " is supposed to be next to " +
                v.toString(),
                outbound.contains(curr));
        }

    }

    /**
     * Description of the Method
     */
    public void verifyAdjVertices(Graph IUT,
                                  Vertex v,
                                  Set expected)
        throws Throwable
    {
        Set adjacent = getAdjVertices(IUT, v);
        Iterator adjV = adjacent.iterator();

        while (adjV.hasNext())
        {
            Vertex curr = (Vertex) adjV.next();
            assertTrue(curr.toString() + " is not supposed to be " +
                "next to " + v.toString(),
                expected.contains(curr));
        }

        Iterator expect = expected.iterator();

        while (expect.hasNext())
        {
            Vertex curr = (Vertex) expect.next();
            assertTrue(curr.toString() + " is supposed to be next to " +
                v.toString(),
                adjacent.contains(curr));
        }
    }

    /**
     * Description of the Method
     */
    public void verifyGraph(Graph g, int numVertex, int numEdge)
        throws Throwable
    {
        assertEquals("Incorrect Number of Vertices.",
            numVertex, g.getVertices().size());
        assertEquals("Incorrect Number of Edges.",
            numEdge, g.getEdges().size());
    }

    /**
     * Description of the Method
     */
    public void printGraph(Throwable t,
                           DirectedGraph IUT)
    {
        System.err.println(testName + ": " + t.toString());
        System.err.println("VERTICES: " + IUT.getVertices());
        System.err.println("EDGES:    " + IUT.getEdges());

        Iterator verts = IUT.getVertices().iterator();
        while (verts.hasNext())
        {
            Vertex vert = (Vertex) verts.next();
            System.err.println("[ " + vert + " ]");

            Iterator inbounds = IUT.getInbound(vert).iterator();
            while (inbounds.hasNext())
            {
                Edge inbound = (Edge) inbounds.next();
                System.err.println("\tI [" + inbound + "]");
            }

            Iterator outbounds = IUT.getOutbound(vert).iterator();
            while (outbounds.hasNext())
            {
                Edge outbound = (Edge) outbounds.next();
                System.err.println("\tO [" + outbound + "]");
            }
        }
    }

}
