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

import java.util.Map;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.graph.*;

public class DataFlowSolutions
{
    private Map inValues = new HashMap(); // VERTEX X BITSET
    private Map outValues = new HashMap();

    public DataFlowSolutions( DirectedGraph graph,
			      DataFlowEquations eq ) {
	calculateDataFlow( graph, eq );
    }

    private void calculateDataFlow( DirectedGraph graph,
				    DataFlowEquations eq ) {
	Iterator vertices = graph.getVertices().iterator();
	while (vertices.hasNext()) {
	    Vertex v = (Vertex) vertices.next();
	    inValues.put( v, new BitSet() );   // Initialize everything to 
	    outValues.put( v, new BitSet() );  // empty
	}

	boolean isOK = true;
	while (isOK) {
	    vertices = graph.getVertices().iterator();
	    isOK = false;
	    while (vertices.hasNext()) {
		Vertex v = (Vertex) vertices.next();

		BitSet out = new BitSet();
		out.or( (BitSet) inValues.get( v ));
		out.or( eq.generates( v ));
		out.andNot( eq.kills( v ));
		
		/*
		System.err.println("Old Out: " + v + ":" + outValues.get( v ));
		System.err.println("New Out: " + v + ":" + out);
		*/
		if (!out.equals( outValues.get( v ))) {
		    isOK = true;
		    outValues.put( v, out );

		    Iterator outbound = graph.getOutbound( v ).iterator();
		    while (outbound.hasNext()) {
			Vertex target = 
			    graph.getTarget( (Edge) outbound.next() );
			
			BitSet in = (BitSet) inValues.get( target );
			in.or( out );
			inValues.put( target, in );
			/*
			System.err.println("Old In: " + target + ":" + 
					   inValues.get( target ));
			System.err.println("New In: " + target + ":" + in );
			*/
		    }
		}
	    }
	}
    }

    public BitSet reaches( Vertex vertex ) {
	return (BitSet) inValues.get( vertex );
    }

    public BitSet leaves( Vertex vertex ) {
	return (BitSet) outValues.get( vertex );
    }
}
