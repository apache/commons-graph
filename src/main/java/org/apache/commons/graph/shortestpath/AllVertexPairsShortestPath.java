package org.apache.commons.graph.shortestpath;

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
import org.apache.commons.graph.VertexPair;
import org.apache.commons.graph.WeightedEdge;
import org.apache.commons.graph.WeightedPath;

/**
 * Represents all shortest paths between all vertex pairs calculated by {@link FloydWarshall} algorithm.
 * 
 * @param <V> the Graph vertices type
 * @param <E> the Graph edges type
 */
public final class AllVertexPairsShortestPath<V extends Vertex, WE extends WeightedEdge<Double>>
{

    private final Map<VertexPair<V>, WeightedPath<V, WE, Double>> paths = new HashMap<VertexPair<V>, WeightedPath<V, WE, Double>>();

    private final Map<VertexPair<V>, Double> shortestDistances = new HashMap<VertexPair<V>, Double>();

    /**
     * Constructor visible only inside the package
     */
    AllVertexPairsShortestPath()
    {
        // do nothing
    }

    /**
     * @param source
     * @param target
     * @param weightedPath
     */
    void addShortestPath( V source, V target, WeightedPath<V, WE, Double> weightedPath )
    {
        if ( source == null )
        {
            throw new IllegalArgumentException( "Impossible to add a shortest path from a null source" );
        }
        if ( target == null )
        {
            throw new IllegalArgumentException( "Impossible to add a shortest path to a null target" );
        }
        if ( weightedPath == null )
        {
            throw new IllegalArgumentException( "Impossible to add a null weightedPath path to a null target" );
        }

        paths.put( new VertexPair<V>( source, target ), weightedPath );
    }

    /**
     * Returns the shortest path between source and target
     * 
     * @param source The source Vertex
     * @param target The target Vertex
     * @return Returns the shortest path between source and target
     */
    public WeightedPath<V, WE, Double> findShortestPath( V source, V target )
    {
        if ( source == null )
        {
            throw new IllegalArgumentException( "Impossible to find the shortest path from a null source" );
        }
        if ( target == null )
        {
            throw new IllegalArgumentException( "Impossible to find the shortest path to a null target" );
        }

        WeightedPath<V, WE, Double> path = paths.get( new VertexPair<V>( source, target ) );

        if ( path == null )
        {
            throw new PathNotFoundException( "Path from '%s' to '%s' doesn't exist", source, target );
        }

        return path;
    }

    /**
     * @param source
     * @param target
     * @param distance
     */
    void addShortestDistance( V source, V target, Double distance )
    {
        if ( source == null )
        {
            throw new IllegalArgumentException( "Impossible to add a shortest distance with a null source" );
        }
        if ( target == null )
        {
            throw new IllegalArgumentException( "Impossible to add a shortest distance with a null target" );
        }
        if ( distance == null )
        {
            throw new IllegalArgumentException( "Impossible to add a shortest distance with a null distance" );
        }

        shortestDistances.put( new VertexPair<V>( source, target ), distance );
    }

    /**
     * Returns the shortest distance between source and target.
     * 
     * @param source The source Vertex
     * @param target The target Vertex
     * @return Returns the shortest distance between source and target.
     */
    Double getShortestDistance( V source, V target )
    {
        if ( source == null )
        {
            throw new IllegalArgumentException( "Impossible to get the shortest distance from a null source" );
        }
        if ( target == null )
        {
            throw new IllegalArgumentException( "Impossible to get the shortest distance to a null target" );
        }

        if ( source.equals( target ) )
        {
            return 0D;
        }

        Double distance = shortestDistances.get( new VertexPair<V>( source, target ) );

        if ( distance == null )
        {
            return Double.POSITIVE_INFINITY;
        }

        return distance;
    }

    @Override
    public String toString()
    {
        return shortestDistances.toString();
    }

}
