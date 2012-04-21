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
 * The class {@link IntegerWeightBaseOperations} provides operations and properties
 * for weights of type {@link Integer}.
 */
public class IntegerWeightBaseOperations
    implements OrderedMonoid<Integer>
{

    private static final long serialVersionUID = -8641477350652350485L;

    /**
     * {@inheritDoc}
     */
    public Integer identity()
    {
        return 0;
    }

    /**
     * {@inheritDoc}
     */
    public Integer append( Integer s1, Integer s2 )
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
    public Integer inverse( Integer element )
    {
        return -element;
    }

    /**
     * {@inheritDoc}
     */
    public int compare( Integer o1, Integer o2 )
    {
        return o1.compareTo( o2 );
    }

}
