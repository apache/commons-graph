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
 * The class {@link LongWeightBaseOperations} provides operations and properties
 * for weights of type {@link Long}.
 */
public class LongWeightBaseOperations
    implements OrderedMonoid<Long>
{

    private static final long serialVersionUID = 3149327896191098756L;

    /**
     * {@inheritDoc}
     */
    public Long identity()
    {
        return 0L;
    }

    /**
     * {@inheritDoc}
     */
    public Long append( Long s1, Long s2 )
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
    public Long inverse( Long element )
    {
        return -element;
    }

    /**
     * {@inheritDoc}
     */
    public int compare( Long s1, Long s2 )
    {
        return s1.compareTo( s2 );
    }

}
