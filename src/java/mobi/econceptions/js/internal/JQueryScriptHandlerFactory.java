package mobi.econceptions.js.internal;

import mobi.econceptions.js.ScriptHandler;
import mobi.econceptions.js.ScriptHandlerFactory;

public class JQueryScriptHandlerFactory implements ScriptHandlerFactory{
	public ScriptHandler createScriptHandler() {
		return new JQueryScriptHandler();
	}
}
