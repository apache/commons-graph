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

import static java.lang.Math.floor;
import static java.lang.Math.log;
import static java.lang.Math.sqrt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/**
 * A Fibonacci Heap implementation based on
 * <a href="http://staff.ustc.edu.cn/~csli/graduate/algorithms/book6/chap21.htm">University of Science and Technology of
 * China</a> lesson.
 *
 * <b>Note</b>: this class is NOT thread safe!
 *
 * @param <E> the type of elements held in this collection.
 */
public final class FibonacciHeap<E>
    implements Queue<E>
{

    /**
     * The Phi constant value.
     */
    private static final double LOG_PHI = log( ( 1 + sqrt( 5 ) ) / 2 );

    /**
     * A simple index of stored elements.
     */
    private final Set<E> elementsIndex = new HashSet<E>();

    /**
     * The comparator, or null if priority queue uses elements'
     * natural ordering.
     */
    private final Comparator<? super E> comparator;

    /**
     * The number of nodes currently in {@code H} is kept in {@code n[H]}.
     */
    private int size = 0;

    /**
     * {@code t(H)} the number of trees in the root list.
     */
    private int trees = 0;

    /**
     * {@code m(H)} the number of marked nodes in {@code H}.
     */
    private int markedNodes = 0;

    /**
     * The root of the tree containing a minimum key {@code min[H]}.
     */
    private FibonacciHeapNode<E> minimumNode;

    /**
     * Creates a {@link FibonacciHeap} that orders its elements according to their natural ordering.
     */
    public FibonacciHeap()
    {
        this( null );
    }

    /**
     * Creates a {@link FibonacciHeap} that orders its elements according to the specified comparator.
     *
     * @param comparator the comparator that will be used to order this queue.
     *                   If null, the natural ordering of the elements will be used.
     */
    public FibonacciHeap( /* @Nullable */Comparator<? super E> comparator )
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

        // FIB-HEAP-INSERT(H, x)

        // p[x] <- NIL
        // child[x] <- NIL
        // left[x] <- x
        // right[x] <- x
        // mark[x] <- FALSE
        addNode( new FibonacciHeapNode<E>( e ) );

        elementsIndex.add( e );

        // n[H] <- n[H] + 1
        size++;
        trees++;

        return true;
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
        minimumNode = null;
        size = 0;
        trees = 0;
        markedNodes = 0;
    }

    /**
     * {@inheritDoc}
     */
    public boolean contains( Object o )
    {
        if ( o == null )
        {
            return false;
        }

        return elementsIndex.contains( o );
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsAll( Collection<?> c )
    {
        if ( c == null )
        {
            return false;
        }

        for ( Object o : c )
        {
            if ( !contains( o ) )
            {
                return false;
            }
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty()
    {
        return minimumNode == null;
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
        if ( isEmpty() )
        {
            throw new NoSuchElementException();
        }
        return peek();
    }

    /**
     * {@inheritDoc}
     */
    public boolean offer( E e )
    {
        return add( e );
    }

    /**
     * {@inheritDoc}
     */
    public E peek()
    {
        if ( isEmpty() )
        {
            return null;
        }

        return minimumNode.getElement();
    }

    /**
     * {@inheritDoc}
     */
    public E poll()
    {
        // FIB-HEAP-EXTRACT-MIN(H)

        if ( isEmpty() )
        {
            return null;
        }

        // z <- min[H]
        FibonacciHeapNode<E> z = minimumNode;

        // for each child x of z
        if ( z.getDegree() > 0 )
        {
            FibonacciHeapNode<E> x = z.getChild();
            FibonacciHeapNode<E> tempRight;

            do
            {
                tempRight = x.getRight();

                // remove x from child list
                x.getLeft().setRight( x.getRight() );
                x.getRight().setLeft( x.getLeft() );

                // add x to the root list of H
                z.getLeft().setRight( x );
                x.setLeft( z.getLeft() );
                z.setLeft( x );
                x.setRight( z );

                // p[x] <- NIL
                x.setParent( null );

                x = tempRight;
            }
            while ( x != z.getChild() );
        }

        // remove z from the root list of H
        z.getLeft().setRight( z.getRight() );
        z.getRight().setLeft( z.getLeft() );

        // if z = right[z]
        if ( z == z.getRight() )
        {
            // min[H] <- NIL
            minimumNode = null;
        }
        else
        {
            // min[H] <- right[z]
            minimumNode = z.getRight();
            // CONSOLIDATE(H)
            consolidate();
        }

        // n[H] <- n[H] - 1
        size--;
        trees--;

        E minimum = z.getElement();
        elementsIndex.remove( minimum );
        return minimum;
    }

    /**
     * {@inheritDoc}
     */
    public E remove()
    {
        // FIB-HEAP-EXTRACT-MIN(H)

        if ( isEmpty() )
        {
            throw new NoSuchElementException();
        }

        return poll();
    }

    /**
     * Implements the {@code CONSOLIDATE(H)} function.
     */
    private void consolidate()
    {
        if ( isEmpty() )
        {
            return;
        }

        // D( n[H] ) <= log_phi( n[H] )
        // -> log_phi( n[H] ) = log( n[H] ) / log( phi )
        // -> D( n[H] ) = log( n[H] ) / log( phi )
        int arraySize = ( (int) floor( log( size ) / LOG_PHI ) );

        // for i <- 0 to D(n[H])
        List<FibonacciHeapNode<E>> nodeSequence = new ArrayList<FibonacciHeapNode<E>>( arraySize );
        for ( int i = 0; i < arraySize; i++ )
        {
            // A[i] <- NIL
            nodeSequence.add( i, null );
        }

        // for each node x in the root list of H
        FibonacciHeapNode<E> x = minimumNode;
        do
        {
            // d <- degree[x]
            int degree = x.getDegree();

            // while A[d] != NIL
            while ( nodeSequence.get( degree ) != null )
            {
                // do y <- A[d]
                FibonacciHeapNode<E> y = nodeSequence.get( degree );

                // if key[x] > key[y]
                if ( compare( x, y ) > 0 )
                {
                    // exchange x <-> y
                    FibonacciHeapNode<E> pointer = y;
                    y = x;
                    x = pointer;
                }

                // FIB-HEAP-LINK(H,y,x)
                link( y, x );

                // A[d] <- NIL
                nodeSequence.set( degree, null );

                // d <- d + 1
                degree++;
            }

            // A[d] <- x
            nodeSequence.set( degree, x );

            x = x.getRight();
        }
        while ( x != minimumNode );

        // min[H] <- NIL
        minimumNode = null;

        // for i <- 0 to D(n[H])
        for ( FibonacciHeapNode<E> pointer : nodeSequence )
        {
            // if A[i] != NIL
            if ( pointer != null )
            {
                addNode( pointer );
            }
        }
    }

    /**
     * Implements the {@code FIB-HEAP-LINK(H, y, x)} function.
     *
     * @param y the node has to be removed from the root list
     * @param x the node has to to become fater of {@code y}
     */
    private void link( FibonacciHeapNode<E> y, FibonacciHeapNode<E> x )
    {
        // remove y from the root list of H
        y.getLeft().setRight( y.getRight() );
        y.getRight().setLeft( y.getLeft() );

        // make y a child of x, incrementing degree[x]
        x.setChild( y );
        y.setParent( x );
        x.incraeseDegree();

        // mark[y] <- FALSE
        y.setMarked( false );

        trees--;
    }

    /**
     * Implements the {@code CUT(H,x,y)} function.
     *
     * @param x the node has to be removed from {@code y} children
     * @param y the node has to be updated
     */
    private void cut( FibonacciHeapNode<E> x, FibonacciHeapNode<E> y )
    {
        // remove x from the child list of y, decrementing degree[y]
        x.getLeft().setRight( x.getRight() );
        x.getRight().setLeft( x.getLeft() );
        y.decraeseDegree();

        // p[x] <- NIL
        x.setParent( null );

        // mark[x] <- FALSE
        x.setMarked( false );
    }

    /**
     * Implements the {@code CASCADING-CUT(H,y)} function.
     *
     * @param y the target node to apply CASCADING-CUT
     */
    private void cascadingCut( FibonacciHeapNode<E> y )
    {
        // z <- p[y]
        FibonacciHeapNode<E> z = y.getParent();

        // if z <- NIL
        if ( z != null )
        {
            // mark[y] = FALSE
            if ( !y.isMarked() )
            {
                y.setMarked( true );
            }
            else
            {
                cut( y, z );
                cascadingCut( z );
            }
        }
    }

    /**
     * The potential of Fibonacci heap {@code H} is then defined by
     * {@code t(H) + 2m(H)}.
     *
     * @return The potential of this Fibonacci heap.
     */
    public int potential()
    {
        return trees + 2 * markedNodes;
    }

    /**
     * Adds a node in the current structure.
     *
     * @param node the node has to be added.
     * @see #offer(Object)
     * @see #add(Object)
     */
    private void addNode( FibonacciHeapNode<E> node )
    {
        // if min[H] = NIL
        if ( isEmpty() )
        {
            // then min[H] <- x
            minimumNode = node;
        }
        else
        {
            // concatenate the root list containing x with root list H
            minimumNode.getLeft().setRight( node );
            node.setLeft( minimumNode.getLeft() );
            node.setRight( minimumNode );
            minimumNode.setLeft( node );

            // if key[x] < key[min[H]]
            if ( compare( node, minimumNode ) < 0 )
            {
                // then min[H] <- x
                minimumNode = node;
            }
        }
    }

    /**
     * Compare the given objects according to to the specified comparator if not null,
     * according to their natural ordering otherwise.
     *
     * @param o1 the first {@link FibonacciHeap} node to be compared
     * @param o2 the second {@link FibonacciHeap} node to be compared
     * @return a negative integer, zero, or a positive integer as the first argument is
     *         less than, equal to, or greater than the second
     */
    private int compare( FibonacciHeapNode<E> o1, FibonacciHeapNode<E> o2 )
    {
        if ( comparator != null )
        {
            return comparator.compare( o1.getElement(), o2.getElement() );
        }
        @SuppressWarnings( "unchecked" ) // it will throw a ClassCastException at runtime
        Comparable<? super E> o1Comparable = (Comparable<? super E>) o1.getElement();
        return o1Comparable.compareTo( o2.getElement() );
    }

}
