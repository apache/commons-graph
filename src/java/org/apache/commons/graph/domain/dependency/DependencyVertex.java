package org.apache.commons.graph.domain.dependency;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class DependencyVertex
     implements Vertex
{
    private Object value;

    /**
     * Constructor for the DependencyVertex object
     *
     * @param value
     */
    public DependencyVertex(Object value)
    {
        this.value = value;
    }

    /**
     * Gets the value attribute of the DependencyVertex object
     */
    public Object getValue()
    {
        return value;
    }
}
