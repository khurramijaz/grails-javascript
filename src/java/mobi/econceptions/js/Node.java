package mobi.econceptions.js;

import java.util.LinkedList;


public abstract class Node {
	private Statement parent;
	public Statement getParent(){ return parent; }
	public void setParent(Statement parent){ this.parent = parent; }
	public abstract String render();
	public void render(LinkedList<Object> list){
		list.add( render());
	}
}
