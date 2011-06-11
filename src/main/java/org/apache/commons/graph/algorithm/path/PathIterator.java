package org.apache.commons.graph.algorithm.path;

import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.apache.commons.graph.Path;

public class PathIterator
    implements PathListener,
	       Iterator
{
    private List queue = new LinkedList();
    private Thread thread = null;
    private int highMark = 50;

    /**
     * @t is the thread which is generating the paths.
     * if this thread is terminated, we assume that
     * we have recieved everything.
     */
    public PathIterator() {
    }
    
    public void setThread( Thread thread ) {
	this.thread = thread;
    }

    public void remove() {
	throw new UnsupportedOperationException();
    }

    public void notifyPath( Path path ) {
	synchronized (queue) {
	    try {
		if (queue.size() >= highMark) {
		    queue.wait();
		}
	    } catch (InterruptedException ex) {}

	    queue.add( path );
	    queue.notifyAll();
	}
    }

    public boolean hasNext() { 
	synchronized (queue) {
	    if (queue.size() > 0) return true;

	    while ((queue.size() == 0) &&
		   (thread.isAlive())) {
		try { 
		    queue.wait(100);
		} catch (InterruptedException ex) { }
	    }

	    return queue.size() > 0;
	}
    }

    public Object next() {
	synchronized (queue) {
	    if (hasNext()) {
		return queue.remove( 0 );
	    } else {
		throw new NoSuchElementException();
	    }
	}
    }
}
