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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The {@link FibonacciHeap} test case taking inspiration from
 * <a href="http://langrsoft.com/jeff/2011/11/test-driving-a-heap-based-priority-queue/">Test-Driving a Heap-Based Priority Queue</a>
 */
public final class FibonacciHeapTestCase
{

    private Queue<Integer> queue;

    @Before
    public void setUp()
    {
        queue = new FibonacciHeap<Integer>();
    }

    @After
    public void tearDown()
    {
        queue = null;
    }

    @Test
    public void emptyWhenCreated()
    {
        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void noLongerEmptyAfterAdd()
    {
        queue.add( 50 );

        assertThat( queue.isEmpty(), is( false ) );
    }

    @Test
    public void singletonQueueReturnsSoleItemOnPoll()
    {
        queue.add( 50 );

        assertThat( queue.poll(), is( 50 ) );
    }

    @Test
    public void isEmptyAfterSoleElementRemoved()
    {
        queue.add( 50 );
        queue.poll();

        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void returnsOrderedItems()
    {
        queue.add( 100 );
        queue.add( 50 );

        assertThat( queue.poll(), is( 50 ) );
        assertThat( queue.poll(), is( 100 ) );
        assertThat( queue.isEmpty(), is( true ) );
    }
    
    @Test
    public void insertSingleItem()
    {
        queue.add( 50 );

        assertThat( queue.poll(), is( 50 ) );
        assertThat( queue.isEmpty(), is( true ) );
    }
    
    @Test
    public void insertSameValuesAndReturnsOrderedItems()
    {
        queue.add( 50 );
        queue.add( 100 );
        queue.add( 50 );


        assertThat( queue.poll(), is( 50 ) );
        assertThat( queue.poll(), is( 50 ) );
        assertThat( queue.poll(), is( 100 ) );
        assertThat( queue.isEmpty(), is( true ) );
    }

    @Test
    public void returnsOrderedItemsFromRandomInsert()
    {
        final Random r = new Random( System.currentTimeMillis() );
        final List<Integer> expected = new ArrayList<Integer>();
        
        for ( int i = 0; i < 1000; i++ )
        {
            Integer number = new Integer( r.nextInt(10000) );
            expected.add( number );
            
            queue.add( number );
        }
        Collections.sort( expected );
        
        for ( Integer integer : expected )
        {
            Integer i = queue.poll();
            assertThat( i, is( integer ) );
        }
    }
}
