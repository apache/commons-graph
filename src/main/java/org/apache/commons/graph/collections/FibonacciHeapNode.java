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

final class FibonacciHeapNode<T>
{

    private final T value;

    private FibonacciHeapNode<T> parent;

    private FibonacciHeapNode<T> previous;

    private FibonacciHeapNode<T> next;

    private FibonacciHeapNode<T> child;

    private int degree = 0;

    private boolean marked = false;

    public FibonacciHeapNode( T value )
    {
        this.value = value;
    }

    public FibonacciHeapNode<T> getParent()
    {
        return parent;
    }

    public void setParent( FibonacciHeapNode<T> parent )
    {
        this.parent = parent;
    }

    public FibonacciHeapNode<T> getPrevious()
    {
        return previous;
    }

    public void setPrevious( FibonacciHeapNode<T> previous )
    {
        this.previous = previous;
    }

    public FibonacciHeapNode<T> getNext()
    {
        return next;
    }

    public void setNext( FibonacciHeapNode<T> next )
    {
        this.next = next;
    }

    public FibonacciHeapNode<T> getChild()
    {
        return child;
    }

    public void setChild( FibonacciHeapNode<T> child )
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

    public T getValue()
    {
        return value;
    }

}
