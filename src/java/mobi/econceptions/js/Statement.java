package mobi.econceptions.js;

import mobi.econceptions.js.internal.JavascriptObjectSupport;

import mobi.econceptions.js.nodes.Name;
import mobi.econceptions.js.nodes.Node;
import mobi.econceptions.js.nodes.Operator;
import mobi.econceptions.js.nodes.Value;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.util.LinkedList;

import java.util.Random;

/**
 * No public method accept groovy magic methods.
 *
 */
public class Statement extends JavascriptObjectSupport {
	long id = 0L;
	private Logger log = LogManager.getLogger( Statement.class);
	boolean debug = JsConfig.debug;
	private Javascript javascript;
	private LinkedList<Node> nodes = new LinkedList<Node>();
	StatementMutator mutator = new DefaultStatementMutator();

	private boolean plainText = false;

	private boolean parameter = false;
	
	private ScriptHandler handler;

	public Statement(Javascript js , ScriptHandler handler){
		this.javascript = js;
		debug = js.debug;
		this.handler = handler;
		if( debug) id = new Random().nextLong();
	}
	protected StatementMutator getMutator(){ return mutator; }

	public Statement methodMissing(String name, Object args){
		handler.methodMissing( mutator , name , (Object[])args);
		return this;
	}
	public Statement propertyMissing(String name ){
		handler.propertyMissing( mutator , name);
		return this;
	}
	public void propertyMissing(String name , Object arg){
		handler.propertyMissing( mutator , name , arg );
	}

	private void appendNode(Node node){
		node.setParent( this );
		nodes.add(node);
		if( debug ) log.info("Added node " + node);
	}
	private void addNode(int index , Node node){
		node.setParent(this);
		nodes.add( index , node);
		if( debug ) log.info("Added node " + node + "at " + index );

	}
	@Override
	public String toString(){
		return "St:" + id + "= " + nodes.toString();
	}
	protected class DefaultStatementMutator implements StatementMutator{
		public void append(Name name) {
			appendNode(name);
		}

		public void append(Node node) {
			appendNode( node );
		}

		public void append(Operator operator) {
			appendNode( operator );
		}

		public void append(Value value) {
			appendNode( value );
		}

		public void appendName(String name) {
			append( new Name(name));
		}

		public void appendOperator(String operator) {
			append(new Operator(operator));
		}

		public void appendValue(Object value) {
			append( new Value(value));
		}

		public int size() {
			return nodes.size();
		}

		public void setParameter(boolean parameter) {

			Statement.this.parameter = parameter;
		}

		public boolean getParameter() {
			return parameter; 
		}

		public Javascript getParent() {
			return javascript;
		}

		public void setParent(Javascript javascript) {
			Statement.this.javascript = javascript; 
		}

		public Statement getStatement() {
			return Statement.this;  
		}

		public boolean render(LinkedList<Object> r) {
			boolean rendered = false;
			for( Node n : nodes){
				r.add( n.render() );
				rendered = true;
			}
			return rendered;
		}

		public void addNode(int index, Node node) {
			Statement.this.addNode(index, node);
		}

		public Node getNode(int index) {
			return nodes.get(index);
		}

		public void removeNode(int index) {
			nodes.remove(index);
		}

		public boolean isPlainText() {
			return plainText;
		}

		public void setPlainText(boolean p) {
			plainText = p;
		}
	}
}
