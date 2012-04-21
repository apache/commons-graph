package org.apache.commons.graph.weight.primitive;

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

import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * The class {@link DoubleWeightBaseOperations} provides operations and properties
 * for weights of type {@link Double}.
 */
public class DoubleWeightBaseOperations
    implements OrderedMonoid<Double>
{
    private static final long serialVersionUID = 4440399710792243877L;

    /**
     * {@inheritDoc}
     */
    public Double identity()
    {
        return 0.0;
    }

    /**
     * {@inheritDoc}
     */
    public Double append( Double s1, Double s2 )
    {
        if ( s1 == null || s2 == null )
        {
            return null;
        }
        return s1 + s2;
    }

    /**
     * {@inheritDoc}
     */
    public Double inverse( Double element )
    {
        return -element;
    }

    /**
     * {@inheritDoc}
     */
    public int compare( Double s1, Double s2 )
    {
        return s1.compareTo( s2 );
    }

}
