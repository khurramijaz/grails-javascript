package mobi.econceptions.js.internal;

import mobi.econceptions.js.StatementMutator;


public class JQueryScriptHandler extends DefaultScriptHandler{
	@Override
	public void callPrefix(StatementMutator mutator) {
		mutator.appendName("$");//for $.ajax $('$id') 
	}
}
