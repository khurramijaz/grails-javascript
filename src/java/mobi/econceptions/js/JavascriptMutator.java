package mobi.econceptions.js;

import groovy.lang.Closure;

import java.util.LinkedList;


public interface JavascriptMutator {
	public void setCurrent(Javascript script);
	public Javascript getCurrent();
	public void execute(Closure c);
	public boolean render(LinkedList<Object> r);
	public LinkedList<Statement> getStatements();
	//public StatementFactory getStatementFactory();
	public ScriptHandler getScriptHandler();
	public Javascript createChild();
}
