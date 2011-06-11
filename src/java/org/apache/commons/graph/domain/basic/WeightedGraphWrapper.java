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
 * This is a simple wrapper to wrap around a graph, and create a weighted graph.
 */

import java.util.Map;
import java.util.HashMap;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 */
public class WeightedGraphWrapper
     extends GraphWrapper
     implements WeightedGraph
{
    private Map weights = new HashMap();// EDGE X WEIGHT

    /**
     * Constructor for the WeightedGraphWrapper object
     *
     * @param graph
     */
    public WeightedGraphWrapper(Graph graph)
    {
        super(graph);
    }

    /**
     * Gets the weight attribute of the WeightedGraphWrapper object
     */
    public double getWeight(Edge e)
    {
        if (weights.containsKey(e))
        {
            return ((Double) weights.get(e)).doubleValue();
        }
        else
        {
            return 1.0;
        }
    }

    /**
     * Sets the weight attribute of the WeightedGraphWrapper object
     */
    public void setWeight(Edge e, double weight)
    {
        weights.put(e, new Double(weight));
    }

}
