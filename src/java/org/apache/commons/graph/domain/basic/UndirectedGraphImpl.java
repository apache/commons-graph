package org.apache.commons.graph.domain.basic;

/*
 * Copyright 2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Iterator;

import java.lang.reflect.*;

import org.apache.commons.graph.*;
import org.apache.commons.graph.exception.*;

/**
 * Description of the Class
 */
public class UndirectedGraphImpl
  implements UndirectedGraph, 
	     WeightedGraph, 
	     MutableGraph,
	     InvocationHandler
{
    private Set vertices = new HashSet();
    private Set edges = new HashSet();

    private Map edgeVerts = new HashMap();// EDGE X SET( VERTS )
    private Map vertEdges = new HashMap();// VERTEX X SET( EDGE )
    private Map edgeWeights = new HashMap(); // EDGE X WEIGHT

    /**
     * Constructor for the UndirectedGraphImpl object
     */
    public UndirectedGraphImpl() { }

    /**
     * Adds a feature to the Vertex attribute of the UndirectedGraphImpl object
     */
    public void addVertex(Vertex v)
        throws GraphException
    {
        vertices.add(v);
    }

  public void removeVertex( Vertex v )
    throws GraphException
  {
    vertices.remove( v );
  }

  public void removeEdge( Edge e ) 
    throws GraphException
  {
    edges.remove( e );
  }

  public void addEdge(Edge e) 
    throws GraphException
  {
    edges.add( e );
  }

  public void disconnect(Edge e, Vertex v) {
    if (edgeVerts.containsKey( e )) {
      ((Set) edgeVerts.get( e )).remove( v );
    }

    if (vertEdges.containsKey( v )) {
      ((Set) vertEdges.get( v )).remove( e );
    }
  }

  public void connect( Edge e, Vertex v ) {
    Set verts = null;
    if (!edgeVerts.containsKey( e )) {
      verts = new HashSet();
      edgeVerts.put( e, verts );
    } else {
      verts = (Set) edgeVerts.get( e );
    }

    verts.add( v );

    Set edges = null;
    if (!vertEdges.containsKey( v )) {
      edges = new HashSet();
      vertEdges.put( v, edges );
    } else {
      edges = (Set) vertEdges.get( v );
    }
    
    edges.add( e );
    
  }

    /**
     * Adds a feature to the Edge attribute of the UndirectedGraphImpl object
     */
    public void addEdge(Edge e,
                        Set vertices)
        throws GraphException
    {
      addEdge( e );
      
      Iterator verts = vertices.iterator();
      while (verts.hasNext()) {
	connect( e, (Vertex) verts.next() );
      }
    }

    // Interface Methods
    /**
     * Gets the vertices attribute of the UndirectedGraphImpl object
     */
    public Set getVertices()
    {
        return new HashSet(vertices);
    }

    /**
     * Gets the vertices attribute of the UndirectedGraphImpl object
     */
    public Set getVertices(Edge e)
    {
        if (edgeVerts.containsKey(e))
        {
            return new HashSet((Set) edgeVerts.get(e));
        }
        else
        {
            return new HashSet();
        }
    }

    /**
     * Gets the edges attribute of the UndirectedGraphImpl object
     */
    public Set getEdges()
    {
        return new HashSet(edges);
    }

    /**
     * Gets the edges attribute of the UndirectedGraphImpl object
     */
    public Set getEdges(Vertex v)
    {
        if (vertEdges.containsKey(v))
        {
            return new HashSet((Set) vertEdges.get(v));
        }
        else
        {
            return new HashSet();
        }
    }

  public void setWeight( Edge e, double w ) {
    if (edgeWeights.containsKey( e )) {
      edgeWeights.remove( e );
    }

    edgeWeights.put( e, new Double( w ) );
  }

  public double getWeight( Edge e ) {
    if (edgeWeights.containsKey( e )) {
      return ((Double) edgeWeights.get( e ) ).doubleValue();
    } else {
      return 1.0;
    }
  }

    /**
     * Description of the Method
     */
    public Object invoke(Object proxy,
                         Method method,
                         Object args[])
        throws Throwable
    {
      try {
	return method.invoke(this, args);
      } catch (InvocationTargetException ex) {
	throw ex.getTargetException();
      }
    }


}









