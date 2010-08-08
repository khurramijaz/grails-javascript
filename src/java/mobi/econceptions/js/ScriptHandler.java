package mobi.econceptions.js;

 

public interface ScriptHandler {
	
	public void propertyMissing(StatementMutator mutator, String name, Object value);
	public void propertyMissing(StatementMutator mutator, String name);
	public void methodMissing(StatementMutator mutator, String name , Object[] args);
	//public void callPrefix(StatementMutator mutator);
	
}
