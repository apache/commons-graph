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
 * A Graph data structure consists of a finite (and possibly mutable) set of ordered pairs,
 * called {@link Edge}s or arcs, of certain entities called {@link Vertex} or node.
 * As in mathematics, an {@link Edge} {@code (x,y)} is said to point or go from {@code x} to {@code y}.
 */
public interface Graph
{
    /**
     * getVertices - Returns the total set of Vertices in the graph.
     */
    Set getVertices();

    /**
     * getEdges - Returns the total set of Edges in the graph.
     */
    Set getEdges();

    /**
     * getEdges( Vertex ) - This method will return all edges which touch this
     * vertex.
     */
    Set getEdges(Vertex v);

    /**
     * getVertices( Edge ) - This method will return the set of Verticies on
     * this Edge. (2 for normal edges, > 2 for HyperEdges.)
     */
    Set getVertices(Edge e);
}

