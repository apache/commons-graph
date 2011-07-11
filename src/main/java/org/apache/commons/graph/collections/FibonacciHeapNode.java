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

final class FibonacciHeapNode<E>
{

    private final E value;

    private FibonacciHeapNode<E> parent;

    private FibonacciHeapNode<E> previous;

    private FibonacciHeapNode<E> next;

    private FibonacciHeapNode<E> child;

    private int degree = 0;

    private boolean marked = false;

    public FibonacciHeapNode( E value )
    {
        this.value = value;
    }

    public FibonacciHeapNode<E> getParent()
    {
        return parent;
    }

    public void setParent( FibonacciHeapNode<E> parent )
    {
        this.parent = parent;
    }

    public FibonacciHeapNode<E> getPrevious()
    {
        return previous;
    }

    public void setPrevious( FibonacciHeapNode<E> previous )
    {
        this.previous = previous;
    }

    public FibonacciHeapNode<E> getNext()
    {
        return next;
    }

    public void setNext( FibonacciHeapNode<E> next )
    {
        this.next = next;
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

    public E getValue()
    {
        return value;
    }

}
