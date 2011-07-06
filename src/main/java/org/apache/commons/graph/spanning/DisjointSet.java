package org.apache.commons.graph.spanning;

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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.graph.Vertex;

/**
 * Simple <a href="http://en.wikipedia.org/wiki/Disjoint-set_data_structure">Disjoint-set</a> implementation.
 *
 * @param <V> the Graph vertices type.
 */
final class DisjointSet<V extends Vertex>
{

    private final Map<V, DisjointSetNode<V>> disjointSets = new HashMap<V, DisjointSetNode<V>>();

    /**
     * Performs the find applying the <i>path compression</i>.
     *
     * @param vertex
     * @return
     */
    public V find( V vertex )
    {
        DisjointSetNode<V> vertexNode = find( getNode( vertex ) );

        if ( vertexNode == vertexNode.getParent() )
        {
            return vertexNode.getVertex();
        }

        vertexNode.setParent( find( vertexNode.getParent() ) );

        return vertexNode.getParent().getVertex();
    }

    /**
     * Performs the find applying the <i>path compression</i>.
     *
     * @param node
     * @return
     */
    private DisjointSetNode<V> find( DisjointSetNode<V> node )
    {
        if ( node == node.getParent() )
        {
            return node;
        }
        return find( node.getParent() );
    }

    /**
     * Performs the merge by applying the <i>union by rank</i>.
     *
     * @param u
     * @param v
     */
    public void union( V u, V v )
    {
        DisjointSetNode<V> uRoot = find( getNode( u ) );
        DisjointSetNode<V> vRoot = find( getNode( v ) );

        if ( uRoot == vRoot )
        {
            return;
        }

        int comparison = uRoot.compareTo( vRoot );
        if ( comparison < 0 )
        {
            uRoot.setParent( vRoot );
        }
        else if ( comparison > 0 )
        {
            vRoot.setParent( uRoot );
        }
        else
        {
            vRoot.setParent( uRoot );
            uRoot.increaseRank();
        }
    }

    private DisjointSetNode<V> getNode( V vertex )
    {
        DisjointSetNode<V> node = disjointSets.get( vertex );

        if ( node == null )
        {
            node = new DisjointSetNode<V>( vertex );
            disjointSets.put( vertex, node );
        }

        return node;
    }

}
