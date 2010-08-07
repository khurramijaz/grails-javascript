package mobi.econceptions.js.internal;

import mobi.econceptions.js.Javascript;
import mobi.econceptions.js.ScriptHandler;
import mobi.econceptions.js.Statement;
import mobi.econceptions.js.StatementFactory;

public class DefaultStatementFactory implements StatementFactory{
	public Statement newStatement(Javascript javascript, ScriptHandler handler) {
		return new Statement(javascript, handler);
	}
}
