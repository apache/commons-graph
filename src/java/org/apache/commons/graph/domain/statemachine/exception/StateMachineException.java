package org.apache.commons.graph.domain.statemachine.exception;

import org.apache.commons.graph.exception.GraphException;

/**
 * Description of the Class
 */
public class StateMachineException
     extends GraphException
{
    /**
     * Constructor for the StateMachineException object
     */
    public StateMachineException()
    {
        super();
    }

    /**
     * Constructor for the StateMachineException object
     *
     * @param msg
     */
    public StateMachineException(String msg)
    {
        super(msg);
    }

    /**
     * Constructor for the StateMachineException object
     *
     * @param t
     */
    public StateMachineException(Throwable t)
    {
        super(t);
    }
}
