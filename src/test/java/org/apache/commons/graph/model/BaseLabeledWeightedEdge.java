package org.apache.commons.graph.model;

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

import static java.lang.String.format;

import org.apache.commons.graph.WeightedEdge;

/**
 * 
 */
public class BaseLabeledWeightedEdge
    extends BaseLabeledEdge
    implements WeightedEdge<BaseLabeledVertex>
{

    private final Double weight;

    public BaseLabeledWeightedEdge( String label, BaseLabeledVertex head, BaseLabeledVertex tail, Double weight )
    {
        super( label, head, tail );
        this.weight = weight;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo( WeightedEdge<BaseLabeledVertex> other )
    {
        return weight.compareTo( other.getWeight() );
    }

    /**
     * {@inheritDoc}
     */
    public Double getWeight()
    {
        return weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ( ( weight == null ) ? 0 : weight.hashCode() );
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals( Object obj )
    {
        if ( this == obj )
        {
            return true;
        }

        if ( !super.equals( obj ) )
        {
            return false;
        }

        if ( getClass() != obj.getClass() )
        {
            return false;
        }

        BaseLabeledWeightedEdge other = (BaseLabeledWeightedEdge) obj;
        if ( weight == null )
        {
            if ( other.getWeight() != null )
            {
                return false;
            }
        }
        else if ( !weight.equals( other.getWeight() ) )
        {
            return false;
        }

        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        return format( "%s[ %s -> %s (%s) ]", getLabel(), getHead().getLabel(), getTail().getLabel(), weight );
    }

}
