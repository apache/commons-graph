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

/**
 * 
 */
final class FibonacciHeapNode<E>
{

    private final E element;

    private FibonacciHeapNode<E> parent;

    /**
     * {@code left[x]}
     */
    private FibonacciHeapNode<E> left = this;

    /**
     * {@code right[x]}
     */
    private FibonacciHeapNode<E> right = this;

    private FibonacciHeapNode<E> child;

    /**
     * The number of children in the child list of node {@code x} is stored in {@code degree[x]}.
     */
    private int degree = 0;

    /**
     * {@code mark[x]} indicates whether node {@code x} has lost a child since
     * the last time {@code x} was made the child of another node.
     */
    private boolean marked = false;

    public FibonacciHeapNode( E element )
    {
        this.element = element;
    }

    public FibonacciHeapNode<E> getParent()
    {
        return parent;
    }

    public void setParent( FibonacciHeapNode<E> parent )
    {
        this.parent = parent;
    }

    public FibonacciHeapNode<E> getLeft()
    {
        return left;
    }

    public void setLeft( FibonacciHeapNode<E> left )
    {
        this.left = left;
    }

    public FibonacciHeapNode<E> getRight()
    {
        return right;
    }

    public void setRight( FibonacciHeapNode<E> right )
    {
        this.right = right;
    }

    public FibonacciHeapNode<E> getChild()
    {
        return child;
    }

    public void setChild( FibonacciHeapNode<E> child )
    {
        this.child = child;
    }

    public int getDegree()
    {
        return degree;
    }

    public void setDegree( int degree )
    {
        this.degree = degree;
    }

    public boolean isMarked()
    {
        return marked;
    }

    public void setMarked( boolean marked )
    {
        this.marked = marked;
    }

    public E getElement()
    {
        return element;
    }

}
