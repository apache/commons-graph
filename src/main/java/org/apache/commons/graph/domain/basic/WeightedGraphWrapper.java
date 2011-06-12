package org.apache.commons.graph.domain.basic;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.util.Map;
import java.util.HashMap;

import org.apache.commons.graph.*;

/**
 * Description of the Class
 *
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public class WeightedGraphWrapper<V extends Vertex, WE extends WeightedEdge<V>>
    extends GraphWrapper<V, WE>
    implements WeightedGraph<V, WE>
{

    private final Map<WE, Number> weights = new HashMap<WE, Number>(); // EDGE X WEIGHT

    /**
     * Constructor for the WeightedGraphWrapper object
     *
     * @param graph
     */
    public WeightedGraphWrapper( Graph<V, WE> graph )
    {
        super( graph );
    }

    /**
     * {@inheritDoc}
     */
    public Number getWeight( WE e )
    {
        if ( weights.containsKey( e ) )
        {
            return weights.get( e );
        }
        return 1.0;
    }

    /**
     * Sets the weight attribute of the WeightedGraphWrapper object
     *
     * @param e
     * @param weight
     */
    public void setWeight( WE e, Number weight )
    {
        weights.put( e, weight );
    }

}
