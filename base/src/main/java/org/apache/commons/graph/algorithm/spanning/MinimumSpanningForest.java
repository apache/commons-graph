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

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;
import org.apache.commons.graph.decorator.*;
import org.apache.commons.graph.domain.basic.*;
import org.apache.commons.graph.algorithm.util.*;

import org.apache.commons.collections.PriorityQueue;
import org.apache.commons.collections.BinaryHeap;

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Comparator;

public class MinimumSpanningForest
  extends UndirectedGraphImpl
  implements UndirectedGraph,
	     WeightedGraph
{
  private PriorityQueue queue = null;
  private Map labels = new HashMap(); // VERTEX X LABEL
  private Set chords = new HashSet(); // Edges not in MSF.
  
  private DecoratedDirectedGraph ddg = null;

  public class WeightedEdgeComparator
    implements Comparator
  {
    private WeightedGraph graph = null;
    public WeightedEdgeComparator( WeightedGraph graph ) {
      this.graph = graph;
    }

    public int compare( Object o1, Object o2 ) {
      if ((o1 instanceof Edge) && 
	  (o2 instanceof Edge)) {
	double val = ( graph.getWeight( (Edge) o1 ) -
		       graph.getWeight( (Edge) o2 ));
	if (val > 0) return 1;
	if (val == 0) return 0;
	if (val < 0) return -1;
      }

      return -1;
    }
  }

  public MinimumSpanningForest( WeightedGraph graph ) {
    calculateMSF( true, graph );
  }

  public MinimumSpanningForest( boolean isMin, WeightedGraph graph ) {
    calculateMSF( isMin, graph );
  }

  protected Label findLabel( Vertex v ) {
    return (Label) labels.get( v );
  }

  protected boolean connectsLabels( Graph graph, 
				    Edge e ) 
  {
    Label first = null;
    Label second = null;

    Iterator vertices = graph.getVertices( e ).iterator();
    if (vertices.hasNext()) {
      first = findLabel( (Vertex) vertices.next() );
    } else { return false; }

    if (vertices.hasNext()) {
      second = findLabel( (Vertex) vertices.next() );
    } else { return false; }

    if (vertices.hasNext()) {
      throw new HyperGraphException("Unable to compute MSF on Hypergraph.");
    }

    return !first.getRoot().equals( second.getRoot() );
  }

  protected void addEdge( WeightedGraph graph,
			  Edge edge ) { 
    addEdge( edge );
    chords.remove( edge );
    Iterator vertices = graph.getVertices( edge ).iterator();
    Label prime = null;

    if (vertices.hasNext()) {
      Vertex p = (Vertex) vertices.next();
      prime = findLabel( p );

      connect( edge, p );
    }

    while (vertices.hasNext()) {
      Vertex v = (Vertex) vertices.next();

      Label l = findLabel( v );
      l.setRoot( prime );

      connect( edge, v );
    }

    setWeight( edge, graph.getWeight( edge ));
  }

  protected void calculateMSF( boolean isMin,
			       WeightedGraph graph ) {
    
    PriorityQueue queue = 
      new BinaryHeap( isMin, new WeightedEdgeComparator( graph ));
    
    chords = new HashSet( graph.getEdges() );

    // Fill the Queue with all the edges.
    Iterator edges = graph.getEdges().iterator();
    while (edges.hasNext()) {
      queue.insert( edges.next() );
    }

    // Fill the graph we have with all the Vertexes.
    Iterator vertices = graph.getVertices().iterator();
    while (vertices.hasNext()) {
      Vertex v = (Vertex) vertices.next();
      labels.put( v, new Label() );
      addVertex( v );
    }

    // Bring the edges out in the right order.
    while (!queue.isEmpty()) {
      Edge e = (Edge) queue.pop();

      if ( connectsLabels( graph, e )) {
	addEdge( graph, e );
      }
    }
  }

  public Set getChords() {
    return chords;
  }
}




