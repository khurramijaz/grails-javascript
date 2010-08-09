package mobi.econceptions.js;

import groovy.lang.Closure;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.util.LinkedList;



public class Javascript extends JavascriptObjectSupport {
	private Logger log = LogManager.getLogger( Javascript.class); 
	boolean debug = JsConfig.debug;
	private LinkedList<Statement> statements = new LinkedList<Statement>();
	private Javascript current;
	private Javascript parent;

	private ScriptHandler scriptHandler;


	protected Javascript(Javascript parent){
		this.parent = parent;
		debug = parent.debug;
		this.scriptHandler = parent.scriptHandler;

	}
	public Javascript(){
		scriptHandler = new ScriptGenerator();
	}

	public Statement methodMissing(String name, Object args){
		if( current != null ){
			return current.methodMissing(name, args);
		}
		Statement stmt = createStatement();
		
		return stmt.methodMissing(name , args );
	}
	public Statement propertyMissing(String name ){
		if( current!= null ){
			return current.propertyMissing(name);
		}
		Statement stmt = createStatement();

		return stmt.propertyMissing(name);
	}
	public void propertyMissing(String name , Object arg){
		if( current != null ){
			current.propertyMissing( name , arg);
			return;
		}
		Statement stmt = createStatement();
		stmt.propertyMissing(name, arg);
	}

	private Statement createStatement(){
		//Statement stmt = statementFactory.newStatement( this , scriptHandler );
		Statement stmt = new Statement(this, scriptHandler);
		statements.add( stmt );
		if( debug) log.info("Statement added " + statements.size());
		return stmt;
	}
	private void execute(Closure c){
		if( parent != null ) parent.current = this;
		try{
			c.setDelegate(this);
			if( c.getMaximumNumberOfParameters() == 1){
				c.call(new Object[]{ new CallArgument(this)});
			}
			else{
				c.call( new Object[0]);
			}
		}finally{
			if( parent != null ) parent.current = null;
		}

	}
	@Override
	public String toString(){
		return "Javascript: " +statements.toString();
	}
	private JavascriptMutator mutator = new DefaultJavascriptMutator();
	protected JavascriptMutator getMutator(){
		return mutator;
	}
	protected class DefaultJavascriptMutator implements JavascriptMutator{
		public ScriptHandler getScriptHandler() {
			return scriptHandler;
		}
		public void execute(Closure c) {
			Javascript.this.execute(c);
		}

		public void setCurrent(Javascript script) {
			current = script;
		}

		public Javascript getCurrent() {
			return current;
		}

		public Javascript createChild() {
			return new Javascript( Javascript.this);
		}


		public boolean render(LinkedList<Object> r) {
			LinkedList<Object> renderItems = new LinkedList<Object>();
			
			LinkedList<Object> strings = new LinkedList<Object>();
			boolean hasRenderItems = false;
			for( Statement stmt: statements){
				StatementMutator m = stmt.getMutator();
				if( !m.getParameter() && m.render(strings)){ //If we know before hand then do not call render method.
					if( ! m.isPlainText()) strings.add(";");
					renderItems.add( strings );
					hasRenderItems = true;
					strings = new LinkedList<Object>();
				}
				else{
					renderItems.add("");
					
				}
			}

			if( hasRenderItems ){
				int index = statements.size()-1;
				while( index >= 0){
					if( statements.get( index).getMutator().getParameter() ){
						renderItems.remove(index);
					}
					index--;
				}
				r.add(renderItems);
				return true;
			}
			return false;
		}

		public LinkedList<Statement> getStatements() {
			return statements;  
		}
	}
}
