package org.apache.commons.graph.collections;

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

import static org.apache.commons.graph.collections.FibonacciNumbers.fibonacci;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public final class FibonacciNumbersTestCase
{

    @Test
    public void nthFibonacciNumberAssertion()
    {
        assertEquals( 0, fibonacci( 0 ) );
        assertEquals( 1, fibonacci( 1 ) );
        assertEquals( 1, fibonacci( 2 ) );
        assertEquals( 8, fibonacci( 6 ) );
        assertEquals( 144, fibonacci( 12 ) );
    }

    @Test
    public void nthNotPreviouslyExplicitlyRequestedFibonacciNumberAssertion()
    {
        assertEquals( 2, fibonacci( 3 ) );
        assertEquals( 3, fibonacci( 4 ) );
        assertEquals( 5, fibonacci( 5 ) );
        assertEquals( 8, fibonacci( 6 ) );
    }

}
