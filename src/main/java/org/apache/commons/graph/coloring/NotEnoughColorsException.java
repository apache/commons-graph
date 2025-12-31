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

import static java.lang.String.format;

import java.util.Set;

import org.apache.commons.graph.GraphException;

/**
 * Exception used by Coloring algorithms. Used when there are not enough colors to 
 * color the graph. 
 *
 */
public class NotEnoughColorsException
    extends GraphException
{

    private static final long serialVersionUID = -8782950517745777605L;

    /**
     * Creates new instance of {@link NotEnoughColorsException}. 
     *
     * @param colors the set of color.
     */
    public NotEnoughColorsException( Set<?> colors )
    {
        super( format( "Input color set %s has not enough colors to color the given graph", colors ) );
    }

}
