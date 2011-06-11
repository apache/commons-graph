package org.apache.commons.graph.domain.basic;

/**
 * GraphWrapper This is a superclass of all Wrapper implementations. It
 * basically does a redirection to the graph.
 */

import java.util.Set;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class GraphWrapper
{
    private Graph impl = null;

    /**
     * Constructor for the GraphWrapper object
     *
     * @param impl
     */
    public GraphWrapper(Graph impl)
    {
        this.impl = impl;
    }

    /**
     * Constructor for the GraphWrapper object
     */
    public GraphWrapper() { }

    /**
     * Sets the graph attribute of the GraphWrapper object
     */
    public void setGraph(Graph impl)
    {
        this.impl = impl;
    }

    // Graph Implementation. . .
    /**
     * Gets the vertices attribute of the GraphWrapper object
     */
    public Set getVertices()
    {
        return impl.getVertices();
    }

    /**
     * Gets the edges attribute of the GraphWrapper object
     */
    public Set getEdges()
    {
        return impl.getEdges();
    }

    /**
     * Gets the vertices attribute of the GraphWrapper object
     */
    public Set getVertices(Edge e)
    {
        return impl.getVertices(e);
    }

    /**
     * Gets the edges attribute of the GraphWrapper object
     */
    public Set getEdges(Vertex v)
    {
        return impl.getEdges(v);
    }
}
