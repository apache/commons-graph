package org.apache.commons.graph.domain.basic;

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
 * DirectedGraphWrapper This is a superclass to all wrappers that work over
 * DirectedGraphs.
 */

import java.util.Set;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class DirectedGraphWrapper
     extends GraphWrapper
     implements DirectedGraph
{
    private DirectedGraph impl = null;

    /**
     * Constructor for the DirectedGraphWrapper object
     *
     * @param graph
     */
    public DirectedGraphWrapper(DirectedGraph graph)
    {
        super(graph);
        impl = graph;
    }

    /**
     * Constructor for the DirectedGraphWrapper object
     */
    public DirectedGraphWrapper()
    {
        super();
    }

    /**
     * Sets the dirGraph attribute of the DirectedGraphWrapper object
     */
    public void setDirGraph(DirectedGraph graph)
    {
        impl = graph;
        setGraph(graph);
    }

    /**
     * Gets the inbound attribute of the DirectedGraphWrapper object
     */
    public Set getInbound(Vertex v)
    {
        return impl.getInbound(v);
    }

    /**
     * Gets the outbound attribute of the DirectedGraphWrapper object
     */
    public Set getOutbound(Vertex v)
    {
        return impl.getOutbound(v);
    }

    /**
     * Gets the source attribute of the DirectedGraphWrapper object
     */
    public Vertex getSource(Edge e)
    {
        return impl.getSource(e);
    }

    /**
     * Gets the target attribute of the DirectedGraphWrapper object
     */
    public Vertex getTarget(Edge e)
    {
        return impl.getTarget(e);
    }

}







