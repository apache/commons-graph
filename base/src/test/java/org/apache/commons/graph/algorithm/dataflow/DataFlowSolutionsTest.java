package org.apache.commons.graph.algorithm.dataflow;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import org.apache.commons.graph.*;

import java.util.BitSet;

public class DataFlowSolutionsTest extends GraphTest {
    private static BitSet BS_000 = null;
    private static BitSet BS_001 = null;
    private static BitSet BS_010 = null;
    private static BitSet BS_011 = null;
    private static BitSet BS_100 = null;
    private static BitSet BS_101 = null;
    private static BitSet BS_110 = null;
    private static BitSet BS_111 = null;
    
    static {
	BS_000 = new BitSet();
	BS_001 = new BitSet();
	BS_010 = new BitSet();
	BS_011 = new BitSet();
	BS_100 = new BitSet();
	BS_101 = new BitSet();
	BS_110 = new BitSet();
	BS_111 = new BitSet();
     
	BS_001.set( 3 );
	BS_011.set( 3 );
	BS_101.set( 3 );
	BS_111.set( 3 );

	BS_010.set( 2 );
	BS_011.set( 2 );
	BS_110.set( 2 );
	BS_111.set( 2 );

	BS_100.set( 1 );
	BS_101.set( 1 );
	BS_110.set( 1 );
	BS_111.set( 1 );
    }

    public DataFlowSolutionsTest( String name ) {
	super( name );
    }

    public void testBitSetEquals() throws Throwable {
	BitSet one = new BitSet();
	BitSet two = new BitSet();

	one.set( 15 );
	two.set( 15 );

	assertTrue( one.equals( two ));
    }

    /**
     * Test a single Vertex.
     *
     * @ [ gen (1) ]  --> [ leaves(1) ]
     *
     * @ [ gen (1) kill(1) ] --> [ ]
     * 
     * @ [ ] --> [ ]
     */
    public void testOneGraph() throws Throwable {
	DirectedGraph graph = makeDirSingleVertex();

	BitSet clear = new BitSet();
	BitSet gen = new BitSet();
	BitSet kill = new BitSet();
	BitSet leaves = new BitSet();

	DataFlowSolutions IUT = null;
	MockDataFlowEq dfe = null;
	
	dfe = new MockDataFlowEq();

	dfe.addVertex( V1, BS_100, BS_000 );
	
	IUT = new DataFlowSolutions( graph, dfe );
	assertEquals( "Expected nothing to reach V1.",
		      BS_000, IUT.reaches( V1 ));
	assertEquals( "Expected 1 to leave V1.",
		      BS_100, IUT.leaves( V1 ) );


	dfe = new MockDataFlowEq();
	dfe.addVertex( V1, BS_100, BS_100 );
	
	IUT = new DataFlowSolutions( graph, dfe );

	assertEquals( "Expected nothing to reach V1.",
		      BS_000, IUT.reaches( V1 ));
	assertEquals( "Expected nothing to leave V1.",
		      BS_000, IUT.leaves( V1 ) );	

	dfe = new MockDataFlowEq();
	dfe.addVertex( V1, BS_000, BS_100 );
	
	IUT = new DataFlowSolutions( graph, dfe );

	assertEquals( "Expected nothing to leave V1.",
		      BS_000, IUT.leaves( V1 ) );	
	assertEquals( "Expected nothing to reach V1.",
		      BS_000, IUT.reaches( V1 ) );	
    }
    
    /**
     * Test two vertices
     *
     *  gen(V1) = (1,2)
     *  kill(V2) = (1,2)
     *  leaves(V1) = (1,2)
     *  reaches(V1) = ()
     *  leaves(V2) = ()
     *  reaches(V2) = (1,2)
     */
    public void testTwoVerts0() 
	throws Throwable
    {
	DirectedGraph graph = makeDirectedEdge();
	MockDataFlowEq dfe = new MockDataFlowEq();

	BitSet genV1 = new BitSet();
	genV1.set( 1 );
	genV1.set( 2 );

	dfe.addVertex( V1, BS_110, BS_000 );
	dfe.addVertex( V2, BS_000, BS_110 );

	DataFlowSolutions IUT = new DataFlowSolutions( graph, dfe );

	assertEquals( "Expected 1 and 2 to leave V1",
		      BS_110, IUT.leaves( V1 ));
	assertEquals( "Expected nothing to reach V1",
		      BS_000, IUT.reaches( V1 ));

	assertEquals( "Expected 1 and 2 to reach V2",
		      BS_110, IUT.reaches( V2 ));
	assertEquals( "Expected nothing to leave V2",
		      BS_000, IUT.leaves( V2 ));
    }

    /**
     * Test two vertices
     *
     *  gen(V1) = (1,2)
     *  kill(V2) = (1)
     *  leaves(V1) = (1,2)
     *  reaches(V1) = ()
     *  leaves(V2) = (2)
     *  reaches(V2) = (1,2)
     */
    public void testTwoVerts1() 
	throws Throwable
    {
	DirectedGraph graph = makeDirectedEdge();
	MockDataFlowEq dfe = new MockDataFlowEq();

	dfe.addVertex( V1, BS_110, BS_000 );
	dfe.addVertex( V2, BS_000, BS_100 );

	DataFlowSolutions IUT = new DataFlowSolutions( graph, dfe );

	assertEquals( "Expected 1 and 2 to leave V1",
		      BS_110, IUT.leaves( V1 ));
	assertEquals( "Expected nothing to reach V1",
		      BS_000, IUT.reaches( V1 ));

	assertEquals( "Expected 1 and 2 to reach V2",
		      BS_110, IUT.reaches( V2 ));
	assertEquals( "Expected 2 to leave V2",
		      BS_010, IUT.leaves( V2 ));
    }

    /**
     * Test two vertices
     *
     *  gen(V1) = (1)
     *  kill(v1) = ()
     *
     *  gen(V2) = (2)
     *  kill(V2) = (1)
     *
     *  leaves(V1) = (1)
     *  reaches(V1) = ()
     *
     *  leaves(V2) = (2)
     *  reaches(V2) = (1)
     */
    public void testTwoVerts2() 
	throws Throwable
    {
	DirectedGraph graph = makeDirectedEdge();
	MockDataFlowEq dfe = new MockDataFlowEq();

	BitSet genV1 = new BitSet();
	genV1.set( 1 );

	dfe.addVertex( V1, BS_100, BS_000 );
	dfe.addVertex( V2, BS_010, BS_100 );

	DataFlowSolutions IUT = new DataFlowSolutions( graph, dfe );

	assertEquals( "Expected 1 to leave V1",
		      BS_100, IUT.leaves( V1 ));
	assertEquals( "Expected nothing to reach V1",
		      BS_000, IUT.reaches( V1 ));

	assertEquals( "Expected 1 to reach V2",
		      BS_100, IUT.reaches( V2 ));
	assertEquals( "Expected 2 to leave V2",
		      BS_010, IUT.leaves( V2 ));
    }

    /**
     * Test with a Cycle:
     *     
     *        * +1
     *       / \
     *   +2 *---* +3
     *
     * 1, 2, 3 reach all and leave all.
     */
    public void testCycle0() 
	throws Throwable
    {
	DirectedGraph graph = makeDirectedCycle();
	MockDataFlowEq dfe = new MockDataFlowEq();
	
	dfe.addVertex( V1, BS_100, BS_000);
	dfe.addVertex( V2, BS_010, BS_000);
	dfe.addVertex( V3, BS_001, BS_000);

	DataFlowSolutions IUT = new DataFlowSolutions( graph, dfe );

	assertEquals("Expecting all to reach V1",
		     BS_111, IUT.reaches( V1 ));
	assertEquals("Expecting all to reach V2",
		     BS_111, IUT.reaches( V2 ));
	assertEquals("Expecting all to reach V3",
		     BS_111, IUT.reaches( V3 ));

	assertEquals("Expecting all to leave V1",
		     BS_111, IUT.leaves( V1 ));
	assertEquals("Expecting all to leave V2",
		     BS_111, IUT.leaves( V2 ));
	assertEquals("Expecting all to leave V3",
		     BS_111, IUT.leaves( V3 ));
    }
    
    /**
     * Test with a Cycle:
     *     
     *           * +1 -3
     *          / \
     *   +2 -1 *---* +3 -2
     *
     */
    public void testCycle1() 
	throws Throwable
    {
	DirectedGraph graph = makeDirectedCycle();
	MockDataFlowEq dfe = new MockDataFlowEq();
	
	dfe.addVertex( V1, BS_100, BS_001);
	dfe.addVertex( V2, BS_010, BS_100);
	dfe.addVertex( V3, BS_001, BS_010);

	DataFlowSolutions IUT = new DataFlowSolutions( graph, dfe );

	assertEquals("Expecting 3 to reach V1",
		     BS_001, IUT.reaches( V1 ));
	assertEquals("Expecting 1 to reach V2",
		     BS_100, IUT.reaches( V2 ));
	assertEquals("Expecting 2 to reach V3",
		     BS_010, IUT.reaches( V3 ));

	assertEquals("Expecting 1 to leave V1",
		     BS_100, IUT.leaves( V1 ));
	assertEquals("Expecting 2 to leave V2",
		     BS_010, IUT.leaves( V2 ));
	assertEquals("Expecting 3 to leave V3",
		     BS_001, IUT.leaves( V3 ));
    }
    
}
