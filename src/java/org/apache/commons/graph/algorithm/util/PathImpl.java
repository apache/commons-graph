package org.apache.commons.graph.algorithm.util;

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

  public Vertex getStart() {
    return (Vertex) vertexList.get( 0 );
  }

  public Vertex getEnd() {
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


