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

import org.apache.commons.graph.*;

public class VertexPair {
  public Vertex i = null;
  public Vertex j = null;

  public VertexPair( Vertex i, Vertex j ) {
    this.i = i;
    this.j = j;
  }

  public boolean equals( Object o ) {
    VertexPair vp = (VertexPair) o;
    return ( this.i == vp.i &&
	     this.j == vp.j );
  }

  public int hashCode() {
    return (17 * i.hashCode()) + j.hashCode();
  }
}



