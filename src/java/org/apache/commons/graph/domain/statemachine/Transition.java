package org.apache.commons.graph.domain.statemachine;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class Transition
     implements Edge, Named
{
    private String name;
    private State source;
    private State target;

    private String action = null;
    private String guard = null;
    private String output = null;
  private String trigger = null;

    /**
     * Description of the Field
     */
    public final static String EPSILON = "\u03B5";

    /**
     * Constructor for the Transition object
     *
     * @param name
     * @param source
     * @param target
     */
    public Transition(String name,
                      State source,
                      State target)
    {
        this.name = name;
        this.source = source;
        this.target = target;
    }

    /**
     * Gets the name attribute of the Transition object
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the source attribute of the Transition object
     */
    public State getSource()
    {
        return source;
    }

    /**
     * Gets the target attribute of the Transition object
     */
    public State getTarget()
    {
        return target;
    }

    /**
     * Sets the action attribute of the Transition object
     */
    public void setTrigger( String trigger )
    {
        this.trigger = trigger;
    }

    /**
     * Sets the action attribute of the Transition object
     */
    public void setAction( String action )
    {
        this.action = action;
    }

    /**
     * Gets the action attribute of the Transition object
     */
    public String getAction()
    {
        return action;
    }

    /**
     * Sets the guard attribute of the Transition object
     */
    public void setGuard(String guard)
    {
        this.guard = guard;
    }

    /**
     * Gets the guard attribute of the Transition object
     */
    public String getGuard()
    {
        return guard;
    }

    /**
     * Gets the output attribute of the Transition object
     */
    public String getOutput()
    {
        return output;
    }

    /**
     * Sets the output attribute of the Transition object
     */
    public void setOutput(String output)
    {
        this.output = output;
    }
}

