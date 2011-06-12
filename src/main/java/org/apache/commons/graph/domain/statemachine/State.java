package org.apache.commons.graph.domain.statemachine;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class State
     implements Vertex, Labeled
{
    private String name;
    private StateMachine subMachine = null;

    /**
     * Constructor for the State object
     *
     * @param name
     */
    public State(String name)
    {
        this.name = name;
    }

    /**
     * Gets the name attribute of the State object
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the submachine attribute of the State object
     */
    public void setSubmachine(StateMachine subMachine)
    {
        this.subMachine = subMachine;
    }

    /**
     * Gets the submachine attribute of the State object
     */
    public StateMachine getSubmachine()
    {
        return this.subMachine;
    }

}
