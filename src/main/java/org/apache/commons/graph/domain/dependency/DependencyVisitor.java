package org.apache.commons.graph.domain.dependency;

import java.util.List;
import java.util.LinkedList;

import org.apache.commons.graph.*;
import org.apache.commons.graph.algorithm.search.*;

/**
 * Description of the Class
 */
public class DependencyVisitor
     implements Visitor
{
    private List deps = null;
    private DFS dfs = new DFS();


    /**
     * Constructor for the DependencyVisitor object
     */
    public DependencyVisitor() { }

    /**
     * Description of the Method
     */
    public void discoverGraph(Graph g) { }

    /**
     * Description of the Method
     */
    public void discoverVertex(Vertex v) { }

    /**
     * Description of the Method
     */
    public void discoverEdge(Edge e) { }

    /**
     * Description of the Method
     */
    public void finishGraph(Graph g) { }

    /**
     * Description of the Method
     */
    public void finishVertex(Vertex v)
    {
        if (v instanceof DependencyVertex)
        {
            deps.add(((DependencyVertex) v).getValue());
        }
        else
        {
            deps.add(v);
        }
    }

    /**
     * Description of the Method
     */
    public void finishEdge(Edge e) { }

    /**
     * Gets the sortedDependencies attribute of the DependencyVisitor object
     */
    public synchronized List
        getSortedDependencies(DependencyGraph dg,
                              Vertex root)
    {
        deps = new LinkedList();
        dfs.visit(dg, root, this);
        return deps;
    }

    public synchronized List
	getSortedDependencies(DependencyGraph dg )
    {
	deps = new LinkedList();
	dfs.visit( dg, this );
	return deps;
    }
}

