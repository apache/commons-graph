package org.apache.commons.graph.algorithm.util;

public class Label
{
  private Label root = null;

  public Label() {
  }

  public Label( Label root ) {
    this.root = root.getRoot();
  }
  
  public void setRoot( Label root ) {
    if (this.root == null ) {
      this.root = root.getRoot();
    } else {
      if (root != this.root) {
	this.root.setRoot( root.getRoot() );
	this.root = root.getRoot();
      }
    }
  }

  public Label getRoot() {
    if ((root == null) ||
	(root == this)) {
      return this;
    } else {
      root = root.getRoot();
      return root;
    }
  }
}





