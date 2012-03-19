package org.apache.commons.graph.export;

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

import java.util.Map;

import org.apache.commons.graph.Graph;
import org.apache.commons.graph.Mapper;

final class GraphMLExporter<V, E>
    extends AbstractExporter<V, E, GraphMLExporter<V, E>>
{

    private static final String GRAPHML = "graphml";

    private static final String XMLNS = "xmlns";

    private static final String GRAPHML_XMLNS = "http://graphml.graphdrawing.org/xmlns";

    private static final String EDGEDEFAULT = "edgedefault";

    private static final String DIRECTED = "directed";

    private static final String KEY = "key";

    private static final String FOR = "for";

    private static final String ID = "id";

    private static final String ATTR_NAME = "attr.name";

    private static final String ATTR_TYPE = "attr.type";

    private static final String GRAPH = "graph";

    private static final String NODE = "node";

    private static final String EDGE = "edge";

    private static final String SOURCE = "source";

    private static final String TARGET = "target";

    private static final String DATA = "data";

    private static final String LABEL = "label";

    private static final String STRING = "string";

    private static final String FLOAT = "float";

    private static final String DOUBLE = "double";

    private static final String LONG = "long";

    private static final String BOOLEAN = "boolean";

    private static final String INT = "int";

    private static final String WEIGHT = "weight";

    GraphMLExporter( Graph<V, E> graph, String name )
    {
        super( graph, name );
    }

    @Override
    protected void startSerialization()
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void endSerialization()
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void startGraph( String name )
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void endGraph()
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void comment( String text )
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void enlistVerticesProperty( String name, Class<?> type )
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void enlistEdgesProperty( String name, Class<?> type )
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void vertex( V vertex, Map<String, Object> properties )
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    @Override
    protected void edge( E edge, V head, V tail, Map<String, Object> properties )
        throws Exception
    {
        // TODO Auto-generated method stub

    }

    private static <O> String getStringType( O object )
    {
        if ( object instanceof Integer )
        {
            return INT;
        }
        else if ( object instanceof Long )
        {
            return LONG;
        }
        else if ( object instanceof Float )
        {
            return FLOAT;
        }
        else if ( object instanceof Double )
        {
            return DOUBLE;
        }
        else if ( object instanceof Boolean )
        {
            return BOOLEAN;
        }
        return STRING;
    }

	@Override
	public <N extends Number> GraphMLExporter<V, E>
	    withEdgeWeights(Mapper<E, N> edgeWeights) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <N extends Number> GraphMLExporter<V, E> withVertexWeights(
			Mapper<V, N> vertexWeights) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphMLExporter<V, E> withEdgeLabels(Mapper<E, String> edgeLabels) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GraphMLExporter<V, E> withVertexLabels(
			Mapper<V, String> vertexLabels) {
		// TODO Auto-generated method stub
		return null;
	}

}
