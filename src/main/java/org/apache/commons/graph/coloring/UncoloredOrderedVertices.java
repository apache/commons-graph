package org.apache.commons.graph.coloring;

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

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.graph.Vertex;

/**
 * 
 *
 * @param <V>
 */
final class UncoloredOrderedVertices<V extends Vertex>
    implements Comparator<Integer>, Iterable<V>
{

    private final Map<Integer, Set<V>> orderedVertices = new TreeMap<Integer, Set<V>>( this );

    public void addVertexDegree( V v, Integer degree )
    {
        Set<V> vertices = orderedVertices.get( degree );

        if ( vertices == null )
        {
            vertices = new HashSet<V>();
        }

        vertices.add( v );
        orderedVertices.put( degree, vertices );
    }

    /**
     * {@inheritDoc}
     */
    public int compare( Integer o1, Integer o2 )
    {
        return o2.compareTo( o1 );
    }

    public Iterator<V> iterator()
    {
        return new Iterator<V>()
        {

            private Iterator<Integer> keys = orderedVertices.keySet().iterator();

            private Iterator<V> pending = null;

            private V next = null;

            public boolean hasNext()
            {
                if ( next != null )
                {
                    return true;
                }

                while ( ( pending == null ) || !pending.hasNext() )
                {
                    if ( !keys.hasNext() )
                    {
                        return false;
                    }
                    pending = orderedVertices.get( keys.next() ).iterator();
                }

                next = pending.next();
                return true;
            }

            public V next()
            {
                if ( !hasNext() )
                {
                    throw new NoSuchElementException();
                }
                V returned = next;
                next = null;
                return returned;
            }

            public void remove()
            {
                pending.remove();
            }

        };
    }

}
