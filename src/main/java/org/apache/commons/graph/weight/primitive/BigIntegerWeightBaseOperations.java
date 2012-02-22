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

import static java.math.BigInteger.ZERO;

import java.math.BigInteger;

import org.apache.commons.graph.weight.OrderedMonoid;

/**
 * The class {@link BigIntegerWeightBaseOperations} provides operations and properties
 * for weights of type {@link BigInteger}.
 */
public class BigIntegerWeightBaseOperations
    implements OrderedMonoid<BigInteger>
{

    /**
     * {@inheritDoc}
     */
    public BigInteger zero()
    {
        return ZERO;
    }

    /**
     * {@inheritDoc}
     */
    public BigInteger append( BigInteger s1, BigInteger s2 )
    {
        if ( s1 == null || s2 == null )
        {
            return null;
        }
        return s1.add( s2 );
    }

    /**
     * {@inheritDoc}
     */
    public BigInteger inverse( BigInteger element )
    {
        return element.negate();
    }

    /**
     * {@inheritDoc}
     */
    public int compare( BigInteger o1, BigInteger o2 )
    {
        return o1.compareTo( o2 );
    }

}
