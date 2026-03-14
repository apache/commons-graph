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

import static java.util.Collections.sort;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Random;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The {@link FibonacciHeap} test case taking inspiration from
 * <a href="http://langrsoft.com/jeff/2011/11/test-driving-a-heap-based-priority-queue/">Test-Driving a Heap-Based Priority Queue</a>
 */
public final class FibonacciHeapTestCase
{

    private Queue<Integer> queue;

    @Test
    public void elementThrowsException()
    {
        assertThrows(NoSuchElementException.class, () -> queue.element());
    }

    @BeforeEach
    public void setUp()
    {
        queue = new FibonacciHeap<Integer>();
    }

    @AfterEach
    public void tearDown()
    {
        queue = null;
    }

    @Test
    public void testAddAllAndContinsItem()
    {
        Collection<Integer> c = new ArrayList<Integer>();
        
        c.add( 50 );
        c.add( 100 );
        c.add( 20 );
        c.add( 21 );

        queue.addAll( c );
            
        assertFalse(queue.isEmpty());
        assertTrue(queue.containsAll(c));
        
        assertTrue(queue.contains(100));
        assertTrue(queue.contains(21));
        assertTrue(queue.contains(50));
        assertTrue(queue.contains(20));
    }

    @Test
    public void testClearQueue()
    {
        final Random r = new Random( System.currentTimeMillis() );
        for ( int i = 0; i < 1000; i++ )
        {
            Integer number = new Integer( r.nextInt( 10000 ) );
            queue.add( number );
        }

        assertFalse( queue.isEmpty() );
        queue.clear();
        assertTrue( queue.isEmpty() );
    }

    @Test
    public void testEmptyWhenCreated()
    {
        assertTrue( queue.isEmpty() );
        assertNull( queue.poll() );
    }

    @Test
    public void testInsertSameValuesAndReturnsOrderedItems()
    {
        queue.add( 50 );
        queue.add( 100 );
        queue.add( 50 );

        assertEquals(50, queue.poll());
        assertEquals(50, queue.poll());
        assertEquals(100, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testInsertSingleItem()
    {
        queue.add( 50 );

        assertEquals(50, queue.poll());
        assertEquals(true, queue.isEmpty());
    }

    @Test
    public void testIsEmptyAfterSoleElementRemoved()
    {
        queue.add( 50 );
        queue.poll();

        assertTrue(queue.isEmpty());
    }

    @Test
    public void testNoLongerEmptyAfterAdd()
    {
        queue.add( 50 );

        assertFalse(queue.isEmpty());
    }

    @Test
    public void testOfferPeekAndElement()
    {
        queue.offer( 50 );
        queue.offer( 100 );
        queue.offer( 20 );
        queue.offer( 21 );

        assertEquals(false, queue.isEmpty());
        assertEquals(20, queue.peek());
        assertEquals(20, queue.element());
        assertEquals(4, queue.size());
    }

    @Test
    public void testReturnsOrderedItems()
    {
        queue.add( 100 );
        queue.add( 50 );

        assertEquals(50, queue.poll());
        assertEquals(100, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    public void testReturnsOrderedItemsFromRandomInsert()
    {
        final Random r = new Random( System.currentTimeMillis() );
        final List<Integer> expected = new ArrayList<Integer>();

        for ( int i = 0; i < 1000; i++ )
        {
            Integer number = new Integer( r.nextInt( 10000 ) );
            expected.add( number );

            queue.add( number );
        }
        sort( expected );

        for ( Integer integer : expected )
        {
            Integer i = queue.poll();
            assertEquals(integer, i);
        }
        
        assertTrue(queue.isEmpty());
    }
    
    @Test
    public void testSingletonQueueReturnsSoleItemOnPoll()
    {
        queue.add( 50 );

        assertEquals(50, queue.poll());
    }
}
