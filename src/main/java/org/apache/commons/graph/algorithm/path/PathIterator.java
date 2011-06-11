package org.apache.commons.graph.algorithm.path;

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
