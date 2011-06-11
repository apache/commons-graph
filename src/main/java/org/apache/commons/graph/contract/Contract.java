package org.apache.commons.graph.contract;

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Interface
 */
public interface Contract
{
    /**
     * The impl that gets passed in is read-only. This is the representation of
     * the graph you should work off of. If an edge or vertex addition is
     * illegal to the contract, raise a GraphException with and explanation.
     */
    public void setImpl(DirectedGraph impl);

    /**
     * getInterface This returns the marker interface which is associated with
     * the Contract. For instance, AcyclicContract will return AcyclicGraph
     * here.
     */
    public Class getInterface();

    /**
     * verify - This verifies that the graph it is working on complies.
     */
    public void verify()
        throws GraphException;

    /**
     * Adds a feature to the Edge attribute of the Contract object
     */
    public void addEdge(Edge e,
                        Vertex start,
                        Vertex end)
        throws GraphException;

    /**
     * Adds a feature to the Vertex attribute of the Contract object
     */
    public void addVertex(Vertex v)
        throws GraphException;

    /**
     * Description of the Method
     */
    public void removeEdge(Edge e)
        throws GraphException;

    /**
     * Description of the Method
     */
    public void removeVertex(Vertex v)
        throws GraphException;

}
