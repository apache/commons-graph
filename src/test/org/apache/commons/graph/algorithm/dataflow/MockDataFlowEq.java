package org.apache.commons.graph.algorithm.dataflow;

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

import java.util.Map;
import java.util.BitSet;
import java.util.HashMap;

import org.apache.commons.graph.*;

public class MockDataFlowEq 
    implements DataFlowEquations
{
    private Map generates = new HashMap(); // VERTEX X BITSET
    private Map kills = new HashMap(); // VERTEX X BITSET

    public MockDataFlowEq() { }

    public void addVertex( Vertex v, BitSet generate, BitSet kill ) {
	generates.put( v, generate );
	kills.put( v, kill );
    }

    public BitSet generates( Vertex v ) {
	return (BitSet) generates.get( v );
    }

    public BitSet kills( Vertex v ) {
	return (BitSet) kills.get( v );
    }
}
