package org.apache.commons.graph.domain.dependency.exception;

import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class CircularDependencyException
     extends CycleException
{
    /**
     * Constructor for the CircularDependencyException object
     */
    public CircularDependencyException()
    {
        super();
    }

    /**
     * Constructor for the CircularDependencyException object
     *
     * @param msg
     */
    public CircularDependencyException(String msg)
    {
        super(msg);
    }

    /**
     * Constructor for the CircularDependencyException object
     *
     * @param cause
     */
    public CircularDependencyException(Throwable cause)
    {
        super(cause);
    }
}
