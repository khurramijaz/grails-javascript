package mobi.econceptions.js.handlers.impl;

import mobi.econceptions.js.StatementMutator;
import mobi.econceptions.js.handlers.HandlerType;
import mobi.econceptions.js.handlers.MethodHandler;

public class AsisMethodHandler extends AbstractHandler implements MethodHandler{

	public AsisMethodHandler(){
		super(HandlerType.Method);
	}

	public boolean methodMissing(StatementMutator m, String name, Object[] args) {
		if( m.size() > 0 || args.length != 1 || !"asis".equals( name ) ||  !(args[0] instanceof String) ){
			return false;
		}
		m.appendName( (String)args[0] );
		m.setPlainText(true);
		return true;
	}
}
