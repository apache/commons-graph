package org.apache.commons.graph.algorithm.util;

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

import java.util.List;
import java.util.ArrayList;

import org.apache.commons.graph.*;

public class PathImpl
  implements Path
{
  protected List vertexList = new ArrayList();
  protected List edgeList = new ArrayList();

  public PathImpl( List vertexList,
		   List edgeList ) {
    this.vertexList = vertexList;
    this.edgeList = edgeList;
  }

    public PathImpl( Vertex start ) {
	this.vertexList.add( start );
    }

  public PathImpl( Vertex start,
		   Vertex end,
		   Edge edge ) {
    vertexList = new ArrayList();
    vertexList.add( start );
    vertexList.add( end );

    edgeList = new ArrayList();
    edgeList.add( edge );
  }

  public PathImpl append( PathImpl impl ) {
    List newVertices = new ArrayList( vertexList );
    newVertices.remove( newVertices.size() - 1 ); // Last should be duplicated
    newVertices.addAll( impl.getVertices() );

    List newEdges = new ArrayList( edgeList );
    newEdges.addAll( impl.getEdges() );

    return new PathImpl( newVertices, newEdges );
  }

    public PathImpl append( Vertex v, Edge e ) {
	List newVertices = new ArrayList( vertexList );
	newVertices.add( v );

	List newEdges = new ArrayList( edgeList );
	newEdges.add( e );
	
	return new PathImpl( newVertices, newEdges );
    }

  public Vertex getSource() {
    return (Vertex) vertexList.get( 0 );
  }

  public Vertex getTarget() {
    return (Vertex) vertexList.get( vertexList.size() - 1 );
  }

  public List getVertices() {
    return vertexList;
  }

  public List getEdges() {
    return edgeList;
  }

    public int size() {
	return vertexList.size();
    }

  public String toString() {
    return vertexList.toString();
  }
}


