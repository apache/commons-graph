package org.apache.commons.graph;

/*
 * Copyright 2001-2011 The Apache Software Foundation.
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

import java.util.Set;

/**
 * Description of the Interface
 */
public interface DirectedGraph
     extends Graph
{
    /**
     * getInbound( Vertex ) Returns the set of edges which are inbound to the
     * Vertex.
     */
    public Set getInbound(Vertex v);

    /**
     * getOutbound( Vertex ) Returns the set of edges which lead away from the
     * Vertex.
     */
    public Set getOutbound(Vertex v);

    /**
     * getSource( Edge ) Returns the vertex which originates the edge.
     */
    public Vertex getSource(Edge e);

    /**
     * getTarget( Edge ) Returns the vertex which terminates the edge.
     */
    public Vertex getTarget(Edge e);
}
