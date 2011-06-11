package org.apache.commons.graph.algorithm.path;

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

import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.apache.commons.graph.*;
import org.apache.commons.graph.algorithm.util.*;

public class AllPaths
{
    private Map allPaths = new HashMap(); // VERTEX PAIR X SET( PATHS )

    private AllPairsShortestPath apsp = null;
    private DirectedGraph graph = null;
    
    public AllPaths( DirectedGraph graph ) {
	this.graph = graph;
	try {
	    apsp = new AllPairsShortestPath( graph );
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
    }
    
    public Iterator findPaths( final Vertex i, 
			       final Vertex j ) {
	final PathIterator RC = new PathIterator();

	Runnable run = new Runnable() {
		public void run() {
		    findPaths( RC, i, j, Integer.MAX_VALUE );
		}
	    };
	Thread thread = new Thread( run );

	RC.setThread( thread );

	thread.start();

	return RC;
    }

    public void findPaths( PathListener listener, 
			   Vertex i, Vertex j,
			   int maxLength ) {
	Set workingSet = new HashSet();
	workingSet.add( new PathImpl( i ));
	
	for (int k = 0; k < maxLength; k++) {
	    Iterator workingPaths = workingSet.iterator();
	    if (!workingPaths.hasNext()) break;

	    Set newWorkingSet = new HashSet();
	    
	    while (workingPaths.hasNext()) {
		PathImpl workingPath = (PathImpl) workingPaths.next();

		Iterator outbound = 
		    graph.getOutbound(workingPath.getEnd()).iterator();

		while (outbound.hasNext()) {
		    Edge obEdge = (Edge) outbound.next();
		    if (apsp.hasPath( graph.getTarget( obEdge ), j)) {
			
			PathImpl path = 
			    workingPath.append(graph.getTarget(obEdge),
					       obEdge );
			
			
			newWorkingSet.add( path );
			
			if (path.getEnd() == j) {
			    listener.notifyPath( path );
			}
		    }
		}
	    }

	    workingSet = newWorkingSet;
	}
    }

    /**
     * getAllPaths will return the set of all possible ways of moving
     * from i to j using the directed graph AllPaths was initialized
     * with.
     *
     * @deprecated Use findPaths instead.  Doesn't work, but code
     * may be useful in the near future.
     */
    public Set getAllPaths( Vertex i, Vertex j ) {
	Set RC = new HashSet();
	VertexPair key = new VertexPair( i, j );

	// If we have already started this, return what we
	// were doing. (May still be in progress.)
	// 
	// If we haven't started, go ahead and start. . .
	if (allPaths.containsKey( key )) {
	    return (Set) allPaths.get( key );
	} else {
	    allPaths.put( key, RC );
	}

	Iterator outbounds = graph.getOutbound(i).iterator();

	while (outbounds.hasNext()) {
	    Edge outbound = (Edge) outbounds.next();
	    if (graph.getTarget( outbound ) == j) {
		RC.add( new PathImpl( i, j, outbound ));
	    }
	}

	Iterator ks = graph.getVertices().iterator();
	while (ks.hasNext()) {
	    Vertex k = (Vertex) ks.next();
	    if (k != i && k != j) {
		appendPaths( RC, 
			     getAllPaths( i, k ), 
			     getAllPaths( k, j ));
	    }
	}

	allPaths.put( key, RC );
	return RC;
    }

    public void appendPaths( Set RC, Set iks, Set kjs ) {
	Iterator ikPaths = iks.iterator();
	while (ikPaths.hasNext()) {
	    PathImpl ik = (PathImpl) ikPaths.next();

	    Iterator kjPaths = kjs.iterator();
	    while (kjPaths.hasNext()) {
		PathImpl kj = (PathImpl) kjPaths.next();
		RC.add( ik.append( kj ));
	    }
	}
    }
}



