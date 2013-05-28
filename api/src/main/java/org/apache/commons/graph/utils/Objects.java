package org.apache.commons.graph.utils;

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

/**
 * Object simple utility methods.
 */
public final class Objects
{

    /**
     * Hidden constructor, this class must not be instantiated.
     */
    private Objects()
    {
        // do nothing
    }

    /**
     * Computes a hashCode given the input objects.
     *
     * @param initialNonZeroOddNumber a non-zero, odd number used as the initial value.
     * @param multiplierNonZeroOddNumber a non-zero, odd number used as the multiplier.
     * @param objs the objects to compute hash code.
     * @return the computed hashCode.
     */
    public static int hash( int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object...objs )
    {
        int result = initialNonZeroOddNumber;
        for ( Object obj : objs )
        {
            result = multiplierNonZeroOddNumber * result + ( obj != null ? obj.hashCode() : 0 );
        }
        return result;
    }

    /**
     * Verifies input objects are equal.
     *
     * @param o1 the first argument to compare
     * @param o2 the second argument to compare
     * @return true, if the input arguments are equal, false otherwise.
     */
    public static <O> boolean eq( O o1, O o2 )
    {
        return o1 != null ? o1.equals( o2 ) : o2 == null;
    }

}
