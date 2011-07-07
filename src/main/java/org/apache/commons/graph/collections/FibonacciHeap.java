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

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;

/**
 * A Fibonacci Heap implementation based on
 * <a href="http://www.cs.princeton.edu/~wayne/cs423/fibonacci/FibonacciHeapAlgorithm.html">Princeton</a> lesson.
 */
public final class FibonacciHeap<E>
    implements Queue<E>
{

    /**
     * The comparator, or null if priority queue uses elements'
     * natural ordering.
     */
    private final Comparator<? super E> comparator;

    private int size = 0;

    private FibonacciHeapNode<E> root;

    public FibonacciHeap()
    {
        this(null);
    }

    public FibonacciHeap( Comparator<? super E> comparator )
    {
        this.comparator = comparator;
    }

    /**
     * {@inheritDoc}
     */
    public boolean add( E e )
    {
        if ( e == null )
        {
            throw new NullPointerException();
        }

        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean addAll( Collection<? extends E> c )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public void clear()
    {
        
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains( Object o )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAll( Collection<?> c )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty()
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public Iterator<E> iterator()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean remove( Object o )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean removeAll( Collection<?> c )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public boolean retainAll( Collection<?> c )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public int size()
    {
        return size;
    }

    /**
     * {@inheritDoc}
     */
    public Object[] toArray()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public <T> T[] toArray( T[] a )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public E element()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public boolean offer( E arg0 )
    {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    public E peek()
    {
        if ( size == 0 )
        {
            return null;
        }
        return root.getValue();
    }

    /**
     * {@inheritDoc}
     */
    public E poll()
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    public E remove()
    {
        return null;
    }

}
