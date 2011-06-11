package org.apache.commons.graph.domain.jdepend;

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
import java.util.HashMap;
import java.util.Iterator;

import java.io.IOException;

import jdepend.framework.*;

import org.apache.commons.graph.*;
import org.apache.commons.graph.domain.basic.*;

public class JDependGraph
  extends DirectedGraphImpl
  implements DirectedGraph
{
  private JDepend jdep = new JDepend();
  private Map pkgMap = new HashMap(); // JP X JPV
  private Map clazzMap = new HashMap(); // JC X JCV

  public JDependGraph( ) {  }

  public void addDirectory( String directory )
    throws IOException
  {
    jdep.addDirectory( directory );
  }

  /**
   * This not only finds the PackageVertex, but also
   * adds it to the graph, if it isn't already there.
   */
  private PackageVertex findPackageVertex( JavaPackage pkg ) {
    if (pkgMap.containsKey( pkg.getName() )) {
      return (PackageVertex) pkgMap.get( pkg.getName() );
    } else {
      PackageVertex RC = new PackageVertex( pkg );
      pkgMap.put( pkg.getName(), RC );
      addVertex( RC );
      return RC;
    }
  }

  /**
   * This not only finds the PackageVertex, but also
   * adds it to the graph, if it isn't already there.
   */
  private ClassVertex findClassVertex( JavaClass clz ) {
    if (clazzMap.containsKey( clz )) {
      return (ClassVertex) clazzMap.get( clz );
    } else {
      ClassVertex RC = new ClassVertex( clz );
      clazzMap.put( clz, RC );
      addVertex( RC );
      return RC;
    }
  }

  public void analyze() {
    Iterator pkgs = jdep.analyze().iterator();

    while (pkgs.hasNext()) {
      JavaPackage pkg = (JavaPackage) pkgs.next();
      PackageVertex pv = findPackageVertex( pkg );

      Iterator clzs = pkg.getClasses().iterator();
      while (clzs.hasNext()) {
	JavaClass clz = (JavaClass) clzs.next();
	ClassVertex cv = findClassVertex( clz );

	OwnershipEdge oe = new OwnershipEdge( pkg, clz );
	addEdge( oe, pv, cv );
	setWeight( oe, 5.0 );

	Iterator ipkgs = clz.getImportedPackages().iterator();
	while (ipkgs.hasNext()) {
	  JavaPackage ipkg = (JavaPackage) ipkgs.next();
	  PackageVertex ipv = findPackageVertex( ipkg );
	  
	  ImportEdge ie = new ImportEdge( clz, pkg );
	  addEdge( ie, cv, ipv );
	  setWeight( ie, 200.0 * ipkg.afferentCoupling() + 100.0 );
	}	
      }
    }
  }
}




