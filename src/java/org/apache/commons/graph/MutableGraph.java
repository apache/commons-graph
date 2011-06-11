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

import org.apache.commons.graph.exception.*;

/**
 * Description of the Interface
 */
public interface MutableGraph
     extends Graph
{
    /**
     * Adds a feature to the Vertex attribute of the MutableGraph object
     */
    public void addVertex(Vertex v)
        throws GraphException;

    /**
     * Description of the Method
     */
    public void removeVertex(Vertex v)
        throws GraphException;

    /**
     * Adds a feature to the Edge attribute of the MutableGraph object
     */
    public void addEdge(Edge e)
        throws GraphException;

    /**
     * Description of the Method
     */
    public void removeEdge(Edge e)
        throws GraphException;

    /**
     * Description of the Method
     */
    public void connect(Edge e, Vertex v)
        throws GraphException;

    /**
     * Description of the Method
     */
    public void disconnect(Edge e, Vertex v)
        throws GraphException;
}
