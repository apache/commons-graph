package org.apache.commons.graph.domain.jdepend;

import org.apache.commons.graph.*;
import jdepend.framework.*;

public class ImportEdge
  implements Edge, Named
{
  private JavaPackage pkg = null;
  private JavaClass clz = null;

  public ImportEdge( JavaClass clz,
		     JavaPackage pkg) {
    this.pkg = pkg;
    this.clz = clz;
  }

  public JavaPackage getJavaPackage() {
    return pkg;
  }

  public JavaClass getJavaClass() {
    return clz;
  }

  public String getName() {
    return clz.getName() + " imports " + pkg.getName();
  }

  public String toString() {
    return getName();
  }
}


