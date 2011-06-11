package org.apache.commons.graph.algorithm.spanning;

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

import java.util.Set;

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

public class MinimumSpanningForestTest
  extends WeightedGraphTest
{
  private String name = null;

  public MinimumSpanningForestTest( String name ) {
    super( name );
    this.name = name;
  }

  public void testNullGraph() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeNullGraph();
    MinimumSpanningForest msf = 
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 0, 0 );
    verifyChords( msf, makeSet() );
  }
  
  public void testDirNullGraph() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeDirNullGraph();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 0, 0 );
    verifyChords( msf, makeSet() );
  }

  public void testSingleVertex() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeSingleVertex();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 1, 0 );
    verifyChords( msf, makeSet() );
  }

  public void testDirSingleVertex() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeDirSingleVertex();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 1, 0 );
    verifyChords( msf, makeSet() );
  }

  public void testSelfLoop() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeSelfLoop();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 1, 0 );

    verifyChords(msf, makeSet( V1_V1 ));
  }

  public void testDoubleVertex() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeDoubleVertex();
    MinimumSpanningForest msf = 
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 2, 0 );
    verifyChords(msf, makeSet() );
  }

  public void testDirDoubleVertex() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeDirDoubleVertex();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 2, 0 );
    verifyChords( msf, makeSet() );
  }

  public void testSingleEdge() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeSingleEdge();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 2, 1 );
    verifyChords( msf, makeSet());
  }

  public void testDirectedEdge() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeDirectedEdge();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 2, 1 );
    verifyChords( msf, makeSet());
  }

  public void testParallelEdges() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeParallelEdges();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 2, 1 );
  }

  public void testDirParallelEdges() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeDirParallelEdges();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 2, 1 );
  }

  public void testCycle() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeCycle();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 3, 2 );
  }

  public void testTwoCycle() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeTwoCycle();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 2, 1 );
  }

  public void testDirectedCycle() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeDirectedCycle();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 3, 2 );
  }

  public void testNoCycle() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeNoCycle();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 3, 2 );
  }

  public void testPipe() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makePipe();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 3, 2 );
  }

  public void testDiamond() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeDiamond();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 4, 3 );
  }

  public void testPipelessCycle() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makePipelessCycle();
    MinimumSpanningForest msf =
      new MinimumSpanningForest( graph );
    verifyGraph( msf, 4, 3 );
  }

  public void testHyperGraph() throws Throwable {
    WeightedGraph graph = (WeightedGraph) makeHyperGraph();
    try {
      MinimumSpanningForest msf =
	new MinimumSpanningForest( graph );
      fail("HyperGraph Exception not thrown.");
    } catch (HyperGraphException hge) { }
  }

  public void testWDirectedEdge()
    throws Throwable
  {
    WeightedGraph graph = makeWDirectedEdge();
    
    MinimumSpanningForest msf = 
      new MinimumSpanningForest( graph );
    
    verifyGraph( msf, 2, 1 );
    verifyEdges( msf, makeSet( V1_V2 ) );
    assertEquals("Edge Weight not the same.",
		 5.0, msf.getWeight(V1_V2), 0.00001 );
    verifyChords( msf, makeSet() );
  }

  public void testWSelfLoop()
    throws Throwable
  {
    MinimumSpanningForest IUT = 
      new MinimumSpanningForest( makeWSelfLoop() );
    verifyGraph(IUT, 1, 0);
    verifyEdges(IUT, makeSet() );
    verifyChords(IUT, makeSet( V1_V1 ));
  }

  public void testPositiveCycle() 
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makePositiveCycle() );
    verifyGraph( IUT, 3, 2 );
    verifyEdges( IUT, makeSet( V3_V1, V1_V2 ));
    verifyChords( IUT, makeSet( V2_V3 ));
  }

  public void testPositivePartNegCycle()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makePositivePartNegCycle() );
    verifyGraph( IUT, 3, 2 );
    verifyEdges( IUT, makeSet( V3_V1, V1_V2 ) );
    verifyChords( IUT, makeSet( V2_V3 ));
  }

  public void testNegativeCycle()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makeNegativeCycle() );
    
    verifyGraph( IUT, 3, 2 );
    verifyEdges( IUT, makeSet( V2_V3, V1_V2 ));
    verifyChords( IUT, makeSet( V3_V1 ));
  }

  public void testNegativePartPosCycle()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makeNegativePartPosCycle() );
    
    verifyGraph( IUT, 3, 2 );
    verifyEdges( IUT, makeSet( V2_V3, V1_V2 ));
    verifyChords( IUT, makeSet( V3_V1 ));
  }

  public void testPositivePipe()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makePositivePipe() );
    
    verifyGraph( IUT, 3, 2 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V3 ));
    verifyChords(IUT, makeSet());
  }

  public void testPositivePartNegPipe()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makePositivePartNegPipe() );
    
    verifyGraph( IUT, 3, 2 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V3 ));
    verifyChords(IUT,  makeSet());
  }

  public void testNegativePipe()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makeNegativePipe() );
    
    verifyGraph( IUT, 3, 2 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V3 ));
    verifyChords(IUT, makeSet());
  }

  public void testNegativePartPosPipe()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makeNegativePartPosPipe() );
    
    verifyGraph( IUT, 3, 2 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V3 ));
    verifyChords(IUT, makeSet());
  }

  public void testMultiplePathL()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makeMultiplePathL() );
    
    verifyGraph( IUT, 4, 3 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V4, V1_V3 ));
    verifyChords(IUT, makeSet( V3_V4 ));
  }

  public void testMultiplePathR()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makeMultiplePathR() );
    
    verifyGraph( IUT, 4, 3 );
    verifyEdges( IUT, makeSet( V1_V3, V3_V4, V2_V4 ));
    verifyChords(IUT, makeSet( V1_V2 ));
  }

  public void testMultiplePathEarlyLow()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makeMultiplePathEarlyLow() );
    
    verifyGraph( IUT, 4, 3 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V4, V1_V3 ));
    verifyChords(IUT, makeSet( V3_V4 ));
  }
  public void testMaxMultiplePathEarlyLow()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( false, 
				 makeMultiplePathEarlyLow() );
    
    verifyGraph( IUT, 4, 3 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V4, V3_V4 ));
    verifyChords(IUT, makeSet( V1_V3 ));
  }

  public void testMultiplePathEarlyHigh()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( makeMultiplePathEarlyHigh() );

    verifyGraph( IUT, 4, 3 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V4, V3_V4 ) );
    verifyChords(IUT, makeSet( V1_V3 ));
  }

  public void testMaxMultiplePathEarlyHigh()
    throws Throwable
  {
    MinimumSpanningForest IUT =
      new MinimumSpanningForest( false, 
				 makeMultiplePathEarlyHigh());
    
    verifyGraph( IUT, 4, 3 );
    verifyEdges( IUT, makeSet( V1_V2, V2_V4, V1_V3 ));
    verifyChords(IUT, makeSet( V3_V4 ));
  }

  
  // Helpers. . .
    
  public void verifyEdges(Graph g, 
			  Set edges) throws Throwable {
    Set gEdges = g.getEdges();
    assertTrue( "Graph has edges not in expected set.",
		gEdges.containsAll( edges ));
    assertTrue( "Graph does not have expected edges.",
		edges.containsAll( edges ));
  }

  public void verifyChords(MinimumSpanningForest msf, 
			  Set chords) throws Throwable {
    Set gChords = msf.getChords();
    assertTrue( "Graph has chords not in expected set.",
		gChords.containsAll( chords ));
    assertTrue( "Graph does not have expected edges.",
		chords.containsAll( gChords ));
  }

}





