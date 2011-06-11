package org.apache.commons.graph.domain.dependency;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class Dependency implements Edge
{
    private Object head = null;
    private Object dep = null;

    /**
     * Constructor for the Dependency object
     *
     * @param head
     * @param dep
     */
    public Dependency(Object head,
                      Object dep)
    {
        this.head = head;
        this.dep = dep;
    }

    /**
     * Description of the Method
     */
    public String description()
    {
        return head + " depends on " + dep;
    }
}
